/**
 * Carter Hill
 * 11162143
 * cgh418
 * CMPT 280
 * Assignment 4
 */

package lib280.dispenser;

import lib280.exception.ContainerEmpty280Exception;
import lib280.exception.ContainerFull280Exception;
import lib280.tree.ArrayedBinaryTreeIterator280;
import lib280.tree.IterableArrayedHeap280;

public class PriorityQueue280<I extends Comparable<? super I>> {
	
	// This is the heap that we are restricting.
	// Items in the priority queue get stored in the heap.
	protected IterableArrayedHeap280<I> items;
	
	
	/**
	 * Create a new priorty queue with a given capacity.
	 * @param cap The maximum number of items that can be in the queue.
	 */
	public PriorityQueue280(int cap) {
		items = new IterableArrayedHeap280<I>(cap);
	}

	/**
	 * Determine if the queue is empty
	 * @return : isEmpty() - true if queue empty, otherwise false
	 */
	private boolean isEmpty() {
		return this.count() < 1;
	}

	/**
	 * Insert an item into the queue
	 * @param x - <I> item to be inserted
	 * @throws ContainerFull280Exception
	 */
	private void insert(I x) throws ContainerFull280Exception{
		if(this.isFull())
			throw new ContainerFull280Exception();
		this.items.insert(x);;
	}

	/**
	 * Get the maximum priority item
	 * @return : y - the highest item in the queue 
	 * @throws ContainerEmpty280Exception
	 */
	private I maxItem() throws ContainerEmpty280Exception {
		if(this.isEmpty())
			throw new ContainerEmpty280Exception();
		ArrayedBinaryTreeIterator280<I> x = this.items.iterator();
		x.goFirst();
		return x.item();
	}

	/**
	 * Return the minimum item in the queue
	 * @return : x.item() - the item at the bottom of the heap
	 * @throws ContainerEmpty280Exception
	 */
	private I minItem() throws ContainerEmpty280Exception {
		if(this.isEmpty())
			throw new ContainerEmpty280Exception();
		ArrayedBinaryTreeIterator280<I> x = this.items.iterator();
		x.goFirst();
		I y = x.item();
		
		for (int i = 1; i < this.count(); i++){
			x.goForth();
			int a = y.compareTo(x.item());
			
			if(a > 0){
				y = x.item();
			}
		}
		return y;
	}

	/**
	 * See how many items are in the queue
	 * @return : count() - int of items
	 */
	private int count() {
		return this.items.count();
	}

	/**
	 * Find out if the queue is full
	 * @return : true if count >= capacity, otherwise false
	 */
	private boolean isFull() {
		return count() >= this.items.capacity();
	}
	
	/**
	 * Delete the item of lowest priority
	 * @throws ContainerEmpty280Exception
	 */
	private void deleteMin() throws ContainerEmpty280Exception {
		if(this.isEmpty())
			throw new ContainerEmpty280Exception();
		
		ArrayedBinaryTreeIterator280<I> x = this.items.iterator();
		x.goFirst();
		I y = x.item();
		int pos = 1;
		
		for (int i = 1; i < this.count(); i++){
			x.goForth();
			int a = y.compareTo(x.item());
			
			if(a > 0){
				y = x.item();
				pos = i+1;
			}
		}
		this.items.deleteAtPosition(pos);
	}

	/**
	 * Delete the item at the top of the Queue
	 * @throws ContainerEmpty280Exception
	 */
	private void deleteMax() throws ContainerEmpty280Exception {
		if(this.isEmpty())
			throw new ContainerEmpty280Exception();
		
		this.items.deleteAtPosition(1);
	}
	
	/**
	 * Delete all items of the highest priority
	 * @throws ContainerEmpty280Exception
	 */
	private void deleteAllMax() throws ContainerEmpty280Exception {
		if(this.isEmpty())
			throw new ContainerEmpty280Exception();
		
		ArrayedBinaryTreeIterator280<I> x = this.items.iterator();
		
		// Max should be in pos 1, set it to y
		x.goFirst();
		I y = x.item();
		int pos = 1;
		
		// Delete all the are equal to y (max)
		for (int i = 1; i <= this.count(); i++){
			if(y.compareTo(x.item())==0){
				this.items.deleteAtPosition(pos);
				pos = 0;
				x = this.items.iterator();
				x.goBefore();
			}
			pos++;
			x.goForth();
		}
		
		
	}
	
	/**
	 * A string representation of the object
	 */
	public String toString() {
		return items.toString();	
	}
	
	public static void main(String args[]) {
		class PriorityItem<I> implements Comparable<PriorityItem<I>> {
			I item;
			Double priority;
						
			public PriorityItem(I item, Double priority) {
				super();
				this.item = item;
				this.priority = priority;
			}

			public int compareTo(PriorityItem<I> o) {
				return this.priority.compareTo(o.priority);
			}
			
			public String toString() {
				return this.item + ":" + this.priority;
			}
		}
		
		PriorityQueue280<PriorityItem<String>> Q = new PriorityQueue280<PriorityItem<String>>(5);
		
		// Test isEmpty()
		if( !Q.isEmpty()) 
			System.out.println("Error: Queue is empty, but isEmpty() says it isn't.");
		
		// Test insert() and maxItem()
		Q.insert(new PriorityItem<String>("Sing", 5.0));
		if( Q.maxItem().item.compareTo("Sing") != 0) {
			System.out.println("??Error: Front of queue should be 'Sing' but it's not. It is: " + Q.maxItem().item);
		}
		
		// Test isEmpty() when queue not empty
		if( Q.isEmpty()) 
			System.out.println("Error: Queue is not empty, but isEmpty() says it is.");
		
		// test count()
		if( Q.count() != 1 ) {
			System.out.println("Error: Count should be 1 but it's not.");			
		}

		// test minItem() with one element
		if( Q.minItem().item.compareTo("Sing")!=0) {
			System.out.println("Error: min priority item should be 'Sing' but it's not.");
		}	

		// insert more items
		Q.insert(new PriorityItem<String>("Fly", 5.0));
		if( Q.maxItem().item.compareTo("Sing")!=0) System.out.println("Front of queue should be 'Sing' but it's not.");
		Q.insert(new PriorityItem<String>("Dance", 3.0));
		if( Q.maxItem().item.compareTo("Sing")!=0) System.out.println("Front of queue should be 'Sing' but it's not.");
		Q.insert(new PriorityItem<String>("Jump", 7.0));
		if( Q.maxItem().item.compareTo("Jump")!=0) System.out.println("Front of queue should be 'Jump' but it's not.");

		
		if(Q.minItem().item.compareTo("Dance") != 0) System.out.println("minItem() should be 'Dance' but it's: " + Q.minItem());
		
		if( Q.count() != 4 ) {
			System.out.println("Error: Count should be 4 but it's not.");			
		}
		
		// Test isFull() when not full
		if( Q.isFull()) 
			System.out.println("Error: Queue is not full, but isFull() says it is.");
		
		Q.insert(new PriorityItem<String>("Eat", 10.0));
		if( Q.maxItem().item.compareTo("Eat")!=0) System.out.println("Front of queue should be 'Eat' but it's not.");

		if( !Q.isFull()) 
			System.out.println("Error: Queue is full, but isFull() says it isn't.");

		// Test insertion on full queue
		try {
			Q.insert(new PriorityItem<String>("Sleep", 15.0));
			System.out.println("Expected ContainerFull280Exception inserting to full queue but got none.");
		}
		catch(ContainerFull280Exception e) {
			// Expected exception
		}
		catch(Exception e) {
			System.out.println("Expected ContainerFull280Exception inserting to full queue but got a different exception.");
			e.printStackTrace();
		}		
		
		// test deleteMin
		Q.deleteMin();
		if(Q.minItem().item.compareTo("Sing") != 0) System.out.println("Min item should be 'Sing', but it's: " + Q.minItem());
		
		
		ArrayedBinaryTreeIterator280<PriorityItem<String>> x = Q.items.iterator();
		x.goFirst();
		
		Q.insert(new PriorityItem<String>("Dig", 1.0));
		if(Q.minItem().item.compareTo("Dig") != 0) System.out.println("minItem() should be 'Dig' but it's not.");

		// Test deleteMax
		Q.deleteMax();
		if( Q.maxItem().item.compareTo("Jump")!=0) System.out.println("Front of queue should be 'Jump' but it's not.");

		Q.deleteMax();
		if( Q.maxItem().item.compareTo("Fly")!=0) System.out.println("Front of queue should be 'Fly' but it's not.");

		if(Q.minItem().item.compareTo("Dig") != 0) System.out.println("minItem() should be 'Dig' but it's not.");

		Q.deleteMin();
		if( Q.maxItem().item.compareTo("Fly")!=0) System.out.println("Front of queue should be 'Fly' but it's not.");

		Q.insert(new PriorityItem<String>("Scream", 2.0));

		if( Q.maxItem().item.compareTo("Fly")!=0) System.out.println("Front of queue should be 'Fly' but it's not.");
		
		// test deleteAllMax()
		Q.deleteAllMax();
		if( Q.maxItem().item.compareTo("Scream")!=0) System.out.println("Front of queue should be 'Scream' but it's not.");
		if( Q.minItem().item.compareTo("Scream") != 0) System.out.println("minItem() should be 'Scream' but it's not.");
		Q.deleteAllMax();

		// Queue should now be empty again.
		if( !Q.isEmpty()) 
			System.out.println("Error: Queue is empty, but isEmpty() says it isn't.");

		System.out.println("Regression test complete.");
	}
}
