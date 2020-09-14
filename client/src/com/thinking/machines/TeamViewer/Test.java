package com.thinking.machines.TeamViewer.client;
import com.thinking.machines.TeamViewer.client.RemoteFileAccess.TreeTransfer.*;
public class Test
{
 public static void main(String gg[])
 {
  try
  {
   new TreeTransferClient("localhost");
  }catch(Exception e)
   {
    System.out.println(e);
   }
 }
}