import java.util.*;
import java.lang.Math.*;

public class linkedLists{
	public static void main(String[] args){
		linkedList ll = new linkedList();
		for(double i =0; i<100; i++){
			if(i%2 == 0){
				ll.insertPriority(i+100);
			}
			else{
				ll.insertPriority(i);
			}
		}
		ll.display();
	}
}

class linkedList{
	class node{
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

	protected node start = null;
	protected node end = null;
	protected int size = 0;
	protected node current = null;
	protected node parent = null;
	protected int currentIndex = 0;
	protected int parentIndex = 0;

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
	
	public int findParent(){
		System.out.println("current index = " + currentIndex);
		int parentIndex = 0;
		if(currentIndex %2 == 0){
			parentIndex = (currentIndex / 2);
		}
		else if(currentIndex % 2 != 0){
			parentIndex = ((currentIndex-1) /2);
		}
		System.out.println("parent index = " +parentIndex);
		return parentIndex;
	}

	public int switchPC(double parentPriority, double priority, double temp, node tempNode){
		parentIndex = findParent();
		parentPriority = parent.data[parentIndex];
		if(parentPriority > priority){
			temp = parentPriority;
			parent.data[parentIndex] = priority;
			current.data[currentIndex] = temp;
			return parentIndex;
		}
		return -1;
	}

	public void insertSorter(double priority, double temp, double parentPriority, int tempIndex, node tempNode){
		while(true){
			if(current.prev != null){
				parent = current.prev;
			}
			else{
				break;
			}
			tempIndex= switchPC(parentPriority, priority, temp, tempNode);
			if(tempIndex == -1){
				break;
			}
			else{
				currentIndex = tempIndex;
			}
		}
	}

	public void addPriority(double priority){
		System.out.println("inserted "+ priority + " at " + current.size);
		current.data[current.size] = priority;
		currentIndex = current.size;
		current.size++;
	}

	public void insertPriority(double priority){
		double temp = 0;
		double parentPriority = 0;
		int tempIndex = 0;
		node tempNode = null;
		int currentIndex = 0;
		if(start != null){
			current = start;
			System.out.println("start not null");
		}
		else{
			System.out.println("start null");
			double[] newArray = new double[1];
			insertArrayAtStart(newArray);
			current = start;
		}
		tempNode = current;
		for(int i = 0; i<size; i++){
			if(current.size != Math.pow(2, i)){
				System.out.println("size: "+current.size);
				addPriority(priority);
				//System.out.println("inserted the priority, size: "+current.size);
				insertSorter(priority, temp, parentPriority, tempIndex, tempNode);
				break;
			}
			else if (current.getNext() != null){
				current = current.getNext();
				//System.out.println("went to next array");
			}
			else{
				double[] newArray = new double[(int)Math.pow(2, i+1)];
				insertArrayAtEnd(newArray);
				System.out.println("created new array of size: "+Math.pow(2, i+1));
				current = current.getNext();
				addPriority(priority);
				System.out.println("inserted priority into new array "+ current.size);
				insertSorter(priority, temp, parentPriority, tempIndex, tempNode);
				break;
			}

		}
	}

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
	
	public void display(){
		System.out.println("\nList: ");
		if (size == 0){
			System.out.println("empty");
			return;
		}
		if(start.getNext() == null){
			System.out.println(start.getData(0) + " start.getNext() null");
			return;
		}
		node ptr = start;
		System.out.println(start.getData(0));
		ptr = start.getNext();
		while(ptr != null){
			System.out.println("getNext() not  null");
			for(int i = 0; i <ptr.size; i++){
				System.out.println(ptr.getData(i));
			}
			if(ptr.getNext() != null){
				ptr = ptr.getNext();
			}
			else{
				ptr = null;
			}
		}
	}
}








