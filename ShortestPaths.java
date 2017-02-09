import java.util.ArrayList;

class ShortestPaths {
    
	private Handle[] handles;
	private int[] shortestPaths;
	private Edge[] shortestEdges;
	

	public ShortestPaths(Multigraph G, int startId, 
			 Input input, int startTime) 
	{
		PriorityQueue<Vertex> Q = new PriorityQueue<Vertex>();
		handles = new Handle[G.nVertices()];
		shortestPaths = new int[G.nVertices()];
		shortestEdges = new Edge[G.nVertices()];
		
		for(Vertex v : G.getVerticies()) {
			handles[v.id()] = Q.insert(Integer.MAX_VALUE, v);
			shortestPaths[v.id()] = Integer.MAX_VALUE;
			shortestEdges[v.id()] = null;
		}
		shortestPaths[startId] = 0;
		Q.decreaseKey(handles[startId], 0);
		while(!Q.isEmpty()) {
			Vertex u = Q.extractMin();
			if(shortestPaths[u.id()] == Integer.MAX_VALUE) break;
			Vertex.EdgeIterator ei = u.adj();
			while(ei.hasNext()) {
				Edge e = ei.next();
				Vertex v = e.to();
				if(Q.decreaseKey(handles[v.id()], e.weight() + shortestPaths[u.id()])) {
					shortestPaths[v.id()] = e.weight() + shortestPaths[u.id()];
					shortestEdges[v.id()] = e;
				}
			}
		}
	}
    
    //
    // returnPath()
    // Return an array containing a list of edge ID's forming
    // a shortest path from the start vertex to the specified
    // end vertex.
    //
    public int [] returnPath(int endId) 
    { 
	// your code here
    	
    	ArrayList<Integer> path = new ArrayList<Integer>();
    	Edge current = shortestEdges[endId]; 

    	while(current != null) {
    		path.add(0, (current.id()));
    		current = shortestEdges[current.from().id()];
    	
    	}

    	int[] p = new int[path.size()];
    	int a = 0;
    	for(Integer b : path) {
    		p[a] = b;
    		a++;
    	}
    	return p;
    }
}
