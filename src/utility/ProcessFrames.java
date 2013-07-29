package utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ProcessFrames {

	private List<ProcessImage> frames;
	public ProcessFrames(String url)
	{
		this.frames = new ArrayList<ProcessImage>();
		try
		{
			File folder = new File(url);
			List<String> candidates = listFilesForFolder(folder);
			for (String name : candidates)
			{
				ProcessImage img = new ProcessImage(url + "/" + name);
				this.frames.add(img);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	public List<ProcessImage> getFrames()
	{
		return this.frames;
	}
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
