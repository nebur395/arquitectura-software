DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
cd $DIR/src/web/
CLASSPATH='/home/inigo/Documentos/AS/apache-tomcat-7.0.68/lib/servlet-api.jar:/home/inigo/Documentos/AS/json-lib.jar:/home/inigo/Documentos/AS/mysql-connector-java-5.1.38-bin.jar' javac servlets/*.java database/conection/*.java database/dataAccessObject/*.java database/valueObject/*.java utils/*.java

mv $DIR/src/web/servlets/*.class $DIR/redSocial/WEB-INF/classes/web/servlets
mv $DIR/src/web/database/conection/*.class $DIR/redSocial/WEB-INF/classes/web/database/conection
mv $DIR/src/web/database/dataAccessObject/*.class $DIR/redSocial/WEB-INF/classes/web/database/dataAccessObject
mv $DIR/src/web/database/valueObject/*.class $DIR/redSocial/WEB-INF/classes/web/database/valueObject
mv $DIR/src/web/utils/*.class $DIR/redSocial/WEB-INF/classes/web/utils



jar -cvf $DIR/redSocial/redSocial.war -C $DIR/redSocial/ .

rm -R /home/inigo/Documentos/AS/apache-tomcat-7.0.68/webapps/redSocial

mv $DIR/redSocial/redSocial.war /home/inigo/Documentos/AS/apache-tomcat-7.0.68/webapps/


