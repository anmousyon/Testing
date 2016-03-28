import java.lang.Math.*;
import java.util.*;

public class sort{
	public static void main(String[] args){
		ArrayList<Integer> unsorted = new ArrayList<Integer>();
		Random rng = new Random();
		int r = 0;
		for(int i=0; i < 100; i++){
			r = rng.nextInt(1000);
			System.out.println(r);
			unsorted.add(r);
		}
		System.out.println("unsorted size:" + unsorted.size());
		double mean = mean(unsorted);
		double stdev = stdev(mean, unsorted);
		ArrayList<Integer> sorted = new ArrayList<Integer>();
		sorted = getList(mean, stdev, unsorted, sorted);
		for(int x: sorted){
			//System.out.println(x);
		}
	}	
	
	public static double mean(ArrayList<Integer> u){
		double mean = 0;
		int count = 0;
		for(int x : u){
			mean += x;
			count += 1;
		}
		mean /= count;
		System.out.println("mean: " + mean);
		return mean;
	}

	public static double stdev(double m, ArrayList<Integer> u){
		double stdsum = 0;
		double stdev = 0;
		int count = 0;
		for(int x: u){
			stdsum = Math.pow((x-m), 2);
			count += 1;
		}
		stdev = stdsum/(count-1);
		System.out.println(stdev);
		return stdev;
	}

	public static ArrayList<Integer> getList(double m, double sd, ArrayList<Integer> u, ArrayList<Integer> s){
		int max = Collections.max(u);
		int min = Collections.min(u);
		int numbuckets = (int)(Math.ceil((max-min)/(sd)));
		//System.out.println("Max: " + max);
		//System.out.println("Min: " + min);
		//System.out.println("# of buckets: " + numbuckets);
		ArrayList<Integer>[] buckets = new ArrayList[numbuckets];
		for(int i = 0; i < numbuckets; i++){
			ArrayList<Integer> bucket = new ArrayList<Integer>();
			buckets[i] = bucket;
		}
		for(int x : u){
			int bucketnum = (int)(Math.ceil((x-m)/sd));
			//System.out.println("bucketnum: " + bucketnum);
			bucketnum += (int)(Math.ceil(numbuckets/2));
			//System.out.println("unsorted: " + x);
			buckets[bucketnum].add(x);
		}
		int totalsize = 0;
		for(ArrayList<Integer> x : buckets){
			if(!x.isEmpty()){
				max = Collections.max(x);
				//System.out.println(max);
				min = Collections.min(x);
				//System.out.println(min);
				int size = 0;
				int[] newArray = new int[max-min+1];
				for(int a: x){
					//System.out.println(a);
					newArray[a-min] = a;
				}
				for(int b : newArray){
					size++;
					if(b!=0){
						s.add(b);
					}
				}
				totalsize+= size;
				System.out.println("bucket size: " + size);
			}
		}
		System.out.println("total size: " + totalsize);
		return s;
	}
}
