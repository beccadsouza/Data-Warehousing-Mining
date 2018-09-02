package DWM;
import java.util.*;
/*
* Created by Rebecca D'souza on 28.08.18
* */
public class Apriori {
    private static void subset(String s,int k,int start,int len,boolean[] visited,ArrayList<String> al){
        if(len==k){
            String temp="";
            for(int i=0;i<s.length();i++)if(visited[i])temp+=String.valueOf(s.charAt(i));
            al.add(temp);
            return;
        }
        if(start==s.length())return;
        visited[start]=true;
        subset(s,k,start+1,len+1,visited,al);
        visited[start]=false;
        subset(s,k,start+1,len,visited,al);
    }
    private static ArrayList<String> subset(String s, int k){
        ArrayList<String> al = new ArrayList<>();
        subset(s,k,0,0,new boolean[s.length()],al);
        return al;
    }
    private static int frequency(String key){
        int count = 0;
        for(String transaction : transactions){
            ArrayList<Character> al = new ArrayList<>();
            boolean present = true;
            for(char x : transaction.toCharArray()) al.add(x);
            for(char x : key.toCharArray()) if(!al.contains(x)){ present = false;break; }
            if(present)count += 1;
        }
        return count;
    }
    private static ArrayList<String> transactions = new ArrayList<>();
    private static ArrayList<HashMap<String,Integer>> iterations = new ArrayList<>();
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        HashSet<Character> hs = new HashSet<>();
        ArrayList<Character> al = new ArrayList<>();
        String alphabet = "";
        int iteration = 1,max = 0,end = 0;
        System.out.print("Enter number of transactions : ");
        int t = sc.nextInt();
        System.out.print("Enter support level (number) : ");
        int support = sc.nextInt();
        System.out.println("Enter the "+t+" transactions :");
        while(t--!=0) {
            String temp = sc.next();
            for(char x : temp.toCharArray()){hs.add(x);al.add(x);}
            if(max<temp.length()) max = temp.length();
            transactions.add(temp);
        }
        for(char x : hs) alphabet += String.valueOf(x);
        while(max--!=-1) iterations.add(new HashMap<>());
        for(char x : hs) iterations.get(1).put(String.valueOf(x),Collections.frequency(al,x));
        while (true){
            for(Map.Entry<String,Integer> x : iterations.get(iteration).entrySet())if(x.getValue()<support){iterations.get(iteration).put(x.getKey(), -1);end++;}
            if(end==iterations.get(iteration).size()) break;
            iteration++;
            for(String x : subset(alphabet,iteration)) iterations.get(iteration).put(x,-1);
            for(Map.Entry<String,Integer> x : iterations.get(iteration).entrySet()){
                ArrayList<String> subsets = subset(x.getKey(),x.getKey().length()-1);
                ArrayList<Integer> values = new ArrayList<>();
                for(String y : subsets) values.add(iterations.get(iteration-1).get(y));
                if(values.contains(-1)) iterations.get(iteration).put(x.getKey(),-1);
                else iterations.get(iteration).put(x.getKey(),99);
            }
            for(Map.Entry<String,Integer> x : iterations.get(iteration).entrySet()) if(x.getValue()!=-1) iterations.get(iteration).put(x.getKey(),frequency(x.getKey()));
            end = 0;
        }
        for(int i = 1;i<iteration;i++) {
            System.out.println();
            for (Map.Entry<String, Integer> x : iterations.get(i).entrySet()) if (x.getValue() != -1) System.out.println(x.getKey() + " " + x.getValue());
        }
    }
}

/*

ACDFG
ABCDF
CDE
ADF
ACDEF
BCDEFG

* */

