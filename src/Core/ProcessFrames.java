package Core;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


/**
 * Class is implemented to do image processing on each frames.
 * @author allenliu
 *
 */
public class ProcessFrames {

	private ProcessImage prev;
	private ProcessImage curr;
	private List<String> files;
	private int counter;
	private int numFrames;
	private String folder;
	public ProcessFrames(String url) throws IOException
	{
		List<String> files = listFilesForFolder(new File(url));
		this.counter = 1;
		this.folder = url;
		this.prev = new ProcessImage(url + "/" + files.get(0));
		this.curr = new ProcessImage(url + "/" + files.get(1));
	}
	/**
	 * select the curr frame to be prev frame and next frame to be curr frame
	 */
	public void next() throws IOException
	{
		this.prev = this.curr;
		this.curr = new ProcessImage(this.folder + "/" + files.get(++counter));
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
