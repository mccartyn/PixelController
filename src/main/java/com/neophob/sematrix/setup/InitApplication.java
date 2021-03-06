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

package com.neophob.sematrix.setup;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.neophob.sematrix.glue.Collector;
import com.neophob.sematrix.glue.FileUtils;
import com.neophob.sematrix.output.ArtnetDevice;
import com.neophob.sematrix.output.E1_31Device;
import com.neophob.sematrix.output.MiniDmxDevice;
import com.neophob.sematrix.output.NullDevice;
import com.neophob.sematrix.output.Output;
import com.neophob.sematrix.output.OutputDeviceEnum;
import com.neophob.sematrix.output.PixelInvadersNetDevice;
import com.neophob.sematrix.output.PixelInvadersSerialDevice;
import com.neophob.sematrix.output.RainbowduinoV2Device;
import com.neophob.sematrix.output.RainbowduinoV3Device;
import com.neophob.sematrix.output.StealthDevice;
import com.neophob.sematrix.output.Tpm2;
import com.neophob.sematrix.output.Tpm2Net;
import com.neophob.sematrix.output.UdpDevice;
import com.neophob.sematrix.properties.ApplicationConfigurationHelper;

/**
 * @author mvogt
 *
 */
public abstract class InitApplication {

    private static final Logger LOG = Logger.getLogger(InitApplication.class.getName());
    
    private static final String APPLICATION_CONFIG_FILENAME = "config.properties";

    
    /**
     * load and parse configuration file
     * 
     * @param papplet
     * @return
     * @throws IllegalArgumentException
     */
    public static ApplicationConfigurationHelper loadConfiguration(FileUtils fileUtils) throws IllegalArgumentException {
        Properties config = new Properties();
        InputStream is = null;
        try {
        	String fileToLoad = fileUtils.getDataDir()+File.separator+APPLICATION_CONFIG_FILENAME;
        	is = new FileInputStream(fileToLoad);
            config.load(is);            
            LOG.log(Level.INFO, "Config loaded, {0} entries", config.size());
        } catch (Exception e) {
        	String error = "Failed to open the configfile "+APPLICATION_CONFIG_FILENAME;
            LOG.log(Level.SEVERE, error, e);
            throw new IllegalArgumentException(error);
        } finally {
        	try {
        		if (is!=null) {
        			is.close();        	
        		}
        	} catch (Exception e) {
        		//ignored
        	}
        }
        
        try {
            return new ApplicationConfigurationHelper(config);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Configuration Error: ", e);
            throw new IllegalArgumentException(e);
        }
    }
    
        
    
    /**
     * 
     * @param applicationConfig
     * @throws IllegalArgumentException
     */
    public static Output getOutputDevice(Collector collector, ApplicationConfigurationHelper applicationConfig) throws IllegalArgumentException {
        OutputDeviceEnum outputDeviceEnum = applicationConfig.getOutputDevice();
        Output output = null;
        try {
            switch (outputDeviceEnum) {
            case PIXELINVADERS:
                output = new PixelInvadersSerialDevice(applicationConfig);
                break;
            case PIXELINVADERS_NET:
                output = new PixelInvadersNetDevice(applicationConfig);
                break;            	
            case STEALTH:
                output = new StealthDevice(applicationConfig);
                break;
            case RAINBOWDUINO_V2:
                output = new RainbowduinoV2Device(applicationConfig);
                break;
            case RAINBOWDUINO_V3:
                output = new RainbowduinoV3Device(applicationConfig);
                break;
            case ARTNET:
                output = new ArtnetDevice(applicationConfig);
                break;
            case E1_31:
                output = new E1_31Device(applicationConfig);
                break;            	
            case MINIDMX:
                output = new MiniDmxDevice(applicationConfig);
                break;
            case NULL:
                output = new NullDevice(applicationConfig);
                break;
            case UDP:
                output = new UdpDevice(applicationConfig);
                break;
            case TPM2:
                output = new Tpm2(applicationConfig);
                break;
            case TPM2NET:
                output = new Tpm2Net(applicationConfig);                
                break;
            default:
                throw new IllegalArgumentException("Unable to initialize unknown output device: " + outputDeviceEnum);
            }
            
            collector.getPixelControllerOutput().addOutput(output);
            
        } catch (Exception e) {
            LOG.log(Level.SEVERE,"\n\nERROR: Unable to initialize output device: " + outputDeviceEnum, e);
        }
        
        return output;
    }
    
    
    
}
