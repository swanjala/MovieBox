package com.example.sam.moviebox.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.support.annotation.NonNull;

@Entity(tableName = "Genre",
        indices = {@Index("id")},
primaryKeys = {"id"})
public class GenreModel {

    @NonNull
    public int id;
    public String name;

    public GenreModel(int id, String name){
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {return "ClassPojo [id= "+id+", name = "+name+"]";
    }
}
