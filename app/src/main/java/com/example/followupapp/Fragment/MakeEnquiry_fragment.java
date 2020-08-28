package com.example.followupapp.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.followupapp.Activities.HomeActivity;
import com.example.followupapp.R;

public class MakeEnquiry_fragment extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_makeenquiry, container, false);
        ((HomeActivity) getActivity()).setActionBarTitle("Make Enquiry");

        return myView;
    }
}
