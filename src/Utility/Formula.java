package Utility;

import java.awt.Color;
import Utility.Constants;

public class Formula {
	public static Color convertConsToColor(int color)
	{
		switch(color)
		{
		case Constants.COLOR_BLACK: 
			return new Color(0, 0, 0);
		case Constants.COLOR_RED:
			return new Color(255, 0, 0);
		default:
			return null;
		}
	}
}
