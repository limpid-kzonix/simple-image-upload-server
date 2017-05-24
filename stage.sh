#!/usr/bin/env bash


echo 'PRODUCTION'
target/universal/stage/bin/./kunderadatabasecloud -J-Xms1280M -J-Xmx2120m -J-server -Dconfig.file=target/universal/stage/conf/application.conf  -Dhttp.port=9220 > LOG.txt
