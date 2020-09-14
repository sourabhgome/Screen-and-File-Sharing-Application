package com.thinking.machines.TeamViewer.Events;

import java.awt.event.*;
import java.io.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class TreeEvents implements Events
{
 private DefaultTreeModel tree;
 private DefaultMutableTreeNode root;
 private double size;
 public byte getEventCode()
 {
  return 4;
 }
 public void setDefaultTreeModel(DefaultTreeModel tree)
 {
  this.tree=tree;
 }
 public DefaultTreeModel getDefaultTreeModel()
 {
  return this.tree;
 }
 public void setDefaultMutableTreeNode(DefaultMutableTreeNode root)
 {
  this.root=root;
 }
 public DefaultMutableTreeNode getDefaultMutableTreeNode()
 {
  return this.root;
 }
 public byte[] getHeader() throws IOException
 {
  ByteArrayOutputStream baos=new ByteArrayOutputStream();
  DataOutputStream dos=new DataOutputStream(baos);
  ByteArrayOutputStream tmp = new ByteArrayOutputStream();
  ObjectOutputStream oos=new ObjectOutputStream(tmp);
  oos.writeObject(tree);
  oos.flush();
  oos.reset();
  double size = tmp.size();
  dos.writeByte(4);
  dos.writeDouble(size);
  dos.flush();
  return baos.toByteArray();
 }
 public byte[] getByteArray() throws IOException
 {
  ByteArrayOutputStream baos=new ByteArrayOutputStream();
  ObjectOutputStream oos=new ObjectOutputStream(baos); 
  oos.writeObject(tree);
  oos.flush();
  oos.reset();
  return baos.toByteArray();
 }
}