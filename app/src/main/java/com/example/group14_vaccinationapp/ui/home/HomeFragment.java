package com.example.group14_vaccinationapp.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.group14_vaccinationapp.CovidWebsite;
import com.example.group14_vaccinationapp.FAQs;
import com.example.group14_vaccinationapp.Login;
import com.example.group14_vaccinationapp.MainActivityAdmin;
import com.example.group14_vaccinationapp.PersonalHealthcare;
import com.example.group14_vaccinationapp.R;
import com.example.group14_vaccinationapp.VaccinationMenu;
import com.example.group14_vaccinationapp.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    private SharedPreferences mPreferences;
    private String sharedPrefFile = "com.example.android.group14_vaccinationapp";

    // Key for current NRIC
    private final String NRICPreference = "NRIC";
    // Key for current isAdmin
    private final String isAdminPreference = "isAdmin";

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText("");
            }
        });
        return root;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.btnVaccination).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), VaccinationMenu.class);
                getActivity().startActivity(intent);
            }
        });

        view.findViewById(R.id.btnFaq).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), FAQs.class);
                getActivity().startActivity(intent);
            }
        });

        view.findViewById(R.id.btnCovidNewsInfo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), CovidWebsite.class);
                getActivity().startActivity(intent);
            }
        });

        view.findViewById(R.id.btnThingsToDo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), PersonalHealthcare.class);
                getActivity().startActivity(intent);
            }
        });

        view.findViewById(R.id.txt_main_logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
// calling method to edit values in shared prefs.
                    mPreferences = getActivity().getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = mPreferences.edit();

                    // below line will clear
                    // the data in shared prefs.
                    editor.clear();

                    // below line will apply empty
                    // data to shared prefs.
                    editor.apply();

                    // starting mainactivity after
                    // clearing values in shared preferences.
                    Intent i = new Intent(getActivity().getApplicationContext(), Login.class);
                    getActivity().startActivity(i);
                    getActivity().finish();
                }catch(Exception e){
                    Toast.makeText(getActivity().getApplicationContext(), e.toString(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}