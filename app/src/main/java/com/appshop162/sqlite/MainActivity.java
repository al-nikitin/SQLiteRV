package com.appshop162.sqlite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupMenu;

import java.util.ArrayList;
import java.util.List;

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

        adapter = new ListElementAdapter(this, elements);
        final ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);
        registerForContextMenu(listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final ListElement listElement = adapter.getItem(position);
                PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
                popupMenu.inflate(R.menu.list_item_click_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_delete:
                                System.out.println(listElement.id);
                                adapter.remove(elements.get(position));
                                adapter.notifyDataSetChanged();
                                dbAdapter.open();
                                dbAdapter.deleteListElement(listElement.id);
                                dbAdapter.close();
                                return false;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
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
