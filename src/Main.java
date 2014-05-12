import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;
import java.util.Scanner;
import java.util.Vector;

public class Main {

	private static final int PRIM = 1;
	private static final int MAX_SIZE = 1000;
	static Vector<PointEdge> edgeList = new Vector<PointEdge>();
	static Vector<PointEdge> edgeListTemp = new Vector<PointEdge>();
	static double[][] weights = new double[MAX_SIZE][MAX_SIZE];

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
		generateMST(numberOfCluster, PRIM);

		cutTree(numberOfCluster);
		for (PointEdge pnt : edgeListTemp) {
			System.out.println(pnt.getClusterNumber());
			// pnt.print();
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
		return null;
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
				p1 = p2 = null;
			}
		}
	}

}
