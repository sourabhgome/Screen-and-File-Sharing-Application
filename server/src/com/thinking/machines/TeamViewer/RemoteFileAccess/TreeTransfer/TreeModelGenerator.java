package com.thinking.machines.TeamViewer.RemoteFileAccess.TreeTransfer;

import java.io.File;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class TreeModelGenerator extends Thread
{
 private TreeModelGenerator(){}
 public static DefaultTreeModel generate()
 {
  File[] drives=File.listRoots();
  DefaultMutableTreeNode root=new DefaultMutableTreeNode("root");
  DefaultTreeModel tree;
  TreeNodeGenerator generator=new TreeNodeGenerator();
  for(File drive:drives)
  {
   System.out.println(drive+" drive generation start");
   root.add(generator.generate(drive));
   System.out.println(drive+" drive generation end");
  }
  tree=new DefaultTreeModel(root);
  return tree;
 }
}