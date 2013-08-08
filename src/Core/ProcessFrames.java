package Core;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import Utility.Constants;

import Data_structure.Rectangle;
import Data_structure.UVColorHistogram;


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
	
	/**
	 * check if we have next frame available
	 * @return true if we have
	 */
	public boolean hasNext()
	{
		return (counter < this.frames.size() - 1) ? true : false;
	}
	
	/**
	 * return the prev frame and curr frame
	 * @return
	 */
	public List<ProcessImage> getPrevCurrImages()
	{
		List<ProcessImage> li = new ArrayList<ProcessImage>();
		li.add(this.prev);
		li.add(this.curr);
		return li;
	}
	
	/**
	 * Methods for outputing the current set of images
	 * @param url
	 * @throws IOException
	 */
	public void outputCurrImg(String url) throws IOException
	{
		this.curr.writeImage(url, "jpg");
	}
	
	public void outputPrevImg(String url) throws IOException
	{
		this.prev.writeImage(url, "jpg");
	}
	
	/**
	 * Given a set of template region in each ProcessImage, move the rectangle box a bit 25px around to determine the best
	 * fit with the previous template in prev image
	 * 
	 * First compute the correlation between the intensity of two bounding box. Then use the correlation of UV color histogram
	 * between two images to verify that this is true.
	 * 
	 * @param region
	 */
	public void drawBestFitInPrevOnCurr()
	{
		List<Rectangle> prevRegions = this.prev.getTemplateRegions();
		List<Rectangle> returnedRectangle = new ArrayList<Rectangle>();
		Random rand = new Random();
		for(Rectangle rec : prevRegions)
		{
			ProcessImage bestFit = null;
			Rectangle bestFitRec = rec;
			ProcessImage prev = this.prev.getRectangleImage(rec);
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
				Rectangle new_rec = new Rectangle(new_x1, new_y1, new_x2, new_y2, Constants.COLOR_RED);
				ProcessImage curr = this.curr.getRectangleImage(new_rec);
				double correlation = curr.getCorrelationBetweenImages(prev);
				if(correlation > highestCorrelation)
				{
					//System.out.println("Best correlation: " + correlation + " ");
					//System.out.println("Best rectangle" + new_rec);
					highestCorrelation = correlation;
					bestFit = curr;
					bestFitRec = new_rec;
				}
			}
			int[][][] prev_yuv = prev.readImageToYUV();
			int[][][] best_yuv = bestFit.readImageToYUV();
			UVColorHistogram prev_h = new UVColorHistogram();
			UVColorHistogram curr_h = new UVColorHistogram();
			for(int i = 0; i < prev_yuv.length; i++)
			{
				for(int j = 0; j < prev_yuv[i].length; j++)
				{
					Integer[] curr_uv = {prev_yuv[i][j][1], prev_yuv[i][j][2]};
					Integer[] prev_uv = {best_yuv[i][j][1], best_yuv[i][j][2]};
					prev_h.put(prev_uv);
					curr_h.put(curr_uv);
				}
			}
			double correlation =  prev_h.getCorrelation(curr_h);
			System.out.println("Histogram correlation: " + correlation);
			if(correlation > 0.70)
			{
				this.curr.strokeRectOnImage(bestFitRec);
				returnedRectangle.add(bestFitRec);
			}
		}
		this.curr.setTemplateRegions(returnedRectangle);
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
	    List<Integer> fileNumbers = new ArrayList<Integer>();
	    //reorder the image files
	    for(String name : candidates)
	    {
	    	fileNumbers.add(Integer.parseInt(name.substring(5, name.lastIndexOf("."))));
	    }
	    Collections.sort(fileNumbers);
	    List<String> result = new ArrayList<String>();
	    for(Integer i : fileNumbers)
	    {
	    	result.add("frame" + i + ".jpg");
	    }
	    return result;
	}
}
