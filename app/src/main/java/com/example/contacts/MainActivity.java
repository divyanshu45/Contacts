package com.example.contacts;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Databasehelper mydb;
    Button add,viewAll,update,delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         mydb = new Databasehelper(this);

        add = findViewById(R.id.button);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                View mview = getLayoutInflater().inflate(R.layout.add_layout,null);

                final EditText name = (EditText) mview.findViewById(R.id.textView5);
                final EditText phone = (EditText) mview.findViewById(R.id.textView6);
                final EditText email = (EditText) mview.findViewById(R.id.textView7);

                builder.setView(mview);
                builder.setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        boolean isInserted = mydb.insertData(name.getText().toString(),phone.getText().toString(),email.getText().toString());
                        if(isInserted){
                            Toast.makeText(MainActivity.this, "Successfully Inserted", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(MainActivity.this, "Data Not Inserted", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                
                builder.create();
                builder.setCancelable(true);
                builder.show();
            }
        });

        viewAll = findViewById(R.id.button2);
        viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Cursor res = mydb.getAllData();

                if(res.getCount() == 0){
                    showMessage("Error","No data found");
                    return;
                }else{
                    StringBuffer buffer = new StringBuffer();
                    while(res.moveToNext()){
                        buffer.append("ID : " + res.getString(0) + "\n");
                        buffer.append("NAME : " + res.getString(1) + "\n");
                        buffer.append("Phone : " + res.getString(2) + "\n");
                        buffer.append("Email : " + res.getString(3) + "\n\n");
                    }

                    showMessage("Data",buffer.toString());
                }
            }
        });

       update = findViewById(R.id.button3);
       update.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
               View mView  = getLayoutInflater().inflate(R.layout.update_layout,null);
               builder.setView(mView);

               final EditText id = (EditText)mView.findViewById(R.id.textView8);
               final EditText name = (EditText)mView.findViewById(R.id.textView9);
               final EditText phone = (EditText)mView.findViewById(R.id.textView10);
               final EditText email = (EditText)mView.findViewById(R.id.textView11);

               builder.setPositiveButton("UPDATE", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       boolean isUpdate = mydb.updateData(id.getText().toString(),name.getText().toString(),email.getText().toString(),phone.getText().toString());
                       if(isUpdate){
                           Toast.makeText(MainActivity.this, "Data is updated", Toast.LENGTH_SHORT).show();
                       }else{
                           Toast.makeText(MainActivity.this, "Error occuring in updating", Toast.LENGTH_SHORT).show();
                       }
                   }
               });

               builder.create();
               builder.setCancelable(true);
               builder.show();
           }
       });

       delete = findViewById(R.id.button4);
       delete.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
               View mView = getLayoutInflater().inflate(R.layout.delete_layout,null);
               builder.setView(mView);

               final EditText id = (EditText) mView.findViewById(R.id.textView12);

               builder.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       int del = mydb.deleteData(id.getText().toString());
                       if(del != 0){
                           Toast.makeText(MainActivity.this,"Data deleted",Toast.LENGTH_SHORT).show();
                       }else{
                           Toast.makeText(MainActivity.this, "Error in deleting", Toast.LENGTH_SHORT).show();
                       }
                   }
               });

               builder.create();
               builder.setCancelable(true);
               builder.show();
           }
       });

    }

    public void showMessage(String title, String message){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}