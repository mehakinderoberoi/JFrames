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
	
	/**
	 * Given a set of template region in each ProcessImage, move the rectangle box a bit 25px around to determine the best
	 * fit with the previous template in prev image
	 * @param region
	 */
	public void drawBestFitInPrev()
	{
		List<Rectangle> prevRegions = this.prev.getTemplateRegions();
		Random rand = new Random();
		for(Rectangle rec : prevRegions)
		{
			Rectangle bestFit = rec;
			int numTrials = rand.nextInt(4) + 2;
			for(int i = 0; i < numTrials; i++)
			{
				int offset = rand.nextInt(11) - 5;
				int x1 = rec.getUpperLeftX();
				int y1 = rec.getUpperLeftY();
				int x2 = rec.getLowerRightX();
				int y2 = rec.getLowerRightY();
				if(x1 + offset < 0)
				{
					x1 = 0;
				}
				else if (x1 + offset > this.curr.getDimention()[0] - 1)
				{
					x1 = this.curr.getDimention()[0] - 1;
				}
				if(y1 + offset < 0)
				{
					y1 = 0;
				}
				else if(y1 + offset > this.curr.getDimention()[1] - 1)
				{
					y1 = this.curr.getDimention()[1] - 1;
				}
				if(x2 + offset < 0)
				{
					x2 = 0;
				}
				else if (x2 + offset > this.curr.getDimention()[0] - 1)
				{
					x2 = this.curr.getDimention()[0] - 1;
				}
				if(y2 + offset < 0)
				{
					y2 = 0;
				}
				else if(y2 + offset > this.curr.getDimention()[1] - 1)
				{
					y2 = this.curr.getDimention()[1] - 1;
				}	
				Rectangle newRegion = new Rectangle(x1, y1, x2, y2);
			}
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
