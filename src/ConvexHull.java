import java.io.File;
import java.util.*;


public class ConvexHull implements Comparator<Points> {


	static Stack<Points> st = new Stack<>();
	static ArrayList<Points> array = new ArrayList<>();
	static ArrayList<Points> arrY = new ArrayList<>();
	public static void read(String fileName) {
		try {
			File path = new File(fileName);
			Scanner input = new Scanner(path);
			int i = 0;
			while (input.hasNext()) {
				int x = input.nextInt();
				int y = input.nextInt();
				Points p = new Points(x, y);
				
				array.add(p);
				arrY.add(p);
			}
			input.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}





	public static double findDistance(Points p1, Points p2) {
		return Math.sqrt(Math.pow(p2.getX() - p1.getX(), 2) + Math.pow(p2.getY() - p1.getY(), 2));
	}

	public Points findExtreme(ArrayList<Points> arr, int min, int max, Points p1, Points p2) {
		Points pp = null;
		double extreme = 0.0;
		for (int i = min; i <= max; i++) {
			double extreme1 = findDistance(arr.get(i), p1) + findDistance(arr.get(i), p2);
			if (extreme < extreme1) {
				extreme = extreme1;
				pp = arr.get(i);
			}
		}
		if(findDistance(p1, pp) != 0 && findDistance(p2, pp) != 0)
			return pp;
		else
			return null;
					
	}

	public void quickHull(Points p1, Points p2) {

		Points pmax = extremeMaxPoint(p1, p2);
		if (pmax == null) {
			st.push(p1);
			st.push(p2);

		} else {

			extremeMaxPoint(p1, pmax);
			extremeMinPoint(pmax, p2);
		}
		Points pmin = extremeMinPoint(p1, p2);
		if (pmin == null) {
			st.push(p1);
			st.push(p2);
		} else {
			extremeMaxPoint(p1, pmin);
			extremeMinPoint(pmin, p2);
		}

	}

	public static void main(String[] args) {

		ConvexHull c = new ConvexHull();
		read("points.txt");
		Points p1 = new Points(1, 4);
		Points p2 = new Points(4, 1);
		Points p3 = new Points(7, 4);
		Points p4 = new Points(4, 7);
		Points p5 = new Points(3, 3);
		arrY.add(p5);
		arrY.add(p4);
		arrY.add(p3);
		arrY.add(p2);
		arrY.add(p1);
		Collections.sort(arrY, new Comparator<Points>() {
			public int compare(Points o1, Points o2) {
				if (o1.getY() > o2.getY())
					return 1;
				else
					return -1;
				
			}
		});
//		Points ss = c.findExtreme(arrY, 0, 1, p1, p3);
//		System.out.println(ss.getX() + " " + ss.getY());
		c.convexhull(arrY, 0, 3, p1, p3);
		System.out.println(st.size());
		while(!st.isEmpty()){
			Points ss = st.pop();
			System.out.println(ss.getX() + " " + ss.getY());
		}		

	}

	@Override
	public int compare(Points o1, Points o2) {
		if (o1.getY() > o2.getY())
			return 1;
		else
			return -1;

	}

	public void convexhull(ArrayList<Points> arrY, int left, int right, Points p1, Points p2) {
		
		if (left < right) {
			int mid = (int)Math.floor(left+right) / 2;
			
			Points pmax = findExtreme(arrY, mid+1, right, p1, p2);
			if (pmax != null){
				convexhull(arrY, mid + 1, right, p1, pmax);
				convexhull(arrY, mid + 1, right, pmax, p2);
			}
			else
				st.push(p2);
			Points pmin = findExtreme(arrY, left, mid, p1, p2);
			if (pmin != null){
				convexhull(arrY, left, mid, p1, pmin);
				convexhull(arrY, left, mid, pmin, p2);				
			}
			else
				return;
			

			
		}
		System.out.println(p2.getX() + " " + p2.getY());
		st.push(p2);
	}

}
