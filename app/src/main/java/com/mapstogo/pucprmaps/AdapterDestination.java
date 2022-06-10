package com.mapstogo.pucprmaps;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AdapterDestination extends BaseAdapter {

    private List<DestinationModelView> destinations;
    private Activity activity;

    public AdapterDestination(List<DestinationModelView> destinations, Activity activity){
        this.destinations = destinations;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return this.destinations.size();
    }

    @Override
    public Object getItem(int i) {
        return this.destinations.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View viewItemList = this.activity.getLayoutInflater().inflate(R.layout.layout_item_list, viewGroup, false);
        DestinationModelView dest = this.destinations.get(i);
        TextView textViewItemList = viewItemList.findViewById(R.id.textViewItemList);
        textViewItemList.setText(dest.getName());
        return viewItemList;
    }
}
