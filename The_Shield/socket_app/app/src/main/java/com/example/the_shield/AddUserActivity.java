package com.example.the_shield;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class AddUserActivity extends AppCompatActivity {
    private int SMS_PERMISSION_CODE = 1;
    private Button setNickName,contacts;
    private EditText userNickName;
    static final int REQUEST_CODE = 123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        userNickName = findViewById(R.id.userNickName);
        setNickName = findViewById(R.id.setNickName);
//        contacts = findViewById(R.id.contacts);

        if (ContextCompat.checkSelfPermission(AddUserActivity.this,
                Manifest.permission.SEND_SMS) +
                ContextCompat.checkSelfPermission(AddUserActivity.this,
                        Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(AddUserActivity.this,
                    Manifest.permission.SEND_SMS) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(AddUserActivity.this,
                            Manifest.permission.READ_CONTACTS)
            ) {

                AlertDialog.Builder builder = new AlertDialog.Builder(
                        AddUserActivity.this
                );
                builder.setTitle("Permission Not Granted");
                builder.setMessage("Contacts and SMS Permission");
                builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(
                                AddUserActivity.this,
                                new String[]{
                                        Manifest.permission.READ_CONTACTS,
                                        Manifest.permission.SEND_SMS
                                },
                                REQUEST_CODE
                        );
                    }
                });
                builder.setNegativeButton("Cancel", null);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
            else {
                ActivityCompat.requestPermissions(
                        AddUserActivity.this,
                        new String[]{
                                Manifest.permission.READ_CONTACTS,
                                Manifest.permission.SEND_SMS
                        },
                        REQUEST_CODE
                );
            }

        } else {
            Toast.makeText(getApplicationContext(),"Permission already Granted",Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(AddUserActivity.this, com.example.the_shield.ContactActivity.class);
//            startActivity(intent);
        }


        userNickName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() > 0) {
                    setNickName.setEnabled(true);
                    Log.i(MainActivity.TAG, "onTextChanged: ABLED");
                } else {
                    Log.i(MainActivity.TAG, "onTextChanged: DISABLED");
                    setNickName.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });


        setNickName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AddUserActivity.this, com.example.the_shield.MainActivity.class);
                intent.putExtra("username", userNickName.getText().toString());
                startActivity(intent);
                Intent i = new Intent(AddUserActivity.this,com.example.the_shield.ContactActivity.class);
                startActivity(i);

            }
        });


//        contacts.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(AddUserActivity.this, com.example.the_shield.ContactActivity.class);
//                startActivity(intent);
//            }
//        });
    }




//    private void requestSmsPermission() {
//        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
//                Manifest.permission.SEND_SMS) ||
//            ActivityCompat.shouldShowRequestPermissionRationale(this,
//                    Manifest.permission.READ_CONTACTS) ) {
//            new AlertDialog.Builder(this)
//                    .setTitle("Permission needed")
//                    .setMessage("This permission is needed because of this and that")
//                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            ActivityCompat.requestPermissions(AddUserActivity.this,
//                                    new String[] {Manifest.permission.SEND_SMS, Manifest.permission.READ_CONTACTS}, REQUEST_CODE);
//                        }
//                    })
//                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    })
//                    .create().show();
//        } else {
//            ActivityCompat.requestPermissions(AddUserActivity.this,
//                    new String[] {Manifest.permission.SEND_SMS, Manifest.permission.READ_CONTACTS}, REQUEST_CODE);
//        }
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE)  {
            if (grantResults.length > 0 && (grantResults[0] + grantResults[1]) == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(AddUserActivity.this, com.example.the_shield.ContactActivity.class);
//                startActivity(intent);
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
