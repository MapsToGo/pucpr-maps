package com.mapstogo.pucprmaps;

import java.util.ArrayList;
import java.util.List;

public class DestinationModelViewMemoryFactory {

    public List<DestinationModelView> createListDestinationsModelView(){
        List<DestinationModelView> listDestinations = new ArrayList<>();
        listDestinations.add(new DestinationModelView("Bloco 9", "Bloco nas disciplinas...", "img_bloco_9"));
        listDestinations.add(new DestinationModelView("Bloco 8", "Bloco nas disciplinas...", "img_bloco_8"));
        listDestinations.add(new DestinationModelView("Bloco 6", "Bloco nas disciplinas...", "img_bloco_6"));
        listDestinations.add(new DestinationModelView("Bloco 5", "Bloco nas disciplinas...", "img_bloco_5"));
        listDestinations.add(new DestinationModelView("Bloco 4", "Bloco nas disciplinas...", "img_bloco_4"));
        listDestinations.add(new DestinationModelView("Bloco 7", "Bloco nas disciplinas...", "img_bloco_7"));
        return listDestinations;
    }

}
