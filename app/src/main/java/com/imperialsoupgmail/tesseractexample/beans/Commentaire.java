package com.imperialsoupgmail.tesseractexample.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tarzob on 15/11/2016.
 */
public class Commentaire {
    private float note;
    private User user;
    private String libelle;

    public Commentaire(int note, User user, String libelle) {
        this.note = note;
        this.user = user;
        this.libelle = libelle;
    }

    public float getNote() {
        return note;
    }

    public void setNote(float note) {
        this.note = note;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}
