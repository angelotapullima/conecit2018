package com.conecit.angelo.conecit2018.fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.conecit.angelo.conecit2018.LoginActivity;
import com.conecit.angelo.conecit2018.R;
import com.conecit.angelo.conecit2018.adapters.ConcursosAdapterRecyclerview;
import com.conecit.angelo.conecit2018.model.DatosConcursos;
import com.conecit.angelo.conecit2018.model.SingletonConecit;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConcursosFragment extends Fragment implements Response.Listener<JSONObject>,Response.ErrorListener{
    RecyclerView recyclerConcursos;
    ArrayList<DatosConcursos> listaConcursos;
    //ProgressDialog progres;
    //RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    private SharedPreferences pref;


    public ConcursosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_concursos, container, false);
        setHasOptionsMenu(true);
        pref = this.getActivity().getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        showToolbar(getResources().getString(R.string.tab_concursos),false,view);

        listaConcursos=new ArrayList<>();
        recyclerConcursos=view.findViewById(R.id.cocursosRecycler);
        recyclerConcursos.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerConcursos.setHasFixedSize(true);
        //request = Volley.newRequestQueue(getContext());
        cargardatos();

        return view;
    }



    private void cargardatos() {
        /*progres=new ProgressDialog(getContext());
        progres.setMessage("Consultando Datos");
        progres.show();*/
        String url="http://conecit.pe/concursos.json";
        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        //request.add(jsonObjectRequest);
        SingletonConecit.getIntanciaVolley(getContext()).addToRequestQueue(jsonObjectRequest);

    }
    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getContext(),"no se pudo conectar"+error.toString(),Toast.LENGTH_SHORT).show();
        System.out.println();
        //progres.hide();
        Log.d("Error: ",error.toString());

    }

    @Override
    public void onResponse(JSONObject response) {
        DatosConcursos concursos=null;
        JSONArray json=response.optJSONArray("concursos");
        try{
            for (int i=0;i<json.length();i++){
                concursos =new DatosConcursos();
                JSONObject jsonObject=null;
                jsonObject=json.getJSONObject(i);

                concursos.setTituloconcursos(jsonObject.optString("tituloConcursos"));
                concursos.setDescripcionconcursos(jsonObject.optString("descripcionConcursos"));
                concursos.setImagenconcursos(jsonObject.optString("imagenConcursos"));
                listaConcursos.add(concursos);

            }
            //progres.hide();

            ConcursosAdapterRecyclerview adapter = new ConcursosAdapterRecyclerview(listaConcursos,getActivity(),getContext());
            recyclerConcursos.setAdapter(adapter);

        }catch (JSONException e){
            e.printStackTrace();
            Toast.makeText(getContext(),"No se puede conectar"+e.toString(),Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout :
                logout();
                //Log.i("item id ", item.getItemId() + "");
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void logout(){
        Intent i = new Intent(getContext(), LoginActivity.class);
        pref.edit().clear().apply();
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);

    }


    public void showToolbar(String tittle, boolean upButton, View view){
        Toolbar toolbar = (Toolbar)view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(tittle);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);

    }


}
