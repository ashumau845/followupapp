package com.example.followupapp.Fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.followupapp.Activities.AddCustomer;
import com.example.followupapp.Activities.HomeActivity;
import com.example.followupapp.DatabaseHelper.DatabaseClient;
import com.example.followupapp.DatabaseHelper.User;
import com.example.followupapp.ModalClass.ModalClass_Product;
import com.example.followupapp.R;
import com.example.followupapp.Utils.CheckInternet;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.view.View.GONE;


public class FollowupAdd extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    TextView startdate, starttime,endtime,txt_content_total;
    int mHour, mMinute;
    LinearLayout layout_sendquotation,layout_followup;
    String am_pm;
    EditText edt_description,edt_productname,edt_quantity,edt_rate;
    ArrayList<String> list_customer,list_service,list_service_type;
    List<ModalClass_Product> productList;
    Spinner spnr_select_customer,spnr_select_service,spnr_select_servicetype;
    ArrayAdapter<String> adapter_selectName,adapter_selectService,adapter_ServiceType;
    Calendar myCalendar = Calendar.getInstance();
    Calendar getMyCalendar=Calendar.getInstance();
    Button btnaddmoreproduct,btn_submit;
    DatePickerDialog.OnDateSetListener date,date2;
    RecyclerView recycler_add_dialog;
    LatLng points_address;
    public List<ModalClass_Product> productList_recycler;

    public FollowupAdd() {
        // Required empty public constructor
    }


    public static FollowupAdd newInstance(String param1, String param2) {
        FollowupAdd fragment = new FollowupAdd();
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
                .setcustomActionBar("ADD FOLLOWUP");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_followup, container, false);
        startdate = view.findViewById(R.id.txt_startdate);
        endtime=view.findViewById(R.id.txt_endtime);
        btn_submit=view.findViewById(R.id.btnfollowup_submit);
        layout_sendquotation=view.findViewById(R.id.layout_addquotation);
        layout_followup=view.findViewById(R.id.linearlayout_followup);
        starttime=view.findViewById(R.id.txt_starttime);
        edt_productname=view.findViewById(R.id.edt_productname);
        edt_quantity=view.findViewById(R.id.edt_quantity);
        edt_rate=view.findViewById(R.id.edt_rate);
        txt_content_total=view.findViewById(R.id.txt_content_amount);
        recycler_add_dialog=view.findViewById(R.id.addrecycler_dialog);
        btnaddmoreproduct=view.findViewById(R.id.btnaddmore_product);
        //spnr_select_customer=view.findViewById(R.id.sp);
        spnr_select_service=view.findViewById(R.id.spnr_select_service);
        spnr_select_customer=view.findViewById(R.id.spnr_select_customer);
        //spnr_select_servicetype=view.findViewById(R.id.spnr_select_servicetype);
        edt_description=view.findViewById(R.id.edt_description);

        productList=new ArrayList<>();
                func_dialog_spinner();

        try {
            func_initialize();
        }catch (Exception e){

        }
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dashboard_fragment homeFragment = new Dashboard_fragment();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, homeFragment);
                fragmentTransaction.commit();
            }
        });

        btnaddmoreproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValidate()){

                    double quantity=Double.parseDouble(edt_quantity.getText().toString());
                    double rate=Double.parseDouble(edt_rate.getText().toString());
                    double total=quantity*rate;

                    ModalClass_Product objproduct=new ModalClass_Product(edt_productname.getText().toString(),edt_quantity.getText().toString(),edt_rate.getText().toString(),total);
                    productList.add(objproduct);
                    edt_productname.setText("");
                    edt_quantity.setText("");
                    edt_rate.setText("");
                    edt_productname.requestFocus();
                    Adapter_Product adapter_product=new Adapter_Product(getContext(),productList);
                    //recycler_add_dialog.setAdapter(adapter_product);
                    LinearLayoutManager linearLayout = new LinearLayoutManager(getContext());
                    linearLayout.setReverseLayout(true);
                    recycler_add_dialog.setLayoutManager(linearLayout);
                    recycler_add_dialog.setAdapter(adapter_product);
                    CheckInternet.hideSoftKeyboard(getActivity());
                    recycler_add_dialog.scrollToPosition(productList.size());
                    Toast.makeText(getContext(), "Added Item Successfully", Toast.LENGTH_SHORT).show();
                    if (productList.size() < 1) {
                        adapter_product.notifyDataSetChanged();
                    }
                    if(productList_recycler.size()>0){
                        double totalamount=0;
                        for(int i=0;i<productList_recycler.size();i++){
                            totalamount+=productList_recycler.get(i).getTotal();
                        }
                        txt_content_total.setText("\u20B9 "+totalamount);
                    }
                }
            }
        });
        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        date2 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                getMyCalendar.set(Calendar.YEAR, year);
                getMyCalendar.set(Calendar.MONTH, monthOfYear);
                getMyCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                //endtime();
            }

        };


       startdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               DatePickerDialog datePicker= new DatePickerDialog(getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
               datePicker.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
               datePicker.show();
            }
        });

       starttime.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               timePicker();
           }
       });

       endtime.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               endtime();
           }
       });

    String arr_service[]={"Demonstration","Followup","New Business","Support Visit","Send Quotation"};
    String arr_user[]={"Test User1","Test User2","Test User2","Test User3","Add Customer"};
    String arr_service_type[]={"Product","AMC","Survey","Repair"};

    list_customer=new ArrayList<>();
    list_service_type=new ArrayList<>();
    list_service=new ArrayList<>();

    for(int j=0;j<arr_service.length;j++){
        list_service.add(arr_service[j]);
    }

    for(int k=0;k<arr_service_type.length;k++){
        list_service_type.add(arr_service_type[k]);
    }

    for(int l=0;l<arr_user.length;l++){
        list_customer.add(arr_user[l]);
    }



        edt_description.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                if (edt_description.hasFocus()) {
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





    adapter_selectName=new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_dropdown_item,list_customer);
    adapter_selectService=new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_dropdown_item,list_service);
//    adapter_ServiceType=new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_dropdown_item,list_service_type);

   spnr_select_customer.setAdapter(adapter_selectName);
  //  spnr_select_servicetype.setAdapter(adapter_ServiceType);
    spnr_select_service.setAdapter(adapter_selectService);
    spnr_select_service.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if(spnr_select_service.getSelectedItemPosition()==list_service.size()){
                //Toast.makeText(getContext(),"You clicked Send quotation",Toast.LENGTH_SHORT).show();
                layout_followup.setVisibility(GONE);
                layout_sendquotation.setVisibility(View.VISIBLE);
            }else if(spnr_select_service.getSelectedItemPosition()==2 || spnr_select_service.getSelectedItemPosition()==1 || spnr_select_service.getSelectedItemPosition()==3 || spnr_select_service.getSelectedItemPosition()==4 ){
                //Toast.makeText(getContext(),"You clicked Followup",Toast.LENGTH_SHORT).show();
                layout_followup.setVisibility(View.VISIBLE);
                layout_sendquotation.setVisibility(GONE);
            }else{
                layout_sendquotation.setVisibility(GONE);
                layout_followup.setVisibility(GONE);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    });
    spnr_select_customer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            if(spnr_select_customer.getSelectedItemPosition()==list_customer.size()){

                //CustomDialogCreateCustomer obj_customdialog=new CustomDialogCreateCustomer(getContext());
                //obj_customdialog.show();
                //obj_customdialog.setCancelable(false);
                Intent intent=new Intent(getActivity(),AddCustomer.class);
                startActivity(intent);
            }


        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    });
  /*  spnr_select_service.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if(spnr_select_service.getSelectedItemPosition()==2) {
                list_service_type.clear();
//                list_service_type.add("Product");
//                list_service_type.add("AMC");
//                list_service_type.add("Survey");
//                list_service_type.add("Repair");
//                adapter_ServiceType=new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_dropdown_item,list_service_type);
//                spnr_select_servicetype.setAdapter(adapter_ServiceType);
//            }
//
//            else if(spnr_select_service.getSelectedItemPosition()==1){
//                list_service_type.clear();
//                list_service_type.add("Product");
//                adapter_ServiceType=new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_dropdown_item,list_service_type);
//                spnr_select_servicetype.setAdapter(adapter_ServiceType);
//            }
//
//            else if(spnr_select_service.getSelectedItemPosition()==3){
//                list_service_type.clear();
//                list_service_type.add("Product");
//                list_service_type.add("AMC");
//                adapter_ServiceType=new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_dropdown_item,list_service_type);
//                spnr_select_servicetype.setAdapter(adapter_ServiceType);
//
//            }
//            else if(spnr_select_service.getSelectedItemPosition()==4){
//                list_service_type.clear();
//                list_service_type.add("AMC");
//                list_service_type.add("Survey");
//                list_service_type.add("Repair");
//                adapter_ServiceType=new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_dropdown_item,list_service_type);
//                spnr_select_servicetype.setAdapter(adapter_ServiceType);

            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    });
*/


        //ArrayAdapter<String> Select_Rack = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, arrayList);
        //spnr_select_rack.setAdapter(Select_Rack);

        return view;
    }

    private boolean isValidate() {
        if (edt_productname.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Please Enter Product ", Toast.LENGTH_SHORT).show();
            return false;
        } else if (edt_quantity.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Please Enter Quantity ", Toast.LENGTH_SHORT).show();
            return false;
        } else if (edt_rate.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Please Enter Rate ", Toast.LENGTH_SHORT).show();
            return false;
        }

       /*else if (edt_rate.getText().toString().equals(Double.parseDouble("0.0"))) {
            Toast.makeText(getContext(), "Please Enter Master boxes", Toast.LENGTH_SHORT).show();
            return false;
        } else if (edt_quantity.getText().toString().equals(Double.parseDouble("0.0"))) {
            Toast.makeText(getContext(), "Please Enter Master pieces per box", Toast.LENGTH_SHORT).show();
            return false;}*/
        return true;
    }

    private void func_dialog_spinner() {

        CustomDialog_Spinner customDialog_spinner=new CustomDialog_Spinner(getContext());
        customDialog_spinner.setCancelable(false);
        customDialog_spinner.show();
    }

    private void func_initialize() throws ParseException {

        Calendar now = Calendar.getInstance();
        now.add(Calendar.MINUTE, 60);
        SimpleDateFormat df = new SimpleDateFormat("h:mm a",Locale.US);
        endtime.setText(df.format(now.getTime()));
        String date_d = new SimpleDateFormat("dd/MM/yy", Locale.getDefault()).format(new Date());
        startdate.setText(date_d);
        starttime.setText(new SimpleDateFormat("h:mm a", Locale.US).format(new Date()));
        txt_content_total.setText("\u20B9 "+0.0);

    }
    private void updateLabel() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        startdate.setText(sdf.format(myCalendar.getTime()));
    }

    private void timePicker() {
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                    mHour = selectedHour;
                    mMinute = selectedMinute;

                    Calendar datetime = Calendar.getInstance();
                    datetime.set(Calendar.HOUR_OF_DAY, selectedHour);
                    datetime.set(Calendar.MINUTE, selectedMinute);

                    if (datetime.get(Calendar.AM_PM) == Calendar.AM)
                        am_pm = "AM";
                    else if (datetime.get(Calendar.AM_PM) == Calendar.PM)
                        am_pm = "PM";

                    String strHrsToShow = (datetime.get(Calendar.HOUR) == 0) ? "12" : datetime.get(Calendar.HOUR) + "";

                    starttime.setText(strHrsToShow + ":" + String.format("%02d", datetime.get(Calendar.MINUTE)) + " " + am_pm);
                    //endtime();
            }
        }, mHour, mMinute, false);//Yes 24 hour time
        mTimePicker.setTitle("Select Start Time");
        mTimePicker.show();
       // endtime();

    }

    private void endtime() {
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                mHour = selectedHour;
                mMinute = selectedMinute;

                Calendar datetime = Calendar.getInstance();
                datetime.set(Calendar.HOUR_OF_DAY, selectedHour);
                datetime.set(Calendar.MINUTE, selectedMinute);

                if (datetime.get(Calendar.AM_PM) == Calendar.AM)
                    am_pm = "AM";
                else if (datetime.get(Calendar.AM_PM) == Calendar.PM)
                    am_pm = "PM";

                String strHrsToShow = (datetime.get(Calendar.HOUR) == 0) ? "12" : datetime.get(Calendar.HOUR) + "";

                endtime.setText(strHrsToShow + ":" + String.format("%02d", datetime.get(Calendar.MINUTE)) + " " + am_pm);
            }
        }, mHour, mMinute, false);//Yes 24 hour time
        mTimePicker.setTitle("Select End Time");
        mTimePicker.show();


        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        //endtime.setText(sdf.format(getMyCalendar.getTime()));
    }



    // TODO: Rename method, update argument and hook method into UI event

    public class CustomDialog_Spinner extends Dialog {
        Button btn_dialog_submit;
        ImageView btn_dialog_close;
        Spinner spnr_custom_select_spinner;
        List<String> list_spinner;
        ArrayAdapter<String> adapter_spinner;
        public CustomDialog_Spinner(@NonNull Context context) {
            super(context);
          }
        //ProgressBar pbar_loader;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.layout_visit_dialog);

            this.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            btn_dialog_submit =findViewById(R.id.btn_dialog_submit);
            btn_dialog_close=findViewById(R.id.btn_dialog_close);
            spnr_custom_select_spinner=findViewById(R.id.spnr_select_service);
            list_spinner=new ArrayList<>();
            list_spinner.add("AMC");
            list_spinner.add("Product");
            list_spinner.add("Repair");
            list_spinner.add("Survey");
            adapter_spinner=new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_dropdown_item,list_spinner);
            spnr_custom_select_spinner.setAdapter(adapter_spinner);

            btn_dialog_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    Dashboard_fragment fragment = new Dashboard_fragment();
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, fragment);
                    fragmentTransaction.commit();
                }
            });
            btn_dialog_submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(spnr_custom_select_spinner.getSelectedItemPosition()!=0){
                        dismiss();
                    }
                }
            });


        }



    }

    public class Adapter_Product extends RecyclerView.Adapter<Adapter_Product.Product_viewholder> {
        Context context;




        public Adapter_Product(Context context,List<ModalClass_Product> List_recycler) {
            this.context = context;
            productList_recycler = List_recycler;
        }

        @NonNull
        @Override
        public Product_viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.layout_recycler_product,parent, false);
            return new Product_viewholder(view);
        }

        @NonNull


        @Override
        public void onBindViewHolder(@NonNull Product_viewholder holder, final int position) {
           holder.imgclose.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   android.app.AlertDialog alertbox = new android.app.AlertDialog.Builder(getActivity())
                           .setTitle("Inside Packing Item")
                           .setMessage("Are you sure you want to Remove the Item?")
                           .setPositiveButton("Yes",
                                   new DialogInterface.OnClickListener() {

                                       // do something when the button is clicked
                                       public void onClick(DialogInterface arg0, int arg1) {
                                           removeAt(position);
                                       }
                                   })
                           .setNegativeButton("No", new DialogInterface.OnClickListener() {
                               @Override
                               public void onClick(DialogInterface dialog, int which) {
                                   dialog.dismiss();
                               }
                           }).show();
               }
           });

           holder.imgedit.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                    if(!(edt_productname.getText().toString().isEmpty() || edt_quantity.getText().toString().isEmpty()|| edt_rate.getText().toString().isEmpty())) {
                        android.app.AlertDialog alertbox = new android.app.AlertDialog.Builder(getActivity())

                                .setMessage("Are you sure you want to Edit the Item, while the previous one will be delete?")
                                .setPositiveButton("Yes",
                                        new DialogInterface.OnClickListener() {

                                            // do something when the button is clicked
                                            public void onClick(DialogInterface arg0, int arg1) {
                                                edt_productname.setText(String.valueOf(productList_recycler.get(position).getProductName()));
                                                edt_quantity.setText(String.valueOf(productList_recycler.get(position).getQuantity()));
                                                edt_rate.setText(String.valueOf(productList_recycler.get(position).getRate()));
                                                removeAt(position);
                                            }
                                        })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).show();
                    }else{

                        android.app.AlertDialog alertbox = new android.app.AlertDialog.Builder(getActivity())
                                .setMessage("Are you sure you want to Edit the Item ?")
                                .setPositiveButton("Yes",
                                        new DialogInterface.OnClickListener() {

                                            // do something when the button is clicked
                                            public void onClick(DialogInterface arg0, int arg1) {
                                                edt_productname.setText(String.valueOf(productList_recycler.get(position).getProductName()));
                                                edt_quantity.setText(String.valueOf(productList_recycler.get(position).getQuantity()));
                                                edt_rate.setText(String.valueOf(productList_recycler.get(position).getRate()));
                                                removeAt(position);
                                            }
                                        })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).show();
                    }
               }
           });

           String txtqtyrate=productList_recycler.get(position).getQuantity()+" x \u20B9 "+productList_recycler.get(position).getRate();
           holder.txt_product.setText(productList_recycler.get(position).getProductName());
           holder.txt_qty_rate.setText(txtqtyrate);
           double quantity=Double.parseDouble(productList_recycler.get(position).getQuantity());
           double rate=Double.parseDouble(productList_recycler.get(position).getRate());
           double total=quantity*rate;
           String str_total=String.valueOf(total);
           holder.txt_totalcost.setText("\u20B9 "+str_total);
        }

        public void removeAt(int position) {
            productList_recycler.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, productList_recycler.size());

            if(productList_recycler.size()>0){
                double totalamount=0;
                for(int i=0;i<productList_recycler.size();i++){
                    totalamount+=productList_recycler.get(i).getTotal();
                }
                txt_content_total.setText("\u20B9 "+totalamount);
            }else {
                txt_content_total.setText("\u20B9 "+0.0);
            }
        }


        @Override
        public int getItemCount() {
            return productList_recycler.size();
        }

        class Product_viewholder extends RecyclerView.ViewHolder {
            TextView txt_product,txt_qty_rate,txt_totalcost;
            ImageView imgclose,imgedit;
            public Product_viewholder(@NonNull View itemView) {
                super(itemView);
                txt_product=itemView.findViewById(R.id.txtproduct);
                txt_qty_rate=itemView.findViewById(R.id.txt_qty_rate);
                txt_totalcost=itemView.findViewById(R.id.txt_totalcost);
                imgclose=itemView.findViewById(R.id.img_edit_product);
                imgedit=itemView.findViewById(R.id.img_cancel_product);
            }

        }
    }
    public class CustomDialogCreateCustomer extends Dialog implements View.OnClickListener{

        TextView btncancel,btnok,txt_content_latitude;
        Button btn_fetch;
        EditText edt_address,edt_comp_name,edt_custName,edt_contact_person,edt_mobileno,edt_emailid;
        public CustomDialogCreateCustomer(@NonNull Context context) {
            super(context);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.layout_createcustomer);
            this.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            spnr_select_customer.setVisibility(GONE);
            btncancel=findViewById(R.id.btnCancel_customer);
            btn_fetch=findViewById(R.id.btn_fetch_dialog);
            btnok=findViewById(R.id.btnOK_customer);
            txt_content_latitude=findViewById(R.id.txt_latlng);
            edt_address=findViewById(R.id.edittext_address_dialog);
            edt_comp_name=findViewById(R.id.companyname_edit_text);
            edt_custName=findViewById(R.id.customer_edit_text);
            edt_contact_person=findViewById(R.id.edittext_contactname);
            edt_mobileno=findViewById(R.id.mobile_edit_text);
            edt_emailid=findViewById(R.id.email_edit_text);

            edt_address.setOnClickListener(this);
            btnok.setOnClickListener(this);
            btncancel.setOnClickListener(this);
            btn_fetch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(edt_address.getText().toString().isEmpty()){
                        edt_address.setError("Please Provide the Address");

                    }else{
                        points_address=getLocationFromAddress(getContext(),edt_address.getText().toString().trim());
                        txt_content_latitude.setVisibility(View.VISIBLE);
                        txt_content_latitude.setText(points_address.latitude+" "+points_address.longitude);
                    }
                }
            });
            /*Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(getContext(), Locale.getDefault());

            try {
                addresses = geocoder.getFromLocation(Dashboard_fragment.latitude, Dashboard_fragment.longitude, 1);
                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName();
                edt_address.setText(address+city+postalCode);// Only if available else return NULL// Here 1 represent max location result to returned, by documents it recommended 1 to 5
            } catch (IOException e) {
                e.printStackTrace();
            }*/

            /*Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(getActivity(), Locale.getDefault());
            try {
                addresses = geocoder.getFromLocation(Dashboard_fragment.latitude, Dashboard_fragment.longitude, 1);
                String address = "";
                String pincode="";
                if (addresses != null && addresses.size() > 0) {
                    address = addresses.get(0).getAddressLine(0);
                    pincode = addresses.get(0).getPostalCode();

                    edt_address.setText(address);
                    //pincodeEditText.setText(pincode);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }*/


        }

        public LatLng getLocationFromAddress(Context context,String strAddress) {

            Geocoder coder = new Geocoder(context);
            List<Address> address;
            LatLng p1 = null;

            try {
                // May throw an IOException
                address = coder.getFromLocationName(strAddress, 5);
                if (address == null) {
                    return null;
                }

                Address location = address.get(0);
                p1 = new LatLng(location.getLatitude(), location.getLongitude() );

            } catch (IOException ex) {

                ex.printStackTrace();
            }

            return p1;
        }

        private void func_save_user() {
            final String str_com_name,str_cust_name,str_cont_person,str_mobileno,str_email;
            str_com_name=edt_comp_name.getText().toString().trim();
            str_cust_name=edt_custName.getText().toString().trim();
            str_cont_person=edt_contact_person.getText().toString().trim();
            str_mobileno=edt_mobileno.getText().toString().trim();
            str_email=edt_emailid.getText().toString().trim();

            if (str_com_name.isEmpty()) {
                edt_comp_name.setError("Company required");
                edt_comp_name.requestFocus();
                return;
            }

            else if (str_cust_name.isEmpty()) {
                edt_custName.setError("Customer Name Required");
                edt_custName.requestFocus();
                return;
            }

           else if (str_cont_person.isEmpty()) {
                edt_contact_person.setError("Contact Person Required");
                edt_contact_person.requestFocus();
                return;
            }

            else if (str_mobileno.isEmpty()) {
                edt_mobileno.setError("Mobile Number is Required");
                edt_mobileno.requestFocus();
                return;
            }


            class SaveTask extends AsyncTask<Void, Void, Void> {

                @Override
                protected Void doInBackground(Void... voids) {

                    //creating a task
                    User user=new User();
                    user.setCompanyName(str_com_name);
                    user.setContactPerson(str_cont_person);
                    user.setCustomerName(str_cust_name);
                    user.setEmail_id(str_email);
                    user.setLatitude(String.valueOf(Dashboard_fragment.latitude));
                    user.setLongitude(String.valueOf(Dashboard_fragment.longitude));

                    //adding to database
                    DatabaseClient.getInstance(getContext()).getAppDatabase().userDao().insertAll(user);
                    DatabaseClient.getInstance(getContext()).close();
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    spnr_select_customer.setVisibility(View.VISIBLE);

                    spnr_select_customer.setSelection(0);
                    dismiss();
                    Toast.makeText(getContext(), "Customer Added Successfully", Toast.LENGTH_SHORT).show();
                }
            }

            SaveTask st = new SaveTask();
            st.execute();

        }

        @Override
        public void onClick(View v) {

            switch (v.getId()){

                case R.id.edittext_address_dialog:

                case R.id.btnOK_customer:

                    func_save_user();
                    //Toast.makeText(getContext(),"Customer Added Successfully",Toast.LENGTH_SHORT).show();
                    spnr_select_customer.setVisibility(View.VISIBLE);
                    //spnr_select_customer.setSelected(false);
                    spnr_select_customer.setSelection(0);
                    this.dismiss();
                    break;
                case R.id.btnCancel_customer:
                    spnr_select_customer.setVisibility(View.VISIBLE);
                    spnr_select_customer.setSelection(0);
                   // spnr_select_customer.setSelection(-1);
                    this.dismiss();
                    break;
            }

        }
    }


}
