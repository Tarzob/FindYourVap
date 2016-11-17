package com.imperialsoupgmail.tesseractexample.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tarzob on 15/11/2016.
 */
public class Liquide {
    private int idPhoto;
    private String libelle;
    private List<Commentaire> listCommentaire = new ArrayList<>();
    private String famille;

    public Liquide(int idPhoto, String libelle) {
        this.idPhoto = idPhoto;
        this.libelle = libelle;
    }

    public String getFamille() {
        return famille;
    }

    public void setFamille(String famille) {
        this.famille = famille;
    }

    public int getIdPhoto() {
        return idPhoto;
    }

    public void setIdPhoto(int idPhoto) {
        this.idPhoto = idPhoto;
    }

    public List<Commentaire> getListCommentaire() {
        return listCommentaire;
    }

    public void setListCommentaire(List<Commentaire> listCommentaire) {
        this.listCommentaire = listCommentaire;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public float getMoyenneRating(){
        float compteur = 0;
        int dividende = 0;
        for(Commentaire commentaire : listCommentaire){
            dividende++;
            compteur+= commentaire.getNote();
        }
        return compteur/dividende;
    }
}
