package com.example.almacenesgdo.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getChildFragmentManager().setFragmentResultListener("requestKey", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                productos= (Productos) bundle.getSerializable("bundleKey");
                Toast.makeText(getContext(), productos.getDescripcion(), Toast.LENGTH_LONG).show();
            }
        });
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
/* Inicia Prueba*/
        FragmentManager fragmentManager = getChildFragmentManager();
        BusquedaFragment datosProducto = new BusquedaFragment();
        fragmentManager.beginTransaction().replace(R.id.lBusqueda, datosProducto).commit();
/*Fin Prueba*/
        return root;
    }
}