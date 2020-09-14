package com.thinking.machines.TeamViewer.RemoteFileAccess.TreeTransfer;

import java.io.*;
public class FileNode implements Serializable
{
 private File file;
 public FileNode(File file)
 {
  this.file=file;
 }
 public String toString()
 {
  String name=file.getName();
  if(name.equals(""))
   return file.getAbsolutePath();
  else
   return name;
 }
 public String getPath()
 {
  return file.getAbsolutePath();
 }
 public File getFile()
 {
  return this.file;
 }
}