package DWM;
import java.util.*;
/*
* Created by Rebecca D'souza on 08/08/18
* */
public class KMeans1D {
    public static void main(String args[]){
        Boolean unclustered = true;
        HashMap<Integer,ArrayList<Double>> hm = new HashMap<>();
        ArrayList<Double> al = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of data points : ");
        int count=1,n = sc.nextInt();
        System.out.println("Enter "+n+" data points : ");
        for(int i = 0;i<n;i++) al.add(sc.nextDouble());
        System.out.print("Enter number of clusters : ");
        int k = sc.nextInt();
        Double[] means = new Double[k+1];
        for (int i = 1;i<k+1;i++) means[i] = al.get(i-1);
        while (unclustered){
            System.out.println("\nIteration "+count++);
            for(Integer i = 1;i<k+1;i++) hm.put(i, new ArrayList<>());
            for(Double x : al) {
                double min = Integer.MAX_VALUE;
                Integer cluster = 0;
                for (int j = 1; j < k + 1; j++) {
                    Double y = means[j];
                    if (Math.abs(x-y) < min) {
                        min = Math.abs(x-y);
                        cluster = j;
                    }
                }
                hm.get(cluster).add(x);
            }
            unclustered = false;
            for(Integer i = 1;i<k+1;i++) {
                Double u = means[i], v = 0.0;
                for (Double x : hm.get(i)) v += x;
                v /= hm.get(i).size();
                System.out.println("Old mean " + u + " New mean " + v + " Equal : " + (u.equals(v)));
                if (!(u.equals(v))) {
                    unclustered = true;
                    means[i] = v;
                }
            }
        }
        for(int i = 1;i<k+1;i++) {
            System.out.print("\nCluster "+i + ":    ");
            for (Double x : hm.get(i)) System.out.print(x + " ");
        }
    }
}
/*
11 2 6 17 8 10 15 20 33 16 13 14
* */