package com.example.fernandowonder.walale.activitys;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSeekBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.fernandowonder.walale.Crud;
import com.example.fernandowonder.walale.Metodos;
import com.example.fernandowonder.walale.Musicas;
import com.example.fernandowonder.walale.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Reproduzindo extends AppCompatActivity {

    LinearLayout linearLayout;
    StringRequest request;
    RequestQueue queue;
    ImageButton btnPrev,btnNext,btnPlay;
    int duracion;
    AppCompatSeekBar seekBar;
    MediaPlayer mediaPlayer;
    Toolbar toolbar;
    TextView txtAutor;
    TextView txtTitulo;
    TextView txtCorpo;
    List<Musicas> musicasList;
    ProgressBar pgr;
    int posicao;
    Musicas musicas;
    ScrollView scrollView;
    Metodos metodos = new Metodos();
    String titulo;
    View view;
    Crud crud;
    Button btnYes;
    Button btnNo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reproduzindo);

        crud = new Crud(getApplicationContext());
        queue = Volley.newRequestQueue(this);

        btnYes = (Button)findViewById(R.id.yes);
        btnNo = (Button)findViewById(R.id.no);
        scrollView = (ScrollView)findViewById(R.id.scroll);
        mediaPlayer = new MediaPlayer();

        linearLayout = (LinearLayout)findViewById(R.id.saveLinear);

        btnNext = (ImageButton)findViewById(R.id.btnNext);
        btnPlay = (ImageButton)findViewById(R.id.btnPlay);
        btnPrev = (ImageButton)findViewById(R.id.btnPrev);
        view = (View) LayoutInflater.from(getContexto()).inflate(R.layout.failed,scrollView,false);
        pgr = (ProgressBar)findViewById(R.id.pgr);
        toolbar = (Toolbar) findViewById(R.id.toolbarPlay);
        seekBar = (AppCompatSeekBar)findViewById(R.id.seekPlay);
        seekBar.setOnSeekBarChangeListener(seekLinstener());

        //////////////////////////////////////////
        btnNo.setOnClickListener(clickBtnNo());
        btnYes.setOnClickListener(clickBtnYes());
        btnNext.setOnClickListener(btnNextClick());
        btnPlay.setOnClickListener(btnPlayClick());
        btnPrev.setOnClickListener(btnPrevClick());
        setToolbar(toolbar);
        ////////////////////////////////////////////

        txtAutor = (TextView)findViewById(R.id.txtAutorR);
        txtTitulo = (TextView)findViewById(R.id.txtTituloR);
        txtCorpo = (TextView)findViewById(R.id.txtLetra);
        posicao = getIntent().getIntExtra("position",0);
        musicasList =  metodos.buscaMusica(this);
        Collections.sort(musicasList, new Comparator<Musicas>() {
            @Override
            public int compare(Musicas musicas, Musicas t1) {
                return musicas.getTitulo().compareTo(t1.getTitulo());
            }
        });
        play();
        if (!mediaPlayer.isPlaying())
        btnPlay.setImageResource(R.drawable.playng);


    }

    public View.OnClickListener clickBtnYes()
    {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Metodos.tosta(getContexto(),"Yes");
                ContentValues contentValues = new ContentValues();
                contentValues.put("idMusica",musicasList.get(posicao).getId());
                contentValues.put("texto",String.valueOf(txtCorpo.getText()));
                contentValues.put("autor",String.valueOf(txtAutor.getText()));
                contentValues.put("titulo",String.valueOf(txtTitulo.getText()));
                if(crud.saveLetra(contentValues))
                    linearLayout.setVisibility(View.INVISIBLE);
                else
                    Metodos.tosta(getContexto(),"Letra não Salva");
            }
        };
    }
    public View.OnClickListener clickBtnNo()
    {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog();
            }
        };
    }

    public AppCompatSeekBar.OnSeekBarChangeListener seekLinstener()
    {
           return new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        };
    }
    public void setButton(int id)
    {
        MenuItem item = (MenuItem)findViewById(R.id.fav);

       if (crud.isFavorito(id))
       {
           item.setIcon(R.drawable.ic_action_name_full);
       }else{
            item.setIcon(R.drawable.ic_action_name);
       }
    }

    public void setSeekPosition(boolean reverse)
    {
        if(reverse) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if ( mediaPlayer != null ){
                        seekBar.setProgress(mediaPlayer.getCurrentPosition());
                        setSeekPosition(true);
                    }
                }
            }, 0);
        }
    }

    public String buscaLetra()
    {
        Crud crud = new Crud(this);
        return crud.buscaMusicaSaved(musicas.getId());
    }

    public void setToolbar (Toolbar toolbar)
    {
        toolbar = (Toolbar) findViewById(R.id.toolbarPlay);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case android.R.id.home:
                mediaPlayer.stop();
                finish();
                break;
            case R.id.fav:
                    item.setIcon(R.drawable.ic_action_name_full);
                isFavorite((int)musicasList.get((int)posicao).getId());
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_play,menu);
        return true;
    }


    public View.OnClickListener btnPrevClick()
    {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               prev();
            }
        };
    }

    public View.OnClickListener btnPlayClick()
    {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             if (mediaPlayer.isPlaying()) {
                 mediaPlayer.pause();
                 btnPlay.setImageResource(R.drawable.playng);
             } else {
                 mediaPlayer.start();
                 btnPlay.setImageResource(R.drawable.pause);
             }
            }
        };
    }

    public View.OnClickListener btnNextClick()
    {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                next();
            }
        };
    }

    public Context getContexto()
    {
        return this;
    }



    public void play()
    {
        try {
            pgr.setVisibility(View.VISIBLE);
            musicasList =  metodos.buscaMusica(this);
            Collections.sort(musicasList, new Comparator<Musicas>() {
                @Override
                public int compare(Musicas musicas, Musicas t1) {
                    return musicas.getTitulo().compareTo(t1.getTitulo());
                }
            });
            musicas = musicasList.get(posicao);
            mediaPlayer.setDataSource(musicas.getPath());
            mediaPlayer.prepare();
            mediaPlayer.start();
            seekBar.setMax(mediaPlayer.getDuration());
            if (mediaPlayer.isPlaying())
                setSeekPosition(true);
                new Handler().postDelayed(new Runnable()
            {
                @Override
                public void run() {
                    txtAutor.setText(musicas.getAutor());
                    txtTitulo.setText(musicas.getTitulo());
                    titulo = musicas.getTitulo();
                    if (buscaLetra()==null)
                    {
                         buscaLetraOnline(musicas.getTitulo(),"http://novimusic.000webhostapp.com/letra/api.php");
                    }else{
                        hideShowProgressBar();
                        txtCorpo.setText(buscaLetra());
                    }
                }
            },1000);

        }catch (Exception erro)
        {
            erro.printStackTrace();
        }
    }
    public void next()
    {
        try {
            setSeekPosition(false);
            mediaPlayer.reset();
            seekBar.setProgress(0);
            if (posicao+1 < musicasList.size())
            {
                posicao++;
            }else{
                posicao = 0;
            }
            musicas = musicasList.get(posicao);
            mediaPlayer.setDataSource(musicas.getPath());
            mediaPlayer.prepare();
            mediaPlayer.start();
            seekBar.setMax(mediaPlayer.getDuration());
            btnPlay.setImageResource(R.drawable.pause);
            if (mediaPlayer.isPlaying())
                setSeekPosition(true);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    txtAutor.setText(musicasList.get((int)posicao).getAutor());
                    txtTitulo.setText(musicasList.get((int)posicao).getTitulo());
                     if (buscaLetra()==null)
                    {
                        buscaLetraOnline(musicas.getTitulo(),"http://novimusic.000webhostapp.com/letra/api.php");
                    }else{
                        hideShowProgressBar();
                        txtCorpo.setText(buscaLetra());
                    }

                }
            },2000);
        }catch (IOException erro)
        {
            Metodos.tosta(getContexto(),"erro next");
            erro.printStackTrace();
        }
    }
    public void prev()
    {
        try {
            mediaPlayer.reset();
            seekBar.setProgress(0);
            if (posicao > 0)
            {
                posicao--;
            }else{
                posicao = musicasList.size()-1;
            }
            musicas =  musicasList.get(posicao);
            mediaPlayer.setDataSource(musicas.getPath());
            mediaPlayer.prepare();
            mediaPlayer.start();
            seekBar.setMax(mediaPlayer.getDuration());
            btnPlay.setImageResource(R.drawable.pause);
              new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    txtAutor.setText(musicas.getAutor());
                    txtTitulo.setText(musicas.getTitulo());
                    txtAutor.setText(musicas.getAutor());
                    if (buscaLetra()==null)
                    {
                        buscaLetraOnline(musicas.getTitulo(),"http://novimusic.000webhostapp.com/letra/api.php");
                    }else{
                        pgr.setVisibility(View.INVISIBLE);
                        txtCorpo.setText(buscaLetra());
                    }

                }
            },2000);
        }catch (Exception erro)
        {
            erro.printStackTrace();
        }
    }

    public void buscaLetraOnline(final String titulo, final String autor, final String apiUrl)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        request = new StringRequest(Request.Method.POST, apiUrl, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try{
                                    JSONObject jsonObject = new JSONObject(response);
                                    if (!jsonObject.getBoolean("erro")) {
                                        hideShowProgressBar();
                                        txtCorpo.setText(jsonObject.getString("texto"));
                                        linearLayout.setVisibility(View.VISIBLE);
                                    }else{
                                        alertDialog();
                                    }
                                }catch (JSONException e)
                                {
                                    Metodos.tosta(getContexto(),"Algo deu errado");
                                    Log.e("TAG","erro -> catch");
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Metodos.tosta(getContexto(),"Verifique a conexao com a internet");
                                hideShowProgressBar();
                                dialogNoSave();
                            }
                        }){
                            @Override
                            protected Map<String, String> getParams()  {
                                Map<String,String> params = new HashMap<>();
                                params.put("titulo",titulo);
                                params.put("autor",autor);
                                return params;
                            }
                        };

                        queue.add(request);
                        queue.start();
                    }
                });
            }
        }).start();
    }

    public void buscaLetraOnline(final String titulo, final String apiUrl)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        request = new StringRequest(Request.Method.POST, apiUrl, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try{
                                    JSONObject jsonObject = new JSONObject(response);
                                    if (!jsonObject.getBoolean("erro")) {
                                        pgr.setVisibility(View.INVISIBLE);
                                        txtCorpo.setText(jsonObject.getString("texto"));
                                        linearLayout.setVisibility(View.VISIBLE);
                                    }else{
                                        try {
                                            alertDialog();
                                        }catch (Exception e){}
                                    }
                                }catch (JSONException e)
                                {
                                    Metodos.tosta(getContexto(),"Algo deu errado");
                                    Log.e("TAG","erro -> catch");
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                try {
                                    Metodos.tosta(getContexto(), "Verifique a conexao com a internet");
                                    hideShowProgressBar();
                                    dialogNoSave();
                                }catch (Exception e)
                                {

                                }
                            }
                        }){
                            @Override
                            protected Map<String, String> getParams()  {
                                Map<String,String> params = new HashMap<>();
                                params.put("titulo",titulo);
                                return params;
                            }
                        };

                        queue.add(request);
                        queue.start();
                    }
                });
            }
        }).start();
    }


    public void alertDialog()
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(getContexto());
        alert.setTitle("Nada Encontrado");
        alert.setMessage("Preenha em baixo para procurar profundamente");
        final View v = LayoutInflater.from(getContexto()).inflate(R.layout.dialog_layout_search,null,false);
        alert.setView(v);
        alert.setPositiveButton("Procurar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                 String autor = ((EditText)v.findViewById(R.id.txtAutorDialog)).getText().toString();
                 String title = ((EditText)v.findViewById(R.id.txtTituloDialog)).getText().toString();
                 buscaLetraOnline(title,autor,"http://novimusic.000webhostapp.com/letra/apiDeep.php");
            }
        });

        alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                hideShowProgressBar();
            }
        });
        alert.create();
        alert.show();
    }

    public void hideShowProgressBar()
    {
        if (pgr.getVisibility()==View.INVISIBLE) {
            pgr.setVisibility(View.VISIBLE);
        }else{
            pgr.setVisibility(View.INVISIBLE);
        }
    }

    public void isFavorite(final int id)
    {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
               if (!crud.isFavorito(id))
               {
                   Metodos.tosta(getContexto(),"id "+id);
                   ContentValues contentValues = new ContentValues();
                   contentValues.put("idMusica",id);
                   crud.addFavoritos(contentValues);
               }
            }
        });
    }

    @Override
    public void onBackPressed() {
        mediaPlayer.stop();
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        if (mediaPlayer.isPlaying() &&  mediaPlayer != null)
            mediaPlayer.release();
        super.onDestroy();
    }
    public void dialogNoSave()
    {
        final View v = LayoutInflater.from(getContexto()).inflate(R.layout.failed,null,false);
        AlertDialog.Builder alert = new AlertDialog.Builder(getContexto());
        alert.setView(v);
        alert.setTitle("Letra Nao salva");
        alert.setMessage("A letra ainda foi salva, conecta-se a internet pra salvar");
        alert.setNeutralButton("Ta fix",null);
        alert.create();
        alert.show();
    }
}
