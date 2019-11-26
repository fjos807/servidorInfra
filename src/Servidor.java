

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

import org.json.JSONObject;


public class Servidor {
   private ServerSocket serverSocket;
   
   public Servidor(int puerto, int tamanoCola) throws IOException {
      serverSocket = new ServerSocket(puerto, tamanoCola);
   }

   public void run() throws InterruptedException{
      Socket socket; 
      while(true) {
         try {
            System.out.println("Esperando cliente en puerto: " + serverSocket.getLocalPort() + "...");
            // Esperar conexiones
            socket = serverSocket.accept();
            
            System.out.println("Se acaba de conectar: " + socket.getRemoteSocketAddress());
            boolean state = true;
            
            int tries = 0;
            
            while (!socket.isClosed()) {
            	DataInputStream in = new DataInputStream(socket.getInputStream());
            	DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            	if(in.available() != 0) {
            		System.out.println("++New read++");
            		
            		System.out.println(in.available());
            		//JSONParser parser = new JSONParser();
            		//Object obj = parser.parse(in.readUTF());
            		//JSONArray array = (JSONArray)obj;
            		
            	//	JSONParser parser = new JSONParser();
            	//	JSONObject json = (JSONObject) parser.parse(in.readUTF());
            		
            	//	String operation = (String) json.get("operation");
            		
            	//	System.out.println(operation);
            		
            		
                	//String message = in.readUTF();
                	//System.out.println("+Message: " + message);
                	//String[] parts = message.split(",");
              	   	//int operation = Integer.parseInt(parts[0]);
              	   	//String values = parts[1];	
            		
            		
            		JSONObject jsonObject = new JSONObject(in.readUTF());
            		String operation = jsonObject.getString("operation");
            		
            		switch(operation) 
                    { 
                        case "crear_usuario": 
                            System.out.println("Se crea el usuario");
                            JSONObject data = (JSONObject)jsonObject.getJSONObject("data");
                            System.out.println("Nombre: " + data.getString("username"));
                            System.out.println("Pass: " + data.getString("password"));
                            //System.out.println(data.toString());
                            
                            break; 
                        case "two": 
                            System.out.println("two"); 
                            break; 
                        case "three": 
                            System.out.println("three"); 
                            break; 
                        default: 
                            System.out.println("no match"); 
                    }
            		
            		
              	    
              	    out.writeUTF("copy");
            	} else {
            		tries ++;
            		if (tries >=500000) {
            			socket.close();
            		} else {
            			System.out.println("**************A dormir**************");
                		Thread.sleep(3000);
            		}
            		
            	}
            }
            
           
           // socket.close(); 
         } 
         
         catch (SocketTimeoutException s) {
            System.out.println("Socket timed out!");
            break;
         } 
         
         catch (IOException e) {
            e.printStackTrace();
            break;
         }
      }
   }
   
   public static void main(String[] args) throws InterruptedException{
      int puerto = 8067;
      int cola = 10;
      
      try {
         Servidor s = new Servidor(puerto, cola);
         s.run();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
}