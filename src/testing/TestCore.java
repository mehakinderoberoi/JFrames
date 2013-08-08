package testing;


import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import Core.ProcessFrames;
import Core.ProcessImage;
import Data_structure.Rectangle;
import Utility.Constants;

public class TestCore {
	public static String dir = Constants.dir;
	public static void main(String[] args)
	{
		testProcessFrames();
	}
	private static void testProcessFrames()
	{
		try
		{
			ProcessFrames frames = new ProcessFrames(dir);
			List<ProcessImage> images = frames.getPrevCurrImages();
			ProcessImage prev = images.get(0);
			ProcessImage curr = images.get(1);
			List<Rectangle> l = new ArrayList<Rectangle>();
			l.add(new Rectangle(34, 110, 114, 198));
			prev.setTemplateRegions(l);
			frames.drawBestFitInPrevOnCurr();
			prev.writeImage(dir +"/frame_prev.jpg" , "jpg");
			curr.writeImage(dir + "/frame_curr.jpg",  "jpg");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	private static void testExtractRectangleImage()
	{
		try
		{
			ProcessImage img1 = new ProcessImage(dir+"/frame1.jpg");
			Rectangle rec = new Rectangle(200, 200, 250, 250);
			ProcessImage recImg = img1.getRectangleImage(rec);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	private static void testBlurRectangleOnImage()
	{
		try
		{
			ProcessImage img1 = new ProcessImage(dir+"/frame1.jpg");
			Rectangle rec = new Rectangle(200, 200, 250, 250);
			img1.blurRectOnImage(rec);
			img1.writeImage(dir + "/frame1_blur.jpg",  "jpg");

		}catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	private static void testStrokingRectangleOnImage()
	{
		try
		{
			ProcessImage img1 = new ProcessImage(dir+"/frame1.jpg");
			Rectangle rec = new Rectangle(200, 200, 250, 250);
			rec.setStrokeColor(Constants.COLOR_RED);
			img1.strokeRectOnImage(rec);
			img1.writeImage(dir + "/frame1_out.jpg",  "jpg");

		}catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	private static void testGetSimilarityBetweenImages()
	{
		try
		{
			ProcessImage img1 = new ProcessImage(dir+"/frame1.jpg");
			ProcessImage img2 = new ProcessImage(dir + "/frame1.jpg");
			System.out.println(img1.getCorrelationBetweenImages(img2));
			System.out.println(img1.getSimilarityBetweenImage(img2));

		}catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	private static void testYUVInfo()
	{
		try
		{
			ProcessImage img1 = new ProcessImage(dir + "/frame1.jpg");
			int[][][] yuv = img1.readImageToYUV();
			printImageDataArray(yuv);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	private static void testGetRGBInfo(ProcessImage img)
	{
		int[][][] rgb = img.getRGBData();
		int[] dimension = img.getDimention();
		for (int i = 0; i < dimension[0]; i++)
		{
			for (int j = 0; j < dimension[1]; j++)
			{
				System.out.println("R: " + rgb[i][j][0] + " G: " + rgb[i][j][1] + " B: " + rgb[i][j][2]);
			}
			System.out.println();
		}
	}
	private static void getImageType()
	{
		try
		{
			ProcessImage img = new ProcessImage(dir+"/frame1.jpg");
			System.out.println("Type of image: " + getImageType(img.getImage().getType()));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	private static void printImageDataArray(int[][][] data)
	{
		for(int i = 0; i < data.length; i++)
		{
			for(int j = 0; j < data[i].length; j++)
			{
				System.out.println("first: " + data[i][j][0] + " second: " + data[i][j][1] + " third: " + data[i][j][2]);
			}
		}
	}
	private static String getImageType(int type)
	{
		switch(type){
		case BufferedImage.TYPE_3BYTE_BGR: return "TYPE_3BYTE_BGR";
		case BufferedImage.TYPE_4BYTE_ABGR: return "4BYTE_ABGR";
		case BufferedImage.TYPE_4BYTE_ABGR_PRE: return "4BYTE_ABGR_PRE";
		default: return "Nothing so far";
		}
	}
}
