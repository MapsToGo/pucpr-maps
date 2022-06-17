package com.mapstogo.pucprmaps;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Destinations {

    private List<DestinationModelView> destinations = new ArrayList<>();

    public void add(DestinationModelView dest){
        this.destinations.add(dest);
    }

    public List<DestinationModelView> getDestinations() {
        return destinations;
    }

    public DestinationModelView getDestinationByName(String name){
        for (DestinationModelView dest : this.destinations){
            if(dest.getName().equals(name))
                return dest;
        }
        return null;
    }

    public DestinationModelView mountInversePath(DestinationModelView start, DestinationModelView finish){
        finish.configNext(null);
        DestinationModelView currentNodePath = finish;
        while(Objects.nonNull(currentNodePath.getPrevious()) &&
                !currentNodePath.getPrevious().equals(start)) {
            currentNodePath.getPrevious().configNext(currentNodePath);
            currentNodePath = currentNodePath.getPrevious();
        }
        return currentNodePath;
    }

}
