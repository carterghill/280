/**
 * @author Carter Hill
 * 11162143
 * cgh418
 * Assignment 3
 * Question 3
 */

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
			this.currentNode = this.findParent(currentNode);
		}
		
		while (x.compareTo(this.item()) > 0
				&& this.currentNode > 0) {

			I y = this.items[currentNode];
			this.items[currentNode] = x;
			this.items[prev] = y;

			prev = this.currentNode;
			
			if(this.currentNode > 1)
				this.currentNode = this.findParent(currentNode);
		}

	}

	@Override
	/** 
	 * Remove the top item from the heap.
	 * 
	 * @precond The heap must not be empty.
	 * @throws ContainerEmpty280Exception if the heap is empty.
	 */
	public void deleteItem() throws ContainerEmpty280Exception {
		if (this.isEmpty())
			throw new ContainerEmpty280Exception();

		items[1] = items[count];
		count--;
		this.currentNode = 1;
		I firstItem = this.item();
		
		if(count > 2){
			this.currentNode = largest(this.findLeftChild(currentNode),
					this.findRightChild(currentNode));
		} else if(count == 2 && largest(1,2) == 2){
			this.items[1] = this.items[2];
			this.items[2] = firstItem;
		}

		while (firstItem.compareTo(this.item()) < 0) {

			I[] temp = items.clone();

			items[this.findParent(currentNode)] = firstItem;
			items[this.currentNode] = temp[this.findParent(currentNode)];

		}

	}

	/**
	 * Return the index of the largest of two items (at indexes x and y)
	 */
	private int largest(int x, int y) {
		this.currentNode = x;
		I itemX = this.item();
		this.currentNode = y;
		I itemY = this.item();
		
		if (itemX.compareTo(itemY) > 0)
			return x;

		return y;
	}
	
	/**
	 * @return item at top of heap
	 */
	private I viewTop(){
		return this.items[1];
	}
	
	private void viewHeap(){
		System.out.println("Here is your heap:\n==================");
		for(int i = 1; i < this.count+1; i++){
			this.currentNode = i;
			System.out.println(this.item());
		}
		System.out.println("==================");
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Boolean success = true;
		ArrayedHeap280<Integer> H = new ArrayedHeap280<Integer>(10);

		// IsEmpty on empty tree.
		if (!H.isEmpty())
			System.out.println("Test of isEmpty() on empty heap failed.");

		// Test deleteItem() on empty tree.
		Exception x = null;
		try {
			H.deleteItem();
		} catch (ContainerEmpty280Exception e) {
			x = e;
		} finally {
			if (x == null)
				System.out.println("Expected exception deleting item in empty heap.  Got none.");
		}
		
		// Only item is four, check if in the heap
		H.insert(4);
		if(!(H.viewTop()==4))
			System.out.println("Test on insert(4) failed");
		
		// After inserting 3, four should stay on top
		H.insert(3);
		if(!(H.viewTop()==4))
			System.out.println("Test on insert(3) failed");
		
		H.insert(2);
		// Delete should remove the highest number and replace it with three
		H.deleteItem();
		if(!(H.viewTop()==3))
			System.out.println("Test on deleteItem() failed");
		H.deleteItem();
		// Top should now be two
		if(!(H.viewTop()==2))
			System.out.println("Test on deleteItem() failed");
		
		// Overload heap and catch exception
		H.insert(4);
		H.insert(5);
		H.insert(10);
		H.insert(1);
		H.insert(88);
		H.insert(-69);
		H.insert(8);
		H.insert(11);
		H.insert(42);
		
		x = null;
		try {
			H.insert(0);
		} catch (ContainerFull280Exception e) {
			x = e;
		} finally {
			if (x == null){
				System.out.println("Expected exception dinserting to many items.  Got none.");
				success = false;
			}
		}
		
		// Make sure children are small than parents
		for(int i = 1; i < H.count; i++){
			H.currentNode = i;
			int left = H.findLeftChild(i);
			int right = H.findRightChild(i);
			if(left <= H.count && right <= H.count){
				int a = H.item();
				H.currentNode = left;
				int b = H.item();
				H.currentNode = right;
				int c = H.item();
				if(a < b || a < c){
					System.out.println("Parent smaller than child, error!");
					success = false;
				}
			} else if(left <= H.count){
				int a = H.item();
				H.currentNode = left;
				int b = H.item();
				if(a < b){
					System.out.println("Parent smaller than child, error!");
					success = false;
				}
			}
		}
		
		if(success) {
			System.out.println("Testing Success!\n");
			H.viewHeap();
		} else {
			System.out.println("Testing failed :(");
		}
			

	}

}
