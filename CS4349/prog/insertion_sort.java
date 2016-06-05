import java.util.*;
import java.nio.file.*;
import java.io.*;

public class insertion_sort{
    
    public static void main(String[] arg){
        ArrayList<Integer> input = read_list();
        System.out.println("Unsorted:");
        for(int x : input){
            System.out.println(x);
        }
        final long startTime = System.currentTimeMillis();
        input = sort(input);
        final long endTime = System.currentTimeMillis();
        System.out.println("\nSorted:");
        for(int x : input){
            System.out.println(x);
        }
        System.out.println("\nSorting took: " + (endTime-startTime) + " ms");
    }

    public static ArrayList<Integer> sort(ArrayList<Integer> input){
        for(int i = 0; i < input.size(); i++){
            for(int j = i; j > 0; j--){
                if(input.get(j) < input.get(j-1)){
                    int temp = input.get(j);
                    input.set(j, input.get(j-1));
                    input.set(j-1, temp);
                }
            }
        }
        return input;
    }

    public static ArrayList<Integer> read_list(){
        ArrayList<Integer> input = new ArrayList<Integer>();
        try{
            for (String line : Files.readAllLines(Paths.get("list.txt"))) {
                //System.out.println(line);
                input.add(Integer.parseInt(line));
            }
        } 
        catch(IOException ex){
            ex.printStackTrace();
        }
        return input;
    }
}
            
