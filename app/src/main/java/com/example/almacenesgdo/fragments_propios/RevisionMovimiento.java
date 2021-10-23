package com.example.almacenesgdo.fragments_propios;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.almacenesgdo.R;
import com.example.almacenesgdo.objetos.Productos;
import com.example.almacenesgdo.recursos.ProductosListaAdapter;
import com.example.almacenesgdo.recursos.VolleySingleton;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RevisionMovimiento extends BottomSheetDialogFragment implements SearchView.OnQueryTextListener{
    private RecyclerView recyclerViewProductos;
    private ProductosListaAdapter productosAdapter;
    private Productos[] productos;
    private List<Productos> productosArrayList;
    private Gson gson = new Gson();
    private String servidor, fecha;
    private SimpleDateFormat dateFormat;
    private Date date = new Date();
    private SearchView svFilter;
    private String idUsuario, idAlmacenO, idAlmacenD;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.lista_operacion,container, false);
        inicializa(v);
        return v;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        productosAdapter.getFilter().filter(s);
        return false;
    }

    public void setIdUsuario(String idUsuario){
        this.idUsuario = idUsuario;
    }

    public  void setIdAlmacenO(String idAlmacenO){
        this.idAlmacenO = idAlmacenO;
    }

    public void setIdAlmacenD(String idAlmacenD){
        this.idAlmacenD = idAlmacenD;
    }

    private String getIdUsuario(){
        return idUsuario;
    }

    private String getIdAlmacenO(){
        return idAlmacenO;
    }

    private String getIdAlmacenD(){
        return idAlmacenD;
    }

    private void inicializa(View root){
        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        fecha = dateFormat.format(date);
        recyclerViewProductos = root.findViewById(R.id.rcvMovimientos);
        recyclerViewProductos.setLayoutManager(new LinearLayoutManager(getContext()));
        servidor = getString(R.string.servername);
        svFilter = root.findViewById(R.id.svMovimientos);
        svFilter.setOnQueryTextListener(this);
        realizaConsulta(getIdUsuario(), getIdAlmacenO(), getIdAlmacenD());
    }

    public void realizaConsulta(String idUsuario, String idAlmacen, String idAlmacenD){
        buscarProducto(idUsuario, idAlmacen, idAlmacenD,fecha);
    }

    private void populateRecyclerView() {
        productosAdapter = new ProductosListaAdapter(productosArrayList, getContext());
        recyclerViewProductos.setAdapter(productosAdapter);
    }

    private void buscarProducto(String idUsuario, String idAlmacen, String idAlmacenD, String fecha){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(servidor+"lista_traspasoUsuario.php?usuario="+idUsuario+"&almacen="+idAlmacen+"&almacenD="+idAlmacenD+"&fecha="+fecha, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                productos = null;
                productos = gson.fromJson(response.toString(), Productos[].class);
                productosArrayList = Arrays.asList(productos);
                if(productosArrayList != null)
                    populateRecyclerView();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "No hay registros para esos parametros", Toast.LENGTH_LONG).show();
            }
        }
        );
        VolleySingleton.getInstance(getContext()).addToRequestQueue(jsonArrayRequest);
    }
}
