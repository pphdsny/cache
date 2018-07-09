#!/bin/bash
#将lib发布到maven的命令，入参接受需要发布的项目名
projectName=$1
./gradlew -p $projectName clean build uploadArchives --info