package data_structure;

public class UVColorHistogram extends Histogram<Integer> {
	
	private int[][] buckets = null;
	private boolean[][] hit = null;
	private int numElements;

	/**
	 * default bucket 225 * 315 matrix.
	 */
	public UVColorHistogram()
	{
		this.buckets = new int[225][315];
		this.hit = new boolean[225][315];
	}
	
	/**
	 * put a color in this histagram
	 */
	@Override
	public void put(Integer[] color) {
		if(color[0] > 112 || color[0] < -112 || color[1] >157 || color[1] < -157)
		{
			throw new IllegalArgumentException("U must be between -112 and 112 (inclusive), V must be between -157 and 157 (inclusive)");
		}
		int u = color[0];
		int v = color[1];
		int u_index = colorToIndex(u, 'u');
		int v_index = colorToIndex(v, 'v');
		this.buckets[u_index][v_index]++;
		this.hit[u_index][v_index] = true;
		this.numElements++;
	}

	/**
	 * Print the statistics in this histagram
	 */
	@Override
	public void printStat() {
		System.out.println("*************************************************************");
		System.out.println("Below is the statistics for the histalgram: ");
		System.out.println("\tThere are " + this.numElements + " elements in the histagram");
		for(int i = 0; i < 225; i++)
		{
			for(int j = 0; j < 315; j++)
			{
				if(this.hit[i][j])
				{
					System.out.println("There are " + this.buckets[i][j] + " elements in the color u: " + indexToColor(i, 'u') + " in the color v: " + indexToColor(j, 'v'));
				}
			}
		}
	}

	/**
	 * Return how many elements are stored in this histagram
	 */
	@Override
	public int getElementsSize() {
		return this.numElements;
	}
	
	
	/**
	 * Given the range start (inclusive) and end (exclusive), returns the index in the bucket
	 * @param start starting range, inclusive
	 * @param end ending range, exclusive
	 * @return index of the corresponding range in the bucket
	 */
	private static int colorToIndex(int color, char option)
	{
		int index = -1;
		switch(Character.toLowerCase(option))
		{
		case 'u':
			index = 112 + color;
			break;
		case 'v':
			index = 157 + color;
			break;
		default:
			throw new IllegalArgumentException("Unidentified option! Must be U, V, u, v!");
		}
		return index;
	}
	/**
	 * Given the index of the bucket, return the range of the value
	 * @param index index in the bucket, could be either U or V
	 * @return 0 index: start range(inclusive) 1 index: end range(exclusive)
	 */
	private static int indexToColor(int index, char option)
	{
		int color;
		switch(Character.toLowerCase(option))
		{
		case 'u': 
			color = index - 112; 
			break;
		case 'v':
			color = index - 157;
			break;
		default:
			throw new IllegalArgumentException("Unidentified option! Must be U, V, u, v!");
		}
		return color;
	}

}
