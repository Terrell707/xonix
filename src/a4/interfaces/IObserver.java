package a4.interfaces;

/**
 * The Observer Interface give the calling class the methods to be signaled when a class it is monitoring
 * has been updated.
 * @author Adrian
 *
 */
public interface IObserver {
	
	/**
	 * This method will let the class know that the class it is observing has been updated.
	 * @param o - The class which has updated itself.
	 * @param arg - Any message that the class wanted to pass.
	 */
	public void update (IObservable o, Object arg);

}
