import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try (
    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {
            String clientAddress = clientSocket.getInetAddress().getHostAddress();
            System.out.println("Client connected: " + clientAddress);

            String requestLine = in.readLine();

            if (requestLine != null && requestLine.startsWith("GET")) {
                System.out.println("Received HTTP request: " + requestLine);

                String httpResponse =
                "HTTP/1.1 200 OK\r\n" +
                "Content-Type: text/html\r\n" +
                "Connection: close\r\n\r\n" +
                "<html><body style='font-family:sans-serif; text-align:center; margin-top:50px;'>" +
                "<h1>Hello, Web Client!</h1>" +
                "<p>This is a simple Java multithreaded web server </p>" +
                "</body></html>";

                out.write(httpResponse);
                out.flush();
            } else {
                System.out.println("Non-HTTP client connected or invalid request from " + clientAddress);
                out.println("Invalid request. Use a web browser to access this server.");
            }

        } catch (IOException e) {
            System.err.println("Error handling client: " + e.getMessage());
        } finally {
            try {
                if (clientSocket != null) {
                    clientSocket.close();
                    System.out.println("Client disconnected: " + clientSocket.getInetAddress().getHostAddress());
                }
            } catch (IOException e) {
                System.err.println("Error closing client socket: " + e.getMessage());
            }}}}
