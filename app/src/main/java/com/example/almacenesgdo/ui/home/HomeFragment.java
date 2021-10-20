package com.example.almacenesgdo.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;

import com.example.almacenesgdo.R;
import com.example.almacenesgdo.fragments_propios.BusquedaFragment;
import com.example.almacenesgdo.objetos.Almacenes;
import com.example.almacenesgdo.objetos.Persona;
import com.example.almacenesgdo.objetos.Productos;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private Productos productos;
    private TextView txtCodigo, txtDescripcion, txtUnidad;
    private Persona persona;
    //private ArrayList<Almacenes> almacenes;
    private Spinner spAlmacenO, spAlmacenD;

    private void inicializa(View root){
        txtCodigo = root.findViewById(R.id.txtCodigoT);
        txtDescripcion = root.findViewById(R.id.txtDescripcionT);
        txtUnidad = root.findViewById(R.id.txtUnidadT);

        spAlmacenO = root.findViewById(R.id.spAlmacenOT);
        spAlmacenD = root.findViewById(R.id.spAlmacenDT);

        //almacenes = new ArrayList<Almacenes>();
        persona = (Persona) getActivity().getIntent().getExtras().getSerializable("usuario");
        //almacenes = (ArrayList<Almacenes>) getActivity().getIntent().getExtras().getSerializable("almacenes");

        //populateSpinner();

        FragmentManager fragmentManager = getChildFragmentManager();
        BusquedaFragment datosProducto = new BusquedaFragment();
        fragmentManager.beginTransaction().replace(R.id.lBusqueda, datosProducto).commit();
    }
/*
    private void populateSpinner() {
        List<String> lables = new ArrayList<String>();
        //Toast.makeText(getContext(), almacenes.get(0).getAlmacen(), Toast.LENGTH_LONG).show();
        for(Almacenes a:almacenes){
            lables.add(a.getId()+" "+a.getAlmacen());
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, lables);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spAlmacenO.setAdapter(spinnerAdapter);
        //spAlmacenD.setAdapter(spinnerAdapter);
    }
*/
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getChildFragmentManager().setFragmentResultListener("requestKey", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                productos = (Productos) bundle.getSerializable("bundleKey");
                cargaDatos(productos);
            }
        });
    }

    private void cargaDatos(Productos producto){
        txtCodigo.setText(producto.getCodigo());
        txtDescripcion.setText(producto.getDescripcion());
        txtUnidad.setText(producto.getUnidades());
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        inicializa(root);
        return root;
    }
}