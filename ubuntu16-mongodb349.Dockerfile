# Docker image of hbase cluster
# VERSION 0.0.1
# Author: bolingcavalry

#基础镜像使用ubuntu16.04
FROM ubuntu:16.04

#作者
MAINTAINER BolingCavalry <zq2599@gmail.com>

#定义工作目录
ENV WORK_PATH /usr/local/

#定义mongodb文件夹名称
ENV MONGODB_PACKAGE_NAME mongodb-linux-x86_64-ubuntu1604-3.4.9

#把mongodb安装包从本地电脑复制到工作目录
COPY ./$MONGODB_PACKAGE_NAME $WORK_PATH/mongodb

#创建数据库文件目录
RUN mkdir -p /data/db

#更新
RUN apt-get update

#把libssl.so.1.0.0装上，否则无法运行mongodb
RUN apt-get install -y libssl1.0.0 libssl-dev

#把mongodb的bin目录加入到PATH环境变量中
ENV PATH=$WORK_PATH/mongodb/bin:$PATH

#mongodb的web端口
EXPOSE 28017

#连接端口
EXPOSE 27017

#启动服务，--rest参数表示开启web服务
CMD ["mongod", "--rest"]