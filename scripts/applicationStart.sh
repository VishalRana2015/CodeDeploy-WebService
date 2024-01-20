#!/bin/bash
nohup java -jar /home/ec2-user/server/webService.jar >> logfile.txt 2>&1 &
ls


