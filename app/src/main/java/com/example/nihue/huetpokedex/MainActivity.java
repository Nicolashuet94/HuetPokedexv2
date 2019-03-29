package com.example.nihue.huetpokedex;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.support.constraint.solver.LinearSystem;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MyAdapter.OnItemClickListener, SearchView.OnQueryTextListener{

    private static final String URL_DATA = "https://raw.githubusercontent.com/Biuni/PokemonGO-Pokedex/master/pokedex.json?fbclid=IwAR1LwHHwA_8e2KAnYPAhhT0NNdqmMtYNeovtrZBAcFfzDCWoAaPw12oI2oI";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private MyAdapter mAdapter;
    private SearchView searchView;
    private List<ListItem> listItems;
    public static final String NAME = "pokeName";
    public static final String NUM = "pokeNum";
    public static final String HEIGHT = "pokeHeight";
    public static final String WEIGHT = "pokeWeight";
    public static final String URL = "pokeImg";
    public static final String TYPE = "pokeType";

    /*public static final String PREFS = "PREFS";
    public static final String TEXT = "text";
    SharedPreferences sharedPreferences;*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*sharedPreferences = getBaseContext().getSharedPreferences(PREFS, MODE_PRIVATE);
        String text = sharedPreferences.getString(TEXT, null);
        Toast.makeText(this, "" + text, Toast.LENGTH_SHORT).show();*/

        searchView = (SearchView)findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(this);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listItems = new ArrayList<>();
        loadRecyclerViewData();
    }

    @Override
    public boolean onQueryTextSubmit(String query){ return false; }

    @Override
    public boolean onQueryTextChange(String newText){
        final List<ListItem> filterPoke = filter(listItems, newText);
        mAdapter.setFilter(filterPoke);
        return false;
        }

        private List<ListItem> filter(List<ListItem> poke, String query){
        query = query.toLowerCase();
        final List<ListItem> filterPoke = new ArrayList<>();
        for(ListItem pokemon : poke){
            String text = pokemon.getHead().toLowerCase();
            if(text.contains(query)){
                filterPoke.add(pokemon);
        }
        }
        return filterPoke;
        }

    private void loadRecyclerViewData(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET
                , URL_DATA
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray array = jsonObject.getJSONArray("pokemon");
                    for(int i = 0; i< array.length(); i++){
                        JSONObject obj = array.getJSONObject(i);
                        //ListItem item = new ListItem(
                               String pokeName = obj.getString("name");
                                String pokeNum = obj.getString("num");
                                String pokeImg = obj.getString("img");
                                String pokeHeight = obj.getString("height");
                                String pokeWeight = obj.getString("weight");
                                String pokeType = obj.getString("type");
                        //);
                        listItems.add(new ListItem(pokeName, pokeNum, pokeImg, pokeHeight, pokeWeight, pokeType));
                    }

                    mAdapter = new MyAdapter(listItems, MainActivity.this);
                    recyclerView.setAdapter(mAdapter);
                    mAdapter.setOnItemClickListener(MainActivity.this);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void onItemClick(int position){
        Intent i = new Intent(this, SecondActivity.class);
        ListItem click = listItems.get(position);
        i.putExtra(NAME, click.getHead());
        i.putExtra(URL, click.getImageUrl());
        i.putExtra(NUM, click.getDescription());
        i.putExtra(HEIGHT, click.getHeight());
        i.putExtra(WEIGHT, click.getWeight());
        i.putExtra(TYPE, click.getType());
        startActivity(i);
    }
}
