import java.util.Arrays;
import java.util.Vector;

public class PriorityQueue<T extends Comparable<T>> {
	private Vector<T> vector;

	public PriorityQueue() {
		vector = new Vector<T>();
	}

	public Vector<T> getVector() {
		return vector;
	}

	public void setVector(Vector<T> vector) {
		this.vector = vector;
	}

	protected int parent(int i) {
		return (int) Math.floor((i + 1) / 2) - 1;
	}

	protected int left(int i) {
		return 2 * (i + 1) - 1;
	}

	protected int right(int i) {
		return 2 * (i + 1);
	}

	protected void maxHeapify(int i) {
		int l = left(i);
		int r = right(i);
		int largest = Math.max(l, r);
		if (l < vector.size())
			if (vector.get(l).compareTo(vector.get(i)) > 0)
				largest = l;
			else
				largest = r;
		if (r < vector.size())
			if (vector.get(r).compareTo(vector.get(largest)) > 0)
				largest = r;
		if (largest != i && largest < vector.size()) {
			T tmp = vector.get(i);
			vector.set(i, vector.get(largest));
			vector.set(largest, tmp);
			maxHeapify(largest);
		}
	}

	protected void buildMaxHeap() {
		for (int i = (int) Math.floor(vector.size() / 2) - 1; i >= 0; i--)
			maxHeapify(i);
	}

	public T heapMaximum() {
		return vector.get(0);
	}

	public T heapExtractMax() {
		if (vector.size() < 0)
			throw new RuntimeException("heap underflow");
		T max = vector.get(0);
		vector.set(0, vector.get(vector.size() - 1));
		vector.removeElementAt(vector.size() - 1);
		maxHeapify(0);
		return max;
	}

	protected void heapIncreaseKey(int i, T key) {
		if (key.compareTo(vector.get(i)) < 0)
			throw new RuntimeException("heap underflow");

		vector.set(i, key);
		T tmp;
		while (i > 0 && vector.get(parent(i)).compareTo(vector.get(i)) < 0) {
			tmp = vector.get(i);
			vector.set(i, vector.get(parent(i)));
			vector.set(parent(i), tmp);
			i = parent(i);
		}
	}

	public void maxHeapInsert(T key) {
		vector.add(key);
		heapIncreaseKey(vector.size() - 1, key);
	}

	public static void main(String[] args) {
		PriorityQueue<Integer> heap = new PriorityQueue<Integer>();
		heap.getVector()
				.addAll(Arrays.asList(new Integer[] { 4, 1, 3, 2, 16, 9, 10,
						14, 8, 7 }));
		System.out.println("Build Max Heap");
		heap.buildMaxHeap();
		System.out.println(heap.getVector());
		heap = new PriorityQueue<Integer>();
		System.out.println("Insert Into Priority Queue");
		for (Integer i : Arrays.asList(new Integer[] { 4, 1, 3, 2, 16, 9, 10,
						14, 8, 7 })) {
			heap.maxHeapInsert(i);
			System.out.println(heap.getVector());
		}
		System.out.println("Remove from Priority Queue");
		while(heap.getVector().size() > 0){
			System.out.print(heap.heapExtractMax()+": ");
			System.out.println(heap.getVector());
		}
	}
}