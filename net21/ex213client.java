package net21;
/* Alex Denney
 * Exercise 21-3 client
 * This program will be a client to send messages to a server
 */
import java.io.*;
import java.net.*;

public class ex213client 
{
    private int port;
    private InetAddress host;
    
    public ex213client()
    {
        try
        {
            port = 7777; // use port #7777 that server is waiting on 
            host = InetAddress.getByName("Cartographer");
            System.out.println(host);
    }
        catch(UnknownHostException u)
        {
            u.printStackTrace();
        }
    }
    
    public void run(String mes)
    {
        try
        {
        	 /* Create DatagramSocket, create DatagramPacket out of message and who
            the host and port are.  Send the packet.  Create byte array and the 
            DatagramPacket for the byte array.  Receive the incoming packet.  Get
            the message out of the packet and print it to the screen.  Close the socket*/
        	byte[] buffer = new byte[256];
            DatagramSocket client = new DatagramSocket();
            DatagramPacket outdgp = new DatagramPacket(mes.getBytes(), mes.length(), host, port);
            client.send(outdgp);
            DatagramPacket indgp = new DatagramPacket(buffer, buffer.length);
            client.receive(indgp);
            String rsp = new String(indgp.getData(), 0, indgp.getLength());
            System.out.println(rsp);
            client.close();
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
    }
    
    public static void main(String[] args) throws IOException
    {
        String line;
        BufferedReader br = new
            BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter a message:  ");
        line = br.readLine();
        ex213client c = new ex213client();
        c.run(line);  // call run method with string line to send to server
    }
}