package a4.interfaces;

/**
 * This interface provides method for the class to go through the items in the collection. 
 * @author Adrian
 *
 */
public interface Iterator{
	
	public boolean hasNext();
	
	public Object next();
	
	public void remove();

}
