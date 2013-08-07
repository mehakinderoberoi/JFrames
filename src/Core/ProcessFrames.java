package Core;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Data_structure.Rectangle;


/**
 * Class is implemented to do image processing on each frames.
 * @author allenliu
 *
 */
public class ProcessFrames {

	private ProcessImage prev;
	private ProcessImage curr;
	private List<String> frames;
	private int counter;
	private String folder;
	public ProcessFrames(String url) throws IOException
	{
	    this.frames = listFilesForFolder(new File(url));
		this.counter = 1;
		this.folder = url;
		this.prev = new ProcessImage(url + "/" + frames.get(0));
		this.curr = new ProcessImage(url + "/" + frames.get(1));
	}
	/**
	 * select the curr frame to be prev frame and next frame to be curr frame
	 */
	public void next() throws IOException
	{
		this.prev = this.curr;
		this.curr = new ProcessImage(this.folder + "/" + frames.get(++counter));
	}
	
	public List<ProcessImage> getPrevCurrImages()
	{
		List<ProcessImage> li = new ArrayList<ProcessImage>();
		li.add(this.prev);
		li.add(this.curr);
		return li;
	}
	
	/**
	 * Given a set of template region in each ProcessImage, move the rectangle box a bit 25px around to determine the best
	 * fit with the previous template in prev image
	 * @param region
	 */
	public void drawBestFitInPrevOnCurr()
	{
		List<Rectangle> prevRegions = this.prev.getTemplateRegions();
		Random rand = new Random();
		for(Rectangle rec : prevRegions)
		{
			Rectangle bestFit = rec;
			double highestCorrelation = Double.NEGATIVE_INFINITY;
			//System.out.println("original: x1" + rec.getUpperLeftX() + " y1: " + rec.getUpperLeftY() + " x2: " + rec.getLowerRightX() + " y2: " + rec.getLowerRightY());
			int numTrials = rand.nextInt(15) + 5;
			for(int i = 0; i < numTrials; i++)
			{
				int offset = rand.nextInt(11) - 5;
				int x1 = rec.getUpperLeftX();
				int y1 = rec.getUpperLeftY();
				int x2 = rec.getLowerRightX();
				int y2 = rec.getLowerRightY();
			
				int new_x1 = x1 + offset;
				int new_y1 = y1 + offset;
				int new_x2 = x2 + offset;
				int new_y2 = y2 + offset;
				//quite impossible cases but just in case
				if (new_x1 > this.curr.getDimention()[0] - 1 || new_y1 > this.curr.getDimention()[1] - 1 || new_x2 < 0 || new_y2 < 0)
				{
					throw new IllegalArgumentException("Image is too small!");
				}
				if(new_x1 < 0)
				{
					new_x1 = 0;
					new_x2 += Math.abs(offset);
				}
				if(new_y1 < 0)
				{
					new_y1 = 0;
					new_y2 += Math.abs(offset);
				}
				if (new_x2 > this.curr.getDimention()[0] - 1)
				{
					new_x2 = this.curr.getDimention()[0] - 1;
					new_x1 -= Math.abs(offset);
				}
				if(new_y2 > this.curr.getDimention()[1] - 1)
				{
					new_y1 -= Math.abs(offset);
					new_y2 = this.curr.getDimention()[1] - 1;
				}	
				//System.out.println("x1: " + new_x1 + " y1: " + new_y1 + " x2: " + new_x2 + " y2: " + new_y2);
				Rectangle new_rec = new Rectangle(new_x1, new_y1, new_x2, new_y2);
				ProcessImage prev = this.prev.getRectangleImage(rec);
				ProcessImage curr = this.curr.getRectangleImage(new_rec);
				double correlation = curr.getCorrelationBetweenImages(prev);
				if(correlation > highestCorrelation)
				{
					System.out.println("correlation: " + correlation);
					highestCorrelation = correlation;
					bestFit = new_rec;
				}
			}
			System.out.println("Best fit rectangle: " + bestFit);
		}
		
	}
	/**
	 * Recursive helper for displaying all files in the folder
	 * @param folder
	 * @return
	 */
	private List<String> listFilesForFolder(File folder) {
		List<String> candidates = new ArrayList<String>();
	    for (File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	            listFilesForFolder(fileEntry);
	        } else {
	        	String filename = fileEntry.getName();
	        	String extension = filename.substring(filename.lastIndexOf('.') + 1).trim();
	        	if(extension.equals("jpg") || extension.equals("jpeg") || extension.equals("jpe") || extension.equals("jfif"))
	        	{
		            candidates.add(fileEntry.getName());
	        	}
	        }
	    }
	    return candidates;
	}
}
