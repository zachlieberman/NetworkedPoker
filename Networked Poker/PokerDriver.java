import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.*;
import java.util.*;
import java.io.*;
import java.lang.Object;

public class PokerDriver
{
   public static JFrame f;
   public static PokerTable screen;
   public static boolean readyServer = false;
   public static boolean readyClient = false;
 
   public static String ipAddress = null;
   public static boolean is_server = true;
   
   public static void main(String[] args) throws IOException
   {
      screen = new PokerTable();
      f = new JFrame("Poker!");
      f.setExtendedState(JFrame.MAXIMIZED_BOTH);
      f.setUndecorated(true);
      f.setLocation(0,0);
      f.setContentPane(screen);
      f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      f.setVisible(true);
      
      
      if (args.length > 0)
      {
         //this means that they are running as client
         is_server = false;        
      }
      else
      {
         //this means that they are running as server      
         is_server = true;      
      }  
      
      SocketThread thread;      
      
      if (args.length > 0)
      {
         //this means that they are running as client       
         ipAddress = new String(args[0]);
         thread = new client_thread(ipAddress, screen);
      }
      else
      {
         //this means that they are running as server          
         thread = new server_thread(screen);
      }      
      thread.start();
      
      f.addKeyListener(new Listen(thread));  
   
      
      
   }
   
   public static class Listen implements KeyListener
   {
      public SocketThread thread;
            
      public Listen(SocketThread t)
      {
         thread = t;
      }
      
      public void keyTyped(KeyEvent e)
      {
      }
         
      public void keyPressed(KeyEvent e) 
      {
                    
      }
      
      public void keyReleased(KeyEvent e)
      {
         int input = e.getKeyCode();
         {         
            screen.processUserInput(input, is_server);
            try
            {
               thread.write(input);
            } 
            catch (Exception ee){
               System.out.println(ee);
            }              
         } 
      }
   
   }
   
   public static class server_thread extends SocketThread
   {
      public InputStream inStream = null;     
      public OutputStream outStream = null;
      public PokerTable screen;
      
      public server_thread(PokerTable s)
      {
         screen = s;
      }
      
      public void write(int input) throws IOException
      {
         byte [] buffer = new byte[1];
         buffer[0] = (byte)input;
         if (outStream != null)
            outStream.write(buffer);
      }
      
      public void run()
      {
         try {
            // new object                this is the port \/ 
            ServerSocket serverSocket = new ServerSocket(9090);
            
            //allows for more then 1 connection 
            while(true)
            {
               //creates the client connection
               Socket clientSocket = serverSocket.accept();
               System.out.println("Client Connected");
               // reads characters coming in
               try 
               {
                  byte [] buffer = new byte[1];
                  int size;
                  int input;
                  
                  inStream = clientSocket.getInputStream();                  
                  outStream = clientSocket.getOutputStream();
                  while(true)
                  { 
                     size = inStream.read(buffer);
                     if (size == 1)
                     {               
                        input = (int)buffer[0];                    
                        screen.processUserInput(input, false);
                     }                  
                  }
               } 
               catch (Exception e){
                  System.out.println(e);
               }     
               
            }
         } 
         catch (Exception e){
            System.out.println(e); 
         }  
      }
   }   
   
   
   public static class client_thread extends SocketThread
   {
      public InputStream inStream = null;     
      public OutputStream outStream = null;
      public PokerTable screen;
      public String ipaddress;
      
      public client_thread(String ip, PokerTable s)
      {
         screen = s;
         ipaddress = new String(ip);
      }
      
      public void write(int key) throws IOException
      {
         byte [] buffer = new byte[1];
         buffer[0] = (byte)key;
         if (outStream != null)
            outStream.write(buffer);
      }
      
      public void run()
      {
         try {
            // new object            this is the port \/ 
            Socket clientSocket = new Socket(ipaddress, 9090);
         
            byte [] buffer = new byte[1];
            int size;
            int input;
            
            inStream = clientSocket.getInputStream();
            outStream = clientSocket.getOutputStream();
            while(true)
            { 
               size = inStream.read(buffer);
               if (size == 1)
               {               
                  input = (int)buffer[0];                    
                  screen.processUserInput(input, true);
               }                  
            }
         } 
         catch (Exception e){
            System.out.println(e);
         }     
      }
   }  
   
}