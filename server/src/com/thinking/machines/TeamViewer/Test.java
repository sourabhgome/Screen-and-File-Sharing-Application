package com.thinking.machines.TeamViewer.server;
import com.thinking.machines.TeamViewer.server.RemoteFileAccess.TreeTransfer.*;
public class Test
{
 public static void main(String gg[])
 {
  try
  {
   new TreeTransferServer();
  }catch(Exception e)
   {
    System.out.println(e);
   }
 }
}