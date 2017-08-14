package com.example.quocanhnguyen.sqliteimage;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnAdd;
    ListView lvItem;
    ArrayList<Item> arrayItem;
    ItemAdapter adapter;
    public static Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // implement
        btnAdd = (Button) findViewById(R.id.buttonAdd);
        lvItem = (ListView) findViewById(R.id.listViewItem);

        // create new adapter and item list
        arrayItem = new ArrayList<>();
        adapter = new ItemAdapter(this, R.layout.row_item, arrayItem);
        lvItem.setAdapter(adapter);

        // create database
        database = new Database(this, "Manage.sqlite", null, 1);
        database.QueryData("CREATE TABLE IF NOT EXISTS Item(Id INTEGER PRIMARY KEY AUTOINCREMENT, Name VARCHAR(150), Description VARCHAR(250), Image BLOB)");

        // get data
        Cursor cursor = database.GetData("SELECT * FROM Item");
        while (cursor.moveToNext()) {
            arrayItem.add(new Item(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getBlob(3)
            ));
        }
        adapter.notifyDataSetChanged();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddItemActivity.class));
            }
        });

        lvItem.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ConfirmDelete(position, arrayItem.get(position).getId());

                return false;
            }
        });
    }

    private void ConfirmDelete(final int position, final long id) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle("Notice!");
        alertDialog.setIcon(R.drawable.warning);
        alertDialog.setMessage("Do you want to delete item " + arrayItem.get(position).getName() + " ?");

        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                which = position;
                database.QueryData("DELETE FROM Item WHERE Id = '" + id + "'");
                arrayItem.remove(which);
                adapter.notifyDataSetChanged();
            }
        });

        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }
}
