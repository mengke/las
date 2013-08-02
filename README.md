## Log Analyzer System
LAS is the abbreviation of the Log Analyzer System. It's a Java-based logs analyzing platform.

## Requirements
las-server is built upon jdk1.7 due to it's written on top of nifty, and for other sub-projects, jdk1.6 is fine.

## Build from source
LAS uses a Gradle-based build system. You can use `./gradlew` in unix or `./gradlew.bat` in windows.

### check out sources
`git clone git://github.com/mengke/las.git`

### compile and test, build all jars, distribution zips
`./gradlew build`

### build a sub-project
`./gradlew :sub-project-name:build`

... and discover more commands with `./gradlew tasks`.

