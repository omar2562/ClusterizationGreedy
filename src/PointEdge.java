import java.util.Vector;

public class PointEdge implements Comparable<PointEdge> {

	private int clusterNumber = 1;
	private int position;
	private double xPosition;
	private double yPosition;
	private double key;
	private Vector<PointEdge> children;
	private PointEdge pi;

	public PointEdge(double xPosition, double yPosition) {
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

	public boolean addChild(PointEdge point) {
		return children.add(point);
	}

	public Vector<PointEdge> getChildren() {
		return children;
	}

	public void setChildren(Vector<PointEdge> children) {
		this.children = children;
	}

	public PointEdge getPi() {
		return pi;
	}

	public void setPi(PointEdge pi) {
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
		return this.position  + " (" + this.xPosition + " " + this.yPosition
				+ ") :"+this.clusterNumber+" | " + this.key;
	}

	@Override
	public int compareTo(PointEdge point) {
		// System.err.println(this + "- " + point + ":"+double.compare(this.key,
		// point.key));
		return 0 - Double.compare(this.key, point.key);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof PointEdge) {
			PointEdge p = (PointEdge) obj;
			return p.position == this.position;
		}
		return false;
	}

	public void print() {
		PointEdge pnt = this;

		while (pnt.getPi() != null) {
			System.out.print(pnt + " ==> ");
			pnt = pnt.getPi();
		}
		System.out.println(pnt);
	}

	public PointEdge getRoot() {
		PointEdge last = this;
		while (last.getPi() != null)
			last = last.getPi();
		return last;
	}

	public void setClusterNumberToRoot(int clusterNumber) {
		PointEdge last = this;
		last.setClusterNumber(clusterNumber);
		while (last.getPi() != null || last.getClusterNumber() != clusterNumber) {
			last = last.getPi();
			last.setClusterNumber(clusterNumber);
		}
	}

}
