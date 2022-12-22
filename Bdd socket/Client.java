package client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client{
    String host;
    int port;

    public String gethost() {
        return host;
    }
    public int getport() {
        return port;
    }

    public void sethost(String host) {
        this.host = host;
    }
    public void setport(int port) {
        this.port = port;
    }

    public static void main(String[] args) {
        System.out.print("Host : ");
        Scanner scanHost = new Scanner(System.in);
        String host = scanHost.nextLine();
        System.out.print("Port : ");
        Scanner scanPort = new Scanner(System.in);
        int port = Integer.parseInt(scanPort.nextLine());
        Client client = new Client();
        client.sethost(host);
        client.setport(port);
        System.out.println("Bienvenue dans SGBD by moi sur :");
        System.out.println("      Host >> "+client.gethost());
        System.out.println("      Port >> "+client.getport());
        try(Socket socket = new Socket(client.gethost(),client.getport())){
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
            Scanner scanner = new Scanner(System.in);
            String user;
            String ClientName=" ";
            ClientThread clientThread = new ClientThread(socket);
            clientThread.start();
            do{
                if(ClientName.equals(" ")){
                    System.out.println("Enter your name:");
                    user = scanner.nextLine();
                    ClientName = user;
                    // out.println(user);
                    if(user.equals("exit")){
                        break;
                    }

                }else{
                    String message = (">>SGBD : ");
                    user = scanner.nextLine();
                    if(!user.equals(" ")){
                        out.println(message+user);
                        if(user.equals("exit")){
                            break;
                        }
                    }
                }
            }while(!user.equals("exit"));
        } catch (Exception e) {
            System.out.println("Exception occured in class: "+e.getMessage());
        }
    }
}