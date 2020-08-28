package com.example.followupapp.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.followupapp.Activities.HomeActivity;
import com.example.followupapp.R;

public class CloseFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    RecyclerView followuprecycler;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public static CloseFragment newInstance(String param1, String param2) {
        CloseFragment fragment = new CloseFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((HomeActivity) getActivity())
                .setcustomActionBar("Closed List");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myview = inflater.inflate(R.layout.fragment_closedlist, container, false);
        followuprecycler = myview.findViewById(R.id.recycler_text_followup);

        //layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        //LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        //followuprecycler.setLayoutManager(linearLayoutManager);
        /*String data[]={"Reliance Industries","Reliance Industries","Reliance Industries","Reliance Industries","Reliance Industries","Reliance Industries","Reliance Industries","Reliance Industries","Reliance Industries","Reliance Industries","Reliance Industries","Reliance Industries","Reliance Industries"};
        RecycleDetailList recycleDetailList=new RecycleDetailList(getContext(),data);
        followuprecycler.setAdapter(recycleDetailList);
        followuprecycler.setLayoutManager(layoutManager);*/

        //followuprecycler=myview.findViewById(R.id.recycler_followup);

        String data[] = {"Reliance Industries", "Reliance Industries", "Reliance Industries", "Reliance Industries", "Reliance Industries", "Reliance Industries"};
        RecycleDetailList adapter_img = new RecycleDetailList(getContext(), data);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        followuprecycler.setLayoutManager(linearLayoutManager);
        followuprecycler.setAdapter(adapter_img);
        return myview;
    }

    public class RecycleDetailList extends RecyclerView.Adapter<RecycleDetailList.Detaillistviewholder> {
        Context context;
        String[] data;
        /*List<ProductDetailsItem> productDetailsItems;
        DecimalFormat numberFormat;
        double number;
        int p=0;*/

        public RecycleDetailList(Context context, String data[]) {
            this.context = context;
            this.data = data;
        }

        @NonNull
        @Override
        public Detaillistviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            View view = inflater.inflate(R.layout.custom_list_recycler, viewGroup, false);
            return new RecycleDetailList.Detaillistviewholder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull Detaillistviewholder detaillistviewholder, final int i) {
            detaillistviewholder.companyname.setText(data[i]);
            detaillistviewholder.linear_layout_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   /* DetailsFollowup homeFragment = new DetailsFollowup();
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, homeFragment);
                    fragmentTransaction.commit();*/

                    Fragment fragment = new DetailsFollowup();
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    Bundle args = new Bundle();
                    args.putInt("disable", 1);
                    fragment.setArguments(args);
                    ft.replace(R.id.fragment_container, fragment);
                    ft.commit();

                }
            });
        }

        @Override
        public int getItemCount() {
            return data.length;
        }

        class Detaillistviewholder extends RecyclerView.ViewHolder {
            TextView companyname;
            LinearLayout linear_layout_view;


            public Detaillistviewholder(@NonNull View itemView) {
                super(itemView);
                companyname = itemView.findViewById(R.id.companyname);
                linear_layout_view=itemView.findViewById(R.id.linear_layout_view);
            }
        }

    }

    // TODO: Rename method, update argument and hook method into UI event


}
