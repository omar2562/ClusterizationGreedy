public interface IUnionFind<T extends AbstractTree> {
	public void makeSet(T t);

	public T findSet(T t);

	public void union(T t1, T t2);
}
