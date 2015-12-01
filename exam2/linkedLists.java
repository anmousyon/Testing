import java.util.*;
import java.lang.Math.*;

public class linkedLists{
	public static void main(String[] args){
		final long startTime = System.currentTimeMillis();
		linkedList ll = new linkedList();
		for(double i =0; i<50000; i++){
			if(i%2 == 0){
				ll.insertPriority(i/2);
			}
			else{
				ll.insertPriority(i);
			}
			
		}
		final long endTime = System.currentTimeMillis();
                System.out.println("Total execution time: " + (endTime - startTime) );
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
		}

		public node(double[]  d, node n, node p){
			prev = p;
			next = n;
			data = d;
			size = 0;
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
	protected node current = null;
	protected node parent = null;
	protected int currentIndex = 0;
	protected int parentIndex = 0;
	protected node tempNode;

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
		parentIndex = -1;
		if((currentIndex % 2) == 0){
			parentIndex = (currentIndex / 2);
		}
		
		else if((currentIndex % 2) != 0){
			parentIndex = ((currentIndex - 1) /2);
		
		}
	}
	
	//gets parent value from parent's index then switches the values of the child and parent if the parent is greater than the child
	public int switchPC(double parentPriority, double priority, double temp, node tempNode){
		
		findParent();

		if(parentIndex == -1){
			System.out.println("ERROR");
			return -1;
		}

		parentPriority = parent.data[parentIndex];//getting value at the parentindex of the previous array
		
		//switches parent and child if necessary
		if(parentPriority > priority){
			temp = parentPriority;
			parent.data[parentIndex] = priority;
			current.data[currentIndex] = temp;
			current = parent;
			return parentIndex;//returns parent's index so that the current index can be changed
		}
		
		else{
			return -1;//returns -1 if it doesnt need to be switched
		}
	}
	
	//looks for parent and switches them if necessary then changes the value of current index to its new index (where parent was)
	public void insertSorter(double priority, double temp, double parentPriority, int tempIndex, node tempNode){
		
		while(true){
			
			//checks if current has a parent array, if it does set parent equal to that node
			if(current.prev != null){
				parent = current.prev;
			}

			else{
				break;
			}
			
			//setting tempIndex equal to the parent's index
			tempIndex= switchPC(parentPriority, priority, temp, tempNode);
			
			//if it needs to be switched, set the current index to the parent index
			if(tempIndex == -1){
				break;
			}

			else{
				currentIndex = tempIndex;
			}
		}
	}

	//adds the new value to the array and stores its index then increments the stored size of array
	public void addPriority(double priority){

		current.data[current.size] = priority;
		currentIndex = current.size;
		current.size++;
	}

	//inserts the value into the first available slot
	public void insertPriority(double priority){
		
		//declaring variables that are needed to switch parent and child and to keep track of the current position
		double temp = 0;
		double parentPriority = 0;
		int tempIndex = 0;
		int currentIndex = 0;
		
		//checks if start is null and creates an array (of size one), and adds it if it is.
		if(start == null){
			
			double[] newArray = new double[1];
			insertArrayAtEnd(newArray);
		}

		current = start;
		tempNode = current;
		
		//iterates through each array in the linked list
		for(int i = 0; i<size; i++){

			//checks if the array is full (each array is a sequential power of two), if it isn't full then it inserts the value at the first available slow
			if(current.size != Math.pow(2, i)){
				addPriority(priority);
				insertSorter(priority, temp, parentPriority, tempIndex, tempNode);
				break;
			}

			//if it is full but there is another array in the linked list, go to that array
			else if (current.getNext() != null){
				current = current.getNext();
			}

			//if all the arrays in the linked list are full create a new array and add it to the end 
			else{
				double[] newArray = new double[(int)Math.pow(2, i+1)];//creating array twice as large as previous array
				insertArrayAtEnd(newArray);
				current = current.getNext();//sets current to the next array
				addPriority(priority);
				insertSorter(priority, temp, parentPriority, tempIndex, tempNode);//will check if the new value needs to be sorted or not and will sort if necessary
				break;
			}

		}
	}
	
	//inserts the array at the start of the linkedlist (not used)
	public void insertArrayAtStart(double[] val){
		node nptr = new node(val, null, null);
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
	public void insertArrayAtEnd(double[] val){
		node nptr = new node(val, null, null);
		size++;
		if (start == null){
			start = nptr;
			end = start;
		}
		else{
			node head = start;
			while(head.getNext() != null){
				head = head.getNext();
			}
			head.next = nptr;
			nptr.prev = head;
		}
	}
	
	//inserts the array at a specific position (not used)
	public void insertArrayAtPosition(double[] val, int pos){
		node nptr = new node(val, null, null);
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
	public void deleteArrayAtPosition(int pos){
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
		System.out.println(start.getData(0)+ "\n");
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








