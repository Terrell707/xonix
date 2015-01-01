package a4.interfaces;

import a4.interfaces.Iterator;
/**
 * This Collection Interface provides the calling class with the methods to add, remove, search, and manipulate a
 * list of objects.
 * @author Adrian
 *
 */
public interface ICollection {
	
	/**
	 * Adds the object into the collection.
	 * @param newObject - The object to add.
	 */
	public void add(Object newObject);
	
	/**
	 * Removes the specified object from the collection
	 * @param oldObject - The object to remove from the collection.
	 * @return Returns True if the object was found and removed. Returns False if the object was not found.
	 */
	public boolean remove(Object oldObject);
	
	/**
	 * Checks to see if the object exists in the collection.
	 * @param o - The object to search for.
	 * @return Returns True if the object was found. Returns False if the object was not found.
	 */
	public boolean contains(Object o);
	
	/**
	 * Returns the current size of the collection.
	 * @return The size of the collection.
	 */
	public int size();
	
	/**
	 * Checks to see if the collection has anything in it.
	 * @return Returns true if the collection is empty. Otherwise it returns false.
	 */
	public boolean isEmpty();
	
	/**
	 * Clears out the collection and makes it empty.
	 */
	public void clear();
	
	/**
	 * Returns an iterator to go through the objects of the collection.
	 * @return
	 */
	public Iterator iterator();
	
}
