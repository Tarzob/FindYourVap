package com.imperialsoupgmail.tesseractexample;

import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.imperialsoupgmail.tesseractexample.adapter.CommentaireAdapter;
import com.imperialsoupgmail.tesseractexample.beans.Commentaire;
import com.imperialsoupgmail.tesseractexample.beans.Liquide;
import com.imperialsoupgmail.tesseractexample.beans.User;

import java.util.ArrayList;
import java.util.List;

public class InfoActivity extends AppCompatActivity {
    private List<Commentaire> listCommentaires = new ArrayList<>();
    private List<Liquide> listLiquide = new ArrayList<>();
    private Liquide myLiquide;
    private ListView mListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        initDataEnDure();
        determineLiquide("Scooby Snack");
        initInterface(myLiquide);
    }

    private void determineLiquide(String liquideName){
        for(Liquide liquide : listLiquide){
            if(liquide.getLibelle().equals(liquideName)) myLiquide = liquide;
        }
    }

    private void initDataEnDure() {
        User user1 = new User(R.drawable.user1, "Maxime Lamarche");
        User user2 = new User(R.drawable.user2, "Adam Dief");
        User user3 = new User(R.drawable.user3, "Yasmine Bennadir");

        //SCOOBY
        Liquide scooby = new Liquide(R.drawable.scoobysnack,"Scooby Snack");
        scooby.setFamille("Epsi");
        listCommentaires.add(new Commentaire(3,user1, "Pas mal !!"));
        listCommentaires.add(new Commentaire(5,user2, "Trop bien !!!"));
        listCommentaires.add(new Commentaire(2,user3, "J'aime pas du tout"));
        listCommentaires.add(new Commentaire(2,user3, "c'est degueu !!!"));
        listCommentaires.add(new Commentaire(2,user3, "#EPSI Bordeaux ma gueule"));
        scooby.setListCommentaire(listCommentaires);

        //AUTRE LIQUIDE


        listLiquide.add(scooby);
    }

    private void initInterface(Liquide liquide){
        //IMAGE
        ImageView photoLiquide = (ImageView) findViewById(R.id.imageInfoView);
        photoLiquide.setImageBitmap(BitmapFactory.decodeResource(getResources(), liquide.getIdPhoto()));

        //LIBELLE
        TextView libelleLiquide = (TextView) findViewById(R.id.libelleLiquid);
        libelleLiquide.setText(liquide.getLibelle());

        //FAMILLE
        TextView libelleFamille = (TextView) findViewById(R.id.FamilleLiquid);
        libelleFamille.setText(liquide.getFamille());

        //MOYENNE RATING
        TextView averageRating = (TextView) findViewById(R.id.MoyenneRating);
        averageRating.setText(String.valueOf(liquide.getMoyenneRating()));

        //AVERAGE RATING BAR
        RatingBar ratingBar = (RatingBar) findViewById(R.id.AverageRatingBar);
        ratingBar.setRating(liquide.getMoyenneRating());

        //LIST VIEW
        mListView = (ListView) findViewById(R.id.listView);
        CommentaireAdapter adapter = new CommentaireAdapter(InfoActivity.this, listCommentaires);
        mListView.setAdapter(adapter);
    }
}
