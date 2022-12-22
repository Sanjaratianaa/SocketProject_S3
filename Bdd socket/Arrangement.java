package base_operation;

import java.security.InvalidAlgorithmParameterException;
import java.util.*;

import relationnelle.*;

public class Arrangement{
    Relation relation1;
    Relation relation2;

    public void setRelation1(Relation Rel1){
        this.relation1=Rel1;
    }
    public void setRelation2(Relation Rel2){
        this.relation2=Rel2;
    }

    public Relation getRelation1(){return this.relation1;}
    public Relation getRelation2(){return this.relation2;}

    // public Arrangement(Relation R1, Relation R2){
    //     this.setRelation1(R1);
    //     this.setRelation2(R2);
    // }

    // UNION
    public Relation UnionArrangement(Relation relation1, Relation relation2){
        Relation temporaire=new Relation();

            Vector<Vector<String>> ensemble=new Vector<Vector<String>>();

            if(relation1.getRow().size() == relation2.getRow().size()){
                    ensemble = relation1.getRow();

                for(int u=0; u<relation2.getRow().size(); u++){
                    ensemble.add(relation2.getRow().get(u));
                }
            }else{}

        temporaire.setName(relation1.getName()+" et "+relation2.getName());
        temporaire.setCell(relation1.getCell());
        temporaire.setRow(ensemble);
        
        return temporaire;
    }

    // INTERSECTION
    public Relation IntersectionArrangement(Relation relation1, Relation relation2){
        Relation temporaire=new Relation();

        Vector<Vector<String>> ensemble=new Vector<Vector<String>>();

            for (int u=0; u<relation1.getRow().size(); u++) {
                for(int v=0; v<relation2.getRow().size(); v++){
                    if(relation1.getRow().get(u).containsAll(relation2.getRow().get(v)) == true){
                        ensemble.add(relation1.getRow().get(u));
                    }
                }
            }
        temporaire.setName(relation1.getName()+" et "+relation2.getName());
        temporaire.setCell(relation1.getCell());
        temporaire.setRow(ensemble);
        
        return temporaire;
    }

    // PROJECTION
    // return indice mitovy amle Cell vaovao
    public Vector<Integer> IndiceProjection(Vector<String> newCell, Relation A_Utilise){
        Vector<Integer> CellVaovao=new Vector<Integer>();
            for(int i=0; i<A_Utilise.getCell().size(); i++){
                for(int j=0; j<newCell.size(); j++){
                    if(A_Utilise.getCell().get(i).compareTo(newCell.get(j)) == 0){
                        CellVaovao.add(i);
                    }
                }
            }
        return CellVaovao;
    }

    public Relation ProjectionArrangement(Vector<String> newCell, Relation A_Utilise){
        Relation temporaire=new Relation();
        Vector<Integer> CellIndice=IndiceProjection(newCell, A_Utilise);
        Vector<Vector<String>> ensemble=new Vector<Vector<String>>();

        for (int i = 0; i < A_Utilise.getRow().size(); i++) {
            Vector<String> mpaka=new Vector<String>();
            for (int j = 0; j < CellIndice.size(); j++) {
                int indice = CellIndice.get(j);
                mpaka.add(A_Utilise.getRow().get(i).get(indice));
            }
            ensemble.add(mpaka);
        }
        
        temporaire.setName(A_Utilise.getName());
        temporaire.setCell(newCell);
        temporaire.setRow(ensemble);

        // System.out.println(ensemble);

        return temporaire;
    }

    // PRODUIT CARTESIEN
    public Relation ProduitCartesien(Relation rel1, Relation rel2){
        Relation temporaire = new Relation();

        Vector<Vector<String>> ensemble=new Vector<Vector<String>>();
        
        int u=0;

        while(u<rel1.getRow().size()){
            for(int i=0; i<rel2.getRow().size(); i++){
                Vector<String> mpaka = new Vector<String>();
                mpaka.addAll(rel2.getRow().get(i));
                mpaka.addAll(rel1.getRow().get(u));
                ensemble.add(mpaka);
            }
            u++;
        }

        temporaire.setName("Produit cartesien");
        temporaire.setCell(rel1.getCell());
        temporaire.setRow(ensemble);
        return temporaire;
    }

    // DIFFERENCE
    // public Vector<Integer> DiffInteger(Relation rel1, Relation rel2){
    //     Vector<Integer> tempVector = new Vector<Integer>();
    //     for (int i = 0; i < rel1.getCell().size(); i++) {
    //         for (int j = 0; j < rel2.getCell().size(); j++) {
    //             if(rel1.getCell().get(i).equals(rel2.getCell().get(j))){

    //             }
    //         }
    //     }

    //     return tempVector;
    // }

    public Relation Difference(Relation rel1, Relation rel2){
        Relation temporaire = new Relation(); 
        Vector<Vector<String>> ensemble = rel1.getRow();

        if(rel1.getCell().size() == rel2.getCell().size()){
            if(rel1.getCell().containsAll(rel2.getCell()) == true){
                Relation temp = IntersectionArrangement(rel1, rel2);
                // System.out.println(temp.getRow().get(0).get(0)+" et "+temp.getRow().get(0).get(0));
                for (int i = 0; i < rel1.getRow().size(); i++) {
                    for (int j = 0; j < temp.getRow().size(); j++) {
                        if(rel1.getRow().get(i).containsAll(temp.getRow().get(j)) == true){
                            // System.out.println(rel1.getRow().get(i));
                            ensemble.remove(i);
                        }
                    }
                    
                }
            }
        }

        temporaire.setName("Difference de "+rel1.getName()+" - "+rel2.getName());
        temporaire.setCell(rel1.getCell());
        temporaire.setRow(ensemble);
        return temporaire;
    }

    // DIVISION

    public Relation Division(Relation rel1, Relation rel2, String nomVaovao,Vector<String> utile){
        Relation tempo = new Relation();

        Relation relOperation1 = ProjectionArrangement(utile,rel1);
        Relation relOperation2 = ProduitCartesien(rel1,rel2);
        Relation relOperation3 = Difference(relOperation2,rel1);
        Relation relOperation4 = Difference(relOperation1,relOperation3);
        // tempo = relOperation4;

        tempo.setName(nomVaovao);
        tempo.setCell(relOperation4.getCell());
        tempo.setRow(relOperation4.getRow());

        return tempo;
    }


    // JOIN
    public Relation Join(Relation rel1, Relation rel2,String fitambarany){
        Relation temporaire = new Relation();
        int a=0;
        int b=0;

        for(int i=0; i<rel1.getCell().size(); i++){
            for(int j=0; j<rel2.getCell().size(); j++){
                if(rel1.getCell().get(i).equals(fitambarany) == true){
                    a = i;
                }else if(rel2.getCell().get(j).equals(fitambarany) == true){
                    b = j;
                }
            }
        }

        Vector<String> colomne = new Vector<>();
        for (int i = 0; i < rel1.getCell().size(); i++) {
            colomne.add(rel1.getCell().get(i));
        }

        for (int i = 0; i < rel2.getCell().size(); i++) {
            if(rel2.getCell().get(i).equals(fitambarany) == false){
                colomne.add(rel2.getCell().get(i));
            }
        }

        Vector<Vector<String>> ensemble = new Vector<Vector<String>>();

        for(int u=0; u<rel1.getRow().size(); u++){
            Vector<String> tempo = new Vector<String>();
            for(int v=0; v<rel2.getRow().size(); v++){
                if(rel1.getRow().get(u).get(a).compareTo(rel1.getRow().get(v).get(b)) == 0){
                    tempo = rel1.getRow().get(u);
                    for(int h=0; h<rel2.getRow().get(v).size(); h++){
                        if(tempo.contains(rel2.getRow().get(v).get(h)) == false){
                            tempo.add(rel2.getRow().get(v).get(h));
                        }
                    }
                }
            }
            ensemble.add(tempo);
        }

        temporaire.setName("Jointure Nat");
        temporaire.setCell(colomne);
        temporaire.setRow(ensemble);
        return temporaire;
    }

}
