package com.mapstogo.pucprmaps;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<DestinationModelView> listDestinationLoaded = new ArrayList<>();
    private List<DestinationModelView> listDestinationsFound = new ArrayList<>();
    private ArrayAdapter<DestinationModelView> adapterListDestinationsFound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadDestinations();
        configEditText();
        configAdapterListDestinationsFound();
        configListView();
    }

    private void loadDestinations() {
        List<DestinationModelView> listLoaded = new DestinationModelViewMemoryFactory().createListDestinationsModelView();
        this.listDestinationLoaded.addAll(listLoaded);
    }

    private void configEditText() {
        EditText editTextSearch = findViewById(R.id.editTextTextSearch);
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchTextChanged(charSequence);
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    private void searchTextChanged(CharSequence charSequence) {
        filterListDestinationFound(charSequence);
        updateListView();
    }

    private void filterListDestinationFound(CharSequence charSequence) {
        this.listDestinationsFound.clear();
        if(charSequence.toString().equalsIgnoreCase(""))
            return;
        String strSearch = charSequence.toString().toLowerCase();
        for(DestinationModelView dest : this.listDestinationLoaded){
            if(dest.getName().toLowerCase().contains(strSearch)){
                this.listDestinationsFound.add(dest);
            }
        }
    }

    private void configAdapterListDestinationsFound() {
        this.adapterListDestinationsFound = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, this.listDestinationsFound);
    }

    private void configListView() {
        ListView listView = findViewById(R.id.listViewDestinationsFound);
        listView.setAdapter(this.adapterListDestinationsFound);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                DestinationModelView dest = (DestinationModelView) adapterView.getAdapter().getItem(i);
                destinationSelected(dest);
            }
        });
    }

    private void updateListView() {
        ListView listView = findViewById(R.id.listViewDestinationsFound);
        int height = 80 * this.listDestinationsFound.size();
        height = height > 240 ? 240 : height;
        height = height <= 0 ? 1 : height;
        listView.getLayoutParams().height = height;
        this.adapterListDestinationsFound.notifyDataSetChanged();
    }

    private void destinationSelected(DestinationModelView dest) {
        updateImageView(dest);
        updateListViewHeight(1);
        hideKeyboard();
    }

    private void hideKeyboard() {
        InputMethodManager input = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        EditText editSearch = findViewById(R.id.editTextTextSearch);
        input.hideSoftInputFromWindow(editSearch.getWindowToken(), 0);
    }

    private void updateListViewHeight(int height) {
        ListView listView = findViewById(R.id.listViewDestinationsFound);
        listView.getLayoutParams().height = height;
    }

    private void updateImageView(DestinationModelView dest) {
        ImageView imageView = findViewById(R.id.imageView);
        imageView.setImageResource(dest.getIdImg());
    }

}