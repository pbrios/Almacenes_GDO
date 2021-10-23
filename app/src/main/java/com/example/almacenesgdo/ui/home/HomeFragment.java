package com.example.almacenesgdo.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.example.almacenesgdo.fragments_propios.RevisionMovimiento;
import com.example.almacenesgdo.objetos.Almacenes;
import com.example.almacenesgdo.objetos.Persona;
import com.example.almacenesgdo.objetos.Productos;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private Productos productos;
    private TextView txtCodigo, txtDescripcion, txtUnidad;
    private Persona persona;
    private ArrayList<Almacenes> almacenes;
    private Spinner spAlmacenO, spAlmacenD;
    private FloatingActionButton btnListaT;
    private RevisionMovimiento revisionMovimiento = new RevisionMovimiento();
    private String idAlmacenO, idAlmacenD;

    private void inicializa(View root){
        txtCodigo = root.findViewById(R.id.txtCodigoT);
        txtDescripcion = root.findViewById(R.id.txtDescripcionT);
        txtUnidad = root.findViewById(R.id.txtUnidadT);
        spAlmacenO = root.findViewById(R.id.spAlmacenOT);
        spAlmacenD = root.findViewById(R.id.spAlmacenDT);
        btnListaT = root.findViewById(R.id.btnListaT);
        almacenes = new ArrayList<Almacenes>();
        persona = (Persona) getActivity().getIntent().getExtras().getSerializable("usuario");
        almacenes = (ArrayList<Almacenes>) getActivity().getIntent().getExtras().getSerializable("almacenes");
        populateSpinner();
        FragmentManager fragmentManager = getChildFragmentManager();
        BusquedaFragment datosProducto = new BusquedaFragment();
        fragmentManager.beginTransaction().replace(R.id.lBusqueda, datosProducto).commit();
    }

    private void populateSpinner() {
        List<String> lables = new ArrayList<String>();
        for(Almacenes a:almacenes)
            lables.add(a.getId()+" "+a.getAlmacen());
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, lables);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spAlmacenO.setAdapter(spinnerAdapter);
        spAlmacenD.setAdapter(spinnerAdapter);
    }

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
        spAlmacenO.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                idAlmacenO = almacenes.get(i).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });

        spAlmacenD.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                idAlmacenD = almacenes.get(i).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });

        btnListaT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!idAlmacenO.equals("7") && !idAlmacenD.equals("7") && !idAlmacenO.equals(idAlmacenD)) {
                    revisionMovimiento.setIdUsuario(persona.getIdUsuario());
                    revisionMovimiento.setIdAlmacenO(idAlmacenO);
                    revisionMovimiento.setIdAlmacenD(idAlmacenD);
                    revisionMovimiento.show(getChildFragmentManager(), "desde lista");
                }
                else
                    Toast.makeText(getContext(), "Almacenes no validos", Toast.LENGTH_LONG).show();
            }
        });
        return root;
    }
}