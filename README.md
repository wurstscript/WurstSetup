[![CircleCI](https://circleci.com/gh/wurstscript/WurstSetup.svg?style=svg)](https://circleci.com/gh/wurstscript/WurstSetup) [![codebeat badge](https://codebeat.co/badges/279cc0f7-8ff6-400a-a763-8861690962dc)](https://codebeat.co/projects/github-com-wurstscript-wurstsetup-master) [![codecov](https://codecov.io/gh/wurstscript/WurstSetup/branch/master/graph/badge.svg)](https://codecov.io/gh/wurstscript/WurstSetup)


# WurstScript Setup App

Allows automated installation of a wurstscript environment and project setup.

## Grill

Grill is the name of the CLI and dependency manager used by the UI internally.
Pro users can make use of grill from the shell:

### Update/Remove wurst installation

Update or remove the global wurst installation by using the special `wurstscript` identifier.

```cmd
> grill install wurstscript
> grill remove wurstscript
```

### Creating a new project

To create a new project, use `generate` and supply your name of choice.

```cmd
> grill generate <project_name>
```

### Updating a project

By not passing any additional arguments grill will assume that the execution location is a wurst project.

To update all project dependencies use:

```cmd
> grill install
```

To add a new dependency to your project, use:

```cmd
> grill install <git_url>
```

### Testing a project

Use `test` to compile the project at the current location and run unit tests.

```cmd
> grill test
```


### Building the project

Use `build` to generate an output map according to `wurst.build` specifications.

```cmd
> grill build
```

## How it works

### Wurst Installation

The wurst compiler gets downloaded into the users home directory into a wurst folder `~/.wurst`

### Project Generation

The setup app downloads this repo https://github.com/Frotty/WurstBareboneTemplate as a wurst project template and then inserts the necessary local parths as well as generating the wurst.dependencies file.
Dependencies are stored in `_build/dependencies/`.
