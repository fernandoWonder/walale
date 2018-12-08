package com.example.fernandowonder.walale;

import android.media.MediaPlayer;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Musicas {

    private String titulo;
    private String autor;
    private long Id;
    private String path;
    public Musicas(String titulo,String autor,long Id,String path){

        this.titulo = titulo;
        this.autor = autor;
        this.Id = Id;
        this.path = path;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public long getId() {
        return Id;
    }

    public String getPath() {
        return path;
    }
}
