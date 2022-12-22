package server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import relationnelle.Dissertation_Requete;

public class ServerThread extends Thread{
    Socket socket;
    ArrayList<ServerThread> threadList;
    PrintWriter out;

    public ServerThread(Socket sock, ArrayList<ServerThread> lists){
        socket = sock;
        threadList = lists;
    }
    public void run(){
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(),true);

            while(true){
                String output = in.readLine();
                if(output.equals("exit")){
                    break;
                }
                printToAll(output);
                System.out.println("Receive: "+output);
                String path = "E:/ITU/Bdd socket";
                Dissertation_Requete requete = new Dissertation_Requete(out,path);
                out.println(returnRequete(output));
                requete.Creation(returnRequete(output));
            }

        } catch (Exception e) {
            out.println("Error: "+e.getLocalizedMessage());
            // System.out.println("Error: "+e.getLocalizedMessage());
        }
    }

    public void printToAll(String output){
         for (ServerThread st : threadList) {
            st.out.println(output);
         }
    }

    public String returnRequete(String ligne){
        String[] temporaire = ligne.split(" ");
        String phrase = "";
        for (int i = 2; i < temporaire.length-1; i++) {
            // out.println(temporaire[i]);
            phrase = phrase+temporaire[i]+" ";
        }
        phrase = phrase+temporaire[temporaire.length-1];
        return phrase;
    }
    
}
