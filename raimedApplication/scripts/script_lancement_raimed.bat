@echo off
cd ..
start java -jar raimed2-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
cd ../raimed2Front
start npm run dev
