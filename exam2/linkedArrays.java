import java.util.*;
import java.lang.Math.*;

public class linkedArrays{
	public static void main(String[] args){
		final long startTime = System.currentTimeMillis();
		linkedList ll = new linkedList();
		for(double i =1; i<5000000; i++){
			if(i%2 == 0){
				ll.insertPriority(i/2);
			}
			else{
				ll.insertPriority(i);
			}
			
		}
		final long endTime = System.currentTimeMillis();
                System.out.println("Total execution time: " + (endTime - startTime) );
		System.out.println(ll.deleteMin());
		//ll.display();
	}
}

class linkedList{
	class node{
		
		//next and previous pointers, an array and the current size of the array
		protected double[] data;
		protected node next;
		protected node prev;
		protected int size;
		
		public node(){
			next = null;
			prev = null;
			data = null;
			size = 0;
		}

		public node(double[]  d, node n, node p, int s){
			prev = p;
			next = n;
			data = d;
			size = s;
		}
	
		public void setNext(node n){
			next = n;
		}
	
		public void setPrev(node p){
			prev = p;
		}
	
		public void setData(double[] d){
			data = d;
		}
	
		public node getPrev(){
			return prev;
		}
		
		public node getNext(){
			return next;
		}
	
		public double getData(int i){
			if(i<size){
				return data[i];
			}
			else 
				return -1;
		}
	}
	
	//start and end node, current and parent node with their indeces, and size of linkedlist
	protected node start = null;
	protected node end = null;
	protected int size = 0;
	protected int arrayLength = 1;
	protected node current = null;
	protected int currentIndex = 0;
	protected int parentIndex = 0;
	protected double temp;
	protected double toInsert;

	public linkedList(){
		start = null;
		end = null;
		size = 0;
	}

	public boolean isEmpty(){
		return start == null;
	}
	
	public int getSize(){
		return size;
	}
	
	//checks if even or odd then finds its parent index by dividing the first child (always even) by two
	public void findParent(){
		if((currentIndex % 2) == 0){
			parentIndex = (currentIndex / 2);
		}
		
		else{
			parentIndex = ((currentIndex - 1) /2);
		
		}
	}
	
	//gets parent value from parent's index then switches the values of the child and parent if the parent is greater than the child
	public int swap(){
		
		findParent();
		
		//switches parent and child if necessary
		if(current.prev.data[parentIndex] > toInsert){
			temp = current.prev.data[parentIndex];
			current.prev.data[parentIndex] = toInsert;
			current.data[currentIndex] = temp;
			current = current.prev;
			return parentIndex;//returns parent's index so that the current index can be changed
		}
		
		else{
			return -1;//returns -1 if it doesnt need to be switched
		}
	}
	
	//looks for parent and switches them if necessary then changes the value of current index to its new index (where parent was)
	public void sort(){
		while(current.prev != null && currentIndex != -1){
			//setting tempIndex equal to the parent's index
			currentIndex = swap();
		}
	}

	//adds the new value to the array and stores its index then increments the stored size of array
	public void addPriority(){
		current.data[current.size] = toInsert;
		currentIndex = current.size;
		current.size++;
	}

	//inserts the value into the first available slot
	public void insertPriority(double priority){
		toInsert = priority;
		//checks if start is null if it is, then it creates an array (of size one) and adds it
		if(start == null){
			double[] newArray = new double[1];
			appendArray(newArray);
		}
		
		current = end;

		//if array is full then create a new array and move current to that next array
		if(current.size == arrayLength){
			double[] newArray = new double[arrayLength*2];//creating array twice as large as previous array
			appendArray(newArray);
			current = current.next;//sets current to the next array
		}

		addPriority();
		sort();//will check if the new value needs to be sorted or not and will sort if necessary
	}
	
	public double deleteMin(){
		double min = 0;
		toInsert = 0;
		if(start == null){
			System.out.println("empty");
		}
		else{
			current = end;
			min = start.data[0];
			toInsert = current.data[current.size-1];
			current.data[current.size] = 0;
			current.size--;
			insertPriority(toInsert);
			node temp;
			if(current.size == 0 && current.getPrev() != null){
				node prevNode = current.prev;
				prevNode.next = null;
				current = null;
				size--;

			}
		}
		return min;
	}

	//inserts the array at the start of the linkedlist (not used)
	public void prependArray(double[] val){
		node nptr = new node(val, null, null, 0);
		size++;
		if(start == null){
			start = nptr;
			end = start;
		}
		else{
			nptr.setNext(end);
			end.setPrev(nptr);
			start = nptr;
		}
	}
	
	//appends the array to the end of the linkedlist (used)
	public void appendArray(double[] val){
		node nptr = new node(val, null, null, 0);
		size++;
		if (start == null){
			start = nptr;
			end = start;
			arrayLength = 1;
		}
		else{
			arrayLength *= 2;
			end.next = nptr;
			nptr.prev = end;
			end = nptr;
		}
	}
	
	//inserts the array at a specific position (not used)
	public void insertArray(double[] val, int pos){
		node nptr = new node(val, null, null, 0);
		node ptr = start;
		pos = pos -1;
		for(int i =1; i<size; i++){
			if (i == pos){
				node temp = ptr.getNext();
				ptr.setNext(nptr);
				nptr.setNext(temp);
				nptr.setPrev(ptr);
				temp.setPrev(nptr);
				break;
			}
			ptr = ptr.getNext();
		}
		size++;
	}
	
	//delets a specific array in the linkedlist (not used)
	public void deleteArray(int pos){
		if(pos == 1){
			start = start.getNext();
			size--;
			return;
		}
		if(pos == size){
			node s = start;
			node t = start;
			while (s != end){
				t=s;
				s = s.getNext();
			}
			end = t;
			end.setNext(null);
			size--;
			return;
		}
		node ptr = start;
		pos = pos-1;
		for(int i =1; i<size-1; i++){
			if(i == pos){
				node temp = ptr.getNext();
				temp = temp.getNext();
				ptr.setNext(temp);
				temp.setPrev(ptr);
				break;
			}
			ptr = ptr.getNext();
		}
		size--;
	}
	
	//displays all the elements in the arrays of the linkedlist sequentially
	public void display(){
		if (size == 0){
			System.out.println("empty");
			return;
		}
		if(start.getNext() == null){
			System.out.println(start.getData(0) + " start.getNext() null");
			return;
		}
		node ptr = start;
		System.out.println("\n" + start.getData(0)+ "\n");
		ptr = start.getNext();
		while(ptr != null){
			for(int i = 0; i <ptr.size; i++){
				System.out.println(ptr.getData(i));
			}
			if(ptr.getNext() != null){
				ptr = ptr.getNext();
				System.out.println("\n");
			}
			else{
				ptr = null;
			}
		}
	}
}








