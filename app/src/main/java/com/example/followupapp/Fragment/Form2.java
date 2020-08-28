package com.example.followupapp.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.followupapp.ModalClass.ModalClass_Problem;
import com.example.followupapp.R;
import com.example.followupapp.Utils.CheckInternet;

import java.util.ArrayList;
import java.util.List;

public class Form2 extends Fragment {

    EditText edt_address,edt_reason,edt_problem,edt_diagnosed;
    RadioButton rdbcomplete,rdbincomplete;
    RadioGroup callgroup;
    LinearLayout reasonlayout;
    Button btn_addmore,btnservice_report;
    RecyclerView recycler_addmore;
    List<ModalClass_Problem> list_problem_main;

    public static Form2 newInstance(String param1, String param2) {
        Form2 fragment = new Form2();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
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
        View myView=inflater.inflate(R.layout.fragment_form2, container, false);
        edt_problem=myView.findViewById(R.id.edittext_problem);
        edt_diagnosed=myView.findViewById(R.id.edittext_diagnosis);
        callgroup=myView.findViewById(R.id.radiogrpcall);
        reasonlayout=myView.findViewById(R.id.reasonlayout);
        rdbincomplete=myView.findViewById(R.id.rdbincomplete);
        rdbcomplete=myView.findViewById(R.id.rdbcomplete);
        edt_reason=myView.findViewById(R.id.edittext_reason);
        recycler_addmore=myView.findViewById(R.id.recycler_addmore);
        //edt_complaindate =myView.findViewById(R.id.compdate_edit_text);
        edt_address = myView.findViewById(R.id.address_edit_text);
        btn_addmore =myView.findViewById(R.id.btnaddmore);
        btnservice_report=myView.findViewById(R.id.btnservice_submit);
        list_problem_main=new ArrayList<>();
        //Adapter_addmore adapter_addmore;
        btn_addmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!edt_diagnosed.getText().toString().isEmpty() && !edt_problem.getText().toString().isEmpty()) {
                    ModalClass_Problem obj_problem = new ModalClass_Problem();
                    obj_problem.setDiagnosed(edt_diagnosed.getText().toString());
                    obj_problem.setProblem(edt_problem.getText().toString());
                    list_problem_main.add(obj_problem);
                    Adapter_addmore adapter_addmore=new
                            Adapter_addmore(getContext(),list_problem_main);
                    LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
                    linearLayoutManager.setReverseLayout(true);
                    linearLayoutManager.setStackFromEnd(true);
                    recycler_addmore.setLayoutManager(linearLayoutManager);
                    recycler_addmore.setAdapter(adapter_addmore);
                    edt_problem.setText("");

                    edt_diagnosed.setText("");
                    edt_problem.requestFocus();
                    CheckInternet.hideSoftKeyboard(getActivity());
                }else{
                    Toast.makeText(getContext(),"Please Enter all details",Toast.LENGTH_SHORT).show();
                }

            }
        });


        btnservice_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dashboard_fragment homefragment = new Dashboard_fragment();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, homefragment);
                fragmentTransaction.commit();

            }
        });

        rdbcomplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reasonlayout.setVisibility(View.GONE);
                edt_reason.setText("");
                getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                CheckInternet.hideSoftKeyboard(getActivity());
            }
        });
        rdbincomplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reasonlayout.setVisibility(View.VISIBLE);
            }
        });
        /*if(rdbcomplete.isChecked()){
            reasonlayout.setVisibility(View.GONE);
            Toast.makeText(getContext(),"1",Toast.LENGTH_SHORT).show();
        }else if(rdbincomplete.isChecked()){
            Toast.makeText(getContext(),"12",Toast.LENGTH_SHORT).show();
            reasonlayout.setVisibility(View.VISIBLE);
        }*/
        return myView;
        //return inflater.inflate(R.layout.fragment_form2, container, false);
    }

    public class Adapter_addmore extends  RecyclerView.Adapter<Form2.Adapter_addmore.Add_more_viewholder_followup> {
        Context context;
        List<ModalClass_Problem> list_problem;
        public Adapter_addmore(Context context, List<ModalClass_Problem> list_problem) {
            this.context = context;
            this.list_problem = list_problem;
        }
        @NonNull
        @Override
        public Add_more_viewholder_followup onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.custom_recycler_addmore,parent, false);
            return new Adapter_addmore.Add_more_viewholder_followup(view);
        }

        @Override
        public void onBindViewHolder(@NonNull Adapter_addmore.Add_more_viewholder_followup holder, final int position) {
            holder.desc_content.setText(list_problem.get(position).getDiagnosed());
            holder.problem.setText(list_problem.get(position).getProblem());
            holder.img_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    android.app.AlertDialog alertbox = new android.app.AlertDialog.Builder(getActivity())
                            .setTitle("Edit Item")
                            .setMessage("Are you sure you want to Delete the Item?")
                            .setPositiveButton("Yes",
                                    new DialogInterface.OnClickListener() {

                                        // do something when the button is clicked
                                        public void onClick(DialogInterface arg0, int arg1) {
                                            removeDel(position);

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
            holder.img_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(edt_diagnosed.getText().toString().isEmpty() && edt_problem.getText().toString().isEmpty()) {
                        android.app.AlertDialog alertbox = new android.app.AlertDialog.Builder(getActivity())
                                .setTitle("Edit Item")
                                .setMessage("Are you sure you want to Edit the Item?")
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

                    }else{
                        android.app.AlertDialog alertbox = new android.app.AlertDialog.Builder(getActivity())
                                .setTitle("Edit Item")
                                .setMessage("Are you sure you want to Edit the Item,While Previous one will be not edited?")
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
                }
            });


        }

        public void removeDel(int i) {
            /*edt_problem.setText(list_problem.get(i).getProblem().toString());
            edt_diagnosed.setText(list_problem.get(i).getDiagnosed().toString());*/
            list_problem.remove(i);
            edt_problem.requestFocus();
            notifyItemRemoved(i);
            notifyItemRangeChanged(i, list_problem.size());
        }


        public void removeAt(int i) {
            edt_problem.setText(list_problem.get(i).getProblem().toString());
            edt_diagnosed.setText(list_problem.get(i).getDiagnosed().toString());
            edt_problem.requestFocus();
            list_problem.remove(i);
            notifyItemRemoved(i);
            notifyItemRangeChanged(i, list_problem.size());
        }


        @Override
        public int getItemCount() {
            return list_problem.size();
        }



        public class Add_more_viewholder_followup extends RecyclerView.ViewHolder{
            TextView problem,desc_content;
            ImageView img_edit,img_delete;
            //ImageButton img_delete,img_edit;
            public Add_more_viewholder_followup(View itemView) {
                super(itemView);
                problem = itemView.findViewById(R.id.txt_problem_content);
                desc_content=itemView.findViewById(R.id.txt_diagnosed_content);
                img_edit=itemView.findViewById(R.id.img_edit_view);
                img_delete=itemView.findViewById(R.id.img_delete_view);
            }

        }
    }
}
