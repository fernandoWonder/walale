package com.example.fernandowonder.walale;

public class Favorito extends Musicas {

    int posicao;
    public Favorito(String titulo, String autor, long Id, String path,int posicao) {
        super(titulo, autor, Id, path);
        this.posicao = posicao;
    }

    public int getPosicao() {
        return posicao;
    }
}
