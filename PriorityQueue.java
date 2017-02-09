import java.util.ArrayList;

//
// PRIORITYQUEUE.JAVA
// A priority queue class supporting sundry operations needed for
// Dijkstra's algorithm.
//

class PriorityQueue<T> {

	private ArrayList<Item<T>> queue;

	// constructor
	//
	public PriorityQueue() {
		queue = new ArrayList<Item<T>>();
		Handle h = new Handle(0);
		Item<T> i = new Item<T>(null, -1, h);
		queue.add(i);
	}

	// Return true iff the queue is empty.
	//
	public boolean isEmpty() {
		if (queue.size() == 1)
			return true;
		return false;
	}

	// Insert a pair (key, value) into the queue, and return
	// a Handle to this pair so that we can find it later in
	// constant time.
	//
	
	Handle insert(int key, T value) {
		Item<T> i = new Item<T>(value, key, new Handle(queue.size()));
		queue.add(i);
		i.handle.valid = true;
		while(i.key < queue.get(i.handle.value/2).key) {
			Handle parent = queue.get(i.handle.value/2).handle;
			swap(i.handle, parent);
		}
		return i.handle;
	}


	// Return the smallest key in the queue.
	//
	public int min() {
		return queue.get(1).key;
	}


	// Extract the (key, value) pair associated with the smallest
	// key in the queue and return its "value" object.
	//
	public T extractMin() {
		T t = (T) queue.get(1).value;
		queue.get(1).handle.valid = false;
		Item<T> i = queue.get(queue.size() -1);
		
		queue.set(1, i);
		i.handle.setHandle(1);
		queue.remove(queue.size() - 1);
		
		if (!isEmpty())
			heapify(1);
		return t;
	}
	


	// Look at the (key, value) pair referenced by Handle h.
	// If that pair is no longer in the queue, or its key
	// is <= newkey, do nothing and return false. Otherwise,
	// replace "key" by "newkey", fixup the queue, and return
	// true.
	//

	
	public boolean decreaseKey(Handle h, int newkey) {
		if(!h.valid || newkey >= queue.get(h.value).key) {
			return false;
		}
		Item<T> i = queue.get(h.value);
		i.key = newkey;
		while(i.handle.value != 1 && i.key < queue.get(i.handle.value/2).key) {
			swap(h, queue.get(i.handle.value/2).handle);
		}
		return true;
	}

	// Get the key of the (key, value) pair associated with a
	// given Handle. (This result is undefined if the handle no longer
	// refers to a pair in the queue.)
	//
	public int handleGetKey(Handle h) {
		return queue.get(h.value).key;
	}

	// Get the value object of the (key, value) pair associated with a
	// given Handle. (This result is undefined if the handle no longer
	// refers to a pair in the queue.)
	//
	public T handleGetValue(Handle h) {
		return queue.get(h.value).value;
	}
	
	public Item<T> handleGetItem(Handle h) {
		return queue.get(h.value);
	}

	public void heapify(int i) {
		Item<T> item = queue.get(i);
		if (2 * i + 1 == queue.size()) { // This is the case when a node has a
											// right child but a null left child
			int leftIndex = i * 2;
			Item<T> left = queue.get(leftIndex);
			if (left.key < item.key) {
				left.handle.setHandle(i);
				queue.set(i, left);
				item.handle.setHandle(leftIndex);
				queue.set(leftIndex, item);
			}
		} else if (2 * i < queue.size()) { // if the node is not a leaf
			int leftIndex = i * 2;
			int rightIndex = i * 2 + 1;
			Item<T> left = queue.get(leftIndex);
			Item<T> right = queue.get(rightIndex);
			Item<T> swapItem = null;
			int j;
			if (left.key < right.key) {
				j = left.handle.value;
				swapItem = left;
			} else {
				j = right.handle.value;
				swapItem = right;
			}
			if (swapItem.key < item.key) {
				swapItem.handle.setHandle(i);
				queue.set(i, swapItem);
				item.handle.setHandle(j);
				queue.set(j, item);
				heapify(j);
			}
		}
	}
	

	
	public void swap(Handle h1, Handle h2) {
		int index1 = h1.value;
		int index2 = h2.value;
		Item<T> i1 = queue.get(index1);
		Item<T> i2 = queue.get(index2);
		queue.set(index1, i2);
		i2.handle.setHandle(index1);
		queue.set(index2, i1);
		i1.handle.setHandle(index2);
		
	}


	// Print every element of the queue in the order in which it appears
	// in the implementation (i.e. the array representing the heap).
	public String toString() {
		String s = "";
		for (Item<T> i : queue) {
			if(i.key != -1) {
				s += "(" + i.key + ", " + i.value + ") \n" ;
			}
		}
		return s;
	}
}
