package a4.interfaces;

/**
 * This Observable Interface gives the calling class the methods to keep a list of objects that are monitoring it,
 * and to signal them when its data has been changed.
 * @author Adrian
 *
 */
public interface IObservable {

	/**
	 * This method will add the Observer into this class's list of Observing classes to notify when its data
	 * gets updated.
	 * @param obs - The Observing class to add to the list.
	 */
	public void addObserver(IObserver obs);
	
	/**
	 * This method will signal all Observing classes to let them know the data has been updated.
	 */
	public void notifyObservers(Object arg);
}
