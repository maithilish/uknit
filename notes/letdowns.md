# Missing Features

## Separate Test methods for branches

Single test method is generated containing statements from all the three branches.

	try {
		if(mode.equals("server")) {
			do something
		} else {
			do some other thing
		}
	catch(Exception e) {
		output log
	}	

Similarly any other type of branches results in a single test method.

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
	
	
