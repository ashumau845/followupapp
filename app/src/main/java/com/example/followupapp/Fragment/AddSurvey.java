package com.example.followupapp.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.example.followupapp.Activities.HomeActivity;
import com.example.followupapp.ModalClass.ModalClass_ACS;
import com.example.followupapp.R;
import com.example.followupapp.Utils.CirclePagerIndicatorDecorationk;

import java.util.ArrayList;
import java.util.List;


public class AddSurvey extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    TextView firesystem,acs,cctv,pasystem,fm_200,fire_ext,sprink_system,vesda_system,intr_system;
    EditText addressedittext;
    ImageView img_flip1,img_flip2;
    List<ModalClass_ACS> acslist;
    RecyclerView recycler_fire,recycler_acs;
    int closed_firesystem,closed_acs;

    public static AddSurvey newInstance(String param1, String param2) {
        AddSurvey fragment = new AddSurvey();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        ((HomeActivity) getActivity())
                .setcustomActionBar("PREVENTIVE MAINTENANCE CHECKLIST");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myview = inflater.inflate(R.layout.fragment_add_survey, container, false);
        firesystem = myview.findViewById(R.id.txt_firesystem);
        acs=myview.findViewById(R.id.txt_acs);
        cctv=myview.findViewById(R.id.txt_cctvsystem);
        addressedittext = myview.findViewById(R.id.address_edit_text);
        recycler_fire=myview.findViewById(R.id.recycler_fire);
        recycler_acs=myview.findViewById(R.id.recycler_acs);
        img_flip1=myview.findViewById(R.id.image_flip1);
        img_flip2=myview.findViewById(R.id.image_flip2);
        closed_firesystem=0;
        closed_acs=0;
        acslist=new ArrayList<ModalClass_ACS>();
        final LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        img_flip1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*CustomDialogImage customDialogImage = new CustomDialogImage(getActivity(),1);
                customDialogImage.show();*/
                //recycler_fire.setVisibility(View.VISIBLE);
                if(closed_firesystem==0) {
                    closed_firesystem=1;
                    String[] fas = {"Are all smoke detectors, heat detectors,hooters & MLPs funtioning properly ?", "Is the smoke detectors Address/Identification clearly visible ?", "Is the Main Fire Alarm Panel in normal operating system ?"};
                    recycler_fire.setVisibility(View.VISIBLE);
                    Adapter_firesystem adapter_firesystem = new Adapter_firesystem(getContext(), fas);
                    recycler_fire.setLayoutManager(linearLayoutManager);
                    recycler_fire.setAdapter(adapter_firesystem);
                    img_flip1.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
                    ViewCompat.setNestedScrollingEnabled(recycler_fire, false);
                }else{
                    closed_firesystem=0;
                    img_flip1.setImageResource(R.drawable.ic_chevron_right_black_24dp);
                    recycler_fire.setVisibility(View.GONE);
                }

            }
        });
        String[] str_acs={"Are all Readers, Access panels, Press to exit switches, Emergency MCPs & Magentic lock functioning properly?","Are all Exits Operational ?",
                "Are related Panic bars for emergency Exits working properly?","Are all access points integrated with fire alarm system?","What is the access & attendance software version no?(Access:,Attendace: )"};

        for(int i=0;i<str_acs.length;i++){
//                        acslist.add(acs);
            ModalClass_ACS modalClass_acs =new ModalClass_ACS();
            modalClass_acs.setQuestion(str_acs[i]);
            modalClass_acs.setAnswer_status(0);
            modalClass_acs.setVisibility_status(0);
            acslist.add(modalClass_acs);
            if(i==str_acs.length-1){
                modalClass_acs.setVisibility_status(1);
            }

        }
        img_flip2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(closed_acs==0){
                    img_flip2.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
                    closed_acs=1;

                    /*for(int i=0;i<acs.length;i++){
//                        acslist.add(acs);
                        ModalClass_ACS modalClass_acs =new ModalClass_ACS();
                        modalClass_acs.setQuestion(acs[i]);
                        modalClass_acs.setAnswer_status(0);
                        modalClass_acs.setVisibility_status(0);
                        acslist.add(modalClass_acs);
                        if(i==acs.length-1){
                            modalClass_acs.setVisibility_status(1);
                        }

                    }*/

                    recycler_acs.setLayoutManager(new LinearLayoutManager(getContext()));
                    Adapter_acs adapter_acs=new Adapter_acs(getContext(),acslist);
                    recycler_acs.setVisibility(View.VISIBLE);
                    recycler_acs.setAdapter(adapter_acs);
                    ViewCompat.setNestedScrollingEnabled(recycler_fire, false);
                }else {
                    closed_acs=0;
                    img_flip2.setImageResource(R.drawable.ic_chevron_right_black_24dp);
                    recycler_acs.setVisibility(View.GONE);
                }
            }
        });


        addressedittext.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                if (addressedittext.hasFocus()) {
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (event.getAction() & MotionEvent.ACTION_MASK) {
                        case MotionEvent.ACTION_SCROLL:
                            v.getParent().requestDisallowInterceptTouchEvent(false);
                            return true;
                    }
                }
                return false;
            }
        });

        return myview;
    }




    public class CustomDialogImage extends Dialog implements View.OnClickListener {
        TextView close;
        ImageView img_close;
        RecyclerView recyclerView_quiz;
        int status;
        SnapHelper snapHelper;

        public CustomDialogImage(@NonNull Context context,int status) {
            super(context);
            this.status=status;
        }
        //ProgressBar pbar_loader;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.customdialogbox);
            recyclerView_quiz = findViewById(R.id.recyclerview_quiz);
            img_close = findViewById(R.id.imageView_close);
            //img_close=findViewById(R.id.a)
            //pbar_loader = findViewById(R.id.pbar_loader);
            //emptyimg = findViewById(R.id.emptyimg);
            //recyclerView_image = findViewById(R.id.recycler_images);

            img_close.setOnClickListener(this);
            this.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            String[] fas = {"Applicable ", "Are all smoke detectors, heat detectors,hooters & MLPs funtioning properly ?", "Is the smoke detectors Address/Identification clearly visible ?", "Is the Main Fire Alarm Panel in normal operating system ?"};
            String[] acs={"Applicable ","Are all Readers, Access panels, Press to exit switches, Emergency MCPs & Magentic lock functioning properly?","Are all Exits Operational ?",
                    "Are related Panic bars for emergency Exits working properly?","Are all access points integrated with fire alarm system?","What is the access & attendance software version no?(Access:,Attendace: )"};
            String[] cctv={"Applicable ","Are all cameras,DVR & Power Supplies functioning properly ?","Are all cameras and related accessories Dust Free?","Are all recording checked and mentioned capacity of Hard disk?"};
            String[] pa={"Applicable ","Are all the speakers, Amplifiers and MIC functioning properly ?","Are all the speakers checked by giving auxiliary Music / Mic input ?"};
            String[] fm={"Applicable","Have checked pressure guage of FM-200 cylinders?","Is the Cross Zone fire Panel in normal condition?"};
            String[] fext={"Applicable","Have checked pressure guage of fire extinguisher cylinders?"};
            String[] ssystem={"Applicable","Have checked pressure of sprinkler?","Are all sprinkler points and related accessories in normal condition?"};
            String[] vsystem={"Applicable","Are all VESDA system working condition?"};
            String[] isystem={"Applicable","motion sensor & keypad working proper ?","Panel healthy"};

            LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
            SnapHelper snapHelper = new PagerSnapHelper();


            recyclerView_quiz.setLayoutManager(horizontalLayoutManagaer);
            Adapter_firealarm adapter_img = new Adapter_firealarm(getContext(),fas);
            recyclerView_quiz.setAdapter(adapter_img);

            snapHelper.attachToRecyclerView(recyclerView_quiz);
            recyclerView_quiz.addItemDecoration(new CirclePagerIndicatorDecorationk());

            /* adapter_img = new Adapter_img(getApplicationContext(),data);
            LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(PurchaseDetailsActivity.this, LinearLayoutManager.HORIZONTAL, false);
            recyclerView_image.setLayoutManager(horizontalLayoutManagaer);
            recyclerView_image.setAdapter(adapter_img);
            SnapHelper snapHelper = new PagerSnapHelper();
            snapHelper.attachToRecyclerView(recyclerView_image);*/
        }


        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.imageView_close:
                    this.dismiss();
                    break;
                default:
                    break;
            }
            this.dismiss();
        }
    }


    public class Adapter_firesystem extends RecyclerView.Adapter<Adapter_firesystem.FireSystem_viewholder> {
        Context context;
        String[] data;


        public Adapter_firesystem(Context context, String data[]) {
            this.context = context;
            this.data = data;
        }
        @NonNull
        @Override
        public FireSystem_viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.recycler_dialog_question,parent, false);
            return new FireSystem_viewholder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final FireSystem_viewholder holder, int i) {
            holder.data_content.setText(data[i]);
            holder.btnok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.btnok.setTextColor(Color.RED);
                    holder.btncancel.setTextColor(Color.BLACK);

                }
            });
            holder.btncancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.btncancel.setTextColor(Color.RED);
                    holder.btnok.setTextColor(Color.BLACK);
                }
            });
        }

        @Override
        public int getItemCount() {
            return data.length;
        }

        class FireSystem_viewholder extends RecyclerView.ViewHolder {
            TextView data_content,btnok,btncancel;
            public FireSystem_viewholder(@NonNull View itemView) {
                super(itemView);
                data_content = itemView.findViewById(R.id.tvTitle);
                btnok=itemView.findViewById(R.id.btnOK);
                btncancel=itemView.findViewById(R.id.btncancel);
            }

        }
    }

    public class Adapter_acs extends RecyclerView.Adapter<Adapter_acs.Adapter_acsviewholder> {
        public List<ModalClass_ACS> modalClassAcsList;
        @NonNull
        @Override
        public Adapter_acsviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.recycler_survey_2,parent, false);
            return new Adapter_acsviewholder(view);
        }

        Adapter_acs(Context context,List<ModalClass_ACS> modalClassAcsList){
        this.modalClassAcsList=modalClassAcsList;
        }
        @Override
        public void onBindViewHolder(@NonNull final Adapter_acsviewholder holder, int i) {
            holder.data_content.setText(modalClassAcsList.get(i).getQuestion());
            if(modalClassAcsList.get(i).getVisibility_status()==1){
                holder.edt_attendance.setVisibility(View.VISIBLE);
                holder.edt_access.setVisibility(View.VISIBLE);
            }
            holder.btncancel_acs.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.btncancel_acs.setTextColor(Color.RED);
                    holder.btnok_acs.setTextColor(Color.BLACK);
                }
            });

            holder.btnok_acs.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.btncancel_acs.setTextColor(Color.BLACK);
                    holder.btnok_acs.setTextColor(Color.RED);
                }
            });
        }

        @Override
        public int getItemCount() {
            return modalClassAcsList.size();
        }

        class Adapter_acsviewholder extends RecyclerView.ViewHolder {
            TextView data_content,btnok_acs,btncancel_acs;
            EditText edt_access,edt_attendance;

            public Adapter_acsviewholder(@NonNull View itemView) {
                super(itemView);
                data_content = itemView.findViewById(R.id.tvTitle);
                edt_access=itemView.findViewById(R.id.edt_access);
                edt_attendance=itemView.findViewById(R.id.edt_attendance);
                btncancel_acs=itemView.findViewById(R.id.btncancel_acs);
                btnok_acs=itemView.findViewById(R.id.btnOK_acs);
            }
        }
    }
    public class Adapter_firealarm extends RecyclerView.Adapter<Adapter_firealarm.programViewholder> {
        Context context;
        String[] data;


        public Adapter_firealarm(Context context, String data[]) {
            this.context = context;
            this.data = data;
        }


        @Override
        public programViewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            View view = inflater.inflate(R.layout.recycler_dialog_question, viewGroup, false);
            return new programViewholder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull programViewholder programViewholder, int i) {
            programViewholder.data_content.setText(data[i]);
        }

        @Override
        public int getItemCount() {
            return data.length;
        }

        class programViewholder extends RecyclerView.ViewHolder {
            TextView data_content;

            public programViewholder(@NonNull View itemView) {
                super(itemView);
                data_content = itemView.findViewById(R.id.tvTitle);
            }
        }
    }

}


/*

Hi there! I am Android Developer at Onerooftechnologies and Learning enthusiast. My skills includes Android, Linux and lot more to discover. If you have any idea that you would want me to develop? Let’s talk: mauryaashu1994@gmail.com

Title:Effects of Brain in Reading Books

Hey Friends, In our Society, Many of them in there daily routines are not reading books because they think that these are book are too heavy and kind of boring stuff;
or whoever reads the book with the intention of cultivating the habit of reading the books after few hours they will close it and take the decision too watch the video instead or do another stuff.

Are those right ? Is reading books good for brain ?
Let's have a big dig into it
Whenever you read, the several parts of brain works together.
        (pics)

In th above figure whenever you read the books, your brain is doing exercise & also from scientist perspective it is like doing post-workout recovery, which is good habit for releaving for stress.

A study has been conducted on several man and women. In which person tends to solve the difficult problem, afterwards that they watch holocaust documentary; due to which there is increase in stress level,;
Later for decreasing the stress level they give 3 different task that is having a tea, have a walk outside and reading your favorite books.
(pics)

During the study, the Researcher found out the stress level decreases upto 42% by drinking tea, 57% by walking outside and 68% by reading books; By reading books usually a person visualize and concentrate on whatever that is
written on it.

Researcher has also found out that it depends on your like towards the books that is why most often student read their syllabus book often boost up stress level instead of getting relaxed...hehehe



One more study has been conducted in which person are categorized into 4 groups i.e literary, popular fiction,non-fiction and which are not reading nothing, on that basis of the conducted study they test the person empath level.
pic()

In which literary fiction category scores maximum; Researchers found out that this happen because the literature books character minds are depicted vaguely, without many details and we are forced to fill in the gaps to understand their intentions and
motivations.

If you want to be smart, your knowledge, reasoning, vocabulary, empathy should be developed; for this to have a habit of reading the booksto level it.Many great people like Steve jobs,warren buffet, Elon Musk, Bill Gates used to daily read atleast 30 minutes
per day .
Pic()

Most of the people do not read books because they don't concentrate on their books. I was also one of them. So I readed the comics first, then novels and after some years started reading self help books that is how i got the habit of Reading books




*/

/*

Title: Should be bath daily ?

Hey friends, in our childhood days our parent use told us that we have to bath daily to stay clean,healthy and looks good. So many a times in winter, you don't want to take bath but our parent force us to take it and in our mind or we say Is it compulsory to bath regularly.
Let's see
 A dermatologist name Dr Ranella Hirsch said in a article that "we over-bathe and that's really important to realize". For many people it is a relaxation; but for others it might be a societal norms because of what other thinks about it when they know about it that you haven't taken
 the bath.

 According to Science, Your skin outer layer called Stratum Corneum which contains millions of microbes that are fungus, bacteria which helps your skin repair and fights and kill the bad microbes. But often many times you bath more than twice that washes away good microbes and it makes the skin more
 chances for infection prone.
 As well as Excessive use of soap to the skin makes not only good microbes washes away as well as make skin dry and gives the invitation to skin diseases.

 So in the daily routine, if you get more sweat take bath once everydat.In winter season, don't bath with boil water insetead use lukewarm water.
 If you have less sweat coming in your daily routine bath in alternate days but when you haven't taken bath just make use of towel to clean the area where sweat glands are more such as underarms and groin areas.

 I hope this article is some what informative content provided ; Please comment your views about it. Thank you!




 */
/*


Title:Snacks

To eat the right food for evening snack is crucial; evening is that time of the dat when we are extremely hungry out we hardly have an healthy option to choose from.As a result most of time
we end up eating junk food which we regret later. An non healthy eating snacks can squander your effort to eat healthy making your fitness goal for a toss
But not Anymore, in these article I am going to share you 6 quick easy healthy snack options.

While selecting the snack option my top priority has been to ensure least cooking time,easily available ingredient without a pinch on compromise on task and satisfaction. Now before uou come with any
excuse let me tell you that 3 out of 5 snack option can be made within 5 minutes.

Let's start
1. Multigrain Pancake

  This 100% multigrain pancake is quick healthy and amazingly tasty.
  To make it,in a bowl add half cup of oats flour, half cup of multigrain flour, One and half teaspoon of jaggery powder,1/4 teaspoon of cinammon powder, a pinch of baking soda, half cup of milk and
  a few raisins. Now whisk this throughly to bring out thick consitency, so your batter for pancake is ready; Next switch on the gas keep a pan on low flame put some butter on it as the butter melts; with a
  serving spoon put some batter on it. Pour the batter evenly just about 30 seconds,one side of it be cooked, flip it to cook on the other side for cook for another 30 seconds, that's all your super healthy pan cakes
  are ready.Similarly, prepare more as per your need, finally drizzle the honey on it to enhance it taste even further.

2.Makhana Chana Crunch

    If you are looking for a light; yet filling crunchy option Makhana Chana Crunch is the ideal option for you,
    Simply put a teaspoon of Ghee over a heated pan. Add some mustard seed let them splutter, put one green chilly, a few peanuts, some roasted chana, turmeric and a half cup of makhana or lotus seeds. Mix them together add rock salt to taste
    and a pinch of black pepper. Mix them properly and your super crunch makhana chana is ready to serve.

3. Banana Peanut Red rice Cake

    If you are someone who does not want to cook yet want to have a healthy, tasty and filling snack option to crunch on the evening, you need to try banana peanut red rice cake.
    Simply take 1 Red Rice cake(easily available in the market). On a red rice cake apply evenly the Peanut butter, top it with half a banana that's all dig in.
    You can even try a different toppings for a change.

4. Masala Paneer Chhenna

    If you are on a low carb diet looking for a healthy boost of proteins in the evening, masala paneer chhenna is the snack for you

    In a bowl, take about a litre of milk on a high flame, Once it comes to boil turn it to low flame, now add a teaspoon of cumin seeds,chaat masala, green chilli, black pepper, Rock salt to taste and a lot of fresh corriander leaves, then put it on hign
    flame again and add lemon juice stir well and you will instantly see the water and paneer seprating. That's all sprain the water out and your high protein,low carbs snack option are ready.
    Enjoy masala paneer chhenna with its byproduct water drink you will be full in no time.

5. Cheesy Vegetable Sandwich
    A snack option list is incomplete without the mention of sandwich isn't it!!

    First to make a sandwich we will take some grated paneer to these put some finely chopped cabagge, capsicum,curd,rock salt,black pepper and mix it thoroghly.
    Take a slice of whole wheat bread take 1 leaf of cabbage and place it over so that it does not make your sandwich soggy while carrying it along, it is although optional.
    Spread the mixture over it and add some cucumber slices and sandwich it and your sandwich is ready.

*/