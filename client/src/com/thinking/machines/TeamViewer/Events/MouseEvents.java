package com.thinking.machines.TeamViewer.Events;

import java.awt.event.*;
import java.io.*;
public class MouseEvents implements Events
{
 private int code;
 private int button;
 private int x;
 private int y;
 public byte getEventCode()
 {
  return 2;
 }
 public void setCode(int code)
 {
  this.code=code;
 }
 public int getCode()
 {
  return this.code;
 }
 public void setButton(int button)
 {
  this.button=button;
 }
 public int getButton()
 {
  return this.button;
 }
 public void setX(int x)
 {
  this.x=x;
 }
 public int getX()
 {
  return this.x;
 }
 public void setY(int y)
 {
  this.y=y;
 }
 public int getY()
 {
  return this.y;
 }
 public byte[] getByteArray() throws IOException
 {
  ByteArrayOutputStream baos=new ByteArrayOutputStream();
  DataOutputStream dos=new DataOutputStream(baos);
  dos.writeInt(this.code);
  dos.writeInt(this.x);
  dos.writeInt(this.y);
  dos.writeInt(this.button);
  dos.flush();
  return baos.toByteArray();
 }
 public byte[] getHeader() throws IOException
 {
  ByteArrayOutputStream baos=new ByteArrayOutputStream();
  DataOutputStream dos=new DataOutputStream(baos);
  dos.writeByte(2);
  dos.writeDouble(16);
  dos.flush();
  return baos.toByteArray();
 }
}