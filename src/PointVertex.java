
public class PointVertex extends AbstractTree implements
		Comparable<PointVertex> {

	private int clusterNumber = 1;
	private int position;
	private double xPosition;
	private double yPosition;

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
			if (pnt.position == ((PointVertex) pnt.getPi()).position)
				break;
			System.out.print(pnt + " ==> ");
			pnt = (PointVertex) pnt.getPi();
		}
		System.out.println(pnt);
	}

	public void setClusterNumberToRoot(int clusterNumber) {
		PointVertex last = this;
		last.setClusterNumber(clusterNumber);
		while (last.getPi() != null || last.getClusterNumber() != clusterNumber) {
			last = (PointVertex) last.getPi();
			last.setClusterNumber(clusterNumber);
		}
	}
}
