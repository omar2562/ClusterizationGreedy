import java.util.Vector;

public class PointVertex implements Comparable<PointVertex> {

	private int clusterNumber = 1;
	private int position;
	private double xPosition;
	private double yPosition;
	private double key;
	private Vector<PointVertex> children;
	private PointVertex pi;

	public PointVertex(double xPosition, double yPosition) {
		this.xPosition = xPosition;
		this.yPosition = yPosition;
	}

	public int getClusterNumber() {
		return clusterNumber;
	}

	public void setClusterNumber(int clusterNumber) {
		this.clusterNumber = clusterNumber;
	}

	public double getxPosition() {
		return xPosition;
	}

	public void setxPosition(double xPosition) {
		this.xPosition = xPosition;
	}

	public double getyPosition() {
		return yPosition;
	}

	public void setyPosition(double yPosition) {
		this.yPosition = yPosition;
	}

	public double getKey() {
		return key;
	}

	public void setKey(double key) {
		this.key = key;
	}

	public boolean addChild(PointVertex point) {
		return children.add(point);
	}

	public Vector<PointVertex> getChildren() {
		return children;
	}

	public void setChildren(Vector<PointVertex> children) {
		this.children = children;
	}

	public PointVertex getPi() {
		return pi;
	}

	public void setPi(PointVertex pi) {
		this.pi = pi;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	@Override
	public String toString() {
		return this.position + " (" + this.xPosition + " " + this.yPosition
				+ ") :" + this.clusterNumber + " | " + this.key;
	}

	@Override
	public int compareTo(PointVertex point) {
		// System.err.println(this + "- " + point + ":"+double.compare(this.key,
		// point.key));
		return 0 - Double.compare(this.key, point.key);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof PointVertex) {
			PointVertex p = (PointVertex) obj;
			return p.position == this.position;
		}
		return false;
	}

	public void print() {
		PointVertex pnt = this;

		while (pnt.getPi() != null) {
			if (pnt.position == pnt.getPi().position)
				break;
			System.out.print(pnt + " ==> ");
			pnt = pnt.getPi();
		}
		System.out.println(pnt);
	}

	public PointVertex getRoot() {
		PointVertex last = this;
		while (last.getPi() != null)
			last = last.getPi();
		return last;
	}

	public void setClusterNumberToRoot(int clusterNumber) {
		PointVertex last = this;
		last.setClusterNumber(clusterNumber);
		while (last.getPi() != null || last.getClusterNumber() != clusterNumber) {
			last = last.getPi();
			last.setClusterNumber(clusterNumber);
		}
	}

	public void makeSet() {
		this.pi = this;
		this.clusterNumber = 0;
	}

	public PointVertex findSet() {
		PointVertex x = this;
		if (!x.equals(x.pi)) {
			x.pi = x.pi.findSet();
		}
		return x.pi;
	}

	public void link(PointVertex y) {
		PointVertex x = this;
		if (x.clusterNumber > y.clusterNumber) {
			y.pi = x;
		} else {
			x.pi = y;
			if (x.clusterNumber == y.clusterNumber) {
				y.clusterNumber += 1;
			}
		}
	}

	public void union(PointVertex y) {
		this.findSet().link(y.findSet());
	}
}
