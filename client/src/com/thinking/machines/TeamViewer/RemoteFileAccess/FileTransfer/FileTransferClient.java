package com.thinking.machines.TeamViewer.RemoteFileAccess.FileTransfer;

import java.net.*;
import java.io.*;

public class FileTransferClient extends Thread
{
 private final int portNumber=8080;
 private String server;
 public FileTransferClient(String server)
 {
  this.server=server;
  this.start();
 }
 public void run()
 {
  startAccepting();
 }
 public void startAccepting()
 {
  Socket socket;
  ByteArrayInputStream bais;
  DataInputStream dIn;
  ByteArrayOutputStream baos;
  DataOutputStream dOut;
  DataOutputStream tempDOut;
  DataInputStream tempDIn;
  int code;
  byte[] header=new byte[9]; 
  byte[] data;
  try
  {
   while(true)
   {
    System.out.println("Client on");
    socket=new Socket(server,portNumber);
    System.out.println("Client Connected");
    dIn=new DataInputStream(socket.getInputStream());
    System.out.println("Got input stream");
    dIn.readFully(header);
    System.out.println("Header recive hua");
    //dIn.flush();
    bais=new ByteArrayInputStream(header); 
    tempDIn=new DataInputStream(bais);
    tempDIn.readByte();
    double sizeOfData=tempDIn.readDouble();
    //tempDIn.flush();
    bais.close();


    dOut=new DataOutputStream(socket.getOutputStream());
    dOut.writeByte(1);
    dOut.flush();
   
    
    data=new byte[(int)sizeOfData];
    dIn.readFully(data);
    System.out.println("Data recive hua");
    bais=new ByteArrayInputStream(data); 
    tempDIn=new DataInputStream(bais);
    code=tempDIn.readByte();
    System.out.println(code);
    if(code==1) //Sending File to Client
    {
     byte s=tempDIn.readByte();
     String filePath="";
     int k=0;
     while(k<s)
     {
      filePath+=tempDIn.readChar();
      k++;
     }
     System.out.println(filePath);
     File temp=new File(filePath);
     RandomAccessFile raf=new RandomAccessFile(temp,"r");
     baos=new ByteArrayOutputStream();
     tempDOut=new DataOutputStream(baos);
     System.out.println(raf.length());
     /*int i;
     byte b;
     i=0;
     while(true)
     {
      b=(byte)raf.read();
      if(b==-1) break;
      tempDOut.writeByte(b);
      i++;
     }*/
     byte[] d=new byte[(int)raf.length()];
     raf.readFully(d);
     System.out.println(d.length);
     tempDOut.write(d);
     tempDOut.flush();
     dOut.write(baos.toByteArray());
     dOut.flush();
     raf.close();
    } 
    
    else if(code==2) //Recieving File From Server
    {
     long fileSize=tempDIn.readLong();
     System.out.println(fileSize);
     byte s=tempDIn.readByte();
     String filePath="";
     int k=0;
     while(k<s)
     {
      filePath+=tempDIn.readChar();
      k++;
     }
     System.out.println(filePath);
     File temp=new File(filePath);
     RandomAccessFile raf=new RandomAccessFile(temp,"rw");
     tempDIn=new DataInputStream(socket.getInputStream());
     byte b;
     int i;
     i=0;
     /*while(i<fileSize)
     {
      b=tempDIn.readByte();
      //if(b==-1) break;
      raf.write(dIn.readByte());
      i++;
     }*/
     byte[] d=new byte[(int)fileSize];
     tempDIn.readFully(d);
     raf.write(d);
     System.out.println(raf.length());
     //tempDIn.flush();
     raf.close();
    }
    else if(code==3) //Creating folder request
    {
     byte len=tempDIn.readByte();
     String dirPath="";
     int k=0;
     while(k<len)
     {
      dirPath+=tempDIn.readChar();
      k++;
     }
     File f=new File(dirPath);
     boolean b=f.mkdir();
     if(b)
     {
      System.out.println(dirPath+" created.");
      try{new File(dirPath+"\\temp").createNewFile();}catch(Exception exp1){System.out.println(exp1);}
     }
     else System.out.println(dirPath+" not created.");
    }
    socket.close();
   }
  }catch(Exception e)
   {
    System.out.println(e);
   }
 }
}













/*   baos=new ByteArrayOutputStream();
   tempData=new DataOutputStream(baos);*/

/*dOut.write(fileEvent.getByteArray());
     dOut.flush();
     System.out.println(fileEvent.getDirPath());
     File temp=new File(fileEvent.getDirPath()+"/"+fileEvent.getFileName());
     RandomAccessFile raf=new RandomAccessFile(temp,"rw");
     dIn=new DataInputStream(socket.getInputStream());
     byte b;
     int i;
     i=0;
     while(i<fileEvent.getFile().length())
     {
      b=dIn.readByte();
      if(b==-1) break;
      raf.write(dIn.readByte());
      i++;
     }
     raf.close();
    }
*/