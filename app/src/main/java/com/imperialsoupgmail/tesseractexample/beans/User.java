package com.imperialsoupgmail.tesseractexample.beans;

import android.graphics.Bitmap;

/**
 * Created by Tarzob on 15/11/2016.
 */
public class User {
    int idPhoto;
    String pseudo;

    public User(int idPhoto, String pseudo) {
        this.idPhoto = idPhoto;
        this.pseudo = pseudo;
    }

    public int getIdPhoto() {
        return idPhoto;
    }

    public void setIdPhoto(int idPhoto) {
        this.idPhoto = idPhoto;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }
}
