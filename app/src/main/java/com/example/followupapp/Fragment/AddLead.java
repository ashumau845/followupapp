package com.example.followupapp.Fragment;

import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.followupapp.Activities.HomeActivity;
import com.example.followupapp.R;
import com.example.followupapp.Utils.DeCryptor;
import com.example.followupapp.Utils.EnCryptor;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class AddLead extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Button btnsubmit;
    EditText edt_address;
    EditText encryptdata,decryptdata;
    Button btn_encrypt,btn_decrypt;
    private EnCryptor encryptor;
    private DeCryptor decryptor;
    private static final String SAMPLE_ALIAS = "MYALIAS";

    public static AddLead newInstance(String param1, String param2) {
        AddLead fragment = new AddLead();
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((HomeActivity) getActivity())
                .setcustomActionBar("");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View myView = inflater.inflate(R.layout.fragment_add_lead, container, false);

        //edt_complaindate =myView.findViewById(R.id.compdate_edit_text);
        edt_address = myView.findViewById(R.id.edittext_address);
        btnsubmit = myView.findViewById(R.id.btn_submit_addlead);
        //Adapter_addmore adapter_addmore;

        encryptor = new EnCryptor();

        try {
            decryptor = new DeCryptor();
        } catch (CertificateException | NoSuchAlgorithmException | KeyStoreException |
                IOException e) {
            e.printStackTrace();
        }

        encryptdata=myView.findViewById(R.id.edit_text_encrypt);
        decryptdata=myView.findViewById(R.id.edit_text_decrypt);
        btn_encrypt =myView.findViewById(R.id.btnencrypt);
        btn_decrypt=myView.findViewById(R.id.btndecrypt);

        btn_decrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decryptText();
            }
        });

        btn_encrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                encryptText();
            }
        });





        edt_address.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (edt_address.hasFocus()) {
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

        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Form2 homefragment = new Form2();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, homefragment);
                fragmentTransaction.commit();

            }
        });







        return myView;
    }

    private void decryptText() {
        try {
            decryptdata.setText(decryptor
                    .decryptData(SAMPLE_ALIAS, encryptor.getEncryption(), encryptor.getIv()));
        } catch (UnrecoverableEntryException | NoSuchAlgorithmException |
                KeyStoreException | NoSuchPaddingException | NoSuchProviderException |
                IOException | InvalidKeyException e) {
            Log.e("TAG", "decryptData() called with: " + e.getMessage(), e);
        } catch (IllegalBlockSizeException | BadPaddingException | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
    }

    private void encryptText() {

        try {
            final byte[] encryptedText = encryptor
                    .encryptText(SAMPLE_ALIAS, encryptdata.getText().toString());
            decryptdata.setText(Base64.encodeToString(encryptedText, Base64.DEFAULT));
        } catch (UnrecoverableEntryException | NoSuchAlgorithmException | NoSuchProviderException |
                KeyStoreException | IOException | NoSuchPaddingException | InvalidKeyException e) {
            Log.e("TAG", "onClick() called with: " + e.getMessage(), e);
        } catch (InvalidAlgorithmParameterException | SignatureException |
                IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
    }

}

