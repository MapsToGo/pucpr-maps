package com.mapstogo.pucprmaps;

import java.util.ArrayList;
import java.util.List;

public class DestinationModelViewMemoryFactory {

    public List<DestinationModelView> createListDestinationsModelView(){
        List<DestinationModelView> listDestinations = new ArrayList<>();
        listDestinations.add(new DestinationModelView("Biblioteca", "Biblioteca", "img_bloco_9", R.drawable.portao_biblioteca));
        listDestinations.add(new DestinationModelView("Bloco azul", "Bloco azul", "img_bloco_8", R.drawable.portao_bloco_azul));
        listDestinations.add(new DestinationModelView("Bloco 9", "Bloco 9", "img_bloco_6", R.drawable.portao_bloco_nove));
        listDestinations.add(new DestinationModelView("Bloco verde", "Bloco verde", "img_bloco_5", R.drawable.portao_bloco_verde));
        listDestinations.add(new DestinationModelView("Bloco vermelho 5", "Bloco vermelho 5", "img_bloco_4", R.drawable.portao_bloco_vermelho_cinco));
        listDestinations.add(new DestinationModelView("Ginazio", "Ginazio", "img_bloco_7", R.drawable.portao_ginasio));
        return listDestinations;
    }

}
