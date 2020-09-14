package com.thinking.machines.TeamViewer.RemoteFileAccess.FileTransfer;
import com.thinking.machines.TeamViewer.Events.*;

import java.util.*;
public class FileQueue extends Thread
{
 private FileEvents fileEvent;
 private Queue<FileEvents> queue;
 private final int max=10;
 public FileQueue()
 {
  queue=new LinkedList<>();
 }
 synchronized public void add(FileEvents fileEvent) throws Exception
 {
  if(queue.size()>=max)
  {
   wait();
  }
  queue.add(fileEvent);
  notify();
 }
 synchronized public FileEvents poll() throws Exception
 {
  if(queue.size()<=0)
  {
   wait();
  }
  fileEvent=queue.poll();
  notify();
  return fileEvent;
 }
}