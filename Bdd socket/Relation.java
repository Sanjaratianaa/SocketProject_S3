package relationnelle;

import java.util.Vector;

import javax.management.relation.RelationSupport;

public class Relation{
    String Name;
    Vector<String> Cell;
    Vector<Vector<String>> Row;

    public void setName(String nom){
        this.Name=nom;
    }
    public void setCell(Vector<String> colonne){
        this.Cell=colonne;
    }
    public void setRow(Vector<Vector<String>> ligne){
        this.Row=ligne;
    }

    public String getName(){return this.Name;}
    public Vector<String> getCell(){return this.Cell;}
    public Vector<Vector<String>> getRow(){return this.Row;}
} 