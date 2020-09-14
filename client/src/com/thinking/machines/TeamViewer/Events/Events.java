package com.thinking.machines.TeamViewer.Events;

import java.io.*;
public interface Events
{
 public byte getEventCode();
 public byte[] getByteArray() throws IOException;
 public byte[] getHeader() throws IOException;
}