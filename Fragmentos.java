package com.example.fernandowonder.walale;

import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class Fragmentos  {

    private Fragment fragment;
    private String nome;
    private List<Fragmentos> lista = new ArrayList<>();
    public Fragmentos()
    {

    }
    public Fragmentos(Fragment fragment,String nome)
    {
        this.nome=nome;
        this.fragment = fragment;
    }


    public void addFrag(Fragment frag, String nome)
    {
        lista.add(new Fragmentos(frag,nome));
    }
    public List<Fragmentos> getLista()
    {
        return lista;
    }

    public void clearAllFragmentos()
    {
        lista.clear();
    }

    public Fragment getFragment() {
        return fragment;
    }

    public String getNome() {
        return nome;
    }
}
