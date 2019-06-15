package br.com.example.hamburgos.model;

import java.io.Serializable;
import java.util.List;

public class Request implements Serializable {

    private int id;
    private String name;
    private String description;

    public int getId(){
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
