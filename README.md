JFrames version 0.1.0
---------------------------------------------------
Creator: Zihao Liu

Description
---------------------------------------------------

JFrames is an light weighted open source Java images/video processing library for developers who worked with frame by frame image processing with videos in java. Some of the techniques includes changing image from RGB color space to other color space such as YUV, motion tracking within video frames, correlation between images.

Requirements
---------------------------------------------------
- Java compiler compliance level: 1.6 & 1.7

Installation
---------------------------------------------------
Get the source and import it as Java library project in the eclipse

Usage
---------------------------------------------------
To process an individual image, first initialize ProcessImage class

    $  ProcessImage p = new ProcessImage(url);		//url is absolute (or relative) path for image

Or

    $  ProcessImage p = new ProcessImage(img);		//img is BufferedImage as specified by official java doc

To process an entire set of frames (note each image name must be of form "frame1.jpg" etc), use ProcessFrame class

	$  ProcessFrames p = new ProcessFrames(url);		//url is absolute (or relative) path for folder containing frames


Then you could do operations such as stroke rectangle on image, convert image to YUV color space, get only image with Y
component, or UV component, get correlation between two images, and output an image to a specified path. See http://cs.wisc.edu/~zihao/other_resources/JFrames/ java doc for API specification

License
---------------------------------------------------
JFrames is distributed under the MIT License. See the bundled LICENSE for more details