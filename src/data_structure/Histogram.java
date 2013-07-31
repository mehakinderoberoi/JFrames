package Data_structure;

/**
 * API for Histogram data-structure. Super class for all other used histogram
 * @author allenliu
 *
 */
public abstract class Histogram<E> {
	
	/**
	 * Put an element in Histagram
	 * @param item
	 */
	public abstract void put(E[] item);
	
	/**
	 * Print the stat for the current Histogram
	 */
	public abstract void printStat();
	
	/**
	 * get number of element in the histagram
	 * @return size of elements
	 */
	public abstract int getElementsSize();
	
	/**
	 * Get corelation between two histogram
	 * @param other
	 * @return
	 */
	public abstract double getCorrelation(Histogram<E> other);
	
	public abstract double getSimilarity(Histogram<E> other);
}
