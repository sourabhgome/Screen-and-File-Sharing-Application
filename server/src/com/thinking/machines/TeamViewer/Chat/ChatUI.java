
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;

public class ChatUI extends JFrame
{
 private Container c;
 private JPanel chat;
 private JTextField messageBox;
 private JButton sendButton;
 private JScrollPane scrollPane;
 private JLabel messageLabel;

 public ChatUI()
 {
  initComponents();
  setAppearance();
  addListeners();
 }
 
  public void initComponents()
  {
   c=getContentPane();
   chat=new JPanel();
   messageBox=new JTextField();
   sendButton=new JButton();
   scrollPane=new JScrollPane(chat);
  }
  
  public void setAppearance()
  {
   c.setLayout(null);
   chat.setBackground(Color.BLACK);
   chat.setLayout(new BorderLayout());
   scrollPane.setBounds(0,10,385,400);
   addLabel();
   c.add(scrollPane);
   messageBox.setBounds(10,410,310,30);
   c.add(messageBox);
   sendButton.setBounds(325,410,50,30);
   c.add(sendButton);
   setSize(400,500);
   setLocation(100,100);
   setVisible(true);
  }
  
  public void addLabel()
  {
   JLabel l1=new JLabel("Hello");
   l1.setForeground(Color.WHITE);
   JLabel l2=new JLabel("World");
   l2.setForeground(Color.WHITE);
   JLabel l3=new JLabel("Guys");
   l3.setForeground(Color.WHITE);
   chat.add(l1,BorderLayout.SOUTH);
   chat.add(l2,BorderLayout.SOUTH);
   chat.add(l3,BorderLayout.SOUTH);
  }
  
  public void addListeners()
  {
  } 
  
  public static void main(String gg[])
  {
   new ChatUI();
  }
} 