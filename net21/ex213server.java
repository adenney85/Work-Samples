package net21;
/* Alex Denney
 * Exercise 21-3 server
 * This program will be a server to receive messages from a client
 * using TCP/IP socket
 */
import java.io.*;
import java.net.*;

public class ex213server 
{
    private InetAddress host;
    private int port;
    
    public ex213server()
    {
        try
        {
            host = InetAddress.getLocalHost(); // use server computer as host
            port = 7777;  // wait on port #7777
            System.out.println("Server name is " + host.getHostName());
            System.out.println("Server address is " + host);
            System.out.println("Port is " + port);
        }
        catch(UnknownHostException u)
        {
            u.printStackTrace();
        }
    }
    public void run()
    {
        try
        {
        	 /* Create DatagramSocket connected to port, create byte array, create 
            DatagramPacket for byte array.  Wait to receive packet, then get the
            message and sender address from the packet.  Print to screen who the
            sender is, what the sender's name is, and what the message is.  Close
            the socket.
         */
        	DatagramSocket ss = new DatagramSocket(port);
        	byte[] buffer = new byte[256];
        	DatagramPacket indgp = new DatagramPacket(buffer, buffer.length);
        	ss.receive(indgp);
        	InetAddress senderaddress = indgp.getAddress();
        	String rsp = new String(indgp.getData(), 0, indgp.getLength());
            System.out.println(senderaddress);
            System.out.println(senderaddress.getHostName());
            System.out.println(rsp);
            String mes = "Message Received";
            DatagramPacket outdgp = new DatagramPacket(mes.getBytes(), mes.length(), senderaddress, port);
            ss.send(outdgp);
        	ss.close();
       }
      catch(SocketException ss)
      {
         ss.printStackTrace();
      }
      catch(IOException e)
      {
         e.printStackTrace();
      }
}
    public static void main(String[] args)
    {
        ex213server s = new ex213server();
        s.run();
    }
}