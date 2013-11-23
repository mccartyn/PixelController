# Roadmap PixelController

## Version 2.0

* add documentation for pixelinvaders.net
* add documentation for integration
* fix crash if no sound card is available (NPE: FFTSpectrum.java:52)

## Version 2.1

* PixelInvaders firmware: support Teensy 3
* PixelInvaders firmware: Support ambient color for the output panel(s)
* Support Multiple Output devices with different matrix sizes
* Random-Mode with selectable time-life
* TouchOSC GUI

# Backlog

* Do not freeze application if output fails (disconnect serial controller)
* Particle generator (rain,snow,fireworks...) remember: maybe rip from cocos2d particle system, should replace fire and metaballs
* More layouts (Half/Half, 3/1...). Current layouts are hardware dependent. New layout shouldn't be. 
```
    +---+---+---+
    | a | b | c |
    +---+---+---+   
    | d | e | f |
    +---+---+---+   
     Example: 3/1 Layout defines 2 virtual panels, panel 1 using physical panel a,b,d,e panel 2 using physical panel c and f.
     Example: Fancy Layout defines 3 virtual panels, panel 1 using physical panel a,b,d,e, panel 2 using physical panel c panel 3 using physical panel f
```

* Option to invert GUI colors
* More GUI improvements (scaling, bigger fonts, save window position, option to bring both windows to front)
* Option to modify colorsets of all visuals
* Add Perlin Noise generator
* add more fader (pixel fader for example)
* support more than 2 rows
* Option to save output of PixelController (byte stream) to a flat file (Offline usage)
* Option to use system audio as input source, currently its only mic input. 
* Syphon support https://code.google.com/p/syphon-implementations/
* OLA (http://www.opendmx.net/index.php/Open_Lighting_Architecture) input plugin see https://github.com/neophob/ola-to-tpm2net
* Game of life generator
* Matrix device specific settings (bpp, cabling, mapping etc) should be inside the MatrixData class.
* Merge code of the output package, alot of duplicate code 
* Add Passthrough Colorset (for example to display an Image on the LED Matrix)


# Rejected backlog features

* UDP output: support multiple output devices (probably not, alternative: use tpm2.net)
* Use Mozilla Rhino to create dynamic generators/effects -> using JDK1.6 Rhino is just too slow and cpu intensive (it needs about 40% cpu power with one Rhino js generator). v8 (java8) should a an alternative, however v8 is OS dependent.
