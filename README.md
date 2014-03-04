## Log Analyzer System
LAS is the abbreviation of the Log Analyzer System. It's a Java-based logs analyzing platform.

## Requirements
las-server is built upon jdk1.7 due to it's written on top of nifty, and for other sub-projects, jdk1.6 is fine.

## Build from source
LAS uses a Gradle-based build system. You can use `./gradlew` in unix or `./gradlew.bat` in windows.

### check out sources
`git clone git://github.com/mengke/las.git`

### import code to your ide
`./gradlew eclipse` or `./gradlew idea`

### compile and test, build all jars, distribution zips
`./gradlew build`

### build a sub-project
`./gradlew :sub-project-name:build`

You can use `./gradlew tasks` to check more available commands.

### 相似度推荐相关介绍

[请点击此处进入](https://github.com/mengke/las/wiki)

### las-analyzer具体Map－Reduce过程

* [用户房源访问记录](https://github.com/mengke/las/wiki/%E7%94%A8%E6%88%B7%E6%88%BF%E6%BA%90%E8%AE%BF%E9%97%AE%E8%AE%B0%E5%BD%95)

* [访问记录转换为用户向量](https://github.com/mengke/las/wiki/%E8%AE%BF%E9%97%AE%E8%AE%B0%E5%BD%95%E8%BD%AC%E6%8D%A2%E4%B8%BA%E7%94%A8%E6%88%B7%E5%90%91%E9%87%8F)

* [计算共生矩阵](https://github.com/mengke/las/wiki/%E8%AE%A1%E7%AE%97%E5%85%B1%E7%94%9F%E7%9F%A9%E9%98%B5)

* [共生矩阵与用户浏览向量相乘](https://github.com/mengke/las/wiki/%E5%85%B1%E7%94%9F%E7%9F%A9%E9%98%B5%E4%B8%8E%E7%94%A8%E6%88%B7%E6%B5%8F%E8%A7%88%E5%90%91%E9%87%8F%E7%9B%B8%E4%B9%98)

