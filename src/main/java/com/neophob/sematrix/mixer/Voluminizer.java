/**
 * Copyright (C) 2011-2013 Michael Vogt <michu@neophob.com>
 *
 * This file is part of PixelController.
 *
 * PixelController is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * PixelController is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with PixelController.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.neophob.sematrix.mixer;

import com.neophob.sematrix.glue.Visual;
import com.neophob.sematrix.input.ISound;
import com.neophob.sematrix.resize.Resize.ResizeName;

/**
 * mix src/dst accoring to volume of sound!.
 *
 * @author michu
 */
public class Voluminizer extends Mixer {

	private ISound sound;
	/**
	 * Instantiates a new voluminizer.
	 *
	 * @param controller the controller
	 */
	public Voluminizer(ISound sound) {
		super(MixerName.VOLUMINIZER, ResizeName.QUALITY_RESIZE);
		this.sound = sound;
	}

	/* (non-Javadoc)
	 * @see com.neophob.sematrix.mixer.Mixer#getBuffer(com.neophob.sematrix.glue.Visual)
	 */
	public int[] getBuffer(Visual visual) {
		if (visual.getEffect2() == null) {
			return visual.getEffect1Buffer();
		}
		
		int[] src1 = visual.getEffect1Buffer();
		int[] src2 = visual.getEffect2Buffer();
		int[] dst = new int [src1.length];

		float snd = sound.getVolumeNormalized();
		for (int i=0; i<src1.length; i++){
    		dst[i]=(int)(src2[i]*snd + ((1.0f-snd)*src1[i]));
        }
	
		return dst;
	}

}
