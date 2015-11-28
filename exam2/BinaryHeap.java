import java.util.*;

class BinaryHeap implements PriorityQueue{
	
	int size;
	double[] queue;
	private double[] temp;

	public BinaryHeap(){
		size =10;
		queue = new double[10];
	}

	public void changeSize(){
		if(size()==size){
			size*=2;
		}
		else if(size()<size/2 && size>=20){
			size /= 2;
		}
		temp = new double[queue.length];
		for(int i=0; i < queue.length; i++){
			temp[i] = queue[i];
		}
		queue = null;
		queue = new double[size];
		for(int i=0; i < temp.length; i++){
			queue[i] = temp[i];
		}
	}
				 
	public boolean isEmpty(){
		for (double priority : queue){
			if (priority != 0){
				return false;
			}
		}
		return true;
	}
	
	public int size(){
		for(int i = 0; i<queue.length; i++){
			if(queue[i] == 0){
				return i;
			}
		}
		return queue.length;
	}
	
	public double findMin(){
		if (isEmpty()){
			throw new EmptyPQException();
		}
		else{
			double minimum = queue[0];
			for (double priority : queue){
				if (priority < minimum && priority != 0){
					minimum = priority;
				}
			}
			return minimum;
		}
	}
	
	public void insert(double x){
		int index = 0;
		double temp;
		double parent;
		changeSize();
		for (int i =0; i<queue.length; i++){
			if (queue[i] == 0){
				queue[i] = x;
				index = i;
				break;
			}
		}
		//System.out.println("Made it to insert while");
		while(true){
			parent = queue[getParentIndex(index)];
			/*System.out.println(getParentIndex(index));
			System.out.println(index);
			System.out.println(parent);
			System.out.println(x);
			*/
			if(parent>x){
				//System.out.println("changing");
				//printArray();
				temp = parent;
				queue[getParentIndex(index)] = x;
				queue[index] = temp;
				index = getParentIndex(index);
			}
			else{
				break;
			}
		}
		//System.out.println("Made it past insert while");	
	}
	
	public double deleteMin(){
		double min = findMin();
		int parentIndex = 0;
		for(int i=0; i<queue.length; i++){
			if(min == queue[i]){
				parentIndex = i;
			}
		}
		int childIndex1;
		int childIndex2;
		int childIndex3;
		System.out.println("Made it to delete while" + parentIndex);
		while(parentIndex != getChildIndex(parentIndex)){
			childIndex1= getChildIndex(parentIndex);
			childIndex2 = childIndex1+1;
			if(queue[childIndex1]>queue[childIndex2]){
				switchParentChild(parentIndex, childIndex2);
			}
			else{
				switchParentChild(parentIndex, childIndex1);
			}
		}
		System.out.println("Made it past delete while");
		changeSize();
		return min;
	}
	
	public void makeEmpty(){
		for (double priority : queue){
			priority = 0;
		}
	}
	
	public int getParentIndex(int childIndex){
		if(childIndex == 0){
			return 0;
		}
		else if(childIndex%2 != 0){
			return (childIndex+1) /2 - 1;
		}
		else if(childIndex%2 == 0){
			return childIndex/2 - 1;
		}
		return 0;
	}
	
	public int getChildIndex(int parentIndex){
		if(queue[(parentIndex*2+1)] == 0 && parentIndex*2+1<size()){
			return parentIndex;
		}
		else if(parentIndex*2+1>size()){
			return -1;
		}
		return parentIndex*2+1;

	}
	public void switchParentChild(int parentIndex, int childIndex){
		double temp;
		temp = queue[parentIndex];
		queue[parentIndex] = queue[childIndex];
		queue[childIndex] = temp;
	}	

	public void printArray(){
		System.out.println("\n");
		for(double priority: queue){
			System.out.print(priority + " ");
		}
		System.out.println("\n");
	}
}

