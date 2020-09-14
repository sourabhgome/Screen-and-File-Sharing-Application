package com.thinking.machines.TeamViewer.Monitoring;
import com.thinking.machines.TeamViewer.Events.*;

//Server 2

import java.net.*;
import java.io.*;
import java.util.*;
public class EventSender extends Thread
{
 private ServerSocket serverSocket;
 Thread runner;
 private final int portNumber=7070;
 Queue<Events> queue=new LinkedList<Events>();
 EventSender() throws IOException
 {
  serverSocket=new ServerSocket(portNumber);
  this.runner=new Thread(this);
  this.runner.start();
 }
 public void add(Events events)
 {
  queue.add(events);
  this.runner.resume();
 }
 public void run()
 {
  try
  {
   Socket socket;
   DataOutputStream dOut;
   ByteArrayOutputStream baos;
   DataInputStream dIn;
   Events event;
   while(true)
   {
    if(queue.peek()==null)
    {
     this.runner.suspend();
    }
    event=queue.poll();
    if(event!=null)
    {
     socket=serverSocket.accept();
     baos=new ByteArrayOutputStream();
     dOut = new DataOutputStream(socket.getOutputStream());
     dOut.write(event.getHeader());
     dOut.flush();
     dIn=new DataInputStream(socket.getInputStream());
     if(dIn.readByte()!=1)
      break;
     dOut.write(event.getByteArray());
     dOut.flush();
     socket.close();
    }
   }
  }
  catch(Exception e)
  {
   System.out.println(e);
  }
 }
}