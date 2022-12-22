package server;

import java.net.Socket;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.util.ArrayList;

public class ServerSocketMain{

    public static void main(String[] args) {
        // try {
        //     ServerSocket serverSocket = new ServerSocket(1234);
        //     Socket socket = serverSocket.accept();
        //     DataInputStream input = new DataInputStream(socket.getInputStream());
        //     ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
        //     System.out.println(input.readUTF());
        //     output.writeObject("Bye");
        //     output.close();
        //     output.flush();
        // } catch (Exception e) {
        //     e.getLocalizedMessage();
        // }
        ArrayList<ServerThread> threadList = new ArrayList<>();

        try(ServerSocket serversocket = new ServerSocket(1234)){
            while(true){
                Socket socket = serversocket.accept();
                ServerThread serverthread = new ServerThread(socket, threadList);

                threadList.add(serverthread);
                serverthread.start();
            }
        }catch(Exception e){
            System.out.println("Error in: "+e.getStackTrace());
        }

    }

}
