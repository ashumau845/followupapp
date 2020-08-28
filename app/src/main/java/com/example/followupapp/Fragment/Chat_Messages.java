package com.example.followupapp.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.followupapp.Activities.HomeActivity;
import com.example.followupapp.R;


public class Chat_Messages extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    RecyclerView recycler_msg;
    private String mParam1;
    private String mParam2;


    public static Chat_Messages newInstance(String param1, String param2) {
        Chat_Messages fragment = new Chat_Messages();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
    public void onResume() {
        super.onResume();
        ((HomeActivity) getActivity())
                .setcustomActionBar("Messages");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myview = inflater.inflate(R.layout.fragment_chat__messages, container, false);
        recycler_msg = myview.findViewById(R.id.recycler_view_message);
        String data[] = {"Akshay", "Abhishek", "Nishant"};
        RecycleDetailMsg adapter_img = new RecycleDetailMsg(getContext(), data);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recycler_msg.setLayoutManager(linearLayoutManager);
        recycler_msg.setAdapter(adapter_img);
        return myview;
    }

    public class RecycleDetailMsg extends RecyclerView.Adapter<RecycleDetailMsg.MsgListInfoViewholder> {
        Context context;
        String[] data;
        /*List<ProductDetailsItem> productDetailsItems;
        DecimalFormat numberFormat;
        double number;
        int p=0;*/

        public RecycleDetailMsg(Context context, String data[]) {
            this.context = context;
            this.data = data;
        }


        @NonNull
        @Override
        public MsgListInfoViewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            View view = inflater.inflate(R.layout.custom_message, viewGroup, false);
            return new RecycleDetailMsg.MsgListInfoViewholder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MsgListInfoViewholder msgListInfoViewholder, int i) {
            msgListInfoViewholder.personname.setText(data[i]);
        }


        @Override
        public int getItemCount() {
            return data.length;
        }

        class MsgListInfoViewholder extends RecyclerView.ViewHolder {
            TextView personname;


            public MsgListInfoViewholder(@NonNull View itemView) {
                super(itemView);
                personname = itemView.findViewById(R.id.name);

            }
        }

    }


}
