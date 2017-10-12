# La recyclette ♺

The aim of project is to provide a simple way for user to tell if one object
can be recycled, by taking a picture of the object to test. 

Project uses machine-learning-based visual recognition to identify object,
then compares it against a database of pre-identified recyclable items.

This project is under Apache 2 License.

## technical details

As of now, this project uses Watson visual recognition service.
A valid Watson api key is required to build/run the project.

The app also requires a dedicated local directory to store the uploaded images.

An internal, embedded HSQL database is used to store the pre-identification information.

## how-to

Required:
* java 8 jdk
* maven
* docker (optional)

### run locally

Command line arguments can be used to provide required apikey and storage folder.
<pre>mvn clean package ⏎
java -jar target/recikligi-`version`.jar ✂
  --recikligi.watson.apikey=`api key` ✂ 
  --recikligi.storageFolder=`path to storage folder` ⏎</pre>

The application uses Spring boot, so you can also use environment variables to
set the values (or SPRING_APPLICATION_JSON)

### docker
Note: you probably want to setup a docker volume to configure the storage folder
on the host machine.

<pre>mvn clean package ⏎
docker build . -t recikligi ⏎
docker run --rm -d -p 8080:8080 -t recikligi ✂
  --recikligi.watson.apikey=`api key` ✂ 
  --recikligi.storageFolder=`path to storage folder` ⏎</pre>