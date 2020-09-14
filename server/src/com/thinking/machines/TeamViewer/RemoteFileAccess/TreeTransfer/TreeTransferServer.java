package com.thinking.machines.TeamViewer.RemoteFileAccess.TreeTransfer;
import com.thinking.machines.TeamViewer.RemoteFileAccess.FileTransfer.*;
import com.thinking.machines.TeamViewer.RemoteFileAccess.UI.*;
import com.thinking.machines.TeamViewer.Events.*;

import java.net.*;
import java.io.*;
import java.awt.image.BufferedImage;
import javax.swing.JTree;
import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class TreeTransferServer extends Thread
{
 private ServerSocket serverSocket;
 private final int portNumber=6060;
 public TreeTransferServer() throws Exception
 {
  serverSocket=new ServerSocket(portNumber);
  //this.start();
  run();
 }
 public void run()
 {
  startListening();
 }
 public void startListening()
 {
  try
  {
   DataInputStream dIn,tempData;
   ByteArrayInputStream bais,bais1;
   TreeEvents treeEvent=new TreeEvents();
   DataOutputStream dOut;
   ObjectInputStream ois;
   Socket socket;
   FileQueue queue=new FileQueue();
   FileTransferUI fileTransferUI=new FileTransferUI(queue);
   FileTransferServer fileTransferServer=new FileTransferServer(queue);
   double size;
   byte[] header;
   byte[] data;
   byte temp;
   while(true)
   {
    System.out.println("Send req");
    socket=serverSocket.accept();
    System.out.println("Connected");
    dIn=new DataInputStream(socket.getInputStream());
    System.out.println("Waiting");
    header=new byte[9];
    dIn.readFully(header);
    bais=new ByteArrayInputStream(header);
    tempData=new DataInputStream(bais);
    tempData.readByte();
    size=tempData.readDouble();
    bais.reset();
    tempData.close();  

    dOut=new DataOutputStream(socket.getOutputStream());
    dOut.writeByte(1);
    dOut.flush();
    
    System.out.println((int)size);
    data=new byte[(int)size];
    dIn.readFully(data);
       
    bais1=new ByteArrayInputStream(data);
    ois=new ObjectInputStream(bais1);
    System.out.println("Accept state");
    treeEvent.setDefaultTreeModel((DefaultTreeModel)ois.readObject());
    System.out.println("Accepted");
    //while(treeEvent.getDefaultTreeModel()==null) this.sleep(1000);
    fileTransferUI.setClientDefaultTreeModel(treeEvent.getDefaultTreeModel());     
    ois.close();
    dOut.close();
    socket.close();

    break;   
   }
  }catch(Exception e)
  {
   e.printStackTrace();
  }
 }
}














/*while(true)
    {
     temp=dIn.readByte();
      if(temp==-1) break;
     bais.read(temp);
    }*/


    //System.out.println(data.length);
    /*ois=new ObjectInputStream(socket.getInputStream());
    //treeEvent.setDefaultTreeModel((DefaultTreeModel)ois.readObject());
    System.out.println("Client tree generated");
    //fileTransferUI=new FileTransferUI(treeEvent.getDefaultTreeModel());
    Test t=new Test((JTree)ois.readObject());*/