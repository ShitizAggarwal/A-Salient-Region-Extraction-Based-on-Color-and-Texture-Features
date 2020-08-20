/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SAYA;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.*;
import java.util.Arrays;
import java.util.Vector;


public class ImageSegmentation {

   //Histogram development variables limit 
	private final static float GAUSSIAN_CUT_OFF = 0.005f;
	private final static float MAGNITUDE_SCALE = 100F;
	private final static float MAGNITUDE_LIMIT = 1000F;
	private final static int MAGNITUDE_MAX = (int) (MAGNITUDE_SCALE * MAGNITUDE_LIMIT);

	
	//image related variables
	private int height;
	private int width;
	private int picsize;
	private int[] data;
	private int[] magnitude;
	private int[] pix;
	private int []segment;
	private BufferedImage sourceImage;
	private BufferedImage edgesImage;
	private BufferedImage colorEdgesImg;
	//segmentation variables
	private float gaussianKernelRadius;
	private float lowThreshold;
	private float highThreshold;
	private int gaussianKernelWidth;
	private boolean contrastNormalized;
	//segmentation variables
	private float[] xConv;
	private float[] yConv;
	private float[] xGradient;
	private float[] yGradient;
	private String fileName=null;
	String fname;
	public int numSegmentAfterMerge;
	//String[] segmentFileName ; 
	
	Vector className;
	File classFileNames[];
	
	public int totalClrfile;
	public ImageSegmentation() {
		lowThreshold = 2.5f;
		highThreshold = 7.5f;
		gaussianKernelRadius = 2f;
		gaussianKernelWidth = 16;
		contrastNormalized = false;//true;
	}

	//to get the image source
	public BufferedImage getSourceImage() {
		return sourceImage;
	}
	
	//set image source
	public void setSourceImage(BufferedImage image) {
		sourceImage = image;
		colorEdgesImg=image;
	}

	
	public BufferedImage getEdgesImage() {
		return edgesImage;
	}
 
	public BufferedImage getColorEdgesImage() {
		return colorEdgesImg;
	}
 
	
	public void setEdgesImage(BufferedImage edgesImage) {
		this.edgesImage = edgesImage;
	}

	

	
	public float getLowThreshold() {
		return lowThreshold;
	}
	
	
	public void setLowThreshold(float threshold) {
		if (threshold < 0) throw new IllegalArgumentException();
		lowThreshold = threshold;
	}
 
	
	public float getHighThreshold() {
		return highThreshold;
	}
	
	
	public void setHighThreshold(float threshold) {
		if (threshold < 0) throw new IllegalArgumentException();
		highThreshold = threshold;
	}

	
	public int getGaussianKernelWidth() {
		return gaussianKernelWidth;
	}
	
	
	public void setGaussianKernelWidth(int gaussianKernelWidth) {
		if (gaussianKernelWidth < 2) throw new IllegalArgumentException();
		this.gaussianKernelWidth = gaussianKernelWidth;
	}

	
	public float getGaussianKernelRadius() {
		return gaussianKernelRadius;
	}
	
	
	public void setGaussianKernelRadius(float gaussianKernelRadius) {
		if (gaussianKernelRadius < 0.1f) throw new IllegalArgumentException();
		this.gaussianKernelRadius = gaussianKernelRadius;
	}
	
	
	public boolean isContrastNormalized() {
		return contrastNormalized;
	}
	
	
	public void setContrastNormalized(boolean contrastNormalized) {
		this.contrastNormalized = contrastNormalized;
	}
	//fetching image values needed for classification
	public void process() {
		width = sourceImage.getWidth();
		height = sourceImage.getHeight();
		picsize = width * height;
		initArrays();
		readLuminance();
		if (contrastNormalized) normalizeContrast();
		computeGradients(gaussianKernelRadius, gaussianKernelWidth);
		int low = Math.round(lowThreshold * MAGNITUDE_SCALE);
		int high = Math.round( highThreshold * MAGNITUDE_SCALE);
		performHysteresis(low, high);
		thresholdEdges();
		writeEdges(data);
	}
 
	//initiating arrays needed for further use
	private void initArrays() {
		if (data == null || picsize != data.length) {
			data = new int[picsize];
			magnitude = new int[picsize];

			xConv = new float[picsize];
			yConv = new float[picsize];
			xGradient = new float[picsize];
			yGradient = new float[picsize];
		}
	}
	
		
	private void computeGradients(float kernelRadius, int kernelWidth) {
		
		//generate the gaussian convolution 
		float kernel[] = new float[kernelWidth];
		float diffKernel[] = new float[kernelWidth];
		int kwidth;
		for (kwidth = 0; kwidth < kernelWidth; kwidth++) {
			float g1 = gaussian(kwidth, kernelRadius);
			if (g1 <= GAUSSIAN_CUT_OFF && kwidth >= 2) break;
			float g2 = gaussian(kwidth - 0.5f, kernelRadius);
			float g3 = gaussian(kwidth + 0.5f, kernelRadius);
			kernel[kwidth] = (g1 + g2 + g3) / 3f / (2f * (float) Math.PI * kernelRadius * kernelRadius);
			diffKernel[kwidth] = g3 - g2;
		}

		int initX = kwidth - 1;
		int maxX = width - (kwidth - 1);
		int initY = width * (kwidth - 1);
		int maxY = width * (height - (kwidth - 1));
		
		//perform convolution in x and y directions
		for (int x = initX; x < maxX; x++) {
			for (int y = initY; y < maxY; y += width) {
				int index = x + y;
				float sumX = data[index] * kernel[0];
				float sumY = sumX;
				int xOffset = 1;
				int yOffset = width;
				for(; xOffset < kwidth ;) {
					sumY += kernel[xOffset] * (data[index - yOffset] + data[index + yOffset]);
					sumX += kernel[xOffset] * (data[index - xOffset] + data[index + xOffset]);
					yOffset += width;
					xOffset++;
				}
				
				yConv[index] = sumY;
				xConv[index] = sumX;
			}
 
		}
 
		for (int x = initX; x < maxX; x++) {
			for (int y = initY; y < maxY; y += width) {
				float sum = 0f;
				int index = x + y;
				for (int i = 1; i < kwidth; i++)
					sum += diffKernel[i] * (yConv[index - i] - yConv[index + i]);
 
				xGradient[index] = sum;
			}
 
		}

		for (int x = kwidth; x < width - kwidth; x++) {
			for (int y = initY; y < maxY; y += width) {
				float sum = 0.0f;
				int index = x + y;
				int yOffset = width;
				for (int i = 1; i < kwidth; i++) {
					sum += diffKernel[i] * (xConv[index - yOffset] - xConv[index + yOffset]);
					yOffset += width;
				}
 
				yGradient[index] = sum;
			}
 
		}
 
		initX = kwidth;
		maxX = width - kwidth;
		initY = width * kwidth;
		maxY = width * (height - kwidth);
		for (int x = initX; x < maxX; x++) {
			for (int y = initY; y < maxY; y += width) {
				int index = x + y;
				int indexN = index - width;
				int indexS = index + width;
				int indexW = index - 1;
				int indexE = index + 1;
				int indexNW = indexN - 1;
				int indexNE = indexN + 1;
				int indexSW = indexS - 1;
				int indexSE = indexS + 1;
				
				float xGrad = xGradient[index];
				float yGrad = yGradient[index];
				float gradMag = hypot(xGrad, yGrad);

				//perform non-maximal supression
				float nMag = hypot(xGradient[indexN], yGradient[indexN]);
				float sMag = hypot(xGradient[indexS], yGradient[indexS]);
				float wMag = hypot(xGradient[indexW], yGradient[indexW]);
				float eMag = hypot(xGradient[indexE], yGradient[indexE]);
				float neMag = hypot(xGradient[indexNE], yGradient[indexNE]);
				float seMag = hypot(xGradient[indexSE], yGradient[indexSE]);
				float swMag = hypot(xGradient[indexSW], yGradient[indexSW]);
				float nwMag = hypot(xGradient[indexNW], yGradient[indexNW]);
				float tmp;
				
				if (xGrad * yGrad <= (float) 0 /*(1)*/
					? Math.abs(xGrad) >= Math.abs(yGrad) /*(2)*/
						? (tmp = Math.abs(xGrad * gradMag)) >= Math.abs(yGrad * neMag - (xGrad + yGrad) * eMag) /*(3)*/
							&& tmp > Math.abs(yGrad * swMag - (xGrad + yGrad) * wMag) /*(4)*/
						: (tmp = Math.abs(yGrad * gradMag)) >= Math.abs(xGrad * neMag - (yGrad + xGrad) * nMag) /*(3)*/
							&& tmp > Math.abs(xGrad * swMag - (yGrad + xGrad) * sMag) /*(4)*/
					: Math.abs(xGrad) >= Math.abs(yGrad) /*(2)*/
						? (tmp = Math.abs(xGrad * gradMag)) >= Math.abs(yGrad * seMag + (xGrad - yGrad) * eMag) /*(3)*/
							&& tmp > Math.abs(yGrad * nwMag + (xGrad - yGrad) * wMag) /*(4)*/
						: (tmp = Math.abs(yGrad * gradMag)) >= Math.abs(xGrad * seMag + (yGrad - xGrad) * sMag) /*(3)*/
							&& tmp > Math.abs(xGrad * nwMag + (yGrad - xGrad) * nMag) /*(4)*/
					) {
					magnitude[index] = gradMag >= MAGNITUDE_LIMIT ? MAGNITUDE_MAX : (int) (MAGNITUDE_SCALE * gradMag);
					
				} else {
					magnitude[index] = 0;
				}
			}
		}
	}
 
	private float hypot(float x, float y) {
		return (float) Math.hypot(x, y);
	}
 
	private float gaussian(float x, float sigma) {
		return (float) Math.exp(-(x * x) / (2f * sigma * sigma));
	}
 
	private void performHysteresis(int low, int high) {
		Arrays.fill(data, 0);
 
		int offset = 0;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (data[offset] == 0 && magnitude[offset] >= high) {
					follow(x, y, offset, low);
				}
				offset++;
			}
		}
 	}
 
	private void follow(int x1, int y1, int i1, int threshold) {
		int x0 = x1 == 0 ? x1 : x1 - 1;
		int x2 = x1 == width - 1 ? x1 : x1 + 1;
		int y0 = y1 == 0 ? y1 : y1 - 1;
		int y2 = y1 == height -1 ? y1 : y1 + 1;
		
		data[i1] = magnitude[i1];
		for (int x = x0; x <= x2; x++) {
			for (int y = y0; y <= y2; y++) {
				int i2 = x + y * width;
				if ((y != y1 || x != x1)
					&& data[i2] == 0 
					&& magnitude[i2] >= threshold) {
					follow(x, y, i2, threshold);
					return;
				}
			}
		}
	}

	private void thresholdEdges() {
		for (int i = 0; i < picsize; i++) {
			data[i] = data[i] > 0 ? -1 : 0xff000000;//0xffffffff;
		}
	}
	
	private int luminance(float r, float g, float b) {
		//System.out.print(" : "+Math.round(0.299f * r + 0.587f * g + 0.114f * b)+" : ");
		return Math.round(0.2998f * r + 0.587f * g + 0.114f * b);
	}
	
	private void readLuminance() {
		int type = sourceImage.getType();
		if (type == BufferedImage.TYPE_INT_RGB || type == BufferedImage.TYPE_INT_ARGB) {
			int[] pixels = (int[]) sourceImage.getData().getDataElements(0, 0, width, height, null);
			for (int i = 0; i < picsize; i++) {
				int p = pixels[i];
				int r = (p & 0xff0000) >> 16;
				int g = (p & 0xff00) >> 8;
				int b = p & 0xff;
				data[i] = luminance(r, g, b);
				//System.out.println(" RGB ");
			}
		} else if (type == BufferedImage.TYPE_BYTE_GRAY) {
			byte[] pixels = (byte[]) sourceImage.getData().getDataElements(0, 0, width, height, null);
			for (int i = 0; i < picsize; i++) {
				data[i] = (pixels[i] & 0xff);
				//System.out.println(" Gray ");
			}
		} else if (type == BufferedImage.TYPE_USHORT_GRAY) {
			short[] pixels = (short[]) sourceImage.getData().getDataElements(0, 0, width, height, null);
			for (int i = 0; i < picsize; i++) {
				data[i] = (pixels[i] & 0xffff) / 256;
				//System.out.println(" UGray ");
			}
		} else if (type == BufferedImage.TYPE_3BYTE_BGR) {
            byte[] pixels = (byte[]) sourceImage.getData().getDataElements(0, 0, width, height, null);
            int offset = 0;
            //System.out.println(pixels.length +"      "+picsize);
            for (int i = 0; i < picsize; i++) {
            	//System.out.println( pixels[offset] );
            	int b = pixels[offset++] & 0xff;//0xff;
            	//System.out.print( "  "+pixels[offset] );
            	int g = pixels[offset++] & 0xff;//0xff;
            	//System.out.print( "   "+pixels[offset] );
                int r = pixels[offset++] & 0xff;//0xff;
                //System.out.print( " b "+b +"  g  "+ g  +" r  "+r+ "   ");
                data[i] = luminance(r, g, b);
            }
            //System.out.println("\n"+pixels.length +"      "+picsize);
        } else {
			throw new IllegalArgumentException("Unsupported image type: " + type);
		}
	}
 
	private void normalizeContrast() {
		int[] histogram = new int[256];
		for (int i = 0; i < data.length; i++) {
			histogram[data[i]]++;
		}
		int[] remap = new int[256];
		int sum = 0;
		int j = 0;
		for (int i = 0; i < histogram.length; i++) {
			sum += histogram[i];
			int target = sum*255/picsize;
			for (int k = j+1; k <=target; k++) {
				remap[k] = i;
			}
			j = target;
		}
		
		for (int i = 0; i < data.length; i++) {
			data[i] = remap[data[i]];
		}
	}
	
	private void writeEdges(int pixels[]) {
		
		if (edgesImage == null) {
			edgesImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		}
		edgesImage.getWritableTile(0, 0).setDataElements(0, 0, width, height, pixels);
	//Point  []p =edgesImage.getWritableTileIndices();
	
		//System.out.println( p.length );
		/*
		pix = new int[picsize];
		int p=0;
		for (int y = 0; y < height; y++) 
		{
 			for (int x = 0; x < width; x++) 
 			{
 				//System.out.println( edgesImage.getRGB(x, y)& 0xff);
 				if( (edgesImage.getRGB(x, y)& 0xff) == 0)
 				{
 					//colorEdgesImg.
 					colorEdgesImg.setRGB(x, y, 0x005f77);//luminance(255,255,255));0x6691b3  0x00b8e6
 							
 				}
 				else 
 					colorEdgesImg.setRGB(x, y, 0xc0c0c0);//luminance(255,0,0));
 			}
 		}
		//colorEdgesImg.getWritableTile(0, 0).setDataElements(0, 0, width, height, pix);
		*/
	
	}
        
        
        	public int segmentation(String fileN, File file)
	{
		//edgesImage.createGraphics();
		//sourceImage.copyData(arg0);
		int numSegment=1;
		int minPixPerSegment=300;
		 pix = (int[]) edgesImage.getData().getDataElements(0, 0, width, height, null);
		 segment=new int[pix.length];
		 Arrays.fill(segment, 0);
		 
		 
		 //System.out.println(pix.length);
		 for(int i=0;i<pix.length;i++)
		 {
			 if((pix[i] & 0xff) != 0)
			 {
				 if(segment[i]==0) 
					 {
					 segment[i]=numSegment++;
					 findConnectedComponent(i);
					 }
			 }
			 
		 }
		 
		 
		 
		 
		 
		 //System.out.println("Num segment "+numSegment+"  \n");
		 int count=0;
		 int imgPix[]=new int[pix.length];
		 int []countPixelPerSegment = new int[numSegment];
		 int rd[] , ld[] , ud[] , dd[]; //right left up and down direction
		 int numSegWithThresh=0;
		 Arrays.fill(imgPix, 0);
		 for(int i=1;i<numSegment;i++)
		 {
			 count=0;
			 for(int j=0;j<pix.length;j++)
			 {
				 if(segment[j]==i) count++;
			 }
			 countPixelPerSegment[i] = count;
			 if(count >= minPixPerSegment)
			 {	 numSegWithThresh++;
			 //System.out.println(i+"  "+count);
			 }
		 }
		 
			 
		  numSegmentAfterMerge=numSegWithThresh;
		 for(int i=1;i<numSegment;i++)
		{
			 if(countPixelPerSegment[i]>=minPixPerSegment)
			 {
			 rd = new int[countPixelPerSegment[i]];
			 ld = new int[countPixelPerSegment[i]];
			 ud = new int[countPixelPerSegment[i]];
			 dd = new int[countPixelPerSegment[i]];
			 Arrays.fill(rd,0);
			 Arrays.fill(ld,0);
			 Arrays.fill(dd,0);
			 Arrays.fill(ud,0);
			 int offset=0;
			 for(int j=16*width;j<pix.length;j++)
		 {
			 if(segment[j]==i )
			 {
					 
				 for(int a=j+1;a<(((int)j/width)+1)*width;a++)
				 {
					 if((pix[a] & 0xff) != 0 && segment[a] != i)
						 {
						 rd[offset]= segment[a];
						 break;
						 }
					 
				 }
				 for(int a=j-1;a>((int)j/width)*width;a--)
				 {
					 if((pix[a] & 0xff) != 0 && segment[a] != i)
					 {
						 ld[offset]= segment[a];
						 break;
					 }
				 }
				 for(int a=j+width;a<pix.length;a=a+width)
				 {
					 if((pix[a] & 0xff) != 0 && segment[a] != i)
					 {
						 dd[offset]= segment[a];
						 break;
					 }
				 }
				 for(int a=j-width;a>16*width;a=a-width)
				 {
					 if((pix[a] & 0xff) != 0 && segment[a] != i)
					 {
						 ud[offset]= segment[a];
						 break;
					 }
				 }
			 if(rd[offset]!= 0 && ld[offset]!= 0 && dd[offset]!= 0 && ud[offset]!= 0)
			 {
				 if(rd[offset]==ld[offset] && rd[offset]==dd[offset] && rd[offset]==ud[offset] && rd[offset] != i)
				 {
					 segment[j]=rd[offset];
					 for(int p=16*width;p<pix.length;p++)
						 if(segment[p]==i)
							 segment[p]=rd[offset];
					 numSegmentAfterMerge--;	 
					 break;
				 }
				 else break;
			 }
			 offset++;
		    }
		 }
			 }
		 }
		 Vector segmentIndex=new Vector();;
		 
		 for(int i=16*width;i<pix.length;i++)
		 {
			 if(countPixelPerSegment[segment[i]]>=minPixPerSegment)
			 {
			 if(segmentIndex.indexOf(segment[i])<0)
			 {
				 segmentIndex.add(segment[i]);
			 }
			 } 
				  
		 }	 
		 //System.out.println("Segment ind "+segmentIndex);
		 int k=0;
		 
		 //System.out.println("  "+numSegWithThresh+" "+numSegmentAfterMerge);
		
		 BufferedImage []cei= new BufferedImage[numSegWithThresh];
		 
		 //for(int i=0;i<numSegmentAfterMerge;i++)
			// cei[i]=new BufferedImage(0, 0, arg2);
		 
		 int imgOffset=1;
		 for(int m=0;m<numSegment;m++)
		 {
			if(segmentIndex.indexOf(m)>=0)
			{
			 Arrays.fill(imgPix, luminance(255,255,255));
			 boolean flag[] = new boolean[pix.length];
			 //if(countPixelPerSegment[m]>=minPixPerSegment)
		   // {
			 //System.out.println(" "+i+"  "+count );
			
			   for(int j=0;j<pix.length;j++)
			  {
				 if(segment[j] == m) 
				 {  imgPix[j]=-1;
				 flag[j]=true;
				 }
				 else
					 imgPix[j]=0xff000000;
				 
			 }
			  //for(int ll=0;ll<pix.length;ll++)
				//  System.out.println(im1pix[ll]+" "+flag[ll] );
			   
			   BufferedImage im1=new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
				 im1.getWritableTile(0, 0).setDataElements(0, 0, width, height, imgPix);
				 k++;
				 File f1= new File(fileN+"Segmnt"+k+".jpg");
					try {
						
						ImageIO.write(im1, "jpg" , f1);
					  } catch (IOException e) { //System.out.println(e.toString());
					  }
					
		              
					  
					  //
							 //if(countPixelPerSegment[m]>=minPixPerSegment)
							 //{
							
					         rd = new int[pix.length];
							 ld = new int[pix.length];
							 ud = new int[pix.length];
							 dd = new int[pix.length];
							 Arrays.fill(rd,0);
							 Arrays.fill(ld,0);
							 Arrays.fill(dd,0);
							 Arrays.fill(ud,0);
							 int offset=0;
							 for(int j=16*width;j<pix.length-16*width;j++)
						 {
							 //if(segment[j] == m )
							 //{
									 
							 for(int a=j+1;a<(((int)j/width)+1)*width;a++)
				              {
					            if( (pix[a] & 0xff) != 0 || (pix[a+width] & 0xff) != 0 || (pix[a-width] & 0xff) != 0)
						         {
					            	  if(segment[a]!=0) rd[offset]= segment[a];
					            	  else if (segment[a+width]!=0) rd[offset]= segment[a+width];
					            	  else rd[offset]= segment[a-width];
						               break;
						           }
					  		 }
				 			for(int a=j-1;a>((int)j/width)*width;a--)
				 			{
					 			if((pix[a] & 0xff) != 0 || (pix[a+width] & 0xff)!= 0 || (pix[a-width] & 0xff) != 0)
					 			{
						 		  if(segment[a]!=0) ld[offset]= segment[a];
					              else if (segment[a+width]!=0) ld[offset]= segment[a+width];
					              else ld[offset]= segment[a-width];
					 			 //ld[offset]= segment[a];
						 		 break;
					 			}
				 			}
				 			for(int a=j+width;a<pix.length-16*width;a=a+width)
				 			{	
					 		if((pix[a] & 0xff) != 0 || (pix[a+1] & 0xff) != 0 || (pix[a-1] & 0xff) != 0)
					 		{
					 			if(segment[a]!=0) dd[offset]= segment[a];
				            	  else if (segment[a+1]!=0) dd[offset]= segment[a+1];
				            	  else dd[offset]= segment[a-1];
					 			//dd[offset]= segment[a];
						 		break;
					 		}
				 		  }
				 				for(int a=j-width;a>16*width;a=a-width)
				 			{
					 			if((pix[a] & 0xff) != 0 || (pix[a+1] & 0xff) != 0 || (pix[a-1] & 0xff)!= 0)
					 		{
					 			if(segment[a]!=0) ud[offset]= segment[a];
					           	  else if (segment[a+1]!=0) ud[offset]= segment[a+1];
					           	  else ud[offset]= segment[a-1];
					 			//ud[offset]= segment[a];
						 		break;
					            }
				              }
							
							 if(rd[offset]!= 0 && ld[offset]!= 0 && dd[offset]!= 0 && ud[offset]!= 0)
							 {
								 if(rd[offset]== m && ld[offset]== m && dd[offset]== m && dd[offset] == m )
								 {
								  flag[j]=true;segment[j]=m;
								 }
								 
							 }
							 offset++;
						    
						   
						 
						} 
							 
							 
							 
							 BufferedImage img = null;
							  try {
							      img = ImageIO.read(new File(file.getAbsolutePath()));
							  } catch (IOException e) {
							  }
							  cei[imgOffset-1]=img;
					  int c=0;
					  int histo[] = new int[256] ;
					  int totalpix=0;// nimber of pixel per image for bhattacharya coef normalization
					  for(int i = 0; i < 256; i++)
					  {
						  histo[i]=0;
					  }
					  for (int y = 0; y < height; y++) 
					  {
						for (int x = 0; x < width; x++) 
						{
							if(flag[c++] != true)
							{
								cei[imgOffset-1].setRGB(x, y, -1);
							}
							else
								{
								totalpix++;
								//System.out.println(" : ");
								int p= cei[imgOffset-1].getRGB(x, y);
								int r = (p & 0xff0000) >> 16;
								int g = (p & 0xff00) >> 8;
								int b = p & 0xff;
								int pixv = luminance(r, g, b);
								
								histo[pixv]=histo[pixv]+1;
								
								}
							
						}
					  }
					  BufferedWriter histog;
					  fileName=fileN;
					  File fhisto= new File(fileN+"Histo"+imgOffset+".txt");
					  try{
						  
					  histog = new BufferedWriter (new FileWriter(fileN+"Histo"+imgOffset+".txt"));
					  int sum=0;
					  for(int i = 0; i < 256; i++)
						  sum+=histo[i];
					
					  //System.out.println(" total "+totalpix+" sum "+ sum);
					  for(int i = 0; i < 256; i++)
						  {
						 // System.out.print(" :h  "+histo[i]+" : ");
						     histog.write(i+":");
						     double j=(double)histo[i]/totalpix;
						     Double.toString(j);
						     //System.out.print(" :j  "++" : ");
						     histog.write(Double.toString(j));
							 histog.write(" "); 
						  }
					  histog.close();
					  }
					  
					  catch (IOException ex){
				  	      ex.printStackTrace();
				  	    }
					  
					  File f2= new File(fileN+"Clr"+imgOffset+".jpg");
						try {
							
							ImageIO.write(cei[imgOffset-1], "jpg" , f2);
						  } catch (IOException e) { //System.out.println(e.toString());
						  }
					  imgOffset++;
					 
			 //}
		  }
		 }
		 //System.out.println("total "+ imgOffset);
		 
		 //totalClrfile=imgOffset-1; 
		 BufferedImage ceib;
		 BufferedImage img = null;
		  try {
		      img = ImageIO.read(new File(file.getAbsolutePath()));
		  } catch (IOException e) {
		  }
		  ceib=img;
		  int c=0;
		  for (int y = 0; y < height; y++) 
		  {
			for (int x = 0; x < width; x++) 
			{
				if(segment[c++] != 0)
				{
					ceib.setRGB(x, y, -1);
				}
			}
		  }
		  
		  File f2= new File(fileN+"ClrBG"+".jpg");
			try {
				
				ImageIO.write(ceib, "jpg" , f2);
			  } catch (IOException e) { //System.out.println(e.toString());
			  }
		  
		 
		/* for(int j=0;j<pix.length;j++)
		 {
			 if(segment[j]==39) im1pix[j]=0xffffff;
			 
		 }
		 //*/
		/* BufferedImage im1=new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		 im1.getWritableTile(0, 0).setDataElements(0, 0, width, height, im1pix);
		 File f1= new File("im1.jpg");
			try {
				
				ImageIO.write(im1, "jpg" , f1);
			  } catch (IOException e) { System.out.println(e.toString());
			  }
        //System.out.println(pixels.length +"      "+picsize);
        */ 
         
         
		return imgOffset; 
		
	}
	

                public int findConnectedComponent(int index)
	{
		int numNbr=7;
		int[][]nbr= new int[numNbr][8];   // neighbouring pixel
		for(int i=0;i<numNbr;i++)
		{
		for(int j=0;j<8;j++)
		{
		nbr[i][0] = index - width*(i+1);
		nbr[i][1] = index + width*(i+1);
		nbr[i][2] = index - (i+1);
		nbr[i][3] = index + (i+1);
		nbr[i][4] = nbr[i][0] - (i+1);
		nbr[i][5] = nbr[i][0] + (i+1);
		nbr[i][6] = nbr[i][1] - (i+1);
		nbr[i][7] = nbr[i][1] + (i+1);
		}
		}
		//for zigzag pattern
		int [] z = new int[8];
		 z[0]= nbr[0][4] - width;     // nbr pixels of  NW
		 z[1]= nbr[0][4] - 1; 
		 z[2]= nbr[0][5] - width;     // nbr pixels of  NE
		 z[3]= nbr[0][5] + 1; 
		 z[4]= nbr[0][6] - 1;     // nbr pixels of  SW
		 z[5]= nbr[0][6] + width; 
		 z[6]= nbr[0][7] + 1;     // nbr pixels of  SE
		 z[7]= nbr[0][7] + width; 
		 
		
		 
		
		for(int i=0;i<numNbr;i++)
		{
		for(int j=0;j<8;j++)
		{
			if((pix[nbr[i][j]] & 0xff) != 0 && segment[nbr[i][j]] == 0)
			{
				segment[nbr[i][j]]=segment[index];
				findConnectedComponent(nbr[i][j]);
			}
		}
		}
		for(int i=0;i<8;i++)
		{
			if((pix[z[i]] & 0xff) != 0 && segment[z[i]] == 0)
			{
				segment[z[i]]=segment[index];
				findConnectedComponent(z[i]);
			}
		}
		
		return 0;
	}
	
	/// learning 
	
	public void createClass(String clsName , String fName, int flag)
	{
		if(flag == 0 )
		try
		{
			//File clsNameF = new File(clsName);
			BufferedReader source =  new BufferedReader(new FileReader(fName));
			BufferedWriter clsNameF = new BufferedWriter (new FileWriter("ClassName/"+clsName+".txt"));
			String iLine=source.readLine();
			clsNameF.write(iLine);
			source.close();
			clsNameF.close();
			BufferedWriter clsInfoF = new BufferedWriter (new
					FileWriter("ClassInfo/"+clsName+"Info"+".txt"));
					clsInfoF.write("0 1 1 0");
					clsInfoF.close();

		}
		catch(IOException e){//System.out.println(e.getMessage());
		}
	

		
		else if(flag == 1)
		{
			try{
			BufferedReader newHF =  new BufferedReader(new FileReader(fName)); // new Histogram file
			BufferedReader clsNameF = new BufferedReader(new FileReader("ClassName/"+clsName+".txt"));
			String iLineN=newHF.readLine(); // new file 
			String iLineO=clsNameF.readLine(); // class name file or old file
			//System.out.println(" line 910 "+iLineN);
			//System.out.println(" line 911 "+iLineO);
			//iLine=source.readLine();
	         String[] siLine=iLineN.split("\\s");
	         //iLine=corpus.readLine();
	         String[] ciLine=iLineO.split("\\s");
	         //System.out.println("hiiiiiii"+siLinegth);
	         
	         String intN[],intO[];
	         double nint[] = new double[256];
	 		double oint[] = new double[256];
	 		double Pi = 0.95; // probability of old intensity value 
	 		double rValue; // resulting intensity value 
	 		String rLine=""; // resulting line 
	         for(int k=0; k<256;k++)
	         {
	        	 intN=siLine[k].split(":");
	        	 intO=ciLine[k].split(":");
	        	 nint[k]=Double.parseDouble(intN[1]);
	        	 oint[k]=Double.parseDouble(intO[1]);
	        	 rValue = Pi * oint[k] + (1-Pi) * nint[k]; 
	        	 if(k == 0)
	        	 rLine = k+":"+rValue;
	        	 else
	        		 rLine = rLine+" "+k+":"+rValue;
	        	 
	         }
	         newHF.close();
				clsNameF.close();
				BufferedWriter clsNameWF = new BufferedWriter(new FileWriter("ClassName/"+clsName+".txt"));
				// System.out.println("line 910 "+rLine);
				clsNameWF.write(rLine);
				//source.close();
				clsNameWF.close();
			
			//clsNameF.write(iLine);
			
			
			}catch(IOException e){//System.out.println(e.getMessage());
			}
		}
	}
	
        
        // Classify image 
	
	public int findSegmentClass(String sourceFName)
	{ 
		double sint[] = new double[256];
		double cint[] = new double[256];
		double nsum = 0;// normalised sum
		double max=0;
		int classIndex=-1;
		String iLine;
		
		String siLine[], ints[], ciLine[],intc[]; 
		try{
		 //BufferedReader source =  new BufferedReader(new FileReader(fileName+"hito"+2+".txt"));
         //BufferedReader corpus =  new BufferedReader(new FileReader(fileName+"hito"+2+".txt"));
			//System.out.println("here i am at 2");
			File corpus = new File("ClassName");
			BufferedReader source =  new BufferedReader(new FileReader(sourceFName));//"HumTumhito1.txt"
	     
	     //System.out.println("here i am at 897");
	     
	     if(corpus.isDirectory())
	     {
	    	 classFileNames = corpus.listFiles();
	    	// System.out.println(" length : "+classFileNames.length);
			 if(classFileNames.length <= 0)
				 {//System.out.println("here i am at 901");
				 return classIndex;}
	     
			 else if(classFileNames.length > 0)
			 {
				 
				 className = new Vector();
				 iLine=source.readLine();
		            siLine=iLine.split("\\s");
		            
				 for(int i=0;i<classFileNames.length;i++)  //Collection of category n file per category
			       {
			    	    nsum=0;
					    String name[]; // to remove extension .txt 
			    	    BufferedReader corpusFile =  new BufferedReader(new FileReader("ClassName/"+classFileNames[i].getName()));//"HumTumhito1.txt";
			    	    
			    	    
			    	    iLine=corpusFile.readLine();
			            ciLine=iLine.split("\\s");
			            //System.out.println("hiiiiiii"+siLine.length);
			            for(int k=0; k<256;k++)
			            {
			           	 ints=siLine[k].split(":");
			           	 intc=ciLine[k].split(":");
			           	 sint[k]=Double.parseDouble(ints[1]);
			           	 cint[k]=Double.parseDouble(intc[1]);
			            }
			            for(int k=0; k<256;k++)
			           	 nsum = nsum+Math.sqrt(sint[k]*cint[k]);
			            
			            if(max < nsum)
			            {	
			            	max = nsum;
			            	classIndex=i;
			            }
			            //System.out.println("Total sum ; "+nsum);
			            //ciLine =corpus.readLine();
			       
			    	    
			    	    //className.add(name[0]);
			            //System.out.println(" Class : "+ i +"  "+name[0]);  
			       }
				 
			 }
	     }
	     
			 
		 //BufferedReader  =  new BufferedReader(new FileReader("ClassName"));
         
	     
	   /*  iLine=source.readLine();
         siLine=iLine.split("\\s");
         //iLine=corpus.readLine();
         ciLine=iLine.split("\\s");
         System.out.println("hiiiiiii"+siLine.length);
         for(int i=0; i<256;i++)
         {
        	 ints=siLine[i].split(":");
        	 intc=ciLine[i].split(":");
        	 sint[i]=Double.parseDouble(ints[1]);
        	 cint[i]=Double.parseDouble(intc[1]);
         }
         for(int i=0; i<256;i++)
        	 nsum = nsum+Math.sqrt(sint[i]*cint[i]);
         System.out.println("Total sum ; "+nsum);
         //ciLine =corpus.readLine();
         */
         
		}
		catch(IOException e){//System.out.println("here i am at error 924"+ e.getMessage());
		}
	    //System.out.println("line 1050 : "+ classIndex);
		return classIndex;	
	}
	
	
	
	//public static void main(String args[])
	//{
	 public int imgSegmentationMain(File file )
	   {
		ImageSegmentation detector = new ImageSegmentation();
		  //adjust its parameters as desired
		  detector.setLowThreshold(1f);//(0.5f);
		  detector.setHighThreshold(1.5f);//(1f);
		  //apply it to an image
		  
		  fileName = new String();
		  
		  String []ff=file.getName().split("\\.");
		  fileName=ff[0];
		  //fileName="1.jpg";
		  //System.out.println(fileName);
		  
		  BufferedImage img = null;
		  try {
		      img = ImageIO.read(new File(file.getAbsolutePath()));
		  } catch (IOException e) {
		  }
           
		  detector.setSourceImage(img);
		  detector.process();
		  //BufferedImage edges = detector.getEdgesImage();
		  BufferedImage edges = detector.getEdgesImage();
		 
		  

			File f1= new File(fileName+"Edging.jpg");
			//System.out.println("-----Edging Done-----");
			try {
				
				ImageIO.write(edges, "jpg" , f1);
			  } catch (IOException e) { //System.out.println(e.toString());
			  }
			 int tf=detector.segmentation(fileName,file); 
			 //System.out.println("-----Segmenting Done-----");
			 //System.out.println(totalClrfile);
			 //findOblectClass();
			 return (tf-1);
	}
	
    
}
