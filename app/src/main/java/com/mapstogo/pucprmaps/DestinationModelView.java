package com.mapstogo.pucprmaps;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DestinationModelView {

    private String name;
    private int idImg;
    private DestinationModelView previous;
    private List<DestinationModelView> adjacentes = new ArrayList<>();
    private DestinationModelView next;

    public DestinationModelView(String name, int id) {
        this.name = name;
        this.idImg = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIdImg() {
        return idImg;
    }

    public void setIdImg(int idImg) {
        this.idImg = idImg;
    }

    public DestinationModelView getPrevious() {
        return previous;
    }

    public DestinationModelView getNext() {
        return next;
    }

    public List<DestinationModelView> getAdjacentes() {
        return adjacentes;
    }

    public DestinationModelView configPrevious(DestinationModelView previous){
        this.previous = previous;
        return this;
    }

    public DestinationModelView configNext(DestinationModelView next){
        this.next = next;
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof DestinationModelView))
            return Boolean.FALSE;
        DestinationModelView that = (DestinationModelView) obj;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return this.name;
    }
}
