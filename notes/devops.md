## JDK Version

Eclipse 4.17 (2020-09) onwards mandates JDK version 11 or newer. Stand alone Eclipse JDT core is also compiled with 11. If tried with JDK 8 then class file has wrong version 55.0, should be 52.0 is thrown.

The eclipse.jdt version should be 3.26.0 or newer.

## JDT Javadoc

Full Javadoc jar is missing from JDT artifact. Copy /cherry/backup/archived/org.eclipse.jdt.core-3.28.0-javadoc.jar to ~/.m2/repository/org/eclipse/jdt/org.eclipse.jdt.core/3.28.0.

## Debugging Internal Call

Internal call processor visit separate instance of Visitor and debug step through may not become recursive. Set additional brake point after the recursive step to break once returned from internal visitor to main visitor.

## Bulk Generator

Derives modules and java class file from the configs - uknit.source.base (project base dir) and uknit.source.dir (src/main/java) and generates tests  for all java class files in project or its modules.

The generated test files lands in uknit/bulk folder under the respective module/project instead of src/test/java dir and doesn't overwrite if test exists in uknit/bulk.

Import the project in Eclipse and add the project to uknit/core class path using Build Path -> Configure Build Path -> Projects -> Classpath.

