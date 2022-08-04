# Missing Features

## Control Flow Branches

  - Branches for internal methods, private and super methods, yet to be implemented.
  - Booleans in all branches are set to true. Developer has to set false for else method branches.
  - Throw statement is not created for exception branches. Developer has to step in and create them.
  - Test branches for switch case statement yet to be implemented.

### Initialization in upper CF Blocks

For lower ctl flow path the initialization statements of higher up Ctl Flow Block are missed. User has to copy these missed statements from appropriate test methods of upper ctl blocks.

In following example, for second if block (third path) selector will be null as the statements of first if/else blocks are not included. User has to copy missed statements from first if or else tests.

    String selector;
	if (flag) {        
       selector = date.toGMTString();
    } else {        
       selector = date.toGMTString();
    }
    String[] parts = selector.split("foo");
    if(parts > 1){
    	....
    }

## No assertThrows 

Exception test such as the below is not yet implemented.

	assertThrows(IllegalStateException.class, () -> cluster.getLeader());

## Same Class Name in Packages

Suppose hz.getCluster() returns object of type Cluster of com.hazelcast.cluster and this method is invoked in Cluster of org.codetab.scoopi.cluster package then uknit behaviour,

	// method invocation in SUT
    hz.getCluster()  
    
    // generated test statement
	Cluster banana = Mockito.mock(Cluster.class);

	// tester has to suffix the proper package as
	com.hazelcast.cluster.Cluster banana = Mockito.mock(com.hazelcast.cluster.Cluster.class);
	
	
