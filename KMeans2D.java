package DWM;
import java.util.*;
/*
* Created by Rebecca D'souza on 08/08/18.
* */
class Point{
    double x,y;
    Point(double x, double y){ this.x = x; this.y = y;}
    @Override
    public String toString() { return "(" + x + ", " + y + ")"; }
}
public class KMeans2D {
    /*
    * Method to calculate mean of a cluster.
    * */
    private static Point mean(ArrayList<Point> al){
        int mx = 0,my = 0,m = al.size();
        for (Point point : al){
            mx += point.x;
            my += point.y;
        }
        return new Point(mx/m,my/m);
    }
    /*
     * Method to calculate distance between two 2 dimensional data elements.
     * */
    private static double distance(Point a, Point b){
        return Math.sqrt(Math.pow(a.x-b.x,2)+Math.pow(a.y-b.y,2));
    }
    /*
     * Method to display clusters and the corresponding data elements they contain.
     * */
    private static void result(HashMap<Integer,ArrayList<Point>> hm,int k){
        for(int i = 1;i<k+1;i++) {
            System.out.print("\nCluster "+i + ":    ");
            for (Point x : hm.get(i)) System.out.print(x + " ");
        }
    }
    public static void main(String args[]){
        Boolean unclustered = true;
        /*
        * HashMap of clusters, key is cluster number, value is cluster created using ArrayList.
        * */
        HashMap<Integer,ArrayList<Point>> hm = new HashMap<>();
        /*
         * ArrayList containing dataset of all 2 dimensional data.
         * */
        ArrayList<Point> al = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of data points : ");
        int count=1,n = sc.nextInt();
        System.out.println("Enter "+n+" data points : ");
        for(int i = 0;i<n;i++) al.add(new Point(sc.nextDouble(),sc.nextDouble()));
        System.out.print("Enter number of clusters : ");
        int k = sc.nextInt();
        /*
         * 1 indexed array containing the respective means for each cluster .
         * */
        Point[] means = new Point[k+1];
        /*
        * Selecting the first k 2D data elements in the dataset to be the cluster means.
        * */
        for (int i = 1;i<k+1;i++) means[i] = al.get(i-1);
        while(unclustered){
            System.out.println("\nIteration "+count++);
            for(Integer i = 1;i<k+1;i++) hm.put(i, new ArrayList<>());
            /*
            * Calculating distance of each 2D data element from the respective 2D mean of each cluster.
            * The data element is added to the cluster who's mean it is nearest to.
            * */
            for(Point x : al) {
                double min = Integer.MAX_VALUE;
                Integer cluster = 0;
                for (int j = 1; j < k + 1; j++) {
                    Point y = means[j];
                    if (distance(x, y) < min) {
                        min = distance(x, y);
                        cluster = j;
                    }
                }
                hm.get(cluster).add(x);
            }
            unclustered = false;
            /*
            * If calculated mean of cluster and corresponding cluster mean present in the array differ,
            * substitute the calculated means of each cluster in the cluster means array and reiterate.
            * */
            for(Integer i = 1;i<k+1;i++){
                Point u = means[i],v = mean(hm.get(i));
                System.out.println("Old mean "+u+" New mean "+v+" Equal : "+(u.x==v.x && u.y==v.y));
                if(!(u.x==v.x && u.y==v.y)) {
                    unclustered = true;
                    means[i] = v;
                }
            }
        }
        result(hm,k);
    }
}
/*              OUTPUT              */
/*
Enter number of data points : 7
Enter 7 data points :
1.0 1.0
1.5 2.0
3.0 4.0
5.0 7.0
3.5 5.0
4.5 5.0
3.5 4.5
Enter number of clusters : 2

Iteration 1
Old mean (1.0, 1.0) New mean (1.0, 1.0) Equal : true
Old mean (1.5, 2.0) New mean (3.0, 4.0) Equal : false

Iteration 2
Old mean (1.0, 1.0) New mean (1.0, 1.0) Equal : true
Old mean (3.0, 4.0) New mean (3.0, 5.0) Equal : false

Iteration 3
Old mean (1.0, 1.0) New mean (1.0, 1.0) Equal : true
Old mean (3.0, 5.0) New mean (3.0, 5.0) Equal : true

Cluster 1:    (1.0, 1.0) (1.5, 2.0)
Cluster 2:    (3.0, 4.0) (5.0, 7.0) (3.5, 5.0) (4.5, 5.0) (3.5, 4.5)
Process finished with exit code 0
* */