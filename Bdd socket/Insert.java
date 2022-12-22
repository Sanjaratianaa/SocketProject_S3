package relationnelle;

import java.io.*;

public class Insert {

    public void insertion(String[] requete){
        try {
            String path = "E:/ITU/Semestre 3/BDD/Exercices/Creation BDD/"+requete[requete.length-1]+"/";
            FileOutputStream out = new FileOutputStream(new File(""+path+requete[2]+".l"),true);
            String[] split1 = requete[4].split("[\\[\\]]");
            String[] split2 = split1[split1.length-1].split(",");
            out.write("//".getBytes());
            for (int i = 0; i < split1.length; i++) {
                out.write(split2[i].getBytes());
                out.write("  ".getBytes());
            }
            System.out.println("tafiditra kou");
            out.close();
        } catch (Exception e) {
            System.out.println(e);
            // TODO: handle exception
        }
    }

}
