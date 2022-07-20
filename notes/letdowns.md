# Missing Features

## Control Flow Branches

  - Branches for internal methods, private and super methods, yet to be implemented.
  - Booleans in all branches are set to true. Developer has to set false for else method branches.
  - Throw statement is not created for exception branches. Developer has to step in and create them.

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
	
	
