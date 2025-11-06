

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
private static final int PORT = 12345; 
private static final int THREAD_POOL_SIZE = 100; 

public static void main(String[] args) {

ExecutorService pool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
ServerSocket serverSocket = null;

        try {
        
serverSocket = new ServerSocket(PORT);
System.out.println("Server is listening on port " + PORT + "...");


while (true) {

Socket clientSocket = serverSocket.accept(); 


pool.execute(new ClientHandler(clientSocket));
            }

        } catch (IOException e) {
            System.err.println("Could not listen on port " + PORT);
            System.err.println("Error: " + e.getMessage());
        } finally {
        
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    System.err.println("Error closing server socket: " + e.getMessage());
                }}

            pool.shutdown(); 
        }}}