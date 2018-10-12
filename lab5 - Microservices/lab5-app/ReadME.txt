Lab 5 
The microservices are implemented as Rest APIs.

To build and deploy the lab5:
Modify the build.properties 
The "json-simple-1.1.jar" is under lab5-noletyve-app and should be added to the lib folder inside all 3 directories along with 
all the jersey libraries and postgres.
Run ant deploy

To create the microservices manually:

To build and deploy the lab5calc:
Navigate to the folder with the dockerfile
docker build .
docker run -d -p <port:port> <image-id>
docker cp lab5calc.war <container-id>:/usr/local/tomcat/webapps

To build and deploy the lab5map:
navigate to the folder with the dockerfile
docker build .
docker run -d -p <port:port> <image-id>
docker cp lab5map.war <container-id>:/usr/local/tomcat/webapps

The port number should be specified along with the URL in lab5ms.properties under the properties folder inside lab5-noletyve-app/lab5/properties
The url for lab5calc:
lab5calc.url=http://192.168.99.100:8050/lab5calc

The url for lab5map:
lab5map.url=http://192.168.99.100:8051/lab5map

To pull the images from dockerhub and run:

Docker Hub: https://hub.docker.com/u/namrathaov/
Image location:
lab5calc : namrathaov/lab5calc:firsttry
lab5map : namrathaov/lab5map:firsttry

Command to pull the lab5calc:
docker run -d -p 8050:8080 namrathaov/lab5calc:firsttry

Command to pull the lab5map:
docker run -d -p 8051:8080 namrathaov/lab5map:firsttry

The port number should be specified along with the URL in lab5ms.properties under the properties folder

The url for lab5calc:
lab5calc.url=http://192.168.99.100:8050/lab5calc

The url for lab5map:
lab5map.url=http://192.168.99.100:8051/lab5map

