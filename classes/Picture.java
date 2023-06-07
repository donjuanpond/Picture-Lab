package classes;

import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.text.*;
import java.util.*;
import java.util.List; // resolves problem with java.awt.List and java.util.List

/**
 * A class that represents a picture.  This class inherits from 
 * SimplePicture and allows the student to add functionality to
 * the Picture class.  
 * 
 * @author Barbara Ericson ericson@cc.gatech.edu
 */
public class Picture extends SimplePicture 
{
  ///////////////////// constructors //////////////////////////////////
  
  /**
   * Constructor that takes no arguments 
   */
  public Picture ()
  {
    /* not needed but use it to show students the implicit call to super()
     * child constructors always call a parent constructor 
     */
    super();  
  }
  
  /**
   * Constructor that takes a file name and creates the picture 
   * @param fileName the name of the file to create the picture from
   */
  public Picture(String fileName)
  {
    // let the parent class handle this fileName
    super(fileName);
  }
  
  /**
   * Constructor that takes the width and height
   * @param height the height of the desired picture
   * @param width the width of the desired picture
   */
  public Picture(int height, int width)
  {
    // let the parent class handle this width and height
    super(width,height);
  }
  
  /**
   * Constructor that takes a picture and creates a 
   * copy of that picture
   * @param copyPicture the picture to copy
   */
  public Picture(Picture copyPicture)
  {
    // let the parent class do the copy
    super(copyPicture);
  }
  
  /**
   * Constructor that takes a buffered image
   * @param image the buffered image to use
   */
  public Picture(BufferedImage image)
  {
    super(image);
  }
  
  ////////////////////// methods ///////////////////////////////////////
  
  /**
   * Method to return a string with information about this picture.
   * @return a string with information about the picture such as fileName,
   * height and width.
   */
  public String toString()
  {
    String output = "Picture, filename " + getFileName() +
      " height " + getHeight()
      + " width " + getWidth();
    return output;
    
  }
  
  /** Method to set the blue to 0 */
  public void zeroBlue()
  {
    Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] r : pixels) for (Pixel p : r) {
        p.setColor(new Color(p.getRed(), p.getGreen(), 0));
    }
  }
  /** Turn the picture into an only red image */
  public void allRed() {
    Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] r : pixels) for (Pixel p : r) {
      p.setColor(new Color(p.getRed(), 0, 0));
    }
  }
  /** Turn the picture into an only green image */
  public void allGreen() {
    Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] r : pixels) for (Pixel p : r) {
      p.setColor(new Color(0, p.getGreen(), 0));
    }
  }
  /** Turn the picture into an only blue image */
  public void allBlue() {
    Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] r : pixels) for (Pixel p : r) {
      p.setColor(new Color(0, 0, p.getBlue()));
    }
  }
  /** Given three images `r` `g` and `b` (only red, only green, only blue) return the image
   * produced by overlaying the top 80% of all three */
  public static Picture overlay(Picture r, Picture g, Picture b) {
    // code out here
    Pixel[][] pixels = r.getPixels2D();
    Pixel[][] gpixel = g.getPixels2D();
    Pixel[][] bpixel = b.getPixels2D();

    for (int i=0; i<(int)(pixels.length*0.8); i++) for (int j=0; j<pixels[0].length; j++) {
      pixels[i][j].setGreen(gpixel[i][j].getGreen());
      pixels[i][j].setBlue(bpixel[i][j].getBlue());
    }
    return r;
  }
  /** Given a base image as a parameter input, this method returns a grayscale
   * version of that image */
  public static Picture grayscale(Picture base) {
    Pixel[][] pixels = base.getPixels2D();
    for (Pixel[] r : pixels) for (Pixel p : r) p.setColor(new Color((int) p.getAverage(), (int) p.getAverage(), (int) p.getAverage()));
    return base;
  }

  /** Method which negates an image. Negates red values only if `red` is true, same for
   * green and blue.*/
  public void negative(boolean red, boolean green, boolean blue) {
    Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] r : pixels) for (Pixel p : r) p.setColor(new Color(red ? 255-p.getRed() : p.getRed(), green ? 255-p.getGreen() : p.getGreen(), blue ? 255-p.getBlue() : p.getBlue()));
  }
  /** Method which shifts RGB values so that green becomes red, blue becomes green, red becomes blue */
  public void colorShift()
  {
    Pixel[][] pixels = this.getPixels2D();
    int green = 0;
    int red = 0;
    int blue = 0;
    for (Pixel[] c: pixels)
    {
      for (Pixel w: c)
      {
        green = w.getGreen();
        red = w.getRed();
        blue = w.getBlue();
        w.setGreen(red);
        w.setBlue(green);
        w.setRed(blue);
      }
    }
  }
  /** Method that mirrors the picture around a
    * vertical mirror in the center of the picture
    * from left to right */
  public void mirrorVertical()
  {
    Pixel[][] pixels = this.getPixels2D();
    for (int r=0; r<pixels.length; r++) for (int c=0; c<pixels[0].length/2; c++) {
      Pixel placeholder = pixels[r][c];
      pixels[r][pixels[0].length - 1 - c].setColor(new Color(placeholder.getRed(), placeholder.getGreen(), placeholder.getBlue()));
    }
  }
  public void mirrorHorizontal()
  {
    Pixel[][] pixels = this.getPixels2D();
    for (int r=0; r<pixels.length/2; r++) for (int c=0; c<pixels[0].length; c++) {
      Pixel placeholder = pixels[r][c];
      pixels[pixels.length - 1 - r][c].setColor(new Color(placeholder.getRed(), placeholder.getGreen(), placeholder.getBlue()));
    }
  }
  public void mirrorDiagonal() {
    Pixel[][] pixels = this.getPixels2D();
      double slope = (double) this.getWidth()/this.getHeight();
      double start = -slope;
      for (int r=0; r<pixels.length; r++) {
        start += slope;
        for (int c = (int) start; c<pixels[0].length; c++){
          int rval = pixels[r][c].getRed(), gval = pixels[r][c].getGreen(), bval = pixels[r][c].getBlue();
          int distance = c - (int)start;
          pixels[Math.min((int)(r+(distance/slope)), pixels.length-1)][(int)start].setColor(new Color(rval,gval,bval));
          if (r>0) pixels[Math.min((int)(r-1+(distance/slope)), pixels.length-1)][(int)start].setColor(new Color(rval,gval,bval));
          if (start>=1) pixels[Math.min((int)(r-1+(distance/slope)), pixels.length-1)][(int)start-1].setColor(new Color(rval,gval,bval));
        }
      }
  }

  /** Mirror just part of a picture of a temple */
  public void mirrorTemple()
  {
    int mirrorPoint = 276;
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    int count = 0;
    Pixel[][] pixels = this.getPixels2D();
    
    // loop through the rows
    for (int row = 27; row < 97; row++)
    {
      // loop from 13 to just before the mirror point
      for (int col = 13; col < mirrorPoint; col++)
      {
        
        leftPixel = pixels[row][col];      
        rightPixel = pixels[row]                       
                         [mirrorPoint - col + mirrorPoint];
        rightPixel.setColor(leftPixel.getColor());
      }
    }
  }
  
  /** copy from the passed fromPic to the
    * specified startRow and startCol in the
    * current picture
    * @param fromPic the picture to copy from
    * @param startRow the start row to copy to
    * @param startCol the start col to copy to
    */
  public void copy(Picture fromPic, 
                 int startRow, int startCol)
  {
    Pixel fromPixel = null;
    Pixel toPixel = null;
    Pixel[][] toPixels = this.getPixels2D();
    Pixel[][] fromPixels = fromPic.getPixels2D();
    for (int fromRow = 0, toRow = startRow; 
         fromRow < fromPixels.length &&
         toRow < toPixels.length; 
         fromRow++, toRow++)
    {
      for (int fromCol = 0, toCol = startCol; 
           fromCol < fromPixels[0].length &&
           toCol < toPixels[0].length;  
           fromCol++, toCol++)
      {
        fromPixel = fromPixels[fromRow][fromCol];
        toPixel = toPixels[toRow][toCol];
        toPixel.setColor(fromPixel.getColor());
      }
    }   
  }

  /** Method to create a collage of several pictures */
  public void createCollage()
  {
    Picture flower1 = new Picture("flower1.jpg");
    Picture flower2 = new Picture("flower2.jpg");
    this.copy(flower1,0,0);
    this.copy(flower2,100,0);
    this.copy(flower1,200,0);
    Picture flowerNoBlue = new Picture(flower2);
    flowerNoBlue.zeroBlue();
    this.copy(flowerNoBlue,300,0);
    this.copy(flower1,400,0);
    this.copy(flower2,500,0);
    this.mirrorVertical();
    this.write("collage.jpg");
  }
  
  
  /** Method to show large changes in color 
    * @param edgeDist the distance for finding edges
    */
  public void edgeDetection(int edgeDist)
  {
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    Pixel[][] pixels = this.getPixels2D();
    Color rightColor = null;
    for (int row = 0; row < pixels.length; row++)
    {
      for (int col = 0; 
           col < pixels[0].length-1; col++)
      {
        leftPixel = pixels[row][col];
        rightPixel = pixels[row][col+1];
        rightColor = rightPixel.getColor();
        if (leftPixel.colorDistance(rightColor) > 
            edgeDist)
          leftPixel.setColor(Color.BLACK);
        else
          leftPixel.setColor(Color.WHITE);
      }
    }
  }
  
  
  /* Main method for testing - each class in Java can have a main 
   * method 
   */
  public static void main(String[] args) 
  {
    Picture beach = new Picture("beach.jpg");
    beach.explore();
    beach.zeroBlue();
    beach.explore();
  }
  
} // this } is the end of class Picture, put all new methods before this
