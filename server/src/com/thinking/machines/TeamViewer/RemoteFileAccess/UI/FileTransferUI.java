package com.thinking.machines.TeamViewer.RemoteFileAccess.UI;
import com.thinking.machines.TeamViewer.RemoteFileAccess.TreeTransfer.*;
import com.thinking.machines.TeamViewer.RemoteFileAccess.FileTransfer.*;
import com.thinking.machines.TeamViewer.Events.*;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.JTree;
import java.io.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

public class FileTransferUI extends JFrame implements Runnable
{
 private JPanel serverPanel,clientPanel;
 private JLabel serverDrives,clientDrives;
 private JComboBox serverList,clientList;
 private JTree serverTree,clientTree;
 private JButton serverButton,clientButton;
 private JButton serverShow,clientShow;
 private JButton serverCreateFolderButton,clientCreateFolderButton;
 private JTextField serverCreateFolder,clientCreateFolder;
 private DefaultTreeModel serverSideTree,clientSideTree;
 private DefaultTreeModel tempServerTree,tempClientTree;
 private JScrollPane serverScrollPane,clientScrollPane;
 private File[] serverDrivesName;
 private String serverPath="",clientPath="";
 private boolean isServerTreeReady=false,isClientTreeReady=false;
 private DefaultMutableTreeNode[] clientDrivesName;
 private boolean s_isDirectory,s_isFile,c_isDirectory,c_isFile;
 private Container c;
 private Thread UI,checkActiveButton;
 private Selector selector;
 private FileQueue queue;
 private File serverFile,clientFile;
 private TreePath serverTreePath,clientTreePath;

 public FileTransferUI(FileQueue queue)
 {
  this.queue=queue;
  UI=new Thread(this);
  UI.start();
  checkActiveButton=new Thread(this);
  //setClientDefaultTreeModel(tree);
  //run();
 }

 public void run()
 {
  Thread temp=Thread.currentThread();
  if(temp==UI)
  {
   initComponents();
   setAppearance();
   addListeners();
  }
  else if(temp==checkActiveButton)
  {
   activateButtons();
  }
 }


 public void initComponents()
 {
  c=getContentPane();
  //checkActiveButton=new CheckActiveButton();
  selector=new Selector();
  serverPanel=new JPanel();
  clientPanel=new JPanel();
  serverDrives=new JLabel("Select Drive :");
  clientDrives=new JLabel("Select Drive :");
  serverList=new JComboBox();
  clientList=new JComboBox();
  serverShow=new JButton("Show");
  clientShow=new JButton("Show");
  serverButton=new JButton(">>");
  clientButton=new JButton("<<");
  serverCreateFolderButton=new JButton("Create Folder");
  clientCreateFolderButton=new JButton("Create Folder");
  serverCreateFolder=new JTextField();
  clientCreateFolder=new JTextField();
  System.out.println("Tree Model generation start");
  serverSideTree=TreeModelGenerator.generate();
  System.out.println("Tree Model generation end");
  isServerTreeReady=true;
 }


 public void setAppearance()
 {
  setLayout(null);
  initServerPanel();
  JSplitPane splitPane = new JSplitPane(SwingConstants.VERTICAL, serverPanel, clientPanel);
  splitPane.setBounds(0,0,1080,600);
  splitPane.setDividerLocation(540);
  splitPane.setOpaque(true);
  splitPane.setBackground(Color.BLACK);
  c.add(splitPane);
  c.setBackground(Color.BLACK);
  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  setSize(1100,650);
  setLocation(50,50);
  while(!isClientTreeReady||!isServerTreeReady){ try{UI.sleep(1000);}catch(Exception e){System.out.println(e);} }
  initClientPanel(); 
  setVisible(true);
 }








//****************************************************************



 public void initServerPanel()
 {
  serverPanel.setLayout(null);
  serverPanel.setBorder(BorderFactory.createLineBorder(Color.black));
  serverPanel.setBackground(Color.BLACK);
  serverButton.setBounds(450,50,50,20);
  
  serverButton.addActionListener(new ActionListener()
  {
   public void actionPerformed(ActionEvent e)
   {
    FileEvents fileEvent=new FileEvents();
    fileEvent.setCode(2);
    fileEvent.setFile(serverFile);
    fileEvent.setFileName(serverFile.getName());
    fileEvent.setFilePath(serverPath);
    fileEvent.setDirPath(clientPath);
    try{queue.add(fileEvent);}catch(Exception exception){System.out.println(exception);}
    System.out.println("Queue me add hua "+fileEvent);
    TreePath parentPath = clientTree.getSelectionPath();
    DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) (parentPath.getLastPathComponent());
    DefaultTreeModel treeModel=(DefaultTreeModel)clientTree.getModel();
    File temp=new File(parentPath+"\\"+serverFile.getName());
    treeModel.insertNodeInto(new DefaultMutableTreeNode(new FileNode(temp)),parentNode,0);
   }
  });
  
  serverPanel.add(serverButton);
  serverDrives.setForeground(Color.WHITE);
  serverDrives.setBackground(Color.BLACK);
  serverDrives.setBounds(50,30,100,20);
  serverPanel.add(serverDrives);
  serverList.setBounds(150,30,100,20);
  serverDrivesName= File.listRoots();
  for(File path:serverDrivesName)
   serverList.addItem(path);
  serverPanel.add(serverList);
  serverShow.setBounds(280,30,80,20);
  serverPanel.add(serverShow);
  serverCreateFolder.setBounds(50,70,200,20);
  serverPanel.add(serverCreateFolder);
  serverCreateFolderButton.setBounds(260,70,150,20);
  serverPanel.add(serverCreateFolderButton);
  serverCreateFolderButton.setEnabled(false);

  serverCreateFolder.getDocument().addDocumentListener(new DocumentListener() 
  {
   public void changedUpdate(DocumentEvent e) 
   {
    warn();
   }
   public void removeUpdate(DocumentEvent e) 
   {
    warn();
   }
   public void insertUpdate(DocumentEvent e) 
   {
    warn();
   }

   public void warn() 
   {
    if(serverCreateFolder.getText().length()>0)
    {
     serverCreateFolderButton.setEnabled(true);
    }
    else
    serverCreateFolderButton.setEnabled(false);
   }
  });

  serverCreateFolderButton.addActionListener(new ActionListener()
  {
   public void actionPerformed(ActionEvent e)
   {
    String dirPath="";
    DefaultMutableTreeNode parentNode=null;
    boolean bool=false;
    DefaultTreeModel treeModel=(DefaultTreeModel)serverTree.getModel();
    if(serverPath.length()<1)
    {
     DefaultMutableTreeNode node=(DefaultMutableTreeNode)serverSideTree.getRoot();
     int i=serverList.getSelectedIndex();
     parentNode=(DefaultMutableTreeNode)serverSideTree.getChild(node,i);
     File temp=((FileNode)((DefaultMutableTreeNode)serverSideTree.getChild(node,i)).getUserObject()).getFile();
     serverPath=temp.getPath();
     dirPath=serverPath+serverCreateFolder.getText();
     bool=true;
    }
    if(dirPath!="") 
    if(serverPath.substring(serverPath.length() - 1)=="\\") dirPath=serverPath+serverCreateFolder.getText();
    else dirPath=serverPath+"\\"+serverCreateFolder.getText();
    File dir=new File(dirPath);
    boolean b=dir.mkdir();
    if(b==true)
    {
     System.out.println(dirPath+" created successfully");
     try{new File(dir+"\\temp").createNewFile();}catch(Exception exp1){System.out.println(exp1);}
    }
    else System.out.println("Unsuccessful operation");
    DefaultMutableTreeNode kkk=new DefaultMutableTreeNode(new FileNode(dir));
    kkk.add(new DefaultMutableTreeNode(new String("temp")));
    TreePath parentPath = serverTree.getSelectionPath();
    if(!bool) parentNode = (DefaultMutableTreeNode) (parentPath.getLastPathComponent());
    treeModel.insertNodeInto(kkk,parentNode,0);
   }
  });

  serverShow.addActionListener(new ActionListener()
  {  
   public void actionPerformed(ActionEvent e)
   {
    if(serverScrollPane!=null)
    {
     serverPanel.remove(serverScrollPane);
     FileTransferUI.this.repaint(); 
     serverPanel.repaint();
    }
    DefaultMutableTreeNode node=(DefaultMutableTreeNode)serverSideTree.getRoot();
    int i=serverList.getSelectedIndex();
    tempServerTree=new DefaultTreeModel((DefaultMutableTreeNode)serverSideTree.getChild(node,i));
    tempServerTree.reload();
    serverTree=new JTree(tempServerTree);
    serverTree.addTreeSelectionListener(selector);

    tempServerTree.addTreeModelListener(new TreeModelListener()
    {
     public void treeNodesChanged(TreeModelEvent te)
     {
      FileTransferUI.this.repaint(); 
     } 	
     public void treeNodesInserted(TreeModelEvent te)
     {
      FileTransferUI.this.repaint(); 
     } 	
     public void treeNodesRemoved(TreeModelEvent te)
     {
      FileTransferUI.this.repaint(); 
     }
     public void treeStructureChanged(TreeModelEvent te)
     {
      System.out.println("Tree structure changed event Fired");
      FileTransferUI.this.repaint(); 
      serverPanel.repaint();
     }
    });
    serverScrollPane=new JScrollPane(serverTree);
    serverScrollPane.setBounds(10,100,520,500);
    serverPanel.add(serverScrollPane);
    serverScrollPane.updateUI();
    serverScrollPane.revalidate();
    serverScrollPane.repaint();
    serverPanel.revalidate();
    serverPanel.repaint();
    FileTransferUI.this.repaint(); 
   }  
  });  
 }








//*****************************************************************





 public void initClientPanel()
 {
  clientPanel.setLayout(null);
  clientPanel.setBorder(BorderFactory.createLineBorder(Color.black));
  clientPanel.setBackground(Color.BLACK);
  clientButton.setBounds(40,50,50,20);
  
  clientButton.addActionListener(new ActionListener()
  {
   public void actionPerformed(ActionEvent e)
   {
    FileEvents fileEvent=new FileEvents();
    fileEvent.setCode(1);
    fileEvent.setFile(clientFile);
    fileEvent.setFileName(clientFile.getName());
    fileEvent.setFilePath(clientPath);
    fileEvent.setDirPath(serverPath);
    try{queue.add(fileEvent);}catch(Exception exception){System.out.println(exception);}
    TreePath parentPath = serverTree.getSelectionPath();
    DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) (parentPath.getLastPathComponent());
    DefaultTreeModel treeModel=(DefaultTreeModel)serverTree.getModel();
    File temp=new File(parentPath+"\\"+clientFile.getName());
    treeModel.insertNodeInto(new DefaultMutableTreeNode(new FileNode(temp)),parentNode,0);
   }
  });
  
  
  clientPanel.add(clientButton);
  clientDrives.setForeground(Color.WHITE);
  clientDrives.setBackground(Color.BLACK);
  clientDrives.setBounds(170,30,100,20);
  clientPanel.add(clientDrives);
  clientList.setBounds(270,30,100,20);
  clientCreateFolder.setBounds(120,70,200,20);
  clientPanel.add(clientCreateFolder);
  clientCreateFolderButton.setBounds(330,70,150,20);
   clientCreateFolderButton.setEnabled(false);
  clientPanel.add(clientCreateFolderButton);


  clientCreateFolder.getDocument().addDocumentListener(new DocumentListener() 
  {
   public void changedUpdate(DocumentEvent e) 
   {
    warn();
   }
   public void removeUpdate(DocumentEvent e) 
   {
    warn();
   }
   public void insertUpdate(DocumentEvent e) 
   {
    warn();
   }

   public void warn() 
   {
    if(clientCreateFolder.getText().length()>0)
    {
     clientCreateFolderButton.setEnabled(true);
    }
    else
    clientCreateFolderButton.setEnabled(false);
   }
  });

  clientCreateFolderButton.addActionListener(new ActionListener()
  {
   public void actionPerformed(ActionEvent e)
   {
    String dirPath="";
    DefaultMutableTreeNode parentNode=null;
    boolean b=false;
    DefaultTreeModel treeModel=(DefaultTreeModel)clientTree.getModel();
    if(clientPath.length()<1)
    {
     DefaultMutableTreeNode node=(DefaultMutableTreeNode)clientSideTree.getRoot();
     int i=clientList.getSelectedIndex();
     parentNode=(DefaultMutableTreeNode)clientSideTree.getChild(node,i);
     File temp=((FileNode)((DefaultMutableTreeNode)clientSideTree.getChild(node,i)).getUserObject()).getFile();
     clientPath=temp.getPath();
     dirPath=clientPath+clientCreateFolder.getText();
     b=true;
    }
    if(dirPath!="") 
    if(clientPath.substring(clientPath.length() - 1)=="\\") dirPath=clientPath+clientCreateFolder.getText();
    else dirPath=clientPath+"\\"+clientCreateFolder.getText();
    FileEvents fe=new FileEvents();
    File dir=new File(dirPath);
    fe.setCode(3);
    fe.setFile(dir);
    fe.setDirPath(dirPath);
    try{queue.add(fe);}catch(Exception exception){System.out.println(exception);}
    DefaultMutableTreeNode kkk=new DefaultMutableTreeNode(new FileNode(dir));
    kkk.add(new DefaultMutableTreeNode(new String("temp")));
    TreePath parentPath = clientTree.getSelectionPath();
    if(!b) parentNode = (DefaultMutableTreeNode) (parentPath.getLastPathComponent());
    treeModel.insertNodeInto(kkk,parentNode,0);
   }
  });

 
  DefaultMutableTreeNode root=(DefaultMutableTreeNode)clientSideTree.getRoot();
  int count=clientSideTree.getChildCount(root);
  int x;
  clientDrivesName=new DefaultMutableTreeNode[count];
  for(x=0;x<count;x++) clientDrivesName[x]=(DefaultMutableTreeNode)clientSideTree.getChild(root,x);
  for(DefaultMutableTreeNode path:clientDrivesName)
  {
   clientList.addItem(path);
   //System.out.println(path);
  }
  clientPanel.add(clientList);


  clientShow.setBounds(400,30,80,20);
  clientPanel.add(clientShow);

  clientShow.addActionListener(new ActionListener()
  {  
   public void actionPerformed(ActionEvent e)
   {
    if(clientScrollPane!=null)
    {
     clientPanel.remove(clientScrollPane);
     FileTransferUI.this.repaint(); 
     clientPanel.repaint();
    }
    DefaultMutableTreeNode node=(DefaultMutableTreeNode)clientSideTree.getRoot();
    int i=clientList.getSelectedIndex();
    tempClientTree=new DefaultTreeModel((DefaultMutableTreeNode)clientSideTree.getChild(node,i));
    tempClientTree.reload();
    clientTree=new JTree(tempClientTree);
    clientTree.addTreeSelectionListener(selector);

    tempClientTree.addTreeModelListener(new TreeModelListener()
    {
     public void treeNodesChanged(TreeModelEvent te)
     {
      FileTransferUI.this.repaint(); 
     } 	
     public void treeNodesInserted(TreeModelEvent te)
     {
      FileTransferUI.this.repaint(); 
     } 	
     public void treeNodesRemoved(TreeModelEvent te)
     {
      FileTransferUI.this.repaint(); 
     }
     public void treeStructureChanged(TreeModelEvent te)
     {
      System.out.println("Tree structure changed event Fired");
      FileTransferUI.this.repaint(); 
      clientPanel.repaint();
     }
    });
    clientScrollPane=new JScrollPane(clientTree);
    clientScrollPane.setBounds(10,100,500,500);
    clientPanel.add(clientScrollPane);
    clientScrollPane.updateUI();
    clientScrollPane.revalidate();
    clientScrollPane.repaint();
    clientPanel.revalidate();
    clientPanel.repaint();
    FileTransferUI.this.repaint();
   }
  }); 
  checkActiveButton.start();
 }


 public void addListeners()
 {
 }


 /*public void setClientDrivesName(File[] cDrives)
 {
  this.clientDrivesName=cDrives;
 }*/


 public void setClientDefaultTreeModel(DefaultTreeModel tree)
 {
  this.clientSideTree=tree;
  isClientTreeReady=true;
 }

 private class Selector implements TreeSelectionListener 
 { 
  public void valueChanged(TreeSelectionEvent e) 
  {
   JTree tree=(JTree)e.getSource();
   if(tree==serverTree)
   {
    TreePath treepath = e.getPath();
    serverTreePath=treepath;
    File temp=((FileNode)((DefaultMutableTreeNode)treepath.getLastPathComponent()).getUserObject()).getFile();
    serverFile=temp;
    if(temp.isDirectory())
    {
     s_isDirectory=true;
     serverPath=temp.getPath();
     s_isFile=false;
    }
    else if(temp.isFile())
    {
     s_isFile=true;
     serverPath=temp.getPath();
     s_isDirectory=false;
    }
   }
   else if(tree==clientTree)
   {
    TreePath treepath = e.getPath();
    clientTreePath=treepath;
    File temp=((FileNode)((DefaultMutableTreeNode)treepath.getLastPathComponent()).getUserObject()).getFile();
    clientFile=temp;
    if(temp.isDirectory())
    {
     c_isDirectory=true;
     clientPath=temp.getPath();
     c_isFile=false;
    }
    else if(temp.isFile())
    {
     c_isFile=true;
     clientPath=temp.getPath();
     c_isDirectory=false;
    } 
   }
  }
 }
 

 public void activateButtons()
 {
  while(true)
  {
   if(s_isDirectory && c_isFile) clientButton.setEnabled(true);
   else if(s_isFile && c_isDirectory) serverButton.setEnabled(true);
   else
   {
    serverButton.setEnabled(false);
    clientButton.setEnabled(false);
   }
   try{checkActiveButton.sleep(500);}catch(Exception e){System.out.println(e);}
  }
 }

}



















  /*serverList.addItemListener(new ItemListener() 
  {
   public void itemStateChanged(ItemEvent arg0) 
   {
    DefaultMutableTreeNode node=(DefaultMutableTreeNode)serverSideTree.getRoot();
    int i=serverList.getSelectedIndex();
    System.out.println(i+" index selected");
    serverTree=new JTree((DefaultMutableTreeNode)serverSideTree.getChild(node,i));
    System.out.println("Client Tree initialised");
    JScrollPane scrollPane=new JScrollPane(serverTree);
    scrollPane.setBounds(10,100,400,500);
    System.out.println("Bounds set");
    serverPanel.add(serverTree);
    System.out.println("Added to panel");
   }
  });*/



/*private class Selector implements TreeSelectionListener 
 { 
  public void valueChanged(TreeSelectionEvent e) 
  {
   JTree tree=(JTree)e.getSource();
   if(tree==serverTree)
   {
    TreePath treepath = e.getPath();
    Object elements[] = treepath.getPath();
    int n = elements.length;
    File temp=((FileNode)((DefaultMutableTreeNode)treepath.getLastPathComponent()).getUserObject()).getFile();
    if(temp.isDirectory())
    {
     System.out.println("Is directory");
     for (int i = 0; i < n; i++) 
     {
      //System.out.print(value);
      serverPath+=elements[i]+"\\";
     } 
     System.out.println(serverPath);
    }
    if(((File)elements[n-1]).isFile())
    {
     System.out.println("Is file");
    }
   }
   else if(tree==clientTree)
   {
     
   }
  }
 }
*/