FROM ubuntu
RUN apt-get update && apt-get -y upgrade && apt-get install -y software-properties-common
# NOW GOES THE TOMCAT STUFF taken from https://gist.github.com/lobster1234/af2dc4eef3ee75117043d950c2c1ec2b
USER root
RUN apt-get -y install openjdk-8-jdk wget
RUN mkdir /usr/local/tomcat
RUN wget http://www-us.apache.org/dist/tomcat/tomcat-8/v8.5.30/bin/apache-tomcat-8.5.30.tar.gz -O /tmp/tomcat.tar.gz
RUN cd /tmp && tar xvfz tomcat.tar.gz
RUN cp -Rv /tmp/apache-tomcat-8.5.30/* /usr/local/tomcat/
EXPOSE 8080

# Commented out the CMD command for Tomcat as we will run 2 things in our container
#CMD /usr/local/tomcat/bin/catalina.sh run

# Set the default command to run when starting the container
# To run both Tomcat and Postgres in one container under one CMD, use a shell script
COPY --chown=root:root wrapper.sh wrapper.sh
CMD ./wrapper.sh