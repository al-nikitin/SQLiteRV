package com.appshop162.sqliterv;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupMenu;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<ListElement> elements;
    ListElementAdapter adapter;
    DBAdapter dbAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbAdapter = new DBAdapter(this);
        dbAdapter.open();
        elements = dbAdapter.getAllListElements();
        dbAdapter.close();

        RecyclerView rv = (RecyclerView) findViewById(R.id.list);
        adapter = new ListElementAdapter(this, elements, dbAdapter);
        rv.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);
        registerForContextMenu(rv);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            ListElement listElement = new ListElement();
            dbAdapter.open();
            listElement = dbAdapter.createListElement();
            dbAdapter.close();
            adapter.add(listElement);
            adapter.notifyDataSetChanged();
        }
        return false;
    }
}
