package net21;
/* Alex Denney
 * Exercise 21-2 client
 * This program will be a client to send messages to a server
 */
import java.io.*;
import java.net.*;

public class ex212client 
{
    private int port;
    private InetAddress host;
    
    public ex212client()
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
     /* Create socket connected to host on port.  Create PrintWriter connected to
        socket and print message over socket.  Create BufferedReader connected to
        socket and print the string read over the socket to the screen.  Close
        the socket.
     */
        	Socket client = new Socket(host, port);
        	PrintWriter pw = new PrintWriter(client.getOutputStream(), true);
            BufferedReader br = new BufferedReader
            		(new InputStreamReader(client.getInputStream()));
            pw.println(mes);
            String lineread = br.readLine();
            System.out.println(lineread);
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
        
        ex212client c = new ex212client();
        c.run(line);  // call run method with string line to send to server
    }
}