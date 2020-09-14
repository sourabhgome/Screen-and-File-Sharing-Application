package com.thinking.machines.TeamViewer.Events;

import java.io.*;
public class FileEvents implements Events
{
 private int code;
 private String filePath;
 private String fileName;
 private String dirPath;
 private File file;
 private byte eventCode;
 private ByteArrayOutputStream baos;
 private DataOutputStream dOut;
 public FileEvents()
 {
  baos=new ByteArrayOutputStream();
  dOut=new DataOutputStream(baos);
 }
 public byte getEventCode()
 {
  return 5;
 }
 public void setCode(int code)
 {
  this.code=code;
 }
 public int getCode()
 {
  return this.code;
 }
 public void setFilePath(String filePath)
 {
  this.filePath=filePath;
 }
 public String getFilePath()
 {
  return this.filePath;
 }
 public void setDirPath(String dirPath)
 {
  this.dirPath=dirPath;
 }
 public String getDirPath()
 {
  return this.dirPath;
 }
 public void setFileName(String fileName)
 {
  this.fileName=fileName;
 }
 public String getFileName()
 {
  return this.fileName;
 }
 public void setFile(File file)
 {
  this.file=file;
 }
 public File getFile()
 {
  return this.file;
 }
 public byte[] getHeader() throws IOException
 {
  ByteArrayOutputStream tempBaos=new ByteArrayOutputStream();
  DataOutputStream tempData=new DataOutputStream(tempBaos);
  tempData.writeByte(5);
  dOut.writeByte(code);
  if(code==1) //Recieving file from client side
  {
   //Sending File path
   dOut.writeByte(filePath.length());
   dOut.writeChars(filePath);
  }
  else if(code==2) //Sending file from server to client side
  {
   //Sending File + dir where to save
   String temp=dirPath+"\\"+fileName;
   dOut.writeLong(file.length());
   dOut.writeByte(temp.length());
   dOut.writeChars(temp);
  }
  else if(code==3) //Creating folder on client side
  {
   dOut.writeByte(dirPath.length());
   dOut.writeChars(dirPath);
  }
  tempData.writeDouble(baos.size());
  return tempBaos.toByteArray();
 }
 public byte[] getByteArray() throws IOException
 {
  return baos.toByteArray();
 }
}