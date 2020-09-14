package com.thinking.machines.TeamViewer.Monitoring;
import com.thinking.machines.TeamViewer.Events.*;

import java.net.*;
import java.io.*;
import java.awt.image.BufferedImage;
import java.awt.*;
import javax.imageio.ImageIO;

public class Server1 extends Thread
{
 private ServerSocket serverSocket;
 private final int portNumber=5050;
 public Server1() throws Exception
 {
  serverSocket=new ServerSocket(portNumber);
  this.start();
 }
 public void run()
 {
  startListening();
 }
 public void startListening()
 {
  try
  {
   ImageEvents imageEvents;
   InputStream is;
   DataInputStream dIn;
   DataInputStream tempData;
   DataOutputStream dOut;
   ByteArrayInputStream bais;
   double x;
   double y;
   double size;
   Socket socket;
   String response;
   EventSender eventSender=new EventSender();
   Show show=new Show(eventSender);
   BufferedImage image;
   byte[] header;
   byte[] data;
   while(true)
   {
    socket=serverSocket.accept();
    dIn=new DataInputStream(socket.getInputStream());
    header=new byte[9];
    dIn.readFully(header);
    bais=new ByteArrayInputStream(header);
    tempData=new DataInputStream(bais);
    tempData.readByte();
    size=tempData.readDouble();
    tempData.close();
 
    dOut=new DataOutputStream(socket.getOutputStream());
    dOut.writeByte(1);
    dOut.flush();

    data=new byte[(int)size];
    dIn.readFully(data);
    bais=new ByteArrayInputStream(data);
    tempData=new DataInputStream(bais);
    imageEvents=new ImageEvents();
    imageEvents.setX(tempData.readDouble());
    imageEvents.setY(tempData.readDouble());
    imageEvents.setImage(ImageIO.read(tempData)); //bais se read kiya tha
    show.setImage(imageEvents);
    dIn.close();
    dOut.close();
    if(false) break;
    socket.close();
   }
  }catch(Exception e)
   {
    System.out.println(e);
   }
 }
}

















/*while(true)
   {
    socket=serverSocket.accept();
    //is=socket.getInputStream();
 
    DataInputStream dIn = new DataInputStream(socket.getInputStream()); 
    int length = dIn.readInt();
    if(length>0) 
    {
     message = new byte[length];
     dIn.readFully(message, 0, message.length); // read the message
    }
    

    ByteArrayInputStream bais = new ByteArrayInputStream(message);
     image=ImageIO.read(bais);
 
    //image = ImageIO.read(is);
    show.setImage(image);
    image.flush();
    //is.close();
    socket.close();
   }
*/