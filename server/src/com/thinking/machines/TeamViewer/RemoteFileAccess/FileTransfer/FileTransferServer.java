package com.thinking.machines.TeamViewer.RemoteFileAccess.FileTransfer;
import com.thinking.machines.TeamViewer.Events.*;

import java.net.*;
import java.io.*;

public class FileTransferServer extends Thread
{
 private final int portNumber=8080;
 private FileQueue queue;
 private static boolean closeServer=false;
 public FileTransferServer(FileQueue queue)
 {
  this.queue=queue;
  this.start();
 }
 public void run()
 {
  startListening();
 }
 public void startListening()
 {
  Socket socket;
  ByteArrayInputStream bais;
  DataInputStream dIn;
  ByteArrayOutputStream baos;
  DataOutputStream dOut;
  DataOutputStream tempData;
  FileEvents fileEvent;
  int code;
  byte[] header=new byte[9]; 
  try
  {
   ServerSocket serverSocket=new ServerSocket(portNumber);
   System.out.println("File Transfer Server On");
   while(!close)
   {
    socket=serverSocket.accept();
    System.out.println("File Transfer Server Connected");
    dOut=new DataOutputStream(socket.getOutputStream());
    fileEvent=queue.poll();
    System.out.println("Queue se remove hua :"+fileEvent);
    System.out.println(fileEvent.getHeader().length);
    dOut.write(fileEvent.getHeader());
    dOut.flush();
    System.out.println("Header send hua");  

    dIn=new DataInputStream(socket.getInputStream());
    if(dIn.readByte()!=1)
    {
     System.out.println("Acknowledgement not recieved");
     break;
    }System.out.println("Acknowledgement recieved");
    
   
    code=fileEvent.getCode();
    System.out.println(code);
    if(code==1) //Receiving File from Client
    {
     dOut.write(fileEvent.getByteArray());
     dOut.flush();
     System.out.println(fileEvent.getDirPath());
     File temp=new File(fileEvent.getDirPath()+"\\"+fileEvent.getFileName());
     RandomAccessFile raf=new RandomAccessFile(temp,"rw");
     dIn=new DataInputStream(socket.getInputStream());
     byte[] d=new byte[(int)fileEvent.getFile().length()];
     dIn.readFully(d);
     raf.write(d);
     System.out.println(raf.length());
     raf.close();
    }
    else if(code==2) //Sending File to Client
    {
     System.out.println("Event 2 chala");
     dOut.write(fileEvent.getByteArray());
     System.out.println(fileEvent.getByteArray().length);
     dOut.flush();
     File temp=fileEvent.getFile();
     RandomAccessFile raf=new RandomAccessFile(temp,"r");
     baos=new ByteArrayOutputStream();
     tempData=new DataOutputStream(baos);
     System.out.println(raf.length());
     byte[] d=new byte[(int)raf.length()];
     raf.readFully(d);
     System.out.println(d.length);
     dOut.write(d);
     dOut.flush();
     raf.close(); 
     System.out.println("File gyi");
    }
    else if(code==3) //Creating folder request
    {
     dOut.write(fileEvent.getByteArray());
     dOut.flush();
    }
    socket.close();
   }
  }catch(Exception e)
   {
    System.out.println(e);
    new ServerUI();
   }
 }
 public void setFileQueue(FileQueue fileQueue)
 { 
  this.queue=fileQueue;
 }
 public static void closeFileTransferServer(boolean close)
 {
  this.close=close;
 }
}











/*   baos=new ByteArrayOutputStream();
   tempData=new DataOutputStream(baos);*/


     /*byte b;
     int i;
     i=0;
     while(i<fileEvent.getFile().length())
     {
      b=dIn.readByte();
      if(b==-1) break;
      raf.write(dIn.readByte());
      i++;
     }*/


/*     int i;
     byte b;
     i=0;
     while(i<raf.length())
     {
      b=(byte)raf.read();
      //if(b==-1) break;
      tempData.writeByte(b);
      i++;
     }*/