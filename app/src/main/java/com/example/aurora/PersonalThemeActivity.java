package com.example.aurora;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.larswerkman.holocolorpicker.ColorPicker;


public class PersonalThemeActivity extends BluetoothConnection implements ColorPicker.OnColorChangedListener {

    private ImageView spotifyIcon;
    private static MediaPlayer oceanMediaPlayer;
    private SeekBar soundSeekBar;
    AudioManager audioManager;
    int volume;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_theme);
        initalizeWidgets();
        setFM();
        setMediaPlayer();
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        setSoundSeekBar();
        trackSoundBarProgress();
        initVolControl();

        getColorPicker();
    }

    /**
     * Sets up fragment managers
     */
    void setFM() {
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragmentTheme = fm.findFragmentById(R.id.fragment_theme);
        if (fragmentTheme == null) {
            fragmentTheme = new ThemeFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_theme, fragmentTheme)
                    .commit();
        }
       /* Fragment fragmentBlob = fm.findFragmentById(R.id.fragment_color_picker);
        if (fragmentBlob == null) {
            fragmentBlob = new ColorPickerFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_color_picker, fragmentBlob)
                    .commit();
        } */
    }

    public void getColorPicker() {
        final ColorPicker picker = (ColorPicker) findViewById(R.id.picker);
        findViewById(R.id.picker);
        //To get the color

        picker.getColor();
        int curColor;
        int red = Color.red(picker.getColor());
        int green = Color.green(picker.getColor());
        int blue = Color.blue(picker.getColor());
        curColor = Color.rgb(red, green, blue);
        System.out.println("color picker color " + picker.getColor() + "cur rgb " + curColor );

        //To set the old selected color u can do it like this
        picker.setOldCenterColor(picker.getColor());
        //adds listener to the colorpicker which is implemented in the activity
        picker.setOnColorChangedListener(this);

        //to turn of showing the old color
        picker.setShowOldCenterColor(false);


        picker.setOnColorChangedListener(new ColorPicker.OnColorChangedListener() {
            @Override
            public void onColorChanged(int color) {
                int green = Color.green(color);
                int red = Color.red(color);
                int blue = Color.blue(color);

                StringBuilder sb = new StringBuilder();
                sb.append("");
                sb.append("," + red + "," + green + "," + blue );
                String s = sb.toString();

                System.out.println("picked color " + s);


               (new Thread (new workerThread(s))).start();
                System.out.println("has sent thread");
                System.out.println(s);
                System.out.println("red " + red + " gr " + green + " blue " + blue );
               // System.out.println("picked color" + pickedColor);
            }
        });

    }

    @Override
    public void onColorChanged(int color) {
       /* int green = Color.green(color);
        int red = Color.red(color);
        int blue = Color.blue(color);

        StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append(red + "," + green + "," + blue );
        String s = sb.toString();

        System.out.println("picked color " + s);


        (new Thread (new workerThread(s))).start();
        System.out.println("has sent thread");
*/
      /*  int green = Color.green(color);
        int red = Color.red(color);
        int blue = Color.blue(color);


        int pickedColor = Color.rgb(red, green, blue);*/
       //System.out.println("picked color" + pickedColor);

        //thread.send (red x, green y, blue z)
    }

    void initalizeWidgets(){
        spotifyIcon = findViewById(R.id.spotify_icon);
    }

    void initVolControl() {

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        soundSeekBar.setMax(audioManager
                .getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        soundSeekBar.setProgress(audioManager
                .getStreamVolume(AudioManager.STREAM_MUSIC));

    }

    void setSoundSeekBar() {
        soundSeekBar = findViewById(R.id.volumeSeekBar);
    }

    void trackSoundBarProgress() {
        soundSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
                volume = progress;
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //code to come
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //Toast.makeText(getApplicationContext(), "Volume: " + Integer.toString(volume), Toast.LENGTH_SHORT).show();
            }
        });
    }


    /**
     * The following section contains code which will be implemented later on in relation to
     * the integration of Spotify
     */

    //for testing bluetooth connection purposes
    // should have SPOTIFY intent here
    void setMediaPlayer() {
        oceanMediaPlayer = MediaPlayer.create(this, R.raw.forest_sounds);
        spotifyIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!oceanMediaPlayer.isPlaying()) {
                    oceanMediaPlayer.start();

                } else {
                    oceanMediaPlayer.pause();

                }
            }
        });

    }
    @Override
    protected void onPause() {
        super.onPause();
        if (oceanMediaPlayer != null) {
            oceanMediaPlayer.pause();
            if (isFinishing()) {
                oceanMediaPlayer.stop();
                oceanMediaPlayer.release();
            }
        }
    }


}
