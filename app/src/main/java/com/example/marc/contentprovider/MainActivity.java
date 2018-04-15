package com.example.marc.contentprovider;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static EditText name, email, number, address, birthday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = (EditText) findViewById(R.id.txtName);
        email = (EditText) findViewById(R.id.txtEmail);
        number = (EditText) findViewById(R.id.txtContact);
        address = (EditText) findViewById(R.id.txtAddress);
        birthday = (EditText) findViewById(R.id.txtBirthday);

        this.deleteDatabase("ContentProvider_Database.db");


        findViewById(R.id.btnAdd).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                // Get all text fields into string
                String getName = name.getText().toString();
                String getEmail = email.getText().toString();
                String getNumber = number.getText().toString();
                String getAddress = address.getText().toString();
                String getBirthday = birthday.getText().toString();

                // Check if all fields are not null
                if (!getName.equals("") && getName.length() != 0
                        && !getEmail.equals("") && getEmail.length() != 0
                        && !getNumber.equals("") && getNumber.length() != 0
                        && !getAddress.equals("") && getAddress.length() != 0
                        && !getBirthday.equals("") && getBirthday.length() != 0) {

                    onClickAddData(getName, getEmail, getNumber, getAddress, getBirthday);// Add data

                    // empty all fields
                    name.setText("");
                    email.setText("");
                    number.setText("");
                    address.setText("");
                    birthday.setText("");
                }
                // else show toast
                else
                    Toast.makeText(MainActivity.this,
                            "Please fill all details.", Toast.LENGTH_SHORT)
                            .show();

            }
        });

        findViewById(R.id.btnShow).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView resultView = (TextView) findViewById(R.id.result);
                Cursor cursor = getContentResolver().query(
                        Custom_ContentProvider.CONTENT_URI, null, null, null,
                        null);// Get cursor

                // Check if it is null or not
                if (cursor != null && cursor.moveToFirst()) {

                    StringBuilder result = new StringBuilder();
                    // Loop to all items
                    while (!cursor.isAfterLast()) {
                        // Add data to string builder
                        result.append("Id - "
                                        + cursor.getString(cursor.getColumnIndex("id"))
                                        + "\nName - "
                                        + cursor.getString(cursor.getColumnIndex("name"))
                                        + "\nEmail - "
                                        + cursor.getString(cursor.getColumnIndex("email"))
                                        + "\nPhone - "
                                        + cursor.getString(cursor.getColumnIndex("number"))
                                        + "\nAddress - "
                                        + cursor.getString(cursor.getColumnIndex("address"))
                                        + "\nBirthday - "
                                        + cursor.getString(cursor.getColumnIndex("birthday"))
                                        + "\n\n");
                        cursor.moveToNext();
                    }
                    resultView.setText(result);// finally set string builder to
                    // textview
                } else {
                    // if cursor is null then set data empty
                    resultView.setText("No Contact.");
                }
            }
        });
    }

    // Add data method
    private void onClickAddData(String name, String email, String number, String address, String birthday) {
        ContentValues values = new ContentValues();// Content values to add data
        values.put(Custom_ContentProvider.name, name);
        values.put(Custom_ContentProvider.email, email);
        values.put(Custom_ContentProvider.number, number);
        values.put(Custom_ContentProvider.address, address);
        values.put(Custom_ContentProvider.birthday, birthday);
        Uri uri = getContentResolver().insert(
                Custom_ContentProvider.CONTENT_URI, values);// Insert data to
        // content provider
        Toast.makeText(getBaseContext(), "New Data Inserted.",
                Toast.LENGTH_LONG).show();
    }
}


