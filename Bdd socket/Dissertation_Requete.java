package relationnelle;

import java.util.Vector;

import java.io.*;
import base_Operation.*;
import base_operation.Arrangement;

public class Dissertation_Requete{
    String mot_cle;
    // DataInputStream input;
    String nompath;
    PrintWriter output;

    public String getMot_cle() {
        return mot_cle;
    }
    public String getnompath(){
        return nompath;
    }
    public PrintWriter getOutput(){
        return output;
    }

    public void setMot_cle(String mot_cle) {
        this.mot_cle = mot_cle;
    }
    public void setnompath(String path) {
        this.nompath = path;
    }
    public void setOutput(PrintWriter output) {
        this.output = output;
    }

    public Dissertation_Requete(PrintWriter out,String path){
        this.setOutput(out);
        this.setnompath(path);
    }

    public String[] SplitParSlash(String phrase){
        String[] temporaire = phrase.split("/");
        return temporaire;
    }

    // public Relation PourSelection(String phrase)throws Exception{
    //     phrase = phrase.toLowerCase();

    //     String[] temporaire=phrase.split(" ");
    //     Arrangement arrangement = new Arrangement();
    //     Relation relation = new Relation();

    //     if(temporaire[3].equals("[") && temporaire[temporaire.length-1].equals("]")){
    //             String requete = temporaire[4];
    //             for (int i = 5; i < temporaire.length-1; i++) {
    //                 requete = requete+" "+temporaire[i];
    //             }
    //             relation = PourSelection(requete);
    //             String[] operation = temporaire[1].split(",");
    //             Vector<String> mamerinaStringHoVector = StringHoVector(operation);
    //             relation = Projection(mamerinaStringHoVector,relation.getName(), "taxibe", arrangement);
    //             // System.out.println(requete);
    //     }else{
    //         String[] operation = temporaire[1].split(",");
    //         Vector<String> mamerinaStringHoVector = StringHoVector(operation);
    //         relation = Projection(mamerinaStringHoVector, temporaire[temporaire.length-3], temporaire[temporaire.length-1], arrangement);
    //     }
    //     return relation;
    // }

    public Relation PourSelection(String phrase)throws Exception{
        phrase = phrase.toLowerCase();
        if(phrase.equals("exit")){
            return null;
        }
        String[] temporaire=phrase.split(" ");
        Arrangement arrangement = new Arrangement();
        Relation relation = new Relation();

                if(temporaire[3].equals("[") && temporaire[temporaire.length-1].equals("]")){
                    if(temporaire.length > 4){
                        String requete = temporaire[4];
                        for (int i = 5; i < temporaire.length-1; i++) {
                            requete = requete+" "+temporaire[i];
                        }
                        relation = PourSelection(requete);
                        String[] operation = temporaire[1].split(",");
                        Vector<String> mamerinaStringHoVector = StringHoVector(operation);
                        relation = Projection(mamerinaStringHoVector,relation.getName(), "taxibe", arrangement);
                        // this.getOutput().println(mamerinaStringHoVector);
                        // this.getOutput().println(relation.getName());
                        // System.out.println(requete);
                    }
                }else if(phrase.contains("join")){
                    relation = PourJoin(phrase, arrangement);
                }else if(phrase.contains("where")){
                    relation = SelectionCondition(phrase);
                }else if(temporaire[1].equals("*")){
                    relation  = selectionDonnees(temporaire[3],temporaire[temporaire.length-1]);
                }else if(temporaire[1].contains("[") == true && temporaire[1].contains("]")){
                    String[] operation = temporaire[1].split("[\\[\\]]");
                    relation = IntersectionAndUnion(operation,temporaire[temporaire.length-1],arrangement);
                    // relation = ProduitAndDifference(operation,temporaire[temporaire.length-1], arrangement);
                }else{
                    String[] operation = temporaire[1].split(",");
                    Vector<String> mamerinaStringHoVector = StringHoVector(operation);
                    relation = Projection(mamerinaStringHoVector, temporaire[temporaire.length-3], temporaire[temporaire.length-1], arrangement);
                }
        return relation;
    }

    public Relation PourJoin(String phrase,Arrangement arrangement)throws Exception{
        Relation joindre = new Relation();
        try {
            String[] temporaire = phrase.split(" ");
            Relation relation1 = this.selectionDonnees(temporaire[3], temporaire[temporaire.length-1]);
            Relation relation2 = this.selectionDonnees(temporaire[5], temporaire[temporaire.length-1]);
            joindre = arrangement.Join(relation1, relation2, temporaire[7]);
        } catch (Exception e) {
            this.getOutput().println("verifier les 2 tables please!");
        }
        return joindre;
    }

    public Relation SelectionCondition(String phrase)throws Exception{
        String[] fizarana = phrase.split(" ");
        Relation relation = this.selectionDonnees(fizarana[3], fizarana[fizarana.length-1]);
        String[] fizarana2 = fizarana[5].split("[\\[\\]]");
        String[] fizarana3 = SplitParSlash(fizarana2[1]);
        Vector<String[]> all = new Vector<>();
        for (int i = 0; i < fizarana3.length; i++) {
            String[] temporaire = fizarana3[i].split("=");
        // this.getOutput().println("nom : "+temporaire);
            all.add(temporaire);
        }
        Relation finale = scanTable(all, relation);
        // affichageMain(finale);
        // affichageTsara(finale);
        return finale;
    }

    public Relation scanTable(Vector<String[]> condition,Relation relation){
        Relation temporaire = new Relation();
        Vector<Integer> miverina = colonneIndice(condition, relation);
        Vector<Vector<String>> fitambarany = new Vector<Vector<String>>();
        for (int i = 0; i < relation.getRow().size(); i++) {
            for (int j = 0; j < miverina.size(); j++) {
                for (int j2 = 0; j2 < condition.size(); j2++) {
                    if(relation.getRow().get(i).get(miverina.get(j)).equals(condition.get(j2)[1])){
                        fitambarany.add(relation.getRow().get(i));
                    }
                }
            }
        }

        temporaire.setName(relation.getName());
        // this.getOutput().println("nom : "+relation.getRow());
        temporaire.setCell(relation.getCell());
        temporaire.setRow(fitambarany);
        return temporaire;
    }

    public Vector<Integer> colonneIndice(Vector<String[]> condition,Relation relation){
        Vector<Integer> temporaire=new Vector<>();
        for (int i = 0; i < condition.size(); i++) {
            for (int j = 0; j < relation.getCell().size(); j++) {
                if(condition.get(i)[0].equals(relation.getCell().get(j)) == true){
                    temporaire.add(j);
                }
            }
        }
        return temporaire;
    }

    public Vector<String> StringHoVector(String[] table){
        Vector<String> temporaire = new Vector<String>();
        for (int i = 0; i < table.length; i++) {
            temporaire.add(table[i]);
        }
        return temporaire;
    }

    //// pour l'intersection et l'Union
    public Relation IntersectionAndUnion(String[] operation,String nomBase,Arrangement arrangement)throws Exception{
        Relation relation = new Relation();
        String[] relations = SplitParSlash(operation[1]);
        Relation relation1 = this.selectionDonnees(relations[0], nomBase);
        Relation relation2 = this.selectionDonnees(relations[1], nomBase);
        if(operation[0].equals("union")){
            relation = arrangement.UnionArrangement(relation1,relation2);
            // affichageMain(relation);
        }else if(operation[0].equals("intersection")){
            relation = arrangement.IntersectionArrangement(relation1, relation2);
            // affichageMain(relation);
        }else if(operation[0].equals("produit")){
            relation = arrangement.ProduitCartesien(relation1, relation2);
        }else if(operation[0].equals("difference")){
            relation = arrangement.Difference(relation1, relation2);
        }
        return relation;
    }

    //// pour produit cartesien et difference
    public Relation ProduitAndDifference(String[] operation,String nomBase,Arrangement arrangement)throws Exception{
        Relation relation = new Relation();
        String[] relations = SplitParSlash(operation[1]);
        Relation relation1 = this.selectionDonnees(relations[0], nomBase);
        Relation relation2 = this.selectionDonnees(relations[1], nomBase);
        Vector<String> title = new Vector<>();
        if(operation[0].equals("produit")){
            relation = arrangement.ProduitCartesien(relation1, relation2);
            for (int i = 0; i < 2; i++) {
                title = relation1.getCell();
            }
            relation.setCell(title);
            // affichageTsara(relation);
        }else if(operation[0].equals("difference")){
            relation = arrangement.Difference(relation1, relation2);
            // affichageTsara(relation);
        }
        return relation;
    }

    //// pour projection
    public Relation Projection(Vector<String> colonnes,String nomTable,String nomBase,Arrangement arrangement)throws Exception{
        Relation relation = new Relation();
        Relation nalaina = this.selectionDonnees(nomTable, nomBase);
        relation = arrangement.ProjectionArrangement(colonnes, nalaina);
        //affichageMain(relation);
        return relation;
    }

    public void Creation(String phrase) throws Exception{
        phrase = phrase.toLowerCase();
        String[] temporaire=phrase.split(" ");

        if(temporaire[0].equals("create") == true){
            if(temporaire[1].equals("database") == true){
                this.getOutput().println("dossier ann");
                creationBase(temporaire);
            }else if(temporaire[1].equals("table") == true){
                this.getOutput().println("fichier kely zany");
                creationTable(temporaire);
            }
        }else if(temporaire[0].equals("insert") == true){
            insertion(temporaire);
            this.getOutput().println("insertion");
        }else if(temporaire[0].equals("select") == true){
            affichageTsara(PourSelection(phrase));
        }else{
            this.getOutput().println(phrase);
        }
    }


    public void creationBase(String[] requete) throws Exception{
        File folder = new File(this.getnompath()+"/"+requete[2]);
        if(folder.mkdir() == true){
            this.getOutput().println("Votre fichier a ete creer avec succees");
        }else{
            this.getOutput().println("tsy creer le base");
            throw new Exception("tsy creer le base");
        }
    }

    public void creationTable(String[] requete) throws Exception{
        try {
            String path = this.getnompath()+"/"+requete[requete.length-1]+"/";
            FileOutputStream out = new FileOutputStream(new File(""+path+requete[2]+".l"),true);
            String[] split1 = requete[3].split("[\\[\\]]");
            String[] split2 = split1[split1.length-1].split(",");
            for (int i = 0; i < split1.length; i++) {
                out.write(split2[i].getBytes());
                out.write("  ".getBytes());
            }
            this.getOutput().println("Votre table a ete creer avec succees dans "+requete[requete.length-1]);
            out.close();
        } catch (Exception e) {
            this.getOutput().println("tsy creer le table");
            throw new Exception("tsy creer le table");
        }
    }

    public String[] splitPourTout(String requete){
        String[] temporaire = requete.split(" ");
        return temporaire;
    }

    public void insertion(String[] requete) throws Exception{
        try {
            String path = this.getnompath()+"/"+requete[requete.length-1]+"/";
            FileOutputStream out = new FileOutputStream(new File(""+path+requete[2]+".l"),true);
            String[] split1 = requete[4].split("[\\[\\]]");
            String[] split2 = split1[split1.length-1].split(",");
            for (int i = 0; i < split2.length; i++) {
                out.write(split2[i].getBytes());
                // this.getOutput().println(split2[i]);
                out.write(" ".getBytes());
            }
            out.write("\n".getBytes());
            this.getOutput().println("Donnees inserer dans "+requete[requete.length-1]+"/"+requete[2]+".l");
            // System.out.println("coucou");
            out.close();
        } catch (Exception e) {
            // System.out.println(e);
            this.getOutput().println("Donnees tsy inserer");
            throw new Exception("Donnees tsy misy");
        }
    }

    //// pour les select dans un fichier
    public Relation selectionDonnees(String nomTable,String nomBase) throws Exception{
        Relation temporaire = new Relation();
        try {
            String path = this.getnompath()+"/"+nomBase+"/";
            BufferedReader in = new BufferedReader(new FileReader(""+path+nomTable+".l"));
            Vector<Vector<String>> temp = new Vector<Vector<String>>();
            String t = null;
            Vector<String[]> objets = new Vector<String[]>();
            while((t = in.readLine())!= null){
                String[] a = splitPourTout(t);
                objets.add(a);
            }
            /// pour colonnes
            Vector<String> anatiny = new Vector<String>();
            for (int i = 0; i < objets.get(0).length; i++) {
                anatiny.add(objets.get(0)[i]);
            }
            temporaire.setCell(anatiny);

            /// pour donnees
            for (int i = 1; i < objets.size(); i++) {
                Vector<String> te = new Vector<String>();
                for (int index = 0; index < objets.get(i).length; index++) {
                    te.add(objets.get(i)[index]);
                    // System.out.println(objets.get(i)[index]);
                }
                temp.add(te);
            }

            temporaire.setRow(temp);
            /// pour titre
            // this.getOutput().println(requete[3]);
            temporaire.setName(nomTable);

        } catch (Exception e) {
            // e.getStackTrace();
            this.getOutput().println("Votre requete n'est pas complet: select * from <nomTable> in <nomBase>");
            throw new Exception(e.getLocalizedMessage());
        }
        return temporaire;
    }

    /// juste pour l'affichage
    public void affichageMain(Relation relation) throws Exception{
        String title = "Table title: "+relation.getName();
        String nomColonne = " ";
        String ligne = " ";
        String total = " ";
        // this.getOutput().println(relation.getName());
        for (int i = 0; i < relation.getCell().size(); i++) {
            nomColonne = nomColonne.concat(relation.getCell().get(i)).concat("  |   ");
        }
        // this.getOutput().println(nomColonne);
        for (int i = 0; i < relation.getRow().size(); i++) {
            for (int j = 0; j < relation.getRow().get(i).size(); j++) {
                ligne = ligne.concat(relation.getRow().get(i).get(j)).concat("  |   ");
            }
            ligne = ligne.concat("\n");
        }
        if(relation.getRow().size() == 0){
            ligne = " Empty set(0)";
        }
        // this.getOutput().println("layah \n ndry");
        total = title+"\n"+nomColonne+"\n"+ligne;
        // this.getOutput().println();
        // System.out.println(nomColonne);
        this.getOutput().println(total);
        // // System.out.println(total);
        // // return total;
    }

    public void affichageTsara(Relation relation)throws Exception{
        this.getOutput().println("Table title: "+relation.getName());
            int countCol = relation.getCell().size();
            for (int j = 0; j < countCol; j++) {
                for (int i = 0; i < 15; i++) {
                    this.getOutput().print("-");
                }
                this.getOutput().print("+");
            }
            this.getOutput().println();
            for (String colonne : relation.getCell()) {
                this.getOutput().print(colonne);
                for (int i = colonne.length(); i < 15; i++) { this.getOutput().print(" "); }
                this.getOutput().print("|");
            }
            this.getOutput().println();
            for (int j = 0; j < countCol; j++) {
                for (int i = 0; i < 15; i++) {
                    this.getOutput().print("-");
                }
                this.getOutput().print("+");
            }
            this.getOutput().println();
            for (Vector<String> donnes : relation.getRow()) {
                for (String donne : donnes) {
                    if (donne==null) {donne="null";}
                    this.getOutput().print(donne);
                    for (int i = donne.length(); i < 15; i++) {
                        this.getOutput().print(" ");
                    }
                    this.getOutput().print("|");
                }
                this.getOutput().println();
                for(String donne : donnes){
                    for (int i = 0; i < 15; i++) {
                        this.getOutput().print("-");
                    }
                        this.getOutput().print("+");
                }
                this.getOutput().println();
            }
        }
}
