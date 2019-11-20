package com.bruno.gamenaves;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

    public class Som {

        Music music;
        Sound sound;
        Sound soundbtn;
       Music vitoria;

    public Som(){
        sound=Gdx.audio.newSound(Gdx.files.internal("sons/Bomba.mp3"));
        soundbtn=Gdx.audio.newSound(Gdx.files.internal("sons/CLICK.wav"));
        music=Gdx.audio.newMusic(Gdx.files.internal("sons/Musica_guerra.mp3"));
      vitoria=Gdx.audio.newMusic(Gdx.files.internal("sons/Aplauso.mp3"));
    }

    public void tocarmusica(){
        music.play();
        music.setLooping(true);
        music.setVolume(0.75f);
    }

    public void tocarson(){
        sound.play();
        sound.play(0.50f);
    }

    public void tocarsonbtn(){
        soundbtn.play();
        soundbtn.play(0.50f);
    }

        public void musicaVitoria(){
           vitoria.play();
            vitoria.setVolume(0.75f);
        }

    public void dispose(){
        sound.dispose();
        music.dispose();
        soundbtn.dispose();
        vitoria.dispose();

    }
}


