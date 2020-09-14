package com.thinking.machines.TeamViewer.Monitoring;
import com.thinking.machines.TeamViewer.Events.*;

//Client2
import java.net.*;
import java.io.*;

public class EventReciver extends Thread
{
 private String serverName;
 private final int portNumber=7070;
 private Thread reciever;
 public EventReciver(String serverName)
 {
  this.serverName=serverName;
  reciever=new Thread(this);
  this.reciever.start();
 }
 public void run()
 {
  startAccepting();
 }
 public void startAccepting()
 {
  Socket socket;
  DataInputStream dIn;
  DataInputStream tempData;
  DataOutputStream dOut;
  ByteArrayOutputStream baos;
  ByteArrayInputStream bais;
  MouseEvents mevent;
  KeyEvents kevent;
  byte eventCode;
  double size;
  byte[] header;
  EventHandler eventHandler=new EventHandler();
  try
  {
   while(true)
   {
    try
    {
     socket=new Socket(serverName,portNumber);
     dIn = new DataInputStream(socket.getInputStream());
     header=new byte[9];
     dIn.readFully(header);
     bais=new ByteArrayInputStream(header);
     tempData=new DataInputStream(bais);
     eventCode=tempData.readByte();
     size=tempData.readDouble();
     //dIn.flush();        
     dOut=new DataOutputStream(socket.getOutputStream());
     dOut.writeByte(1);
     dOut.flush();
     
     if(eventCode==2)
     {
      mevent=new MouseEvents();
      mevent.setCode(dIn.readInt());
      mevent.setX(dIn.readInt());
      mevent.setY(dIn.readInt());
      mevent.setButton(dIn.readInt());
      eventHandler.add(mevent);
     }
     else if(eventCode==3)
     {
      kevent=new KeyEvents();
      kevent.setCode(dIn.readInt());
      kevent.setKeyCode(dIn.readInt());
      eventHandler.add(kevent);
     }
     if(false) break;
     socket.close();
    }
    catch(ConnectException e)
    {
     System.out.println(e);
     continue;
    }
   }
  }catch(Exception exp) {System.out.println(exp);}
 }
}