package com.conecit.angelo.conecit2018.fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.conecit.angelo.conecit2018.R;
import com.conecit.angelo.conecit2018.adapters.ConferenciasAdapterRecyclerview;
import com.conecit.angelo.conecit2018.model.DatosConferencias;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConferenciasFragment extends Fragment implements Response.Listener<JSONObject>,Response.ErrorListener {
    RecyclerView recyclerConferencias;
    ArrayList<DatosConferencias> listaConferencias;
    ProgressDialog progres;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;


    public ConferenciasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_conferencias, container, false);
        showToolbar(getResources().getString(R.string.tab_conferencias),false,view);
        listaConferencias=new ArrayList<>();
        recyclerConferencias=view.findViewById(R.id.confeRecycler);
        recyclerConferencias.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerConferencias.setHasFixedSize(true);
        request = Volley.newRequestQueue(getContext());
        cargardatos();
        return view;

    }

    private void cargardatos() {
        progres=new ProgressDialog(getContext());
        progres.setMessage("Consultando Datos");
        progres.show();
        String url="http://conecit.pe/conferencias.json";
        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        request.add(jsonObjectRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getContext(),"no se pudo conectar"+error.toString(),Toast.LENGTH_SHORT).show();
        System.out.println();
        progres.hide();
        Log.d("Error: ",error.toString());

    }


    @Override
    public void onResponse(JSONObject response) {
        DatosConferencias conferencias=null;
        JSONArray json=response.optJSONArray("conferencias");
        try{
            for (int i=0;i<json.length();i++){
                conferencias =new DatosConferencias();
                JSONObject jsonObject=null;
                jsonObject=json.getJSONObject(i);

                conferencias.setTituloconfe(jsonObject.optString("tituloConferencias"));
                conferencias.setPonenteconfe(jsonObject.optString("ponenteConferencias"));
                conferencias.setDescripcionconfe(jsonObject.optString("descripcionConferencias"));
                conferencias.setShortconfe(jsonObject.optString("shortConferencias"));
                conferencias.setImagenconfe(jsonObject.optString("imagenConferencias"));
                listaConferencias.add(conferencias);

            }
            progres.hide();

            ConferenciasAdapterRecyclerview adapter = new ConferenciasAdapterRecyclerview(listaConferencias,getActivity(),getContext());
            recyclerConferencias.setAdapter(adapter);

        }catch (JSONException e){
            e.printStackTrace();
            Toast.makeText(getContext(),"No se puede conectar"+e.toString(),Toast.LENGTH_SHORT).show();
        }

    }
    public void showToolbar(String tittle, boolean upButton, View view){
        Toolbar toolbar = (Toolbar)view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(tittle);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);

    }
}
