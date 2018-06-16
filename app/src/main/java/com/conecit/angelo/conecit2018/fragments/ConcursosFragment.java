package com.conecit.angelo.conecit2018.fragments;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.conecit.angelo.conecit2018.Login.LoginActivity;
import com.conecit.angelo.conecit2018.R;
import com.conecit.angelo.conecit2018.adapters.ConcursosAdapterRecyclerview;
import com.conecit.angelo.conecit2018.model.DatosConcursos;
import com.conecit.angelo.conecit2018.model.SingletonConecit;
import com.google.firebase.auth.FirebaseAuth;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConcursosFragment extends Fragment implements Response.Listener<JSONObject>,Response.ErrorListener{
    RecyclerView recyclerConcursos;
    ArrayList<DatosConcursos> listaConcursos;
    ConcursosAdapterRecyclerview adapter;
    SearchView searchView;
    JsonObjectRequest jsonObjectRequest;
    private FirebaseAuth auth;


    public ConcursosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_concursos, container, false);
        setHasOptionsMenu(true);
        showToolbar(getResources().getString(R.string.tab_concursos),false,view);

        listaConcursos=new ArrayList<>();
        recyclerConcursos=view.findViewById(R.id.cocursosRecycler);
        recyclerConcursos.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerConcursos.setHasFixedSize(true);
        auth = FirebaseAuth.getInstance();
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

            adapter = new ConcursosAdapterRecyclerview(listaConcursos,getActivity(),getContext());
            recyclerConcursos.setAdapter(adapter);

        }catch (JSONException e){
            e.printStackTrace();
            Toast.makeText(getContext(),"No se puede conectar"+e.toString(),Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);final MenuItem myActionMenuItem=menu.findItem(R.id.search);
        searchView=(SearchView) myActionMenuItem.getActionView();
        changeSearchViewTextColor(searchView);

        ((EditText)searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text)).setHintTextColor(getResources().getColor(R.color.edittextColorWhite));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!searchView.isIconified()){
                    searchView.setIconified(true);
                }
                myActionMenuItem.collapseActionView();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                final ArrayList<DatosConcursos> filteredModeList=filter(listaConcursos,newText);
                adapter.setfilter(filteredModeList);


                return true;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    private ArrayList<DatosConcursos> filter (ArrayList<DatosConcursos> pl , String query)
    {
        query=query.toLowerCase();
        final ArrayList<DatosConcursos> filteredModeList = new ArrayList<>();
        for (DatosConcursos model:pl)
        {
            final String text=model.getTituloconcursos().toLowerCase();
            if (text.startsWith(query))
            {
                filteredModeList.add(model);
            }
        }
        return filteredModeList;
    }

    private void changeSearchViewTextColor(View view)
    {
        if (view != null)
        {
            if (view instanceof TextView)
            {
                ((TextView) view).setTextColor(Color.WHITE);
                return;
            }else if (view instanceof ViewGroup)
            {
                ViewGroup viewGroup = (ViewGroup)view;
                for (int i=0;1<viewGroup.getChildCount();i++)
                {
                    changeSearchViewTextColor(viewGroup.getChildAt(i));
                }
            }
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout :
                logout();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void logout(){
        auth.signOut();
        if (auth.getCurrentUser() == null)
        {
            Intent i = new Intent(getContext(), LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }

    }


    public void showToolbar(String tittle, boolean upButton, View view){
        Toolbar toolbar = (Toolbar)view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(tittle);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);

    }


}
