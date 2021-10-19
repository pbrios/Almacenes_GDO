package com.example.almacenesgdo.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.almacenesgdo.R;
import com.example.almacenesgdo.fragments_propios.BusquedaFragment;

public class DashboardFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        /* Inicia Prueba*/
        FragmentManager fragmentManager = getChildFragmentManager();
        BusquedaFragment datosProducto = new BusquedaFragment();
        fragmentManager.beginTransaction().replace(R.id.lInventario, datosProducto).commit();
        /*Fin Prueba*/

        return root;
    }
}