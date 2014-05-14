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
	private static Vector<PointVertex> vertexList = new Vector<PointVertex>();
	private static Vector<PointVertex> vertexListTemp = new Vector<PointVertex>();
	private static double[][] weights = new double[MAX_SIZE][MAX_SIZE];
	private static Vector<Edge> edgeVector = new Vector<Edge>();
	private static Vector<Edge> edgeVectorTmp = new Vector<Edge>();
	private static Iterator<Edge> it;
	private static IUnionFind<PointVertex> unionFind = new UnionFind<PointVertex>();

	private static IPriorityQueue<PointVertex> priorityQueue = new PriorityQueue<PointVertex>();

	public static void main(String[] args) throws FileNotFoundException {
		File file = new File("data.txt");
		int numberOfCluster = 7;
		Scanner scanner = new Scanner(file);
		// Scanner scanner = new Scanner(System.in);
		scanner.useLocale(Locale.US);
		PointVertex point;
		int position = 0;
		while (scanner.hasNext()) {
			double x = scanner.nextDouble();
			double y = scanner.nextDouble();
			point = new PointVertex(x, y);
			point.setPosition(position++);
			vertexList.add(point);
		}
		generateGraph();
		generateMST(numberOfCluster, KRUSKAL);
		//generateMST(numberOfCluster, PRIM);
		cutTree(numberOfCluster);
		for (PointVertex pnt : vertexList) {
			System.out.println(pnt.getClusterNumber());
		}
		scanner.close();
	}

	private static void cutTree(int numberOfCluster) {
		Collections.sort(vertexListTemp);
		PointVertex pnt;
		for (int i = 1; i < numberOfCluster; i++) {
			pnt = vertexListTemp.get(i - 1);
			paintTree(pnt, i + 1);
		}
		Comparator<PointVertex> compareMethod = new Comparator<PointVertex>() {

			@Override
			public int compare(PointVertex o1, PointVertex o2) {
				return Integer.compare(o1.getPosition(), o2.getPosition());
			}
		};
		Collections.sort(vertexListTemp, compareMethod);
		vertexList = vertexListTemp;
	}

	public static void paintTree(PointVertex pnt, int numberOfCluste) {
		pnt.setPi(null);
		pnt.setClusterNumber(numberOfCluste);
		for (PointVertex point : vertexListTemp) {
			if (((PointVertex)point.getRoot()).getPosition() == pnt.getPosition()) {
				point.setClusterNumberToRoot(numberOfCluste);
			}
		}
	}

	private static PointVertex generateMST(int numberOfCluster, int method) {
		if (method == PRIM)
			return primAlgorith(numberOfCluster);
		else
			return kruskal(numberOfCluster);
	}

	private static PointVertex kruskal(int numberOfCluster) {
		for (PointVertex edge : vertexList) {
			unionFind.makeSet(edge);
		}
		Collections.sort(edgeVector);
		PointVertex p1, p2;
		for (Edge edge : edgeVector) {
			p1 = unionFind.findSet( edge.getVertex1());
			p2 = unionFind.findSet( edge.getVertex2());
			if (!p1.equals(p2)) {
				edgeVectorTmp.add(edge);
				unionFind.union(p1, p2);
				//edge.getVertex1().union(edge.getVertex2());
			}
		}
		PointVertex root = unionFind.findSet(vertexList.get(0));
		root.setKey(0);
		root.setPi(null);
		linkTree(root, new Vector<Edge>(edgeVectorTmp));
		vertexListTemp = vertexList;
		return root;
	}

	private static void linkTree(PointVertex father, Vector<Edge> vector) {
		father.setClusterNumber(0);
		PointVertex p1, p2, pt;
		it = edgeVectorTmp.iterator();
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
				}
				Vector<Edge> vec = new Vector<Edge>();
				edgeVectorTmp.remove(edge);
				it = edgeVectorTmp.iterator();
				linkTree(pt, vec);
			}
		}
		it = edgeVectorTmp.iterator();
	}

	private static PointVertex primAlgorith(int numberOfCluster) {
		for (PointVertex vertex : vertexList) {
			vertex.setKey(Float.MAX_VALUE);
			vertexListTemp.add(vertex);
		}
		PointVertex pStart = vertexList.get(0);
		pStart.setKey(0);
		((PriorityQueue<PointVertex>) priorityQueue).setVector(vertexList);
		priorityQueue.updateQueue();
		PointVertex point = null;
		while (!priorityQueue.isEmpty()) {
			priorityQueue.updateQueue();
			point = priorityQueue.remove();
			for (int adj = 0; adj < vertexList.size(); adj++) {
				PointVertex p = vertexList.get(adj);
				if (weights[p.getPosition()][point.getPosition()] < p.getKey()) {
					p.setPi(point);
					p.setKey(weights[p.getPosition()][point.getPosition()]);
				}
				p = null;
			}
		}
		return point;
	}

	private static void generateGraph() {

		PointVertex p1, p2;
		for (int i = 0; i < vertexList.size() - 1; i++) {
			for (int j = i + 1; j < vertexList.size(); j++) {
				p1 = vertexList.get(i);
				p2 = vertexList.get(j);
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
