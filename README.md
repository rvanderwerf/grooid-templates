Grooid templates
-----------------

This project tries to help Groovy/Android developers to speed up project creation and configuration by
using predefined templates. This is a fork of http://mariogarcia.github.io/grooid-templates/ project that supports
Google Glass and Goodle Wearable templates.

The only difference in config is the entries in the .lazybones/config.groovy file:

bintrayRepositories = [
    "rvanderwerf/grooid-templates",
    "marioggar/grooid-templates",
    "pledbrook/lazybones-templates"
]


Then to run:

lazybones create grooid-new-glass-project grooidGlassTest
lazybones create grooid-new-wear-project grooidWearTest


Please read the original documentation [here](http://mariogarcia.github.io/grooid-templates/)
