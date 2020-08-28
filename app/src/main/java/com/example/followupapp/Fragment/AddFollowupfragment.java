package com.example.followupapp.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.followupapp.Activities.HomeActivity;
import com.example.followupapp.ModalClass.ModalClass_Product;
import com.example.followupapp.R;

import java.util.List;
import java.util.Locale;


public class AddFollowupfragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    String srchString;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    EditText searchEditText;
    RecyclerView recycler_followup;




    @Override
    public void onResume() {

        super.onResume();
        ((HomeActivity) getActivity())
                .setcustomActionBar("FOLLOWUP LIST");
    }

    public static AddFollowupfragment newInstance(String param1, String param2) {
        AddFollowupfragment fragment = new AddFollowupfragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemid=item.getItemId();
        switch (itemid){
            case R.id.add_button:
                FollowupAdd followupAdd=new FollowupAdd();
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_followup, container, false);
        searchEditText = view.findViewById(R.id.ed_search);


        searchEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.setFocusable(true);
                v.setFocusableInTouchMode(true);
                return false;
            }
        });

        searchEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    searchEditText.getText().clear();
                }
            }
        });

        recycler_followup = view.findViewById(R.id.recycler_followup);
        String data[] = {"Reliance Industries", "Reliance Industries", "Reliance Industries", "Reliance Industries", "Reliance Industries", "Reliance Industries"};
        final Adapter_img adapter_img = new Adapter_img(getContext(), data);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recycler_followup.setLayoutManager(linearLayoutManager);
        recycler_followup.setAdapter(adapter_img);
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                srchString = searchEditText.getText().toString().toLowerCase(Locale.getDefault());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        return view;
    }



    public class Adapter_img extends RecyclerView.Adapter<Adapter_img.imgViewholder> {
        Context context;
        String data[];

        //ScaleGestureDetector scaleGDetector;

        public Adapter_img(Context context, String data[]) {
            this.context = context;
            this.data = data;
        }


        @NonNull
        @Override
        public imgViewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            View view = inflater.inflate(R.layout.custom_list_recycler, viewGroup, false);
            return new imgViewholder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull imgViewholder imgViewholder, int i) {

            //Glide.with(context).load(baseurl + imagelist.get(i).getPath()).into(imgViewholder.imag_view);
            imgViewholder.companyperson.setText(data[i]);
            imgViewholder.layout_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  /*  DetailsFollowup homeFragment = new DetailsFollowup();
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, homeFragment);
                    fragmentTransaction.commit();*/


                    Fragment fragment = new DetailsFollowup();
                    //Fragment fragment=new Form3_New();
                    FragmentTransaction ft = getFragmentManager().beginTransaction();

                    //Log.e("baseurlatform2", baseurl);
                    Bundle args = new Bundle();
                    args.putInt("disable", 0);
                    // args.putSerializable("imageslist", imageslist);
                    //args.putString("partno", partno);
                    fragment.setArguments(args);
                    ft.replace(R.id.fragment_container, fragment);
                    //  ft.addToBackStack(null);
                    ft.commit();

                }
            });

        }


        @Override
        public int getItemCount() {
            return data.length;
        }

        class imgViewholder extends RecyclerView.ViewHolder {
            //ZoomageView imag_view;
            TextView companyname, companyperson;
            LinearLayout layout_view;


            public imgViewholder(@NonNull View itemView) {
                super(itemView);
                companyname = itemView.findViewById(R.id.companyname);
                companyperson = itemView.findViewById(R.id.companyperson);
                layout_view=itemView.findViewById(R.id.linear_layout_view);
                // textno=itemView.findViewById(R.id.textno);
            }
        }

    }


}
/*

Title:Do you have Low Immunity?

Do you catch cold every now and then or do you feel tired most of the time or you are facing skin issues or may be patchy hair loss,well these are the sign of weak immune system.

Immune System is a group of organs which protect our body from infection;weak immune system is like an inviation to health problems sometimes the immune system get so weak that
it mistakenly attacking our own body.

Stress,unhealthy lifestyle and ageing are some of the causes of a weak immune system at this age; we should not take it for granted and should actually take an effort to keep it strong
so that we can enjoy the long term health benefit.So here are 5 simple ways to boost your immune sytem naturally.

1.Ashwagandha

(pic from google)

Ashwagandha, an Indian herb whose properties are so powerful that it has been nicknamed Indian GINSENG. Ashwagandha is well known to instantly boost your immune system.

Simply take one glass of hot milk and add 1 tablespoon of Ashwagandha powder.The best time to have it 1 hour before sleep at night. If you issue digesting with milk replace it with water.


2.Giloy
(pics from googel)

Giloy another ayurvedic herb is also known as AMRITA, which literally translates to the root of immortality.It helps to move the toxins,purifies the blood and fights the bad bacteria.
Just mix 1 tablespoon of Giloy powder in lukewarm water and have it empty stomach in morning.

3.Mint Drink
(pics from video)

This is one sweet soft drink to make your Immune system stronger. Simply put 500ml of water in the pan and add half teaspoon of turmeric into it.Let it boil for 2 minutes, now add 10 mint leaves;
let it further boil for another 2-3 minutes; Pour the mixture in the glass let it cool a bit;now add one tablespon of honey that's it.

This simple drink have the ability to fight the unwanted microbes in the body.

4.Turmeric
(Pics from google)

Turmeric is a common spice in Indian cooking; boost the Immune system through its amazing anti-oxidant,anti-inflamatory and anti-microbial property that its holds.
Simply add half teaspoon of turmeric in hot milk and have it one hour before going to sleep at night.Turmeric Milk will instantly shelid your body from infectious microbes.


5.Amla

Vitamin C is the number one vitamin to reduce Immune deficiency; amla or Indian Gooseberry is full of Vitamin C,so much that amount of 1 amla is equal to that of 20 oranges. So just one Amla a day keeps the Infections away.
You can consume Amla in any form:juice,pickle or raw.

Ofcourse you need not try all these home remedies at same time,pick one of your choice and practice it regularly for few months and you will clearly see the resullts in your way.
Immune System weakens with age; however following these remedies, you can be secure of an extra strong Immune system which will serve you healthy life.

 */