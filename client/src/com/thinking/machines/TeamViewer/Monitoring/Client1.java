package com.thinking.machines.TeamViewer.Monitoring;
import com.thinking.machines.TeamViewer.Events.*;
import java.io.*;
import java.net.*;
import java.awt.image.BufferedImage;
import java.awt.*;
import javax.imageio.ImageIO;
public class Client1 extends Thread
{
 private String server;
 private Thread client;
 public Client1(String serverName)
 {
  this.server=serverName;
  client=new Thread(this);
  this.client.start();
 }
 public void run()
 {
  startSending();
 }
 public void startSending()
 {
  try
  {
   BufferedImage img;
   ImageEvents imageEvents;
   ImageQueue queue=new ImageQueue();
   DesktopImage screen=new DesktopImage(queue);
   OutputStream os;
   final int serverPort=5050;
   System.out.println("Establishing connection with "+server+" listening on port "+serverPort);
   Socket socket;
   ByteArrayOutputStream baos;
   byte[] header;
   DataOutputStream dOut;
   DataInputStream dIn;
   while(true)
   {
    socket=new Socket(server,serverPort);
    imageEvents=queue.poll();
    baos = new ByteArrayOutputStream();
    header=imageEvents.getHeader();
    dOut = new DataOutputStream(socket.getOutputStream());
    dOut.write(header); // Sending header
    dOut.flush();
    dIn = new DataInputStream(socket.getInputStream());
    if(dIn.read()!=1)
    {
     System.out.println("Acknowledgement not recived");
     break;
    }
    
    dOut.write(imageEvents.getByteArray());
    dOut.flush();
    dOut.close();
    socket.close();
   }
  }catch(Exception e)
   {
    System.out.println(e.getMessage());
    System.exit(0);
   }
 }
}