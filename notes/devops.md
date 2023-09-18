## JDK Version

Eclipse 4.17 (2020-09) onward mandates JDK version 11 or newer. Stand alone Eclipse JDT core is also compiled with 11. If tried with JDK 8 then class file has wrong version 55.0, should be 52.0 is thrown.

The eclipse.jdt version should be 3.26.0 or newer.

## JDT Dependency

uKnit uses jdt core library to parse and generate the tests. There are three options to add the dependency.

  1. Add regular maven dependency to use jdt artifacts from maven central. It pulls latest eclipse platform poms every time which changes frequently. and build error occurs if any of the dependency is unresolved owing to name mismatch between p2 and maven central repositories. On maven central you do not have the notion of Eclipse Releases.

  2. To overcome the above issue https://jmini.github.io/ecentral/ publishes stable BOM for every release of Eclipse. We can use the BOM as jdt dependency. However there is an extra dependency on ecentral repo.

  3. uKnit JDT use about 20 JDT related transitives deps and we can add these dependencies directly in uknit/pom.xml. At present we use this method and it is explained below.

### Manually Add JDT Deps

  - First add regular maven central jdt dependency (say version 3.33.0) and build which downloads the artifacts to local repo. Go to **$HOME/.m2/repository/org/eclipse/jdt/org.eclipse.jdt.core/3.33.0** and copy **org.eclipse.jdt.core-3.33.0.pom** as **pom.xml**.
  - Next run `mvn dependency:tree -Dverbose=true` and paste the output dependency tree to a file. Grep the file with `grep "compile" tree.txt | grep -v omitted > dep.txt` to get the used set of artifact coordinates. Using dep.txt prepare maven dependencies and add it to uknit/pom.xml.
  - To further fine tune the artifacts version use version from ecentral BOM. To do that, import github ecentral project in Eclipse IDE (Imports -> Git -> Projects from Git [with smart import]). By default, the project is downloaded to **$HOME/git/ecentral**. In IDE open **src/test/java/fr/jmini/utils/ecentral/RunTest.java** file and add a new test for latest Eclipse Platform version with proper update site url. For example, version is 4.27 for Eclipse release 2023-03.
  - Run the test. It takes couple of minutes to complete. For 4.27 the BOM is placed in **$HOME/git/ecentral/repo/fr/jmini/ecentral/eclipse-platform-dependencies/4.27** and named as **eclipse-platform-dependencies-4.27.pom**. It contains stable set of dependencies for Eclipse release 4.27. Next search the version properties in the pom and update uknit/pom.xml with stable versions.
  
## JDT Javadoc

Full Javadoc jar is missing from JDT artifact. Copy /cherry/backup/archived/org.eclipse.jdt.core-3.28.0-javadoc.jar to ~/.m2/repository/org/eclipse/jdt/org.eclipse.jdt.core/3.28.0.

## Debugging Internal Call

Internal call processor visit separate instance of Visitor and debug step through may not become recursive. Set additional brake point after the recursive step to break once returned from internal visitor to main visitor.

## Bulk Generator

Derives modules and java class file from the configs to generate tests for all java class files in project or its modules. Set following configs in uknit.properties,

uknit.source.base=/orange/work/bulk/commons-dbcp
uknit.source.dir=src/main/java
uknit.source.package=org.apache.commons.dbcp2

For maven projects, import using **Existing Maven project** option. Use mvn -U clean install to force download dependencies. Add the project to uknit/core class path using Build Path -> Configure Build Path -> Projects -> Classpath. 

Run BulkRunner as Java Application. The generated test files lands in uknit/bulk folder under the respective module/project instead of src/test/java dir and doesn't overwrite if test exists in uknit/bulk. The BulkRunner unset uknit.source.clz and uknit.source.method configs and generate test for all classes in the package. It sets the config uknit.source.error.ignore to true and ignores any error in source.

To generate test for single class file and method set uknit.source.clz and uknit.source.method configs and run uKnit app.

Usual routine is to run BulkRunner for a base package of project or for a sub package. Next, run uknit for each problematic class to know method which has error and run uknit for that method. Once method is fixed again run uknit for class to find out any other error in the class.

Multi module project - update this section later.

## Maven

### Tests and ITests

Run tests and itests

	mvn clean -Duknit.itest.copy.expFile=false -Duknit.itest.copy.testFile=false verify
	
### ITests

Run only the itests

	mvn clean -Duknit.itest.copy.expFile=false -Duknit.itest.copy.testFile=false -Dtest=zzz.java -DfailIfNoTests=false -Dsurefire.failIfNoSpecifiedTests=false integration-test

## Updates

To know version updates

	mvn versions:display-dependency-updates
	mvn versions:display-plugin-updates
	
