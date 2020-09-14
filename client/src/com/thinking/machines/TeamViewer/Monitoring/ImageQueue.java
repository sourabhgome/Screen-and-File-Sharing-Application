package com.thinking.machines.TeamViewer.Monitoring;
import com.thinking.machines.TeamViewer.Events.*;

import java.util.*;
public class ImageQueue extends Thread
{
 private ImageEvents imageEvent;
 private Queue<ImageEvents> queue;
 private final int max=25;
 public ImageQueue()
 {
  queue=new LinkedList<>();
 }
 synchronized public void add(ImageEvents imageEvent) throws Exception
 {
  if(queue.size()>=max)
  {
   wait();
  }
  queue.add(imageEvent);
  notify();
 }
 synchronized public ImageEvents poll() throws Exception
 {
  if(queue.size()<=0)
  {
   wait();
  }
  imageEvent=queue.poll();
  notify();
  return imageEvent;
 }
}