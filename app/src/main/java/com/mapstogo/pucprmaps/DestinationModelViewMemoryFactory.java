package com.mapstogo.pucprmaps;

import java.util.ArrayList;
import java.util.List;

public class DestinationModelViewMemoryFactory {

    public Destinations createListDestinations(){

        Destinations destinations = new Destinations();

        destinations.add(new DestinationModelView("Portão", R.drawable.img_map_init));
        destinations.add(new DestinationModelView("Biblioteca", R.drawable.portao_biblioteca));
        destinations.add(new DestinationModelView("Bloco azul", R.drawable.portao_bloco_azul));
        destinations.add(new DestinationModelView("Bloco 9", R.drawable.portao_bloco_nove));
        destinations.add(new DestinationModelView("Bloco verde", R.drawable.portao_bloco_verde));
        destinations.add(new DestinationModelView("Bloco vermelho 5", R.drawable.portao_bloco_vermelho_cinco));
        destinations.add(new DestinationModelView("Ginazio", R.drawable.portao_ginasio));

        destinations.add(new DestinationModelView("Térreo bloco azul", R.drawable.bloco_azul_terrreo_destino_escada));
        destinations.add(new DestinationModelView("Sala Steve Jobs", R.drawable.bloco_azul_1_andar_destino_stevejobs));
        destinations.add(new DestinationModelView("Lab 12", R.drawable.bloco_azul_2andar_destino_lab12));

        destinations.getDestinationByName("Biblioteca").configPrevious(destinations.getDestinationByName("Portão"));
        destinations.getDestinationByName("Bloco azul").configPrevious(destinations.getDestinationByName("Portão"));
        destinations.getDestinationByName("Bloco 9").configPrevious(destinations.getDestinationByName("Portão"));
        destinations.getDestinationByName("Bloco verde").configPrevious(destinations.getDestinationByName("Portão"));
        destinations.getDestinationByName("Bloco vermelho 5").configPrevious(destinations.getDestinationByName("Portão"));
        destinations.getDestinationByName("Ginazio").configPrevious(destinations.getDestinationByName("Portão"));

        destinations.getDestinationByName("Térreo bloco azul").configPrevious(destinations.getDestinationByName("Bloco azul"));
        destinations.getDestinationByName("Sala Steve Jobs").configPrevious(destinations.getDestinationByName("Térreo bloco azul"));
        destinations.getDestinationByName("Lab 12").configPrevious(destinations.getDestinationByName("Térreo bloco azul"));

        destinations.getDestinationByName("Portão").getAdjacentes().add(destinations.getDestinationByName("Biblioteca"));
        destinations.getDestinationByName("Portão").getAdjacentes().add(destinations.getDestinationByName("Bloco azul"));
        destinations.getDestinationByName("Portão").getAdjacentes().add(destinations.getDestinationByName("Bloco 9"));
        destinations.getDestinationByName("Portão").getAdjacentes().add(destinations.getDestinationByName("Bloco verde"));
        destinations.getDestinationByName("Portão").getAdjacentes().add(destinations.getDestinationByName("Bloco vermelho 5"));
        destinations.getDestinationByName("Portão").getAdjacentes().add(destinations.getDestinationByName("Ginazio"));

        destinations.getDestinationByName("Bloco azul").getAdjacentes().add(destinations.getDestinationByName("Térreo bloco azul"));
        destinations.getDestinationByName("Térreo bloco azul").getAdjacentes().add(destinations.getDestinationByName("Sala Steve Jobs"));
        destinations.getDestinationByName("Térreo bloco azul").getAdjacentes().add(destinations.getDestinationByName("Lab 12"));

        return destinations;
    }

}
