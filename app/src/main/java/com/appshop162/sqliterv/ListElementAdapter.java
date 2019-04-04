package com.appshop162.sqliterv;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.ArrayList;

public class ListElementAdapter extends RecyclerView.Adapter<ListElementAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private Context context;
    private ArrayList<ListElement> elements;
    private DBAdapter dbAdapter;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layout;
        ImageView imageView;
        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.layout = (LinearLayout) itemView.findViewById(R.id.list_element);
            this.imageView = (ImageView) itemView.findViewById(R.id.image);
            this.textView = (TextView) itemView.findViewById(R.id.text);
        }

    }

    public ListElementAdapter(Context context, ArrayList<ListElement> elements, DBAdapter dbAdapter) {
        super();
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.elements = elements;
        this.dbAdapter = dbAdapter;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(inflater.inflate(R.layout.list_element, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        final ListElement listElement = elements.get(i);
        viewHolder.imageView.setImageResource(listElement.getDrawable());
        viewHolder.textView.setText(listElement.text);
        viewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
                popupMenu.inflate(R.menu.list_item_click_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_delete:
                                elements.remove(i);
                                ListElementAdapter.this.notifyDataSetChanged();
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
    public int getItemCount() {
        return elements.size();
    }

    public void add(ListElement element) {
        elements.add(0, element);
        //notifyItemInserted(0);
        //notifyDataSetChanged();
    }
}
