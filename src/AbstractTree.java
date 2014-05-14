public abstract class AbstractTree {
	protected double key;
	protected AbstractTree pi;
	protected int rank;

	public double getKey() {
		return key;
	}

	public void setKey(double key) {
		this.key = key;
	}

	public AbstractTree getPi() {
		return pi;
	}

	public void setPi(AbstractTree pi) {
		this.pi = pi;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public AbstractTree getRoot() {
		AbstractTree last = this;
		while (last.getPi() != null)
			last = last.getPi();
		return last;
	}

}
