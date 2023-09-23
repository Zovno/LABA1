import java.io.*;
import java.net.*;

public class MultiClientServer {
    public static void main(String[] args) 
    {
        bank b = new bank(10);
        final int portNumber = 1234;
        int clientCount = 0;

        try (ServerSocket serverSocket = new ServerSocket(portNumber)) 
        {
            System.out.println("Сервер запущен и ожидает подключения клиентов...");

            while (clientCount < 2) 
            {
                Socket clientSocket = serverSocket.accept();
                clientCount++;

                System.out.println("Клиент " + clientCount + " подключен.");

                ClientHandler client = new ClientHandler(clientSocket, clientCount, b);
                client.start();
            }

        } catch (IOException e) {
            System.err.println("Ошибка при работе с серверным сокетом: " + e.getMessage());
        }
    }
}

class ClientHandler extends Thread 
{
    private Socket clientSocket;
    private int clientNumber;
    private bank bbBank;

    public ClientHandler(Socket socket, int clientNumber, bank bbBank) 
    {

        this.clientSocket = socket;
        this.clientNumber = clientNumber;
        this.bbBank = bbBank;
    }

    public void run() 
    {
        try (PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())) 
        ) 
        {
            String inputLine;
            while ((inputLine = in.readLine()) != null) 
            {
                System.out.println("Клиент " + clientNumber + ": " + inputLine);
            
                if ("1".equals(inputLine)) 
                {
                    bbBank.addMoney();
                } 
                else if ("2".equals(inputLine)) 
                {
                    bbBank.decreaseMoney();
                } 
                else if ("3".equals(inputLine)) 
                {
                    out.println(bbBank.getMoney());
                } 
                else {
                    out.println("Сервер: Вы сказали чушь");
                }
            }
        }
        catch (IOException e) 
        {
            System.err.println("Ошибка при работе с клиентом " + clientNumber + ": " + e.getMessage());
            clientNumber--;
        }
    }

}