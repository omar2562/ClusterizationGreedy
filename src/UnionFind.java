public class UnionFind<T extends AbstractTree> implements IUnionFind<T> {
	@Override
	public void makeSet(T t) {
		t.pi = t;
		t.rank = 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T findSet(T x) {
		if (!x.equals(x.pi)) {
			x.pi = findSet((T) x.pi);
		}
		return (T) x.pi;
	}

	public void link(T x, T y) {
		if (x.rank > y.rank) {
			y.pi = x;
		} else {
			x.pi = y;
			if (x.rank == y.rank) {
				y.rank += 1;
			}
		}
	}

	@Override
	public void union(T x, T y) {
		link(findSet(x), findSet(y));
	}
}
