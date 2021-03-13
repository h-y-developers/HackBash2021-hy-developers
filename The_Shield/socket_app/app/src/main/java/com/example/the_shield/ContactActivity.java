package com.example.the_shield;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.the_shield.Adapters.MyAdapters;
import com.example.the_shield.Model.MyContacts;

import java.util.ArrayList;

public class ContactActivity extends AppCompatActivity {
    RecyclerView recyclerView;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadContacts();
    }

    private void loadContacts(){
//        Cursor cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,null,null,null,ContactsContract.CommonDataKinds.Phone.NUMBER);
        Cursor cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,null,null,null,null);

        ArrayList<MyContacts> arrayList = new ArrayList<>();

        if(cursor.getCount() > 0){
            while (cursor.moveToNext()){
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                String number = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                if(number.length() > 0){
                    Cursor phoneCursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?", new String[]{id},null);

                    if(phoneCursor.getCount() >0){
                        while (phoneCursor.moveToNext()){
                            String phoneNumberValue = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                            MyContacts myContacts = new MyContacts(name,phoneNumberValue);

                            arrayList.add(myContacts);
                        }
                    }
                    phoneCursor.close();


                }

            }

            MyAdapters myAdapters = new MyAdapters(this,arrayList);
            recyclerView.setAdapter(myAdapters);
            myAdapters.notifyDataSetChanged();

        }
        else{
            Toast.makeText(getApplicationContext(),"No Contacts Found",Toast.LENGTH_SHORT).show();
        }
    }

}
