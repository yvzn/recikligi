# recikligi

(temporary name)

The aim of project is to provide a simple way for user to tell if one object
can be recycled, by taking a picture of the object to test. 

Project uses machine-learning-based visual recognition to identify object,
then compares it against a database of pre-identified recyclable items.

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

### kubernetes / bluemix cluster
You need a running Bluemix kubernetes cluster to continue from here.
Mine is running at http://173.193.102.118:32117/

Bluemix deployment how-to-s:
* https://developer.ibm.com/recipes/tutorials/deploying-ibm-containers-in-kebernetes-on-ibm-bluemix/
* https://medium.com/ibm-watson-data-lab/zero-to-kubernetes-on-the-ibm-bluemix-container-service-fd104fd193c1

The yaml files required to build the pod/services are in src/main/kubernetes.

The apikey is configured using an environment variable, built from a
dedicated kubernetes secret.
* https://kubernetes.io/docs/concepts/configuration/secret/

(optional) I used this docker image to avoid installing bluemix CLI and
kubernetes CLI locally : https://hub.docker.com/r/reachlin/bluemix/

Note: To push/pull images this way, you want to mount the container on
the host's docker socket, as described in previous link's "Advanced usage"
section.