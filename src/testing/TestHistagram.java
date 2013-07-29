package testing;

import Core.Constants;
import Core.ProcessImage;
import Data_structure.UVColorHistogram;

public class TestHistagram 
{
	public static String dir = Constants.dir;
	public static void main(String[] args)
	{
		UVColorHistogram h = new UVColorHistogram();
		try
		{
			ProcessImage img1 = new ProcessImage(dir + "/frame1.jpg");
			int[][][] yuv = img1.readImageToYUV();
			for(int i = 0; i < yuv.length; i++)
			{
				for(int j = 0; j < yuv[i].length; j++)
				{
					Integer[] color = {new Integer(yuv[i][j][1]), new Integer(yuv[i][j][2])};
					h.put(color);
				}
			}
			h.printStat();
			int[] popular = h.getMostPopularElement();
			System.out.println("U : " + popular[0] + " V: " + popular[1]);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
}
