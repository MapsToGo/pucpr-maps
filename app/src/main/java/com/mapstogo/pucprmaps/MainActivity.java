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
    private List<DestinationModelView> listDestinationsRecents = new ArrayList<>();
    private ArrayAdapter<DestinationModelView> adapterListDestinationsFound;
    private ArrayAdapter<DestinationModelView> adapterListDestinationsRecentes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadDestinations();
        configEditText();
        configAdapterListDestinationsFound();
        configAdapterListDestinationsRecents();
        configListViewFounds();
        configListViewRecents();
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
        /*
        editTextSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    return;
                }
                updateListViewFoundHeight();
                updateListView();
            }
        });
        */
    }

    private void searchTextChanged(CharSequence charSequence) {
        filterListDestinationFound(charSequence);
        if(charSequence.toString().equalsIgnoreCase("")){
            updateListViewRecentsHeight();
        } else {
            updateListViewRecentsHeight(1);
        }
        updateListViewFoundHeight();
        updateListViewFound();
    }



    private void filterListDestinationFound(CharSequence charSequence) {
        this.listDestinationsFound.clear();
        if(charSequence.toString().equalsIgnoreCase("")){
            return;
        }
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

    private void configAdapterListDestinationsRecents() {
        this.adapterListDestinationsRecentes = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, this.listDestinationsRecents);
    }

    private void configListViewFounds() {
        ListView listView = findViewById(R.id.listViewDestinationsFound);
        listView.setAdapter(this.adapterListDestinationsFound);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                DestinationModelView dest = (DestinationModelView) adapterView.getAdapter().getItem(i);
                addToRecentes(dest);
                destinationSelected(dest);
                updateListViewFoundHeight(1);
                updateListViewRecentsHeight(1);
            }
        });
    }

    private void configListViewRecents() {
        ListView listView = findViewById(R.id.listViewDestinationsRecents);
        listView.setAdapter(this.adapterListDestinationsRecentes);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                DestinationModelView dest = (DestinationModelView) adapterView.getAdapter().getItem(i);
                destinationSelected(dest);
                updateListViewFoundHeight(1);
                updateListViewRecentsHeight(1);
            }
        });
    }

    private void updateListViewFound() {
        this.adapterListDestinationsFound.notifyDataSetChanged();
    }

    private void updateListViewFoundHeight() {
        ListView listView = findViewById(R.id.listViewDestinationsFound);
        int height = 80 * this.listDestinationsFound.size();
        height = height > 240 ? 240 : height;
        height = height <= 0 ? 1 : height;
        listView.getLayoutParams().height = height;
    }

    private void updateListViewRecentsHeight() {
        ListView listView = findViewById(R.id.listViewDestinationsRecents);
        int height = 80 * this.listDestinationsRecents.size();
        height = height > 240 ? 240 : height;
        height = height <= 0 ? 1 : height;
        listView.getLayoutParams().height = height;
    }

    private void destinationSelected(DestinationModelView dest) {
        setEditSearch(dest);
        updateImageView(dest);
        hideKeyboard();
    }

    private void setEditSearch(DestinationModelView dest) {
        EditText editSearch = findViewById(R.id.editTextTextSearch);
        editSearch.setText(dest.getName());
    }

    private void addToRecentes(DestinationModelView dest) {
        if(this.listDestinationsRecents.size() >= 3){
            this.listDestinationsRecents.remove(0);
        }
        this.listDestinationsRecents.add(dest);
    }

    private void hideKeyboard() {
        InputMethodManager input = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        EditText editSearch = findViewById(R.id.editTextTextSearch);
        input.hideSoftInputFromWindow(editSearch.getWindowToken(), 0);
    }

    private void updateListViewFoundHeight(int height) {
        ListView listView = findViewById(R.id.listViewDestinationsFound);
        listView.getLayoutParams().height = height;
    }

    private void updateListViewRecentsHeight(int height) {
        ListView listView = findViewById(R.id.listViewDestinationsRecents);
        listView.getLayoutParams().height = height;
    }

    private void updateImageView(DestinationModelView dest) {
        ImageView imageView = findViewById(R.id.imageView);
        imageView.setImageResource(dest.getIdImg());
    }

}