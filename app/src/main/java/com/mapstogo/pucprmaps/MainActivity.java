package com.mapstogo.pucprmaps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private List<DestinationModelView> listDestinationsFound = new ArrayList<>();
    private List<DestinationModelView> listDestinationsRecents = new ArrayList<>();
    private AdapterDestination adapterListDestinationsFound;
    private AdapterDestination adapterListDestinationsRecentes;

    private Destinations destinations;
    private DestinationModelView destStart;
    private DestinationModelView destCurrent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        configDestinations();
        configEditText();
        configAdapterListDestinationsFound();
        configAdapterListDestinationsRecents();
        configListViewFounds();
        configListViewRecents();
        configIconQrCode();
        configImgDestinations();
        configImgRecentes();
        checkImgFromIntent();
    }

    private void configDestinations() {
        this.destinations = new DestinationModelViewMemoryFactory().createListDestinations();
        this.destStart = this.destinations.getDestinationByName("Portão");
    }

    private void configIconQrCode() {
        ImageView view = findViewById(R.id.imageViewIconQrCode);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoImageViewReadCamera();
            }
        });
    }

    private void gotoImageViewReadCamera() {
        Intent intentGoToImageViewCamera = new Intent(this, ReadQrCodeActivity.class);
        startActivity(intentGoToImageViewCamera);
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
        for(DestinationModelView dest : this.destinations.getDestinations()){
            if(dest.getName().toLowerCase().contains(strSearch)){
                this.listDestinationsFound.add(dest);
            }
        }
    }

    private void configAdapterListDestinationsFound() {
        this.adapterListDestinationsFound = new AdapterDestination(this.listDestinationsFound, this);
    }

    private void configAdapterListDestinationsRecents() {
        this.adapterListDestinationsRecentes = new AdapterDestination(this.listDestinationsRecents, this);
    }

    private void configListViewFounds() {
        ListView listView = findViewById(R.id.listViewDestinationsFound);
        listView.setAdapter(this.adapterListDestinationsFound);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                DestinationModelView dest = (DestinationModelView) adapterView.getAdapter().getItem(i);
                addToRecentes(dest);
                DestinationModelView start = findDestStartPath(dest);
                destinationSelected(start, dest);
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
                DestinationModelView start= findDestStartPath(dest);
                destinationSelected(start, dest);
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
    /*
    private void destinationSelected(DestinationModelView dest) {
        this.destCurrent = dest;
        setEditSearch(dest);
        updateImageView(dest);
        hideKeyboard();
    }
    */
    private void destinationSelected(DestinationModelView currentDest, DestinationModelView arriveDest) {
        this.destCurrent = currentDest;
        setEditSearch(arriveDest);
        updateImageView(currentDest);
        hideKeyboard();
    }

    private void destinationNext(DestinationModelView dest) {
        this.destCurrent = dest;
        updateImageView(dest);
        hideKeyboard();
    }

    private DestinationModelView findDestStartPath(DestinationModelView arrival) {
        return this.destinations.mountInversePath(this.destStart, arrival);
    }

    private void setEditSearch(DestinationModelView dest) {
        EditText editSearch = findViewById(R.id.editTextTextSearch);
        editSearch.setText(dest.getName());
    }

    private void setEditSearch2(String str) {
        EditText editSearch = findViewById(R.id.editTextTextSearch);
        editSearch.setText(str);
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
        ImageView imageView = findViewById(R.id.imageViewDestinations);
        imageView.setImageResource(dest.getIdImg());
    }

    private void setImageSearchInit() {
        ImageView imageView = findViewById(R.id.imageViewDestinations);
        imageView.setImageResource(R.drawable.img_map_init);
    }

    private void configImgDestinations() {
        ImageView imageView = findViewById(R.id.imageViewDestinations);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkSavedCurrentDest();
            }
        });
    }

    private void configImgRecentes() {
        ImageView imageView = findViewById(R.id.imageViewRecentesControle);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView imageView = findViewById(R.id.imageViewRecentes);
                imageView.getLayoutParams().height = 300;
            }
        });
    }

    private void checkSavedCurrentDest() {
        if(Objects.nonNull(this.destCurrent) &&
                Objects.nonNull(this.destCurrent.getNext()))
            destinationNext(this.destCurrent.getNext());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        checkSavedCurrentDest(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        saveCurrentDest(outState);
        super.onSaveInstanceState(outState);
    }

    private void saveCurrentDest(Bundle outState) {
        if(Objects.isNull(this.destCurrent)){
            return;
        }
        DestinationModelView destAux = this.destCurrent;
        int count = 0;
        while(destAux != null){
            outState.putString("img_"+String.valueOf(++count), destAux.getName());
            destAux = destAux.getNext();
        }
    }

    private void checkSavedCurrentDest(Bundle savedInstanceState) {
        if(savedInstanceState == null || savedInstanceState.getString("img_1") == null){
            return;
        }
        String destCurrentAsStr = savedInstanceState.getString("img_1");
        DestinationModelView destCurrent = this.destinations.getDestinationByName(destCurrentAsStr);
        DestinationModelView destAux = destCurrent;
        int count = 1;
        do {
            destCurrentAsStr = savedInstanceState.getString("img_"+String.valueOf(++count));
            if(Objects.nonNull(destCurrentAsStr)){
                destAux.configNext(this.destinations.getDestinationByName(destCurrentAsStr));
                destAux = destAux.getNext();
            }
        } while(Objects.nonNull(destCurrentAsStr));
        destinationSelected(destCurrent, destAux);
    }

    private void checkImgFromIntent() {
        Intent intent = getIntent();
        int idImg = intent.getIntExtra("imgIntent", 0);
        if(idImg > 0){
            ImageView imageView = findViewById(R.id.imageViewDestinations);
            imageView.setImageResource(idImg);
        }
    }

}