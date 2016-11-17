package com.imperialsoupgmail.tesseractexample.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.imperialsoupgmail.tesseractexample.R;
import com.imperialsoupgmail.tesseractexample.beans.Commentaire;
import com.imperialsoupgmail.tesseractexample.viewHolder.CommentaireViewHolder;

import java.util.List;

/**
 * Created by Tarzob on 16/11/2016.
 */
public class CommentaireAdapter extends ArrayAdapter<Commentaire> {

    public CommentaireAdapter(Context context, List<Commentaire> commentaires) {
        super(context, 0, commentaires);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.root_list_commentaire,parent, false);
        }

        CommentaireViewHolder viewHolder = (CommentaireViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new CommentaireViewHolder();
            viewHolder.pseudo = (TextView) convertView.findViewById(R.id.pseudo);
            viewHolder.text = (TextView) convertView.findViewById(R.id.text_commentaire);
            viewHolder.avatar = (ImageView) convertView.findViewById(R.id.avatar);
            viewHolder.ratingBar = (RatingBar) convertView.findViewById(R.id.ratingBarComment);
            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<Tweet> tweets
        Commentaire commentaire = getItem(position);

        //il ne reste plus qu'à remplir notre vue
        viewHolder.pseudo.setText(commentaire.getUser().getPseudo());
        viewHolder.text.setText(commentaire.getLibelle());
        viewHolder.avatar.setImageBitmap(BitmapFactory.decodeResource(getContext().getResources(), commentaire.getUser().getIdPhoto()));
        viewHolder.ratingBar.setRating(commentaire.getNote());
        return convertView;
    }
}
