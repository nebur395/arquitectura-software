cd /home/ruben/GitHub/arquitectura-software/src/web/
CLASSPATH='/home/ruben/GitHub/arquitectura-software/apache-tomcat-7.0.68/lib/servlet-api.jar:/home/ruben/GitHub/arquitectura-software/json-lib.jar:/home/ruben/GitHub/arquitectura-software/mysql-connector-java-5.1.38-bin.jar' javac servlets/*.java database/conection/*.java database/dataAccessObject/*.java database/valueObject/*.java

mv /home/ruben/GitHub/arquitectura-software/src/web/servlets/*.class /home/ruben/GitHub/arquitectura-software/redSocial/WEB-INF/classes/web/servlets
mv /home/ruben/GitHub/arquitectura-software/src/web/database/conection/*.class /home/ruben/GitHub/arquitectura-software/redSocial/WEB-INF/classes/web/database/conection
mv /home/ruben/GitHub/arquitectura-software/src/web/database/dataAccessObject/*.class /home/ruben/GitHub/arquitectura-software/redSocial/WEB-INF/classes/web/database/dataAccessObject
mv /home/ruben/GitHub/arquitectura-software/src/web/database/valueObject/*.class /home/ruben/GitHub/arquitectura-software/redSocial/WEB-INF/classes/web/database/valueObject


jar -cvf /home/ruben/GitHub/arquitectura-software/redSocial/redSocial.war -C /home/ruben/GitHub/arquitectura-software/redSocial/ .

rm -R /home/ruben/GitHub/arquitectura-software/apache-tomcat-7.0.68/webapps/redSocial

mv /home/ruben/GitHub/arquitectura-software/redSocial/redSocial.war /home/ruben/GitHub/arquitectura-software/apache-tomcat-7.0.68/webapps/


