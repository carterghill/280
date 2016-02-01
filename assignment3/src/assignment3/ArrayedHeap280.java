package assignment3;

import lib280.base.Dispenser280;
import lib280.tree.ArrayedBinaryTree280;
import lib280.exception.*;

public class ArrayedHeap280<I extends Comparable<? super I>> extends
		ArrayedBinaryTree280<I> implements Dispenser280<I> {

	/**
	 * Constructor.
	 * 
	 * @param cap
	 *            Maximum number of elements that can be in the tree.
	 */
	@SuppressWarnings("unchecked")
	public ArrayedHeap280(int cap) {
		super(cap);
		this.items = (I[]) new Comparable[cap + 1];
	}

	@Override
	/** 
	 * Inserts a new item into the tree
	 * 
	 * @precond The tree must not be empty.  
	 * @throws ContainerEmpty280Exception if the tree is empty. 
	 */
	public void insert(I x) throws ContainerFull280Exception {
		if (this.isFull())
			throw new ContainerFull280Exception(
					"Cannot add item to a tree that is full.");
		else {
			count++;
			items[count] = x;
		}

		this.currentNode = count;
		int prev = this.currentNode;
		
		if(this.currentNode > 1){
			//prev = this.currentNode;
			this.currentNode = this.findParent(currentNode);
		}
		
		while (x.compareTo(this.item()) > 0
				&& this.currentNode > 1) {
			I[] temp = items.clone();
			this.items[currentNode] = temp[prev];
			this.items[prev] = temp[currentNode];
		}

	}

	@Override
	/** 
	 * Remove the current item from the tree.
	 * 
	 * @precond The tree must not be empty.
	 * @throws ContainerEmpty280Exception if the tree is empty.
	 */
	public void deleteItem() throws ContainerEmpty280Exception {
		if (this.isEmpty())
			throw new ContainerEmpty280Exception();

		items[1] = items[count];
		count--;
		int currentIndex = 1;

		while (items[currentIndex].compareTo(items[largest(
				this.findLeftChild(currentIndex),
				this.findRightChild(currentIndex))]) < 0) {

			int largestChildIndex = largest(this.findLeftChild(currentIndex),
					this.findRightChild(currentIndex));

			I[] temp = items.clone();

			items[currentIndex] = temp[largestChildIndex];
			items[largestChildIndex] = temp[currentIndex];

		}

	}

	/**
	 * Return the index of the largest of two items (at indexes x and y)
	 */
	private int largest(int x, int y) {
		if (items[x].compareTo(items[y]) > 0)
			return x;

		return y;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		ArrayedHeap280<Integer> H = new ArrayedHeap280<Integer>(10);

		H.insert(4);
		H.toString();

	}

}
