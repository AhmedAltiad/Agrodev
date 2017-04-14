package com.agrobourse.dev.domain;
import com.agrobourse.dev.domain.Annonce;
import java.util.*;

public class AnnonceImage {
private Annonce annonce ;
private byte[] file ;
private String nom ;
private String type ;
public AnnonceImage(){

}
public AnnonceImage(Annonce annonce,byte[] file ,String nom , String type){
this.annonce= annonce;
this.file=file;
this.nom =nom;
this.type= type;

  
}
public Annonce getAnnonce(){
    return annonce;
}

public void setAnnonce(Annonce annonce){
    this.annonce = annonce;
}
public byte[] getFile(){
    return file;
}

public void setFile( byte[] file){
    this.file = file;
}
public String getNom(){
    return nom;
}

public void setNom(String nom){
    this.nom = nom;
}

public String getType(){
    return type;
}

public void setType(String type){
    this.type = type;
}


}