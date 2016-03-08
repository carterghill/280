/**
 * Carter Hill
 * 11162143
 * cgh418
 * CMPT 280
 * Assignment 4
 */

package lib280.tree;

import lib280.base.LinearIterator280;
import lib280.exception.AfterTheEnd280Exception;
import lib280.exception.ContainerEmpty280Exception;
import lib280.exception.NoCurrentItem280Exception;

public class ArrayedBinaryTreeIterator280<I> extends ArrayedBinaryTreePosition280 implements LinearIterator280<I> {

	// This is a reference to the tree that created this iterator object.
	ArrayedBinaryTree280<I> tree;
	
	// An integer that represents the cursor position is inherited from
	// ArrayedBinaryTreePosition280.
	
	/**
	 * Create a new iterator from a given heap.
	 * @param t The heap for which to create a new iterator.
	 */
	public ArrayedBinaryTreeIterator280(ArrayedBinaryTree280<I> t) {
		super(t.currentNode);
		this.tree = t;
	}

	/**
	 * Get the item at the current position
	 * @return : item<I> at cursor
	 * @throws NoCurrentItem280Exception
	 */
	@Override
	public I item() throws NoCurrentItem280Exception {
		// TODO Auto-generated method stub
		return this.tree.item();
	}

	/**
	 * See if there is an item at the current node
	 * @return : true if the is an item, false otherwise
	 */
	@Override
	public boolean itemExists() {
		// TODO Auto-generated method stub
		return !(this.tree.item()==null);
	}

	/**
	 * See if the cursor is before the start of the structure
	 * @returns : true if node is less than 1, otherwise false
	 */
	@Override
	public boolean before() {
		return this.tree.currentNode <= 0;
	}

	/**
	 * See if the cursor is after the end of the structure
	 * @returns : true if node is more than count, otherwise false
	 */
	@Override
	public boolean after() {
		return this.tree.currentNode > this.tree.count;
	}

	/**
	 * Move cursor to next node
	 * @throws AfterTheEnd280Exception
	 */
	@Override
	public void goForth() throws AfterTheEnd280Exception {
		if(this.after()) {
			throw new AfterTheEnd280Exception("Cannot advance cursor in the after position.");
		}
		this.tree.currentNode++;
	}

	/**
	 * Move cursor to the first node in the structure
	 * @throws ContainerEmpty280Exception
	 */
	@Override
	public void goFirst() throws ContainerEmpty280Exception {
		if( this.tree.isEmpty() ) 
			throw new ContainerEmpty280Exception("Cannot move to first item of an empty tree.");
		this.tree.currentNode = 1;
	}

	/**
	 * Move cursor to before the start of the structure
	 */
	@Override
	public void goBefore() {
		this.tree.currentNode = 0;		
	}

	/**
	 * Move cursor to after the end of the structure
	 */
	@Override
	public void goAfter() {
		while(!this.after()){
			this.goForth();
		}
	}
}
