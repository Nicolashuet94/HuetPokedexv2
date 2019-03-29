package com.example.nihue.huetpokedex;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import static com.example.nihue.huetpokedex.MainActivity.NAME;
import static com.example.nihue.huetpokedex.MainActivity.NUM;
import static com.example.nihue.huetpokedex.MainActivity.HEIGHT;
import static com.example.nihue.huetpokedex.MainActivity.WEIGHT;
import static com.example.nihue.huetpokedex.MainActivity.URL;
import static com.example.nihue.huetpokedex.MainActivity.TYPE;



public class SecondActivity extends AppCompatActivity {
    TextView head;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);

        Intent intent = getIntent();
        String pokeName = intent.getStringExtra(NAME);
        String pokeNum = intent.getStringExtra(NUM);
        String pokeHeight = intent.getStringExtra(HEIGHT);
        String pokeWeight = intent.getStringExtra(WEIGHT);
        String pokeImg = intent.getStringExtra(URL);
        String pokeType = intent.getStringExtra(TYPE);

        ImageView imageViewPoke = findViewById(R.id.imageViewPoke);
        TextView viewName = findViewById(R.id.textViewHead);
        TextView viewNum = findViewById(R.id.textViewDescription);
        TextView viewHeight = findViewById(R.id.height);
        TextView viewWeight = findViewById(R.id.weight);
        TextView viewType = findViewById(R.id.type);

        Picasso.get().load(pokeImg).into(imageViewPoke);
        viewName.setText(pokeName);
        viewNum.setText("Numero " + pokeNum);
        viewHeight.setText("Taille " + pokeHeight);
        viewWeight.setText("Poids " + pokeWeight);
        viewType.setText("Type " + pokeType);
    }
}

