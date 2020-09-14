package com.thinking.machines.TeamViewer.RemoteFileAccess.TreeTransfer;

import java.io.File;
import java.util.*;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class TreeNodeGenerator
{
 private DefaultMutableTreeNode root;
 private DefaultTreeModel treeModel; 
 private File fileRoot;
 public DefaultMutableTreeNode generate(File fileRoot)
 {
  this.fileRoot=fileRoot;
  root=new DefaultMutableTreeNode(new FileNode(fileRoot));
  treeModel = new DefaultTreeModel(root);
  new CreateChildNodes(fileRoot, root);
  return root;
 }
}
class CreateChildNodes extends Thread
{
 private DefaultMutableTreeNode root;
 private File fileRoot;
 private Thread child;
 private ArrayList<Thread> activeThreads;
 public CreateChildNodes(File fileRoot,DefaultMutableTreeNode root)
 {
  activeThreads=new ArrayList<>();
  this.fileRoot=fileRoot;
  this.root=root;
  //createChildren(fileRoot, root);
  try
  {
   //System.out.println(Thread.activeCount());
   new Thread(this).start();
   //System.out.println(Thread.activeCount());
   this.sleep(1000);
   int i;
   int count=0;
   while(true)
   {
    count=0;
    for(i=0;i<activeThreads.size();i++)
    {
     if(activeThreads.get(i).isAlive())
     {
      count++;
      this.sleep(1000);
     }
    }
    if(count==0) break;
   } 
  }catch(Exception e) {System.out.println(e);} 
 }
 public void run()
 {
  activeThreads.add(Thread.currentThread());
  createChildren(fileRoot, root);
 }
 synchronized public void createChildren(File fileRoot,DefaultMutableTreeNode node)
 {
  try
  { 
   File[] files=fileRoot.listFiles();
   if(files==null) return;
   for(File file : files)
   {
    //System.out.println(file);
    FileNode n=new FileNode(file);
    DefaultMutableTreeNode childNode=new DefaultMutableTreeNode(new FileNode(file));
    node.add(childNode);
    if(file.isDirectory())
    {
     createChildren(file, childNode);
     //System.out.println("Notify kiya");
     //this.notify();
    }
   }
  }catch(Exception e)
   {System.out.println(e);}
 }
}



/*while(Thread.activeCount()>4)
    {
     //System.out.println("Thread Active");
     this.sleep(1000);
    }*/