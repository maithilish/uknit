
## Install Intellij Idea

Module means Intellij module and not Maven module.

1. Add release artifact jar to project class path. There are two ways to do this.

   1. Download release from GitHub and place it in some folder. Next open Project Structure menu, select your module and open 
      dependency tab. Add (click +), in drop-down select Jar or Dir option and in the file browser, select the jar you have downloaded.
        
   2. Alternatively you can simply add uKnit dependency to your module pom.xml
                
2. Add Run configuration to run uKnit
        Edit Run Configuration click + to add new conf
        in SDK field select Java version
        in -cp field select your module
        in main class field enter - org.codetab.uknit.core.Uknit
        in working dir select your module base dir

3. Finally, add uknit.properties file your module src/main/resource dir and point to java file to generate the test.

delete the below method as it is cumbersome.

Clone uKnit from GitHub to idea folder.
Clone your module to idea folder.

Start IDE and in welcome screen click Open and in Open File or Project dialog, select uKnit dir under Idea folder. 
Next go to File -> New -> Module from existing sources and import your module. In Import Module dialog choose Import module from External Model and select Maven to add your module to the project.
Next we need to add the requried modules as dependices to uKnit core module. Open Project Structure and in left tab select Modules to list all the modules of the project. From the list select core module and open dependencies tab. Press + and select Module Dependecies from drop-down menu. Choose required modules. 
Next we need to add all the libraries used by the added modules. Click + and choose Library from drop-down. Choose Libraries dialog lists all the libraries used by dependent modules. Select all and add selected. 
Finally Run the org/codetab/uknit/core/Uknit.java.


## Control Flow and Test Method Name

The control flow paths in a method under test results in multiple test, one for each execution path. While uKnit strives to compact the test names it also tries to include enough tokens to assist the developer to known which region of source method is tested by the test method. It is important to understand naming logic so that you can zero in relevant part of the source code.

Method with multiple control flow branches results in lengthy test method names. Here are some examples how to interpret the test method names to know which region of the code is being tested:

    public String foo(final Duck duck, final boolean canSwim) {
        duck.swim("start");
        if (canSwim) {
            duck.swim("if");
        }
        duck.swim("end");
        return duck.toString();
    }

The code results in two test methods

  - testFooIfCanSwim() - test focus is on if (canSwim) block.
    - The test name is constructed as test + Foo (method name) + If (focus is on if block) + CanSwim (if var)

  - testFooElseCanSwim() - focus is on else path of if (canSwim), note that else block itself is absent.
    - The test name is constructed as test + Foo (method name) + Else (focus is on else block/path) + CanSwim (if var)

Following code results in three test methods

        if (canSwim) {
            duck.swim("if");
            if (done) {
                duck.swim("if if");
            } else {
                duck.swim("if else");
            }
        } else {
            duck.swim("else");
        } 

   - testFooIfCanSwimIfDone() - the **IfCanSwimIfDone** in the test name indicates that focus is on if (done) block nested in if (canSwim) block.
   - testFooIfCanSwimElseDone() - the **IfCanSwimElseDone** indicates that focus is on else of if (done) block nested in if (canSwim) block.
   - testFooElseCanSwim() - the **ElseCanSwim** indicates that focus is on else of if (canSwim) block.
   
Trick is to look for If, Else, Try, Catch and Finally in the test method name and tokenize test method name into parts and then look for relevant blocks in the method under test. For example, tokenize the IfCanSwimElseDone into two parts IfCanSwim and ElseDone and then look for else block of if (done) nested within a if (canSwim) block.

Following configuration affects the test method name.

  - The config uknit.controlFlow.method.name.depth, default value is 3. It tells how many control nodes are considered for the test name. For example, when four if blocks are nested with one another, then only the innermost three if blocks are include in the name to reduce length of the test method name.

        if (canSwim) {
            if (canFly) {
                if (canQuack) {
                    if (ready) {
                        duck.swim("start");
                    ...    

The test method name testFooIfCanFlyIfCanQuackIfReady() contains only the inner most three nodes IfCanFly, IfCanQuack and IfReady. However it excludes the outer IfCanSwim part.

  - The config uknit.controlFlow.method.name.add.methodInvokeArgs defaults to false and by default the name of method invoked in a if test is considered for naming but not the arguments.
  
        if (Objects.nonNull(duck)) {
            duck.swim("if");
        }
  
The IfObjectsNonNull part in test method the testFooIfObjectsNonNull() uses the method invoke part Objects.nonNull and not the arg duck. This results in compact method name with enough parts to assist the developer to look for relevant block in source method.
          
  - The config uknit.controlFlow.method.name.add.infixRight defaults to false. When there are multiple vars in the if test statement then only the first var is used for the test name.
  
        if (canSwim && canFly) {
            duck.swim("if");
        }

Only the first arg canSwim is used for the test name - testFooIfCanSwim().

### Inner to Outer

See step.base.BaseLoaderTest




                



  

