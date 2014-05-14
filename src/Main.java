import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Locale;
import java.util.Scanner;
import java.util.Vector;

public class Main {

	private static final int PRIM = 1;
	private static final int KRUSKAL = 2;
	private static final int MAX_SIZE = 1000;
	static Vector<PointEdge> edgeList = new Vector<PointEdge>();
	static Vector<PointEdge> edgeListTemp = new Vector<PointEdge>();
	static double[][] weights = new double[MAX_SIZE][MAX_SIZE];
	static Vector<Edge> edgeVector = new Vector<Edge>();
	static Vector<Edge> edgeVectorTmp = new Vector<Edge>();
	static Iterator<Edge> it;

	static PriorityQueue<PointEdge> priorityQueue = new PriorityQueue<PointEdge>();

	public static void main(String[] args) throws FileNotFoundException {
		File file = new File("data.txt");
		int numberOfCluster = 7;
		Scanner sc = new Scanner(file).useLocale(Locale.US);
		PointEdge point;
		int position = 0;
		while (sc.hasNext()) {
			double x = sc.nextDouble();
			double y = sc.nextDouble();
			point = new PointEdge(x, y);
			point.setPosition(position++);
			edgeList.add(point);
		}
		generateGraph();
		generateMST(numberOfCluster, KRUSKAL);
		/*
		 * for (Edge edge : edgeVectorTmp) {
		 * System.out.println(edge.getVertex1() + "-----" + edge.getVertex2() +
		 * ">>>>>" + edge.getVertex1().findSet().getClusterNumber() + "," +
		 * edge.getVertex2().findSet().getClusterNumber()); //
		 * edge.getVertex1().print(); // edge.getVertex2().print(); }
		 * System.out.println("_________");
		 */
		cutTree(numberOfCluster);
		for (PointEdge pnt : edgeList) {
			System.out.println(pnt.getClusterNumber());
			// pnt.print();
			// System.out.println("===================>"+pnt.getClusterNumber());
		}
	}

	private static void cutTree(int numberOfCluster) {
		Collections.sort(edgeListTemp);
		PointEdge pnt;
		for (int i = 1; i < numberOfCluster; i++) {
			pnt = edgeListTemp.get(i - 1);
			paintTree(pnt, i + 1);
		}
		Comparator<PointEdge> compareMethod = new Comparator<PointEdge>() {

			@Override
			public int compare(PointEdge o1, PointEdge o2) {
				return Integer.compare(o1.getPosition(), o2.getPosition());
			}
		};
		Collections.sort(edgeListTemp, compareMethod);

	}

	public static void paintTree(PointEdge pnt, int numberOfCluste) {
		// System.err.println(pnt);
		pnt.setPi(null);
		pnt.setClusterNumber(numberOfCluste);
		for (PointEdge point : edgeListTemp) {
			if (point.getRoot().getPosition() == pnt.getPosition()) {
				point.setClusterNumberToRoot(numberOfCluste);
			}
		}
	}

	private static PointEdge generateMST(int numberOfCluster, int method) {
		if (method == PRIM)
			return primAlgorith(numberOfCluster);
		else
			return kruskal(numberOfCluster);
	}

	private static PointEdge kruskal(int numberOfCluster) {
		for (PointEdge edge : edgeList) {
			edge.makeSet();
		}
		Collections.sort(edgeVector);
		PointEdge p1, p2;
		for (Edge edge : edgeVector) {
			p1 = edge.getVertex1().findSet();
			p2 = edge.getVertex2().findSet();
			if (!p1.equals(p2)) {
				edgeVectorTmp.add(edge);
				edge.getVertex1().union(edge.getVertex2());
			}
		}
		PointEdge root = edgeList.get(0).findSet();
		root.setKey(0);
		root.setPi(null);
		linkTree(root, new Vector<Edge>(edgeVectorTmp));
		edgeListTemp = edgeList;
		return root;
	}

	private static void linkTree(PointEdge father, Vector<Edge> vector) {
		father.setClusterNumber(0);
		PointEdge p1, p2, pt;
		it = edgeVectorTmp.iterator();
		// while(!vector.isEmpty()){
		while (it.hasNext()) {
			Edge edge = it.next();
			p1 = edge.getVertex1();
			p2 = edge.getVertex2();
			if (p1.equals(father) || p2.equals(father)) {
				if (!p1.equals(father)) {
					p1.setPi(father);
					p1.setKey(edge.getDistance());
					pt = p1;
				} else /* if (!p2.equals(father)) */{
					p2.setPi(father);
					p2.setKey(edge.getDistance());
					pt = p2;
					// linkTree(p2, new Vector<Edge>(vector));
				}
				Vector<Edge> vec = new Vector<Edge>();
				edgeVectorTmp.remove(edge);
				it = edgeVectorTmp.iterator();
				// System.err.println(edgeVectorTmp);
				// pt.print();
				linkTree(pt, vec);
			}
		}
		it = edgeVectorTmp.iterator();
		// if(vector.isEmpty()) return;
		// }
		/*
		 * Edge edge; for (int i = 0; i < vector.size(); i++) { edge =
		 * vector.get(i); p1 = edge.getVertex1(); p2 = edge.getVertex2(); if
		 * ((p1.equals(father) || (p2.equals(father)) { vector.remove(edge); if
		 * (!p1.equals(father)) { p1.setPi(father);
		 * p1.setKey(edge.getDistance()); linkTree(p1, new
		 * Vector<Edge>(vector)); } if (!p2.equals(father)) { p2.setPi(father);
		 * p2.setKey(edge.getDistance()); linkTree(p2, new
		 * Vector<Edge>(vector)); } } }
		 */
	}

	private static PointEdge primAlgorith(int numberOfCluster) {
		for (PointEdge edge : edgeList) {
			edge.setKey(Float.MAX_VALUE);
			edge.setChildren(new Vector<PointEdge>());
			edgeListTemp.add(edge);
		}
		PointEdge pStart = edgeList.get(0);
		pStart.setKey(0);
		priorityQueue.setVector(edgeList);
		priorityQueue.updateQueue();
		/*
		 * for (PointEdge point : edgeList) {
		 * priorityQueue.maxHeapInsert(point); }
		 */
		PointEdge point = null;
		while (!priorityQueue.isEmpty()) {
			priorityQueue.updateQueue();
			point = priorityQueue.heapExtractMax();
			// System.err.println(point);
			for (int adj = 0; adj < edgeList.size(); adj++) {
				PointEdge p = edgeList.get(adj);
				if (weights[p.getPosition()][point.getPosition()] < p.getKey()) {
					p.addChild(point);
					p.setPi(point);
					p.setKey(weights[p.getPosition()][point.getPosition()]);
					// System.err.println(p+"--->"+point);
				}
				p = null;
			}
		}
		return point;
	}

	private static void generateGraph() {

		PointEdge p1, p2;
		for (int i = 0; i < edgeList.size() - 1; i++) {
			for (int j = i + 1; j < edgeList.size(); j++) {
				p1 = edgeList.get(i);
				p2 = edgeList.get(j);
				weights[i][j] = Math.sqrt(Math.pow(
						p1.getxPosition() - p2.getxPosition(), 2)
						+ Math.pow(p1.getyPosition() - p2.getyPosition(), 2));
				weights[j][i] = weights[i][j];
				edgeVector.add(new Edge(p1, p2, weights[j][i]));
				p1 = p2 = null;
			}
		}
	}

}
