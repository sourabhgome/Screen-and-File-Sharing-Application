package com.thinking.machines.TeamViewer.MainFrame;
import com.thinking.machines.TeamViewer.Monitoring.*;
import com.thinking.machines.TeamViewer.RemoteFileAccess.TreeTransfer.*;

import javax.swing.*;
import java.awt.*;
import javax.swing.event.*;
import java.awt.event.*;
public class ServerUI extends JFrame implements ActionListener
{
 private Container c;
 //private JLabel ipAddressLabel;
 //private JTextField ipAddress;
 private JButton connect;
 private ButtonGroup buttonGroup;
 private JRadioButton monitoring,fileAccess;
 public ServerUI()
 {
  initComponents();
  setAppearance();
  addListeners();
 }
 public void initComponents()
 {
  c=getContentPane();
  //ipAddressLabel=new JLabel("IP Address : ");
  //ipAddress=new JTextField(20);
  buttonGroup=new ButtonGroup();
  monitoring=new JRadioButton("Monitoring",true);
  fileAccess=new JRadioButton("Remote File Access",false);
  buttonGroup.add(monitoring);
  buttonGroup.add(fileAccess);
  connect=new JButton("Connect");
 }
 
 public void setAppearance()
 {
  c.setLayout(null);

  /*ipAddressLabel.setForeground(Color.WHITE);
  ipAddressLabel.setBounds(50,80,100,30); 

  ipAddress.setBounds(140,80,180,30);*/

  monitoring.setForeground(Color.WHITE);
  monitoring.setBackground(Color.BLACK);
  monitoring.setBounds(100,150,150,20);

  fileAccess.setForeground(Color.WHITE);
  fileAccess.setBackground(Color.BLACK);
  fileAccess.setBounds(100,180,150,20);

  connect.setBounds(100,250,100,30); 

  //c.add(ipAddressLabel);
  //c.add(ipAddress);
  c.add(monitoring);
  c.add(fileAccess);
  c.add(connect); 
  c.setBackground(Color.BLACK);
  setDefaultCloseOperation(EXIT_ON_CLOSE);
  setSize(400,500);
  setLocation(50,50);
  setVisible(true);
 }
 
 public void addListeners()
 {
  connect.addActionListener(this);
 }
 public void actionPerformed(ActionEvent e)
 {
  if (monitoring.isSelected()) 
  { 
   this.setVisible(false);
   this.dispose();
   try
   {
    new Server1();
   }catch(Exception exception) {System.out.println(exception);}
  } 
  else if (fileAccess.isSelected()) 
  {
   this.setVisible(false);
   this.dispose();
   try
   {
    new TreeTransferServer();
   }catch(Exception exception) {System.out.println(exception);} 
  } 
  else 
  {
  }
 }
}