package DWM;
import java.io.*;
import java.util.*;
/*
* Created by Rebecca D'souza on 29.08.18
* */
public class Bayesian {
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        HashMap<String,ArrayList<String>> hm = new HashMap<>();
        hm.put("Income",new ArrayList<>());
        hm.put("Age",new ArrayList<>());
        hm.put("House",new ArrayList<>());
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File("C:\\Users\\Dell\\IdeaProjects\\coursework\\src\\DWM\\house_data.csv")));
            String line;
            String[] tempArr;
            while ((line = br.readLine()) != null) {
                tempArr = line.split(",");
                hm.get("Income").add(tempArr[0]);
                hm.get("Age").add(tempArr[1]);
                hm.get("House").add(tempArr[2]);
            }
            br.close();
        } catch (IOException e) { e.printStackTrace(); }
        System.out.println("Income values: \n\t\tLow\n\t\tMedium\n\t\tHigh\n\t\tVeryHigh");
        System.out.println("Age values: \n\t\tYoung\n\t\tMedium\n\t\tOld");
        System.out.println("House values: \n\t\tOwner\n\t\tRented");
        double PR = 0,PO = 0,likelihoodOwner,likelihoodRented;
        String income="",age="";
        int r = hm.get("House").size(),option = 0;
        String incomeValues[] = {"High","Low","Medium","VeryHigh"};
        String ageValues[] = {"Young","Medium","Old"};
        String columns[] = {"countO","countR","probO","probR"};
        HashMap<String,HashMap<String,HashMap<String,Double>>> table = new HashMap<>();
        table.put("Income",new HashMap<>());
        table.put("Age",new HashMap<>());
        for(String x : incomeValues){
            table.get("Income").put(x,new HashMap<>());
            for(String y : columns) table.get("Income").get(x).put(y,0.0);
        }
        for(String x : ageValues){
            table.get("Age").put(x,new HashMap<>());
            for(String y : columns) table.get("Age").get(x).put(y,0.0);
        }
        for(int i=0;i<r;i++){
            String val = hm.get("House").get(i);
            if(val.equals("Owner")){
                PO++;
                table.get("Age").get(hm.get("Age").get(i)).put("countO",table.get("Age").get(hm.get("Age").get(i)).get("countO")+1);
                table.get("Income").get(hm.get("Income").get(i)).put("countO",table.get("Income").get(hm.get("Income").get(i)).get("countO")+1);
            }
            else{
                PR++;
                table.get("Age").get(hm.get("Age").get(i)).put("countR",table.get("Age").get(hm.get("Age").get(i)).get("countR")+1);
                table.get("Income").get(hm.get("Income").get(i)).put("countR",table.get("Income").get(hm.get("Income").get(i)).get("countR")+1);
            }
        }
        for(String x : incomeValues){
            table.get("Income").get(x).put("probR",table.get("Income").get(x).get("countR")/PR);
            table.get("Income").get(x).put("probO",table.get("Income").get(x).get("countO")/PO);
        }
        for(String x : ageValues){
            table.get("Age").get(x).put("probR",table.get("Age").get(x).get("countR")/PR);
            table.get("Age").get(x).put("probO",table.get("Age").get(x).get("countO")/PO);
        }
        for(Map.Entry x : table.entrySet()) {
            System.out.println(x.getKey());
            HashMap<String,HashMap<String,Integer>> temp1 = (HashMap<String, HashMap<String, Integer>>) x.getValue();
            for(Map.Entry y:temp1.entrySet()){
                System.out.println("\t"+y.getKey()+" "+y.getValue());
            }
        }
        PR = PR / r;
        PO = PO / r;
        System.out.printf("\nP(Owner) = %.2f\nP(Rented) = %.2f\n",PO,PR);
        while(option!=2){
            System.out.println("Press 1 for prediction\nPress 2 to exit");
            option = sc.nextInt();
            System.out.println("Enter income : ");
            income = sc.next();
            System.out.println("Enter age : ");
            age = sc.next();
            likelihoodOwner = table.get("Income").get(income).get("probO")*table.get("Age").get(age).get("probO")*PO;
            likelihoodRented = table.get("Income").get(income).get("probR")*table.get("Age").get(age).get("probR")*PR;
            System.out.printf("Likelihood of Own house : %.4f\nLikelihood of Rented house : %.4f\n",likelihoodOwner,likelihoodRented);
            System.out.println("Press 1 for prediction\nPress 2 to exit");
            option = sc.nextInt();
        }
    }
}