package frontier;

public class TestHeapTime {
	public static void main(String args[]){
		FixSizedPriorityQueue<HeapNode> heap_time = new FixSizedPriorityQueue(10);
		int i= 0;
		while(i<10){
			HeapNode hp = new HeapNode();
			hp.setTime_access(i);
			heap_time.add(hp);
			i++;
		}
		while(heap_time.size()>0)
		System.out.println(heap_time.poll().getTime_access());
	}
}
