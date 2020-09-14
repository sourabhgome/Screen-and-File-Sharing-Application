package com.thinking.machines.TeamViewer.Monitoring;
import com.thinking.machines.TeamViewer.Events.*;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import java.io.*;
import javax.imageio.ImageIO;

public class ImgPanel extends JPanel implements MouseListener,MouseMotionListener,KeyListener
{
 BufferedImage image;
 private EventSender eventSender;
 private KeyEvents kevent;
 private MouseEvents mevent;
 private int frameWidth;
 private int frameHeight;
 private double mouseX,mouseY;
 public ImgPanel(EventSender eventSender)
 {
  this.addMouseListener(this);
  this.addMouseMotionListener(this);
  this.addKeyListener(this);
  this.setFocusable(true);
  this.requestFocusInWindow();
  this.eventSender=eventSender;
  initBufferedImage();
 }
 public void setBackground(ImageEvents imageEvents,int frameWidth,int frameHeight) 
 {
  this.frameWidth=frameWidth;
  this.frameHeight=frameHeight;
  this.setSize(frameWidth,frameHeight-40);
  this.image = imageEvents.getImage();
  this.mouseX = imageEvents.getX();
  this.mouseY = imageEvents.getY();
 }
 public void paintComponent(Graphics G) 
 {
  super.paintComponent(G);
  if(image!=null)
  {
   ImageIcon cursor=new ImageIcon("c:/networkprogramming/TeamViewer/client/client/cursor.png");
   
   //Sticking mouse pointer
   BufferedImage outputBufImage = image;
   //scales the input image to the output image
   Graphics2D g2d = outputBufImage.createGraphics();
   g2d.drawImage(cursor.getImage(), (int)mouseX , (int)mouseY , cursor.getIconWidth(), cursor.getIconHeight(), null);
   g2d.dispose(); 
   image=outputBufImage;


   Image result=resizeImage(image,frameWidth,frameHeight-40,true);
   G.drawImage(new ImageIcon(result).getImage(), 0, 0, result.getWidth(null), result.getHeight(null), null);
  }
 }
 
 public static Image resizeImage(BufferedImage image, int scaledWidth, int scaledHeight, boolean preserveRatio) 
 {  
  if (preserveRatio) 
  { 
   double imageHeight = image.getHeight();
   double imageWidth = image.getWidth();
   if (imageHeight/scaledHeight > imageWidth/scaledWidth) 
   { 
    scaledWidth = (int) (scaledHeight * (double)((double)imageWidth / (double)imageHeight));
   } 
   else 
   {
    scaledHeight = (int) (scaledWidth * (double)((double)imageHeight / (double)imageWidth));
   }        
  }
  if(scaledWidth==0 ||scaledHeight==0)
  {
   scaledWidth=1200;
   scaledHeight=700;
  }                   
  return image.getScaledInstance(scaledWidth,scaledHeight,Image.SCALE_SMOOTH);
 }

 //Listeners
 
 public void mouseClicked(MouseEvent e) 
 {
  //System.out.println("Mouse Clicked Chala : "+e.getX()+" "+e.getY()+" "+e.getClickCount());
  mevent=new MouseEvents();
  mevent.setCode(1);
  mevent.setButton(e.getButton());
  mevent.setX(e.getX());
  mevent.setY(e.getY());
  eventSender.add(mevent);
 } 
 public void mousePressed(MouseEvent e) 
 { 
  //System.out.println("Mouse Pressed Chala : "+e.getX()+" "+e.getY()+" "+e.getClickCount());       
  mevent=new MouseEvents();
  mevent.setCode(2);
  mevent.setButton(e.getButton());
  mevent.setX(e.getX());
  mevent.setY(e.getY());
  eventSender.add(mevent);
 } 
 public void mouseReleased(MouseEvent e) 
 { 
  //System.out.println("Mouse Released Chala : "+e.getX()+" "+e.getY()+" "+e.getClickCount());      
  mevent=new MouseEvents();
  mevent.setCode(3);
  mevent.setButton(e.getButton());
  mevent.setX(e.getX());
  mevent.setY(e.getY());
  eventSender.add(mevent);
 } 
 public void mouseEntered(MouseEvent e) 
 { 
  //System.out.println("Mouse Entered Chala : "+e.getX()+" "+e.getY()+" "+e.getClickCount());       
  mevent=new MouseEvents();
  mevent.setCode(4);
  mevent.setButton(e.getButton());
  mevent.setX(e.getX());
  mevent.setY(e.getY());
  eventSender.add(mevent);
 } 
 public void mouseExited(MouseEvent e) 
 {
  //System.out.println("Mouse Exited Chala : "+e.getX()+" "+e.getY()+" "+e.getClickCount());
  mevent=new MouseEvents();
  mevent.setCode(5);
  mevent.setButton(e.getButton());
  mevent.setX(e.getX());
  mevent.setY(e.getY());
  eventSender.add(mevent);
 }

 public void mouseDragged(MouseEvent e) 
 {
  //System.out.println("Mouse Dragged Chala : "+e.getX()+" "+e.getY()+" "+e.getClickCount()); 
  mevent=new MouseEvents();
  mevent.setCode(6);
  mevent.setButton(e.getButton());
  mevent.setX(e.getX());
  mevent.setY(e.getY());
  eventSender.add(mevent);
 }
 public void mouseMoved(MouseEvent e) 
 {
  //System.out.println("Mouse Moved Chala : "+e.getX()+" "+e.getY()+" "+e.getClickCount()); 
  mevent=new MouseEvents();
  mevent.setCode(7);
  mevent.setButton(e.getButton());
  mevent.setX(e.getX());
  mevent.setY(e.getY());
  eventSender.add(mevent);
 }
 
 public void keyPressed(KeyEvent e)
 {
  //System.out.println("Key Pressed Chala");
  kevent=new KeyEvents();
  kevent.setCode(8);
  kevent.setKeyCode(e.getKeyCode());
  eventSender.add(kevent);
 }
 public void keyReleased(KeyEvent e)
 {
  //System.out.println("Key Released Chala");
  kevent=new KeyEvents();
  kevent.setCode(9);
  kevent.setKeyCode(e.getKeyCode());
  eventSender.add(kevent);
 }
 public void keyTyped(KeyEvent e)
 {
  //System.out.println("Key Typed Chala");
  kevent=new KeyEvents();
  kevent.setCode(10);
  kevent.setKeyCode(e.getKeyCode());
  eventSender.add(kevent);
 }
 public void initBufferedImage()
 {
  //image = getImage("C:\\phase1_TeamViewer\\com\\thinking\\machines\\TeamViewer\\server\\410316.jpg");
  try
  {
   image = ImageIO.read(new File("C:\\phase1_TeamViewer\\com\\thinking\\machines\\TeamViewer\\server\\410316.jpg"));
   this.setPreferredSize(new Dimension(800,460));
  }catch(Exception e) {}
  /*Image img=new Image();
  image = new BufferedImage(1190, 660, BufferedImage.TYPE_INT_ARGB);
  Graphics2D bGr = image.createGraphics();
  bGr.drawImage(img, 0, 0, null);
  bGr.dispose();*/
 }
}


















