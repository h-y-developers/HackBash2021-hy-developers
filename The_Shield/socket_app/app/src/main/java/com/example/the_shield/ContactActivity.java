package com.example.the_shield;

import android.Manifest;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.security.Provider;
import java.util.ArrayList;
import java.util.List;

public class ContactActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    private List<ContactModel> contactModelList = new ArrayList<>();
    ContactAdapter contactAdapter;
    ProgressDialog progressDialog;
    EditText userNickName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressDialog = new ProgressDialog(ContactActivity.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent
        );
        userNickName = findViewById(R.id.userNickName);
//        recyclerView = findViewById(R.id.rv);
        setDataToAdapter();
//        sendSms("8511653435","Please don't use abusive word..");
        getContactInfo();

    }

    @Override
    public void onBackPressed() {
        progressDialog.dismiss();
    }

    private void setDataToAdapter(){
        contactAdapter = new ContactAdapter(contactModelList);
//        initRecyclerView();
    }

    private void sendSms(String no,String msg){

        SmsManager sms=SmsManager.getDefault();
        sms.sendTextMessage(no, null, msg,null,null);
        Toast.makeText(ContactActivity.this,"Message Sent",Toast.LENGTH_SHORT).show();
    }

//    private void initRecyclerView(){
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setAdapter(contactAdapter);
//    }

    private void getContactInfo(){
        Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;
        String ID = ContactsContract.Contacts._ID;
        String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
        String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;

        Uri PHONE_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String PHONE_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
        String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;

        ContentResolver contentResolver = getContentResolver();
//        final String[] projection = { ID, DISPLAY_NAME, NUMBER };
//        final String sa1 = "%Papa%"; // contains an "A"

        Cursor cursor = contentResolver.query(CONTENT_URI,null,null,null,DISPLAY_NAME);
//        Cursor cursor = contentResolver.query(CONTENT_URI, projection, DISPLAY_NAME + " LIKE ?",
//                new String[] { sa1 }, null);
        if (cursor.getCount() > 0){
            while (cursor.moveToNext()){
                String CONTACT_ID = cursor.getString(cursor.getColumnIndex(ID));
                String name = cursor.getString(cursor.getColumnIndex(DISPLAY_NAME));
                Log.i("ContactActivity","Contact : "+ name);
                int hasPhoneNumber = cursor.getInt(cursor.getColumnIndex(HAS_PHONE_NUMBER));
                ContactModel contactModel = new ContactModel();
                if (hasPhoneNumber > 0){
                    contactModel.setName(name);

                    Cursor phoneCursor = contentResolver.query(PHONE_URI, new String[]{NUMBER},PHONE_ID+" = ?",new String[]{CONTACT_ID},null);
                    List<String> contactList = new ArrayList<>();
                    phoneCursor.moveToFirst();
                    while (!phoneCursor.isAfterLast()){
                        String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER)).replace(" ","");
                        contactList.add(phoneNumber);
                        phoneCursor.moveToNext();
                    }
                    contactModel.setNumber(contactList);
                    contactModelList.add(contactModel);
                    phoneCursor.close();
                }
            }
            contactAdapter.notifyDataSetChanged();
            progressDialog.dismiss();
            Toast.makeText(ContactActivity.this,"Contact Synced",Toast.LENGTH_SHORT).show();
        }
    }


}
