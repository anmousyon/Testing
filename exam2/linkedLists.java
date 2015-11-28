import java.util.*;
import java.lang.Math.*;

public class linkedLists{
	public static void main(String[] args){
		linkedList ll = new linkedList();
		for(double i =0; i<10; i++){
			ll.insertPriority(i);
			ll.display();
		}
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
	
		public double[] getData(){
			return data;
		}
	}

	protected node start;
	protected node end;
	protected int size;

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
	
	public void insertPriority(double priority){
		node current = start;
		for(int i = 0; i<size; i++){
			if (current != null){
				if(current.size != Math.pow(2, i)){
					current.data[i] = priority;
					break;
				}
				else if (current.getNext() != null){
					current = current.getNext();
				}
			}
			else{
				double[] newArray = new double[(int)Math.pow(2, i)];
				insertArrayAtPosition(newArray, i);
				break;
			}

		}
		current.size++;
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
			end.setNext(nptr);
			nptr.setPrev(end);
			end = nptr;
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
		System.out.println("\n Singly Linked List: ");
		if (size == 0){
			System.out.println("empty");
			return;
		}
		if(start.getNext() == null){
			System.out.println(start.getData());
			return;
		}
		node ptr = start;
		System.out.println(start.getData() + " -> ");
		ptr = start.getNext();
		while(ptr.getNext() != null){
			System.out.println(ptr.getData() + " -> ");
			ptr = ptr.getNext();
		}
		System.out.println(ptr.getData()+"\n");
	}
}








