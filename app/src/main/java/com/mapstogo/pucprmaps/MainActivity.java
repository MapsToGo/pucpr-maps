package com.mapstogo.pucprmaps;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<DestinationModelView> listDestinationLoaded = new ArrayList<>();
    private List<DestinationModelView> listDestinationsFound = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadDestinations();
        configEditText();
    }

    private void loadDestinations() {
        List<DestinationModelView> listLoaded = new DestinationModelViewMemoryFactory().getAllDestinations();
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
                filterListDestinationFound(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    private void filterListDestinationFound(CharSequence charSequence) {
        this.listDestinationsFound.clear();
        String strSearch = charSequence.toString().toLowerCase();
        for(DestinationModelView dest : this.listDestinationLoaded){
            if(dest.getName().toLowerCase().contains(strSearch)){
                this.listDestinationsFound.add(dest);
            }
        }
    }
}