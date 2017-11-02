package com.ares.pulkit.studyease;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
ArrayList list=new ArrayList();
        EditText topic;
    String l_name;
    ListView listView;
    ArrayList<String> listItems;
    ArrayAdapter<String> adapter;
    SQLiteDatabase db;
    Database study1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        study1=new Database(this);

        SQLiteOpenHelper study=new Database(this);

        SQLiteDatabase db=study.getReadableDatabase();


        topic=(EditText) findViewById(R.id.edit);
        listView = (ListView) findViewById(R.id.list);
        listItems = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, listItems);
        listView.setAdapter(adapter);
        restore();

        Button button =(Button) findViewById(R.id.database);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent dbmanager = new Intent(MainActivity.this,AndroidDatabaseManager.class);
                startActivity(dbmanager);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {
                 l_name = (String) arg0.getItemAtPosition(pos);
              dialog();
                return true;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position,
                                    long arg3)
            {
                Intent intent=new Intent(MainActivity.this,Main2Activity.class);
                intent.putExtra("l_name",l_name);
                startActivity(intent);
            }
        });

    }

    public void Add(View view)
    {
        String topic1=topic.getText().toString();
        listItems.add(topic1);
        topic.setText("");
        study1.addit(topic1);
        adapter.notifyDataSetChanged();
    }
    public void restore()
    {
        ArrayList<String> arrTblNames = new ArrayList<String>();

        SQLiteOpenHelper study=new Database(this);
        SQLiteDatabase db=study.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
                int i=0;
            while (c.moveToNext() ) {
                String s = c.getString(0);
                if (s.equals("android_metadata")||s.equals("sqlite_sequence")) {
                    //System.out.println("Get Metadata");
                    continue;
                }
                else
                {
                   arrTblNames.add(c.getString(c.getColumnIndex("name")));
                   listItems.add(arrTblNames.get(i));
                    i++;
                    adapter.notifyDataSetChanged();
                }
        }
        // make sure to close the cursor
        c.close();
    }
    public void dialog(){

        new AlertDialog.Builder(this)
                .setMessage(l_name)
                .setCancelable(true)
                .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancellef1wwwd the dialog
                    study1.delete(l_name);
                     listItems.remove(l_name);
                        adapter.notifyDataSetChanged();
                    }
                }).create().show();


    }


}
