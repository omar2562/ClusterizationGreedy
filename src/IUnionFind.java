public interface IUnionFind<T> {
	public void makeSet();

	public T findSet();

	public void union(T t);
}
