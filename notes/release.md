# Release

## Assembly

Run `mvn clean package` to create the release zip. The release zip `uknit-1.0-release.zip` is created in `core/target` dir.

The contents of zip are

	quickstart/pom.xml
	quickstart/src/main/java/org/codetab/uknit/quickstart/Quickstart.java
	uknit/pom.xml
	uknit/modules/uknit-release-1.0.jar
	uknit/resources/uknit.properties
	uknit/resources/uknit-names.properties
	uknit/resources/log4j2.xml

It contains two maven projects - uknit and quickstart. The `uknit/modules/uknit-release-1.0.jar` is the uber jar created by shade plugin with all dependencies.

## Assembly

### Assembly folders

The `assembly/uknit` and `assembly/quickstart` folders contains basic pom.xml for uknit-release and quickstart. These folders are copied as it is to release zip by assembly. The `uknit.properites` of core is used for development so it is not copied to zip, instead a custom file `assembly/uknit/resources/uknit.properties` that is specific to quickstart is copied to zip. The `uknit-names.properties` and `log4j2.xml` are part of core and copied from `core/src/main/resources` to zip. The uknit-release-1.0.jar is the shade jar copied from `core/target`.

### Why Shade Jar

The jar plugin produces jar for just uknit project and assembly plugin copies this jar and each and every depend jar to modules folder in release zip. Whereas the shade jar make a flat jar with all classes from uknit and its depends and assembly copy shade single jar to modules folder.

In IntelliJ the jars can be added in two ways either in Module Settings or in run config classpath. The Module settings allows to add modules folder or shade jar but is removed every time Maven projects are rebuild in Maven Tool Window: it is not useful. If you add module folder to classpath then class not found error is thrown on run. To work we have to add each and every jar of uknit and its depends. If we add shade jar to run classpath then it works as expected. The resources folder added to run classpath works as expected and uknit.properties is loaded without problem.

## Release


### gpg and keyserver

gpg --full-gen-key
gpg --list-key

view public key
gpg --armor --export <key id>

gpg --keyserver keyserver.ubuntu.com --send-keys <key id>

gpg --keyserver hkp://keyserver.ubuntu.com --search-key 'maithilish@gmail.com'

https://central.sonatype.org/pages/working-with-pgp-signatures.html

export and backup

### Sonatype Repository

https://s01.oss.sonatype.org/