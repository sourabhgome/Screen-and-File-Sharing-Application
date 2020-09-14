package com.thinking.machines.TeamViewer.Events;

import java.awt.image.BufferedImage;
import java.awt.event.*;
import java.io.*;
import javax.imageio.ImageIO;
public class ImageEvents implements Events
{
 private BufferedImage image;
 private double x;
 private double y;
 public byte getEventCode()
 {
  return 1;
 }
 public void setImage(BufferedImage image)
 {
  this.image=image;
 }
 public BufferedImage getImage()
 {
  return this.image;
 }
 public void setX(double x)
 {
  this.x=x;
 }
 public double getX()
 {
  return this.x;
 }
 public void setY(double y)
 {
  this.y=y;
 }
 public double getY()
 {
  return this.y;
 }
 public byte[] getHeader() throws IOException
 {
  ByteArrayOutputStream baos=new ByteArrayOutputStream();
  DataOutputStream dos=new DataOutputStream(baos);
  ByteArrayOutputStream tmp = new ByteArrayOutputStream();
  ImageIO.write(this.image, "jpg", tmp);
  tmp.close();
  double size = tmp.size();
  dos.writeByte(1);
  dos.writeDouble(size+16);
  dos.flush();
  return baos.toByteArray();
 }
 public byte[] getByteArray() throws IOException
 {
  ByteArrayOutputStream baos=new ByteArrayOutputStream();
  DataOutputStream dos=new DataOutputStream(baos);
  dos.writeDouble(this.x);
  dos.writeDouble(this.y);
  ImageIO.write(this.image, "jpg", baos);
  dos.flush();
  return baos.toByteArray();
 }
}