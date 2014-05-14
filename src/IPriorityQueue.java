public interface IPriorityQueue<T extends Comparable<T>> {
	public T remove();

	public void insert(T t);

	public T next();

	public int size();

	public boolean isEmpty();

	public void updateQueue();
}
