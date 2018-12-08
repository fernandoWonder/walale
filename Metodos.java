package com.example.fernandowonder.walale;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Metodos {
    public List<Musicas> buscaMusica(Context context)
    {
        List<Musicas> musicasList = new ArrayList<Musicas>();
        ContentResolver contentResolver = context.getContentResolver();
        Uri musicUri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = contentResolver.query(musicUri,null,null,null,null);
        if(cursor!=null)
            if(cursor.moveToFirst()) {
                int colunaId = cursor.getColumnIndex(android.provider.MediaStore.Audio.Media._ID);
                int colunaTitulo = cursor.getColumnIndex(android.provider.MediaStore.Audio.Media.TITLE);
                int colunaAutor = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
                int index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
                do {

                    String titulo = cursor.getString(colunaTitulo);
                    String autor = cursor.getString(colunaAutor);
                    String path = cursor.getString(index);
                    long id = cursor.getLong(colunaId);

                    musicasList.add(new Musicas(titulo, autor, id,path));

                } while (cursor.moveToNext());
                cursor.close();
            }

        return musicasList;
    }

    public static void tosta(Context contexto,String s)
    {
        Toast.makeText(contexto,s,Toast.LENGTH_LONG).show();
    }
}
