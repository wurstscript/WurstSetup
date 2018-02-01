[![Build Status](https://travis-ci.org/wurstscript/WurstSetup.svg?branch=master)](https://travis-ci.org/wurstscript/WurstSetup) [![codebeat badge](https://codebeat.co/badges/279cc0f7-8ff6-400a-a763-8861690962dc)](https://codebeat.co/projects/github-com-wurstscript-wurstsetup-master) [![codecov](https://codecov.io/gh/wurstscript/WurstSetup/branch/master/graph/badge.svg)](https://codecov.io/gh/wurstscript/WurstSetup)


# WurstScript Setup App

Allows automated installation of a wurstscript environment and project setup.

## How it works

### Wurst Installation

The wurst compiler gets downloaded into the users home directory into a wurst folder `~/.wurst`

### Project Generation

The setup app downloads this repo https://github.com/Frotty/WurstBareboneTemplate as a wurst project template and then inserts the necessary local parths as well as generating the wurst.dependencies file.
Dependencies are stored in `_build/dependencies/`
