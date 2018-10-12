#!/bin/bash

# Start the second process Tomcat
echo "Starting Tomcat"
#export JAVA_HOME=""
export CATALINA_HOME="/usr/local/tomcat"
#export CATALINA_BASE=""
export CATALINA_PID=/tmp/$$
. $CATALINA_HOME/bin/catalina.sh start
status=$?
if [ $status -ne 0 ]; then
  echo "Failed to start Tomcat: $status"
  exit $status
fi

# Now load the warfile, this should also be
echo "Now issue the docker copy command: docker cp <local warfile> <container id>:/usr/local/tomcat/webapps"
# Naive check runs checks once a minute to see if either of the processes exited.
# This illustrates part of the heavy lifting you need to do if you want to run
# more than one service in a container. The container exits with an error
# if it detects that either of the processes has exited.
# Otherwise it loops forever, waking up every 60 seconds

while sleep 60; do
  ps aux |grep tomcat |grep -q -v grep
  PROCESS_2_STATUS=$?
  # If the greps above find anything, they exit with 0 status
  # If they are not both 0, then something is wrong
  if [ $PROCESS_2_STATUS -ne 0 ]; then
      echo `date` "Processes still running "
      exit 1      
  fi
done
