import java.util.*;

public class BinaryHeap{
    
    public static void main(String[] args){
        final long startTime = System.currentTimeMillis();

		BinaryHeap binaryHeap = new BinaryHeap();
		for(double i = 1; i<20; i++){
                    if (i%2 == 0){
			binaryHeap.insert(i/2);
                    }
                    else{
                        binaryHeap.insert(i);
                    }
		}
                final long endTime = System.currentTimeMillis();
                System.out.println("Total execution time: " + (endTime - startTime) );
		binaryHeap.printArray();
		boolean binaryCheck = false;
		System.out.println(binaryHeap.findMin());
		System.out.println(binaryHeap.numElements);
		int binaryChild;
		int counter = 0;
		for(int i=0; i < binaryHeap.numElements; i++){
			binaryChild = binaryHeap.getChildIndex(i);
			if(binaryChild == -1 || binaryChild ==0){
				//System.out.println(i);
				binaryCheck = true;
				break;
			}
			if(binaryHeap.queue[i]<=binaryHeap.queue[binaryHeap.getChildIndex(i)] && binaryHeap.queue[i]<=binaryHeap.queue[binaryHeap.getChildIndex(i)+1]){
				counter ++;
			}
			if(counter == binaryHeap.queue.length){
				binaryCheck = true;													
			}
		}
		if(binaryCheck){
			System.out.println("correct");
		}
		else{
			System.out.println("wrong");
		}

		for(int i = 0; i<10; i++){
			binaryHeap.deleteMin();
		}
		binaryHeap.printArray();
    }
	int size;
	double[] queue;
	private double[] temp;
	int numElements = 0;

	//starts out with size of 10
	public BinaryHeap(){
		size =21;
		queue = new double[21];
	}

	//doubles the size of the array once it becomes full, halves it if it is less than half full
	public void changeSize(){
		if(size()==size){
			size*=2;
		}
		/*else if(size()<size/2 && size>=42){
			size /= 2;
		}*/
                else{
                    return;
                }
		temp = new double[queue.length*2];
		for(int i = 0; i<queue.length; i++){
			temp[i] = queue[i];
		}
		queue = temp;
	}
	
	//since each elements is initialized to 0, it checks to see if there are any non-zero elements and returns false if there are
	public boolean isEmpty(){
		for (double priority : queue){
			if (priority != 0){
				return false;
			}
		}
		return true;
	}
	
	//looks for the first zero and returns the index that it is at
	public int size(){
		return numElements;
	}
	
	//looks for the smallest non-zero elements by iterating through the array and keeping track of the min
	public double findMin(){
		if (isEmpty()){
                    return 0;
			//throw new EmptyPQException();
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
	
	//inserts the value at the first 0 in the array
	public void insert(double x){
                int index = 0;
		double temp;
		double parent;
		changeSize();
		queue[numElements] = x;
		index = numElements;
		numElements++;
		//if its parent is greater than it, then it switches the values
		while(true){
			parent = queue[getParentIndex(index)];
			if(parent>x){
				temp = parent;
				queue[getParentIndex(index)] = x;
				queue[index] = temp;
				index = getParentIndex(index);
			}
			else{
				break;
			}
		}
	}
	
	public void deleteSort(int parentInd){
		int childIndex1 = getChildIndex(parentInd);
		int childIndex2 = childIndex1 + 1;
		int childToSwitch;
		double temp;
		if(childIndex1 == -1){
			return;
		}
		if(queue[childIndex1]<queue[childIndex2]){
			childToSwitch = childIndex1;
		}
		else{
			childToSwitch = childIndex2;
		}
		if(queue[childToSwitch]<queue[parentInd]){
			temp = queue[parentInd];
			queue[parentInd] = queue[childToSwitch];
			queue[childToSwitch] = temp;
			deleteSort(childToSwitch);
		}
		else{
			return;
		}
	}

	//after finding the minimum value, it delets it then re-sorts the array
	public double deleteMin(){
		double min = queue[0];
		if (isEmpty()){
			return -1;
			//throw new EmptyPQException();
		}
		else{
			queue[0] = queue[numElements - 1];
			queue[numElements-1] = 0;
			numElements--;
			deleteSort(0);
		}
		changeSize();
		return min;
	}
	
	//sets every value to zero
	public void makeEmpty(){
		for (double priority : queue){
			priority = 0;
		}
	}
	
	//find any indexes parent index and returns the parent
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
	
	//fins and indexes first child index and returns it
	public int getChildIndex(int parentInd){
		parentInd = parentInd * 2 + 1;
		if(parentInd+1>size() || queue[parentInd] == 0){
			return -1;
		}
		return parentInd;

	}

	//switches the parent and child values
	public void switchParentChild(int parentIndex, int childIndex){
		double temp;
		temp = queue[parentIndex];
		queue[parentIndex] = queue[childIndex];
		queue[childIndex] = temp;
	}	
	
	//iterates through the array and prints each value
	public void printArray(){
		System.out.println("\n");
		for(double priority: queue){
			System.out.println(priority);
		}
		System.out.println("\n");
	}
}

