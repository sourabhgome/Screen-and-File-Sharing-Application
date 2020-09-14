package com.thinking.machines.TeamViewer.Monitoring;
import com.thinking.machines.TeamViewer.Events.*;

import java.util.*;
import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
public class EventHandler extends Thread
{
 Queue<Events> queue=new LinkedList<Events>();
 Events event;
 MouseEvents mevent;
 KeyEvents kevent;
 int code;
 Thread runner;
 Robot robot;
 int x,y;
 Dimension d;
 byte[] byteArray;
 ByteArrayInputStream byteArrayInputStream;
 DataInputStream dataInputStream;
 public EventHandler()
 {
  this.runner=new Thread(this);
  this.runner.start();
  try{
  robot=new Robot();
  }catch(Exception e) {}
  d=Toolkit.getDefaultToolkit().getScreenSize();
 }
 public void add(Events events)
 {
  queue.add(events);
  //System.out.println("Thread 2 resume hua.");
  this.runner.resume();
 }
 public void run()
 {
  try
  {
   while(true)
   {
    if(queue.peek()==null)
    {
     //System.out.println("Thread 2 suspend hua.");
     this.runner.suspend();
    }
    event=queue.poll();
    byte c=event.getEventCode();
    //System.out.println(c);
    if(c==2)
    {
     mevent=(MouseEvents)event;
     code=mevent.getCode();
     int x=mevent.getX();
     int y=mevent.getY();
     int button=mevent.getButton();
     if(code<8)
     {
      x=(int)((x/1190.0)*d.getWidth());
      y=(int)((y/660.0)*d.getHeight())+40;
      if(code==2)
      {
       robot.mousePress(InputEvent.getMaskForButton(button));
       //System.out.println("Mouse Pressed on X : "+x+" Y : "+y);
       }
      if(code==3)
      {
       robot.mouseRelease(InputEvent.getMaskForButton(button));
       //System.out.println("Mouse Released on X : "+x+" Y : "+y);
      } 
      else if(code==7)
      { 
       robot.mouseMove(x,y);
       //System.out.println("Mouse Moved");
      }
     }
    }
    else if(c==3)
    {
     //dataInputStream.readDouble();
     kevent=(KeyEvents)event;
     code=kevent.getCode();
     int keyCode=kevent.getKeyCode();
     if(code>7) 
     {
      if(code==8)
       robot.keyPress(keyCode);
      else if(code==9)
       robot.keyRelease(keyCode); 
     }
    }//sleep(100);
    else
    {
     throw new Exception("Unidentified Event");
    }
   }
  }catch(Exception e)
  {
   System.out.println(e);
  }
 }
}