## JDK Version

Eclipse 4.17 (2020-09) onwards mandates JDK version 11 or newer. Stand alone Eclipse JDT core is also compiled with 11. If tried with JDK 8 then class file has wrong version 55.0, should be 52.0 is thrown.

The eclipse.jdt version should be 3.26.0 or newer.

## JDT Javadoc

Full Javadoc jar is missing from JDT artifact. Copy /cherry/backup/archived/org.eclipse.jdt.core-3.28.0-javadoc.jar to ~/.m2/repository/org/eclipse/jdt/org.eclipse.jdt.core/3.28.0.

## Debugging Internal Call

Internal call processor visit separate instance of Visitor and debug step through may not become recursive. Set additional brake point after the recursive step to break once returned from internal visitor to main visitor.