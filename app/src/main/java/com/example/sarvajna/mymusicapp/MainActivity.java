package com.example.sarvajna.mymusicapp;

import android.content.ContentResolver;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    Button btnPause,btnStop;
    ListView lvMusic;
    String name[],path[];
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPause= (Button) findViewById(R.id.btnPause);
        btnStop= (Button) findViewById(R.id.btnStop);
        lvMusic= (ListView) findViewById(R.id.lvMusic);

        mp=new MediaPlayer();


        ContentResolver cr=getContentResolver();
        Cursor cursor=cr.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,null,null,null,null);

        name=new String[cursor.getCount()];
        path=new String[cursor.getCount()];

        int i=0;


        while (cursor.moveToNext()){
            name[i]=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
            path[i]=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
            i++;

        }

        ArrayAdapter<String> adapter =new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,name);
        lvMusic.setAdapter(adapter);


        lvMusic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String p=path[i];

                try{
                    mp.reset();
                    mp.setDataSource(p);
                    mp.prepare();
                    mp.start();
                    btnPause.setEnabled(true);
                    btnStop.setEnabled(true);
                    btnPause.setText("Pause");
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        });
        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mp.isPlaying()){
                    mp.pause();
                    btnPause.setText("Reesume");
                }else{
                    mp.start();
                    btnPause.setText("Pause");
                }
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.stop();
                btnPause.setEnabled(false);
                btnStop.setEnabled(false);
            }
        });




    }
}


