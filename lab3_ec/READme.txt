Database used: MySQL

Extra Credits: 
The Richardson Maturity Model 3 is implemented in version 2.
Extra Credit #1 and #3 are implemented in version 2.

Apidocs works as expected in Windows. 
1. Navigate to BooktownRest in command prompt
2. Run command: 'apidoc -i ./src -o ./apidocs'

To generate apidocs in MAC or other OS: 
replace target api doc in build.xml:
<target name="apidoc">
	<mkdir dir="${apidocdir}"/>
  	<exec dir="." executable="apidoc">
  		<arg line="-i ./${srcdir} -o ./${apidocdir}"/>
  	</exec>
  </target>

The library for json+hal is at the root of the folder: jackson-dataformat-hal-1.0.0.jar. 
This file is to be included in lib folder.