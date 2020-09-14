package com.thinking.machines.TeamViewer.Monitoring;
import com.thinking.machines.TeamViewer.Events.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
public class Show extends JFrame
{
 private Container c;
 private ImgPanel imagePanel1;
 //private BufferedImage image;
 private ImageEvents imageEvents;
 Show(EventSender eventSender)
 {
  c=getContentPane();
  c.setLayout(null);
  this.setFocusable(true);
  this.requestFocusInWindow();
  imagePanel1=new ImgPanel(eventSender);
  c.add(imagePanel1);
  imagePanel1.setBounds(0,0,1190,700-40); 
  setDefaultCloseOperation(EXIT_ON_CLOSE);
  setSize(1190,700);
  setLocation(0,0);
  setVisible(true);
 }
 public void setImage(ImageEvents imageEvents)
 {
  imagePanel1.setBackground(imageEvents,this.getWidth(),this.getHeight());
  repaint(); 
 }
 /*public BufferedImage getImage()
 {
  return this.image;
 }*/
}