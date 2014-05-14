import java.util.Arrays;
import java.util.Iterator;
import java.util.Vector;

public class PriorityQueue<T extends Comparable<T>> implements
		IPriorityQueue<T> {
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
				largest = i;
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

	protected T heapMaximum() {
		return vector.get(0);
	}

	protected T heapExtractMax() {
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

	protected void maxHeapInsert(T key) {
		vector.add(key);
		heapIncreaseKey(vector.size() - 1, key);
	}

	protected boolean hasElement(T p) {
		Iterator<T> it = vector.iterator();
		while (it.hasNext()) {
			if (p.equals(it.next()))
				return true;
		}
		return false;
	}

	@Override
	public void updateQueue() {
		buildMaxHeap();
	}

	@Override
	public T remove() {
		return heapExtractMax();
	}

	@Override
	public void insert(T t) {
		maxHeapInsert(t);
	}

	@Override
	public T next() {
		return heapMaximum();
	}

	@Override
	public int size() {
		return vector.size();
	}

	@Override
	public boolean isEmpty() {
		return vector.isEmpty();
	}

	@Override
	public String toString() {
		return vector + "";
	}

	public static void main(String[] args) {

		PriorityQueue<Integer> heap = new PriorityQueue<Integer>();
		heap.getVector()
				.addAll(Arrays.asList(new Integer[] { 4, 1, 3, 2, 16, 9, 10,
						14, 8, 7 }));
		System.out.println("Build Max Heap");
		heap.buildMaxHeap();
		System.out.println(heap.getVector());

		IPriorityQueue<Integer> heap2 = new PriorityQueue<Integer>();
		System.out.println("Insert Into Priority Queue");
		for (Integer i : Arrays.asList(new Integer[] { 4, 1, 3, 2, 16, 9, 10,
				14, 8, 7 })) {
			heap2.insert(i);
			System.out.println(heap2);
		}
		System.out.println("Remove from Priority Queue");
		while (heap2.size() > 0) {
			System.out.print(heap2.remove() + ": ");
			System.out.println(heap2);
		}
	}
}
