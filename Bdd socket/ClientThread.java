package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientThread extends Thread{
    Socket socket;
    BufferedReader input;

    public ClientThread(Socket sock) throws IOException{
        socket = sock;
        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

    }

    public void run(){
        try {
            while (true) {
                String response = input.readLine();
                System.out.println(response);
            }
        } catch (Exception e) {
            System.out.println("Thread1: "+e.getLocalizedMessage());
        }finally{
            try {
                input.close();
            } catch (Exception e) {
                System.out.println("Thread2: "+e.getLocalizedMessage());
            }
        }
    }
}