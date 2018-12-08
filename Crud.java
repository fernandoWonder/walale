package com.example.fernandowonder.walale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Crud {
    private Context contexto;
    private SQLiteDatabase db;
    private Conection conection;

    public Crud(Context contexto)
    {
        this.contexto = contexto;
        conection = new Conection(contexto);
    }

    public boolean saveLetra(ContentValues contentValues)
    {
        db = conection.getWritableDatabase();
        long retorno = db.insert("tbletra",null,contentValues);
        if(retorno>=0){
            db.close();
            return true;
        }else{
            db.close();
            return false;
        }
    }

    public List<Favorito> getFavoritos(List<Musicas> listaMusica)
    {



        List<Favorito> favoritos = new ArrayList<>();
        List<Long> fav = new ArrayList<>();
        db = conection.getReadableDatabase();
        android.database.Cursor c =  db.query("tbfavoritos",new String[]{"idMusica"},null,null,null,null,null,null);
        if (c!=null && c.moveToFirst())
        {
            int idFavoritos = c.getColumnIndex("idMusica");
            do {
                fav.add(c.getLong(idFavoritos));

            }while (c.moveToNext());
            c.close();
        }



      for (int i = 0; i < fav.size(); i++ )
      {
          for (int a = 0 ; a < listaMusica.size(); a++)
          {
              if (fav.get(i) == listaMusica.get(a).getId()) {
                  favoritos.add(new Favorito(listaMusica.get(a).getTitulo(),listaMusica.get(a).getAutor(),listaMusica.get(a).getId(),listaMusica.get(a).getPath(),a));
              }
          }

      }
        db.close();
      return favoritos;
    }

    public String buscaMusicaSaved(long id)
    {
       db = conection.getReadableDatabase();
       android.database.Cursor c =  db.query("tbletra",new String[]{"_id,autor,titulo,texto,path"},"idMusica = ?",new String[]{String.valueOf(id)},null,null,null,null);
       if (c!=null && c.moveToFirst())
       {
           String letra = c.getString(c.getColumnIndex("texto"));
           c.close();
           return letra;
       }else{
           db.close();
           return null;
       }
    }

    public boolean addFavoritos(ContentValues contentValues)
    {
        db = conection.getWritableDatabase();
        long retorno = db.insert("tbfavoritos",null,contentValues);
        if(retorno>=0){
            db.close();
            return true;
        }else{
            db.close();
            return false;
        }
    }

    public boolean removeFavorito(int idMusica)
    {

        db = conection.getWritableDatabase();

        long retorno = db.delete("tbfavoritos","idMusica = ?",new String[]{String.valueOf(idMusica)});

        if (retorno>=0) {
            db.close();
            return true;
        }else {
            db.close();
            return false;
        }
    }

    public boolean isFavorito(int id)
    {
        db = conection.getReadableDatabase();
        Cursor cursor = db.query("tbfavoritos",new String[]{"idMusica"},"idMusica = ? ",new String[]{String.valueOf(id)},null,null,null);
        if (cursor.moveToFirst() && cursor != null)
        {
            cursor.close();
            return true;
        }else{
            cursor.close();
            return false;
        }
    }
}