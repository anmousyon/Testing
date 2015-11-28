import java.util.*;
import java.lang.Math.*;

public class linkedLists{
	public static void main(String[] args){
		linkedList ll = new linkedList();
		for(int i =0; i<10; i++){
			double[] array = new double[(int)Math.pow(2,i)];
			ll.insertAtEnd(array);
		}
	}
}

class node{
	protected double[] data;
	protected node next;
	protected node prev;

	public node(){
		next = null;
		prev = null;
		data = null;
	}

	public node(double[]  d, node n, node p){
		prev = p;
		next = n;
		data = d;
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

class linkedList{
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

	public void insertAtStart(double[] val){
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

	public void insertAtEnd(double[] val){
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

	public void insertAtPosition(double[] val, int pos){
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
	
	public void deleteAtPosition(int pos){
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








