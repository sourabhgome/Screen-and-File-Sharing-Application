package com.thinking.machines.TeamViewer.RemoteFileAccess.TreeTransfer;
import com.thinking.machines.TeamViewer.RemoteFileAccess.FileTransfer.*;
import com.thinking.machines.TeamViewer.Events.*;

import java.io.*;
import java.net.*;
import java.awt.*;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

public class TreeTransferClient
{
 private String server;
 public TreeTransferClient(String server)
 {
  this.server=server;
  run();
 }
 public void run()
 {
  startSending();
 }
 public void startSending()
 {
  try
  {
   final int serverPort=6060;
   TreeEvents treeEvent=new TreeEvents();
   FileTransferClient ftc=new FileTransferClient(server);
   DataInputStream dIn;
   DataOutputStream dOut;
   ByteArrayOutputStream baos;
   //ObjectOutputStream oos;
   Socket socket;
   byte[] header;
   JTree tree;
   while(true)
   {
    System.out.println("estabilishing connection with serverPort : "+serverPort);
    socket=new Socket(server,serverPort);
    System.out.println(socket.getInetAddress());
    System.out.println("Fetching Tree");
    Thread t1=new Thread(new Runnable()
    {
     public void run()
     {
      treeEvent.setDefaultTreeModel(TreeModelGenerator.generate());
     }
    });
    t1.start();
    t1.join(0);
    System.out.println("Tree fetched");
    baos=new ByteArrayOutputStream();
    header=treeEvent.getHeader();
    dOut=new DataOutputStream(socket.getOutputStream());
    dOut.write(header);
    dOut.flush();
    
    dIn=new DataInputStream(socket.getInputStream());
    if(dIn.read()!=1)
    {
     System.out.println("Acknowledgement not recived");
     break;
    }
    System.out.println("Sending state");
    System.out.println(treeEvent.getByteArray().length);
    dOut.write(treeEvent.getByteArray());
    System.out.println("Tree sent");
    dOut.flush();
    dOut.close();
    socket.close();
    break;
   }
  }catch(Exception e)
   {
    System.out.println(e);
   }
 }
}