package com.thinking.machines.TeamViewer.Events;

import java.awt.event.*;
import java.io.*;
public class KeyEvents implements Events
{
 private int code;
 private int keyCode;
 public byte getEventCode()
 {
  return 3;
 }
 public void setCode(int code)
 {
  this.code=code;
 }
 public int getCode()
 {
  return this.code;
 }
 public void setKeyCode(int keyCode)
 {
  this.keyCode=keyCode;
 }
 public int getKeyCode()
 {
  return this.keyCode;
 }
 public byte[] getByteArray() throws IOException
 {
  ByteArrayOutputStream baos=new ByteArrayOutputStream();
  DataOutputStream dos=new DataOutputStream(baos);
  dos.writeInt(this.code);
  dos.writeInt(this.keyCode);
  dos.flush();
  return baos.toByteArray();
 }
 public byte[] getHeader() throws IOException
 {
  ByteArrayOutputStream baos=new ByteArrayOutputStream();
  DataOutputStream dos=new DataOutputStream(baos);
  dos.writeByte(3);
  dos.writeDouble(8);
  dos.flush();
  return baos.toByteArray();
 }
}