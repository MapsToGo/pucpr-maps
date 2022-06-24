package com.mapstogo.pucprmaps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.appsearch.AppSearchSchema;
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
    private String[] arrayNext = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13"};
    private String[] arrayPrevious = {"101", "102", "103", "104", "105", "106", "107", "108", "109", "110", "111", "112", "113"};
    private boolean stateRestore;

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
        configImgActiveBackArrow();
        checkImgFromIntent();
    }

    private void configImgActiveBackArrow() {
        ImageView activeImg = findViewById(R.id.imageViewSetaVoltarAti);
        activeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                previousDestination();
            }
        });
    }

    private void configDestinations() {
        this.destinations = new DestinationModelViewMemoryFactory().createListDestinations();
        this.destStart = this.destinations.getDestinationByName("Portão");
        this.destCurrent = this.destStart;
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
        if(this.stateRestore){
            configStateRestore(false);
            return;
        }
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

    private void configStateRestore(boolean stateRestore) {
        this.stateRestore = stateRestore;
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

    private void destinationSelected(DestinationModelView nextCurrentDest, DestinationModelView arriveDest) {
        nextCurrentDest.configPrevious(this.destCurrent);
        this.destCurrent = nextCurrentDest;
        setEditSearch(arriveDest);
        updateImageBackArrow();
        updateImageView(this.destCurrent);
        hideKeyboard();
    }

    private void restaureState(DestinationModelView nextCurrentDest, DestinationModelView arriveDest) {
        this.destCurrent = nextCurrentDest;
        updateImageBackArrow();
        updateImageView(this.destCurrent);
        hideKeyboard();
    }

    private void previousDestination() {
        if(Objects.isNull(this.destCurrent) ||
                Objects.isNull(this.destCurrent.getPrevious()))
            return;
        this.destCurrent.getPrevious().configNext(this.destCurrent);
        this.destCurrent = this.destCurrent.getPrevious();
        updateImageBackArrow();
        updateImageView(this.destCurrent);
        hideKeyboard();
    }

    private void nextDestination() {
        if(Objects.isNull(this.destCurrent) ||
                Objects.isNull(this.destCurrent.getNext()))
            return;
        this.destCurrent.getNext().configPrevious(this.destCurrent);
        this.destCurrent = this.destCurrent.getNext();
        updateImageBackArrow();
        updateImageView(this.destCurrent);
        hideKeyboard();
    }

    private DestinationModelView findDestStartPath(DestinationModelView arrival) {
        return this.destinations.mountInversePath(this.destStart, arrival);
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
        if(this.stateRestore)return;
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
                nextDestination();
            }
        });
    }

    private void configImgRecentes() {
        ImageView imageView = findViewById(R.id.imageViewRecentes);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateListViewRecentsHeight();
            }
        });
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        configStateRestore(true);
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

        outState.putString("current", this.destCurrent.getName());
        DestinationModelView destAux = this.destCurrent.getNext();

        for(String next : this.arrayNext){
            if(destAux == null)
                break;
            outState.putString(next, destAux.getName());
            destAux = destAux.getNext();
        }

        destAux = this.destCurrent.getPrevious();
        for(String previous : this.arrayPrevious){
            if(destAux == null)
                break;
            outState.putString(previous, destAux.getName());
            destAux = destAux.getPrevious();
        }
    }

    private void checkSavedCurrentDest(Bundle savedInstanceState) {

        if(savedInstanceState == null || savedInstanceState.getString("current") == null){
            return;
        }

        String destCurrentAsStr = savedInstanceState.getString("current");
        DestinationModelView destCurrent = this.destinations.getDestinationByName(destCurrentAsStr);
        DestinationModelView nextDestAux = destCurrent;

        for(String next : this.arrayNext){
            destCurrentAsStr = savedInstanceState.getString(next);
            if(Objects.nonNull(destCurrentAsStr)){
                nextDestAux.configNext(this.destinations.getDestinationByName(destCurrentAsStr));
                nextDestAux = nextDestAux.getNext();
            } else {
                break;
            }
        }

        DestinationModelView previousDestAux = destCurrent;
        for(String previous : this.arrayPrevious){
            destCurrentAsStr = savedInstanceState.getString(previous);
            if(Objects.nonNull(destCurrentAsStr)){
                previousDestAux.configPrevious(this.destinations.getDestinationByName(destCurrentAsStr));
                previousDestAux = previousDestAux.getPrevious();
            } else {
                break;
            }
        }

        restaureState(destCurrent, nextDestAux);
    }

    private void checkImgFromIntent() {
        Intent intent = getIntent();
        int idImg = intent.getIntExtra("imgIntent", 0);
        if(idImg > 0){
            ImageView imageView = findViewById(R.id.imageViewDestinations);
            imageView.setImageResource(idImg);
        }
    }

    private void updateImageBackArrow() {
        ImageView activeImg = findViewById(R.id.imageViewSetaVoltarAti);
        ImageView inactiveImg = findViewById(R.id.imageViewSetaVoltarIna);
        if(Objects.nonNull(this.destCurrent.getPrevious())){
            activeImg.setVisibility(View.VISIBLE);
            inactiveImg.setVisibility(View.INVISIBLE);
        } else {
            activeImg.setVisibility(View.INVISIBLE);
            inactiveImg.setVisibility(View.VISIBLE);
            clearEditSearch();
        }
    }

    private void clearEditSearch() {
        EditText editSearch = findViewById(R.id.editTextTextSearch);
        editSearch.setText(null);
    }

}