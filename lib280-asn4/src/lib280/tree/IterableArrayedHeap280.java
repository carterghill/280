/**
 * Carter Hill
 * 11162143
 * cgh418
 * CMPT 280
 * Assignment 4
 */


package lib280.tree;

public class IterableArrayedHeap280<I extends Comparable<? super I>> extends ArrayedHeap280<I>  {

	/**
	 * Create an iterable heap with a given capacity.
	 * @param cap The maximum number of elements that can be in the heap.
	 */
	public IterableArrayedHeap280(int cap) {
		super(cap);
	}
	
	/**
	 * Delete the item at the current node
	 */
	public void deleteAtPosition(int pos){
		
		// Swap the last value in to the current position and decrease the count to 
		// delete the item
		this.items[pos] = this.items[count];
		this.count--;
		
		// If we deleted the last remaining item, make the the current item
		// invalid, and we're done.
		if (this.count == 0) {
			this.currentNode = 0;
			return;
		}

		// Propagate the new root down the tree.
		int n = 1;

		// While offset n has a left child...
		while (findLeftChild(n) <= count) {
			// Select the left child.
			int child = findLeftChild(n);

			// If the right child exists and is larger, select it instead.
			if (child + 1 <= count && items[child].compareTo(items[child + 1]) < 0)
				child++;

			// If the parent is smaller than the root...
			if (items[n].compareTo(items[child]) < 0) {
				// Swap them.
				I temp = items[n];
				items[n] = items[child];
				items[child] = temp;
				n = child;
			} else
				return;

		}

	}
	
	/**
	 * Return a new tree
	 * @return: ArrayedBinaryTreeIterator280<I> object for the tree
	 */
	public ArrayedBinaryTreeIterator280<I> iterator(){
		return new ArrayedBinaryTreeIterator280<I>(this); 
	}
	
}
