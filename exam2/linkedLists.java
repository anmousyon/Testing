import java.util.*;

public class linkedLists{
	public static void main(String[] args){
	linkedList ll = new linkedList();
	ll.insertAtEnd(15);
	ll.insertAtEnd(20);
	ll.insertAtEnd(46);
	ll.insertAtEnd(30);
	ll.insertAtEnd(43);
	ll.display();
	}
}

 class node{
	protected double data;
	protected node link;

	public node(){
		link = null;
		data = 0;
	}

	public node(double  d, node l){
		link = l;
		data =d;
	}

	public void setLink(node n){
		link = n;
	}

	public void setData(double d){
		data = d;
	}

	public node getLink(){
		return link;
	}

	public double getData(){
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

	public void insertAtStart(double val){
		node nptr = new node(val, null);
		size++;
		if(start == null){
			start = nptr;
			end = start;
		}
		else{
			end.setLink(nptr);
			start = nptr;
		}
	}

	public void insertAtEnd(double val){
		node nptr = new node(val, null);
		size++;
		if (start == null){
			start = nptr;
			end = start;
		}
		else{
			end.setLink(nptr);
			end = nptr;
		}
	}

	public void insertAtPosition(double val, int pos){
		node nptr = new node(val, null);
		node ptr = start;
		pos = pos -1;
		for(int i =1; i<size; i++){
			if (i == pos){
				node temp = ptr.getLink();
				ptr.setLink(nptr);
				nptr.setLink(temp);
				break;
			}
			ptr = ptr.getLink();
		}
		size++;
	}
	
	public void deleteAtPosition(int pos){
		if(pos == 1){
			start = start.getLink();
			size--;
			return;
		}
		if(pos == size){
			node s = start;
			node t = start;
			while (s != end){
				t=s;
				s = s.getLink();
			}
			end = t;
			end.setLink(null);
			size--;
			return;
		}
		node ptr = start;
		pos = pos-1;
		for(int i =1; i<size-1; i++){
			if(i == pos){
				node temp = ptr.getLink();
				temp = temp.getLink();
				ptr.setLink(temp);
				break;
			}
			ptr = ptr.getLink();
		}
		size--;
	}
	
	public void display(){
		System.out.println("\n Singly Linked List: ");
		if (size == 0){
			System.out.println("empty");
			return;
		}
		if(start.getLink() == null){
			System.out.println(start.getData());
			return;
		}
		node ptr = start;
		System.out.println(start.getData() + " -> ");
		ptr = start.getLink();
		while(ptr.getLink() != null){
			System.out.println(ptr.getData() + " -> ");
			ptr = ptr.getLink();
		}
		System.out.println(ptr.getData()+"\n");
	}
}








