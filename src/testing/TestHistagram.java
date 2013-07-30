package testing;

import java.util.List;

import Core.Constants;
import Core.ProcessImage;
import Data_structure.Pixel;
import Data_structure.UVColorHistogram;

public class TestHistagram 
{
	public static String dir = Constants.dir;
	public static void main(String[] args)
	{
		UVColorHistogram h = new UVColorHistogram();
		try
		{
			ProcessImage img1 = new ProcessImage(dir + "/first.jpg");
			int[][][] yuv = img1.readImageToYUV();
			for(int i = 0; i < yuv.length; i++)
			{
				for(int j = 0; j < yuv[i].length; j++)
				{
					Integer[] color = {new Integer(yuv[i][j][1]), new Integer(yuv[i][j][2])};
					h.put(color);
				}
			}
			int counter = 0;
			List<Pixel> sorted = h.getSortedHitsFromHistogram().subList(0, 40);
			
			for(Pixel p : sorted)
			{
				int[] popular = p.getData();
				int[] yuv_color = {0, popular[0], popular[1]};
				ProcessImage popular_color = ProcessImage.createOneColorImage(ProcessImage.CREATE_FILE_COLOR_SPACE_YUV, yuv_color, 300, 300);
				popular_color.writeImage(dir + "/color" + (counter++) + ".jpg", ProcessImage.OUTFILE_TYPE_JPG);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
}
