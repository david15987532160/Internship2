package com.example.quocanhnguyen.mediaapp;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView txtvTitle, txtvTimeSong, txtvTotalTime;
    ImageView imgDisk;
    SeekBar skSong;
    ImageButton imgbtnPrev, imgbtnPlay, imgbtnNext, imgbtnStop;

    ArrayList<Song> arraySong;
    int position = 2;

    MediaPlayer mediaPlayer;
    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Implement();
        AddSong();

        animation = AnimationUtils.loadAnimation(this, R.anim.disk_rotate);

        InitMediaPlayer();

        imgbtnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetTimeTotal();
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    imgbtnPlay.setImageResource(R.drawable.play);
                    imgDisk.clearAnimation();
                } else {
                    mediaPlayer.start();
                    imgbtnPlay.setImageResource(R.drawable.pause);
                    imgDisk.startAnimation(animation);
                }
                UpdateTimeSong();
            }
        });

        imgbtnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                mediaPlayer.release();
                imgbtnPlay.setImageResource(R.drawable.play);
                InitMediaPlayer();
                imgDisk.clearAnimation();
            }
        });

        imgbtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ++position;
                if (position > arraySong.size() - 1) {
                    position = 0;
                }
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
                InitMediaPlayer();
                mediaPlayer.start();
                imgbtnPlay.setImageResource(R.drawable.pause);
                SetTimeTotal();
                UpdateTimeSong();
                imgDisk.startAnimation(animation);
            }
        });

        imgbtnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                --position;
                if (position < 0) {
                    position = arraySong.size() - 1;
                }
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
                InitMediaPlayer();
                mediaPlayer.start();
                imgbtnPlay.setImageResource(R.drawable.pause);
                SetTimeTotal();
                UpdateTimeSong();
                imgDisk.startAnimation(animation);
            }
        });

        skSong.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(skSong.getProgress());
            }
        });
    }

    private void UpdateTimeSong() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
                txtvTimeSong.setText(simpleDateFormat.format(mediaPlayer.getCurrentPosition()));
                // update progress song
                skSong.setProgress(mediaPlayer.getCurrentPosition());

                // check time song -> if end, next
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        ++position;
                        if (position > arraySong.size() - 1) {
                            position = 0;
                        }
                        if (mediaPlayer.isPlaying()) {
                            mediaPlayer.stop();
                        }
                        InitMediaPlayer();
                        mediaPlayer.start();
                        imgbtnPlay.setImageResource(R.drawable.pause);
                        SetTimeTotal();
                        UpdateTimeSong();
                    }
                });

                handler.postDelayed(this, 500);
            }
        }, 100);
    }

    private void SetTimeTotal() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("mm:ss");
        txtvTotalTime.setText(timeFormat.format(mediaPlayer.getDuration()));
        skSong.setMax(mediaPlayer.getDuration());

    }

    private void InitMediaPlayer() {
        mediaPlayer = MediaPlayer.create(MainActivity.this, arraySong.get(position).getFile());
        txtvTitle.setText(arraySong.get(position).getTitle());
    }

    private void AddSong() {
        arraySong = new ArrayList<>();

        arraySong.add(new Song("Cánh đồng yêu thương", R.raw.canh_dong_yeu_thuong));
        arraySong.add(new Song("Có thật không tôi", R.raw.co_that_khong_toi));
        arraySong.add(new Song("Có điều gì sao không nói cùng anh", R.raw.co_dieu_gi_sao_khong_noi_cung_anh));
    }

    private void Implement() {
        txtvTitle = (TextView) findViewById(R.id.textViewTitle);
        txtvTimeSong = (TextView) findViewById(R.id.textViewTimeSong);
        txtvTotalTime = (TextView) findViewById(R.id.textViewTotalTime);
        skSong = (SeekBar) findViewById(R.id.seekBar);
        imgbtnPrev = (ImageButton) findViewById(R.id.imageViewPre);
        imgbtnPlay = (ImageButton) findViewById(R.id.imageViewPlay);
        imgbtnNext = (ImageButton) findViewById(R.id.imageViewNext);
        imgbtnStop = (ImageButton) findViewById(R.id.imageViewStop);
        imgDisk = (ImageView)findViewById(R.id.imageViewDisk);
    }
}
