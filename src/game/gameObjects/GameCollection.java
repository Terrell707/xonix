package a4.gameObjects;

import java.util.Vector;

import a4.interfaces.ICollection;
import a4.interfaces.Iterator;

public class GameCollection implements ICollection{
	
	private Vector<GameObject> gameObjects;						// Holds the game objects that will be placed on the field.
	
	/**
	 * No-arg Constuctor. Initializes the Collection so that items can be added.
	 */
	public GameCollection()
	{
		gameObjects = new Vector<>();
	}
	
	/**
	 * Adds the object into the collection.
	 * @param newObject - The object to add.
	 */
	@Override
	public void add(Object newObject)
	{
		gameObjects.add((GameObject) newObject);
	}
	
	/**
	 * Removes the specified object from the collection
	 * @param oldObject - The object to remove from the collection.
	 * @return Returns True if the object was found and removed. Returns False if the object was not found.
	 */
	@Override
	public boolean remove(Object oldObject)
	{
		return gameObjects.remove(oldObject);
	}
	
	/**
	 * Checks to see if the object exists in the collection.
	 * @param o - The object to search for.
	 * @return Returns True if the object was found. Returns False if the object was not found.
	 */
	@Override
	public boolean contains(Object obj)
	{
		return gameObjects.contains(obj);
	}
	
	/**
	 * Returns the current size of the collection.
	 * @return The size of the collection.
	 */
	@Override
	public int size()
	{
		return gameObjects.size();
	}
	
	/**
	 * Checks to see if the collection has anything in it.
	 * @return Returns true if the collection is empty. Otherwise it returns false.
	 */
	@Override
	public boolean isEmpty()
	{
		return gameObjects.isEmpty();
	}
	
	/**
	 * Clears out the collection and makes it empty.
	 */
	@Override
	public void clear()
	{
		gameObjects.clear();
	}

	/**
	 * Allows the calling class a way to access the items in the Collection without knowing exactly what is in
	 * the collection.
	 * @return An iterator that allows access to the items in the collection.
	 */
	@Override
	public Iterator iterator() {
		return new GCIterator();
	}
	
	/**
	 * Class that creates an iterator so that calling functions can access the items in the GameCollection without having
	 * to know exactly how the items are stored.
	 * @author Adrian
	 *
	 */
	private class GCIterator implements Iterator{
		
		private int currIndex;		// Holds the current position of the iterator.
		
		public GCIterator()
		{
			// Starts at -1 so that the first element will be 0. Or if the collection is empty,
			//	the iterator will know.
			currIndex = -1;
		}
		
		/**
		 * Checks to see if another element exists in the collection.
		 * @return Returns true if another object exists. Otherwise it returns false.
		 */
		@Override
		public boolean hasNext()
		{
			// If the collection's size is less than or equal to 0, then nothing exists in the collection
			//	and it doesn't have another element.
			if (gameObjects.size() <= 0){
				return false;
			}
			
			// If the current index is equal to one less than the size of the collection, then the end of
			//	the collection has been reached.
			if (currIndex >= gameObjects.size() - 1){
				return false;
			}
			else{
				return true;
			}
		}
		
		/**
		 * Returns the next element in the collection.
		 * @return The next element in the collection.
		 */
		@Override
		public GameObject next()
		{
			currIndex++;
			return gameObjects.elementAt(currIndex);
		}
		
		/**
		 * Removes from the collection the last element returned by the iterator.
		 */
		@Override
		public void remove()
		{
			if (currIndex > -1){
				gameObjects.remove(gameObjects.elementAt(currIndex));
			}
		}
	}
}
