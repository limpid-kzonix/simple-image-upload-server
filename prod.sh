#!/usr/bin/env bash

echo 'Prepare project directory'
sbt clean
echo 'Stage before production'
sbt stage
echo 'Build distributive'
sbt dist
echo 'PRODUCTION'
sudo target/universal/stage/bin/./kunderadatabasecloud -J-Xms1280M -J-Xmx2120m -J-server -Dconfig.file=target/universal/stage/conf/application.conf -Dpidfile.path=/var/run/play.pid -Dhttp.port=9220
