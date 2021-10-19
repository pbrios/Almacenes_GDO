package com.example.almacenesgdo.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;

import com.example.almacenesgdo.R;
import com.example.almacenesgdo.fragments_propios.BusquedaFragment;
import com.example.almacenesgdo.objetos.Productos;

public class HomeFragment extends Fragment {
    private Productos productos;
    private TextView txtCodigo, txtDescripcion, txtUnidad;

    private void inicializa(View root){
        txtCodigo = root.findViewById(R.id.txtCodigoT);
        txtDescripcion = root.findViewById(R.id.txtDescripcionT);
        txtUnidad = root.findViewById(R.id.txtUnidadT);

        FragmentManager fragmentManager = getChildFragmentManager();
        BusquedaFragment datosProducto = new BusquedaFragment();
        fragmentManager.beginTransaction().replace(R.id.lBusqueda, datosProducto).commit();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getChildFragmentManager().setFragmentResultListener("requestKey", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                productos= (Productos) bundle.getSerializable("bundleKey");
                cargaDatos(productos);
                Toast.makeText(getContext(), productos.getDescripcion(), Toast.LENGTH_LONG).show();
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