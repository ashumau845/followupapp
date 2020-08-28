package com.example.followupapp.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.followupapp.Activities.HomeActivity;
import com.example.followupapp.R;

public class DetailsFollowup extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    RecyclerView followup_recyclerview;
    Button btnfollowup;
    Button btncall;
    public static int disable=0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        ((HomeActivity) getActivity())
                .setcustomActionBar("FollowUp Details");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myview= inflater.inflate(R.layout.fragment_followup_details, container, false);
        disable=getArguments().getInt("disable");
        btnfollowup=myview.findViewById(R.id.btn_followup);
        btncall=myview.findViewById(R.id.btn_call);
        followup_recyclerview=myview.findViewById(R.id.followup_recyclerview);
        followup_recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        String[] acs_date={"Applicable ","Are all Readers, Access panels, Press to exit switches, Emergency MCPs & Magentic lock functioning properly?","Are all Exits Operational ?",
                "Are related Panic bars for emergency Exits working properly?","Are all access points integrated with fire alarm system?","What is the access & attendance software version no?(Access:,Attendace: )"};
        Adapter_followup adapter_followup=new Adapter_followup(acs_date);
        followup_recyclerview.setAdapter(adapter_followup);

        btncall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:0123456789"));
                startActivity(intent);
            }
        });

        if(disable==0){
        btnfollowup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FollowupAdd homeFragment = new FollowupAdd();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, homeFragment);
                fragmentTransaction.commit();
            }
        });}else{
            btnfollowup.setClickable(false);
            btnfollowup.setBackgroundColor(0x66000000);
        }
        return myview;
    }

    public class Adapter_followup extends RecyclerView.Adapter<Adapter_followup.Viewholder_followup>{
        String[] acs_date;
        Adapter_followup(String[] acs_date){
            this.acs_date=acs_date;
        }
        @NonNull
        @Override
        public Viewholder_followup onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.content_followup_details,parent, false);
            return new Viewholder_followup(view);
        }

        @Override
        public void onBindViewHolder(@NonNull Viewholder_followup holder, int position) {
            holder.desc_content.setText(acs_date[position]);
            holder.img_edit.setVisibility(View.GONE);
            holder.img_delete.setVisibility(View.GONE);
        }

        @Override
        public int getItemCount() {
            return acs_date.length;
        }

        public class Viewholder_followup extends RecyclerView.ViewHolder{
            TextView desc_content;
            ImageButton img_delete,img_edit;
            public Viewholder_followup(View itemView) {
                super(itemView);
                desc_content = itemView.findViewById(R.id.txt_meeting_decp);
                img_delete=itemView.findViewById(R.id.imgbtn_delete);
                img_edit=itemView.findViewById(R.id.imgbtn_edit);
            }

        }
    }
}
