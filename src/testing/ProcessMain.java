package testing;

import utility.Constants;
import utility.ProcessFrames;
import utility.ProcessImage;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class ProcessMain {
	public static String dir = Constants.dir;
	public static void main(String[] args)
	{
		int intensity_counter = 0;
		int color_counter = 0;

		ProcessFrames p = new ProcessFrames(dir);
		for(ProcessImage img : p.getFrames())
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
			/*
			try
			{
				BufferedImage Y_img = img.getYUVImage("Y");
				ImageIO.write(Y_img, "jpg", new File(dir + "\\intensity" + (++intensity_counter) + ".jpg"));
				BufferedImage UV_img = img.getYUVImage("UV");			
				ImageIO.write(UV_img, "jpg", new File(dir + "\\color" + (++color_counter) + ".jpg"));

			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			*/

		}
		/*
		try{
			ProcessImage intensity = new ProcessImage(dir + "\\intensity1.jpg");
			ProcessImage color = new ProcessImage(dir + "\\color1.jpg");
			BufferedImage combined = intensity.restoreToNormal(color);
			ImageIO.write(combined, "jpg", new File(dir + "\\normal_first.jpg"));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		for(ProcessImage img : p.getFrames())
		{
			System.out.println("Type of image: " + getImageType(img.getImage().getType()));
		}
		*/
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
