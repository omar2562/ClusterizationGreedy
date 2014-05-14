public class Edge implements Comparable<Edge> {
	private PointVertex vertex1;
	private PointVertex vertex2;
	private double distance;

	public Edge(PointVertex vertex1, PointVertex vertex2) {
		this.vertex1 = vertex1;
		this.vertex2 = vertex2;
	}

	public Edge(PointVertex vertex1, PointVertex vertex2, double distance) {
		this.vertex1 = vertex1;
		this.vertex2 = vertex2;
		this.distance = distance;
	}

	public PointVertex getVertex1() {
		return vertex1;
	}

	public void setVertex1(PointVertex vertex1) {
		this.vertex1 = vertex1;
	}

	public PointVertex getVertex2() {
		return vertex2;
	}

	public void setVertex2(PointVertex vertex2) {
		this.vertex2 = vertex2;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	@Override
	public int compareTo(Edge o) {
		return Double.compare(distance, o.distance);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Edge) {
			Edge o = (Edge) obj;
			if (vertex1.equals(o.vertex1) && vertex2.equals(o.vertex2))
				return true;
			if (vertex2.equals(o.vertex1) && vertex1.equals(o.vertex2))
				return true;
		}
		return false;

	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return vertex1.getPosition() + "----" + distance + "----"
				+ vertex2.getPosition();
	}

	public static void main(String[] args) {
		PointVertex v1 = new PointVertex(2, 0);
		v1.setPosition(2);
		PointVertex v2 = new PointVertex(1, 1);
		v2.setPosition(4);
		PointVertex v3 = new PointVertex(1, 1);
		v3.setPosition(3);
		Edge e1 = new Edge(v1, v2);
		Edge e2 = new Edge(v2, v1);
		Edge e3 = new Edge(v3, v1);
		System.out.println(e1.equals(e2));
		System.out.println(e2.equals(e1));
		System.out.println(e1.equals(e1));
		System.out.println(e2.equals(e2));
		System.out.println(e1.equals(e3));
		System.out.println(e2.equals(e3));
	}

}
