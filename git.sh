#!/usr/bin/env bash
#初始化项目
git init

#将本地项目添加远程源
git remote add origin https://github.com/mumuhadoop/mumu-neo4j.git

#拉取远程源到本地
git pull origin master

#设置本地origin为master分支
git branch --set-upstream-to origin/master master