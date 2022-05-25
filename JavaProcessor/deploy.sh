#!/bin/sh
mvn clean compile assembly:single -Dmaven.test.skip=true
cd target
rsync --rsh='sshpass -p "raissalinda" ssh -p 13777' -a ./MedicaoArduino-1.0-SNAPSHOT-jar-with-dependencies.jar pi@187.20.157.119:projetoTi/arduinoDataConsolidator

