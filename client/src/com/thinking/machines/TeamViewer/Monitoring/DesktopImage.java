package com.thinking.machines.TeamViewer.Monitoring;
import com.thinking.machines.TeamViewer.Events.*;

import java.awt.*;
import javax.swing.*;
import java.io.*;
import javax.imageio.stream.ImageOutputStream;
import javax.imageio.*;
import java.awt.image.BufferedImage;
public class DesktopImage extends Thread
{
 private Thread capture;
 private ImageQueue queue;
 public DesktopImage(ImageQueue queue)
 {
  this.queue=queue;
  capture=new Thread(this);
  capture.start();
 }
 public void run()
 {
  try
  {
   while(true)
   {
    captureImage();
    //sleep(100);
   }
  }catch(Exception e) {System.out.println(e);}
 }
 public void captureImage() throws Exception
 {
  Rectangle rec=new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
  Robot robot=new Robot();
  BufferedImage img=robot.createScreenCapture(rec);

  ByteArrayOutputStream compressed = new ByteArrayOutputStream();
  ImageOutputStream outputStream = ImageIO.createImageOutputStream(compressed);

  ImageWriter jpgWriter = ImageIO.getImageWritersByFormatName("jpg").next();

  ImageWriteParam jpgWriteParam = jpgWriter.getDefaultWriteParam();
  jpgWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
  jpgWriteParam.setCompressionQuality(0.5f);

  jpgWriter.setOutput(outputStream);

  jpgWriter.write(null, new IIOImage(img, null, null), jpgWriteParam);

  jpgWriter.dispose();

  byte[] jpegData = compressed.toByteArray();

  PointerInfo pi = MouseInfo.getPointerInfo(); 
  Point p = pi.getLocation(); 

  ImageEvents imageEvents=new ImageEvents();
  imageEvents.setImage(ImageIO.read(new ByteArrayInputStream(jpegData)));
  imageEvents.setX(p.getX());
  imageEvents.setY(p.getY());
   
  queue.add(imageEvents);
 }
}














//ImageIcon cursor=new ImageIcon("c:/networkprogramming/TeamViewer/client/client/cursor.png");
   

 /*BufferedImage outputBufImage = img;
   //scales the input image to the output image
   Graphics2D g2d = outputBufImage.createGraphics();
   g2d.drawImage(cursor.getImage(), (int)p.getX() , (int)p.getY() , cursor.getIconWidth(), cursor.getIconHeight(), null);
   g2d.dispose(); 
  
   return outputBufImage;*/