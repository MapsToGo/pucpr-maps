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
        destinations.add(new DestinationModelView("Frente Andar 1 Bloco Azul", R.drawable.bloco1_terreo));
        destinations.add(new DestinationModelView("Fundo Andar 1 Bloco Azul", R.drawable.bloco5_terreo));
        destinations.add(new DestinationModelView("Frente Andar 3 Bloco Azul", R.drawable.bloco7_terreo));
        destinations.add(new DestinationModelView("Sala Silva", R.drawable.bloco8_terreo));
        destinations.add(new DestinationModelView("Sala Laertes", R.drawable.bloco9_terreo));

        destinations.getDestinationByName("Biblioteca").configPrevious(destinations.getDestinationByName("Portão"));
        destinations.getDestinationByName("Bloco azul").configPrevious(destinations.getDestinationByName("Portão"));
        destinations.getDestinationByName("Bloco 9").configPrevious(destinations.getDestinationByName("Portão"));
        destinations.getDestinationByName("Bloco verde").configPrevious(destinations.getDestinationByName("Portão"));
        destinations.getDestinationByName("Bloco vermelho 5").configPrevious(destinations.getDestinationByName("Portão"));
        destinations.getDestinationByName("Ginazio").configPrevious(destinations.getDestinationByName("Portão"));

        destinations.getDestinationByName("Frente Andar 1 Bloco Azul").configPrevious(destinations.getDestinationByName("Bloco azul"));
        destinations.getDestinationByName("Fundo Andar 1 Bloco Azul").configPrevious(destinations.getDestinationByName("Bloco azul"));
        destinations.getDestinationByName("Frente Andar 3 Bloco Azul").configPrevious(destinations.getDestinationByName("Frente Andar 1 Bloco Azul"));

        destinations.getDestinationByName("Sala Silva").configPrevious(destinations.getDestinationByName("Frente Andar 3 Bloco Azul"));
        destinations.getDestinationByName("Sala Laertes").configPrevious(destinations.getDestinationByName("Frente Andar 3 Bloco Azul"));

        destinations.getDestinationByName("Portão").getAdjacentes().add(destinations.getDestinationByName("Biblioteca"));
        destinations.getDestinationByName("Portão").getAdjacentes().add(destinations.getDestinationByName("Bloco azul"));
        destinations.getDestinationByName("Portão").getAdjacentes().add(destinations.getDestinationByName("Bloco 9"));
        destinations.getDestinationByName("Portão").getAdjacentes().add(destinations.getDestinationByName("Bloco verde"));
        destinations.getDestinationByName("Portão").getAdjacentes().add(destinations.getDestinationByName("Bloco vermelho 5"));
        destinations.getDestinationByName("Portão").getAdjacentes().add(destinations.getDestinationByName("Ginazio"));

        destinations.getDestinationByName("Bloco azul").getAdjacentes().add(destinations.getDestinationByName("Frente Andar 1 Bloco Azul"));
        destinations.getDestinationByName("Bloco azul").getAdjacentes().add(destinations.getDestinationByName("Fundo Andar 1 Bloco Azul"));
        destinations.getDestinationByName("Frente Andar 1 Bloco Azul").getAdjacentes().add(destinations.getDestinationByName("Frente Andar 3 Bloco Azul"));

        destinations.getDestinationByName("Frente Andar 3 Bloco Azul").getAdjacentes().add(destinations.getDestinationByName("Sala Silva"));
        destinations.getDestinationByName("Frente Andar 3 Bloco Azul").getAdjacentes().add(destinations.getDestinationByName("Sala Laertes"));

        return destinations;
    }

}
