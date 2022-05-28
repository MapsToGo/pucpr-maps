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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadDestinations();
    }

    private void loadDestinations() {
        List<DestinationModelView> listLoaded = new DestinationModelViewMemoryFactory().getAllDestinations();
        this.listDestinationLoaded.addAll(listLoaded);
    }

}