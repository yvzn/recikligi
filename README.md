# La recyclette ♺

The aim of project is to provide a simple way for user to tell if one object
can be recycled, by taking a picture of the object to test. 

Project uses visual recognition (based on machine learning) to identify object,
then compares it against a database of pre-identified recyclable items.

[Demo website here](https://recyclette.azurewebsites.net/).

This project is under Apache 2 License.

## Technical details

For object detection, this project uses Azure computer vision service
or Watson visual recognition service (the later is deprecated).
Valid api key and/or enpoint are required to run the project.

The app also requires a dedicated local directory to store the uploaded images.

An embedded HSQL database is used to store the pre-identification
information.

## Run locally

Requirements:
* java 11 jdk
* maven
* docker (optional)

Command line arguments can be used to provide required apikey and storage folder.

### With Azure computer vision

<pre>mvn clean package ⏎

java -jar target/recikligi-`version`.jar ✂
  --recikligi.azure.computervision.endpoint=`api endpoint` ✂
  --recikligi.azure.computervision.apikey=`api key` ✂
  --recikligi.storageFolder=`path to storage folder` ⏎</pre>

As an alernative you can also use environment variables to set the values.

### With Watson visual recognition

<pre>mvn clean package ⏎

java -jar target/recikligi-`version`.jar ✂
  -Dspring.profiles.active=watson ✂
  --recikligi.watson.apikey=`api key` ✂ 
  --recikligi.storageFolder=`path to storage folder` ⏎</pre>

## Run with docker

Note: you want to setup a docker volume to configure the storage folder
on the host machine.

<pre>mvn clean package ⏎

docker build . -t recikligi ⏎

docker run --rm -d -p 8080:8080 -t recikligi ✂
  --recikligi.azure.computervision.endpoint=`api endpoint` ✂
  --recikligi.azure.computervision.apikey=`api key` ✂
  --recikligi.storageFolder=`path to storage folder` ⏎</pre>
