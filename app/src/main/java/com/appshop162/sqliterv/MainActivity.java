package com.appshop162.sqliterv;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

        for (ListElement element : elements) System.out.println(element.id);

        adapter = new ListElementAdapter(this, elements, dbAdapter);
        RecyclerView rv = (RecyclerView) findViewById(R.id.list);
        rv.setAdapter(adapter);
        registerForContextMenu(rv);
        // TODO -> ViewHolder

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
            elements.add(0, listElement);
            dbAdapter.open();
            dbAdapter.createListElement(listElement);
            dbAdapter.close();
        }
        adapter.notifyDataSetChanged();
        return false;
    }
}
