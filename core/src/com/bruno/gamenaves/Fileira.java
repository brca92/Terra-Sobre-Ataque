package com.bruno.gamenaves;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

import static com.bruno.gamenaves.Cons.*;

public class Fileira {

    public float y;

    public int correta;

    private int pos;

    private boolean ok;

    private boolean dest;

    private float anim;

    Texture txtnave;

    Texture txtbomba1;

    Texture txtbomba2;

    Texture txtbomba3;

    Texture txtbomba4;

    Texture txtbomba5;




    public Fileira(float y, int correta){
        this.y = y;
        this.correta = correta;
        ok = false;
        dest = false;
        anim = 0;
        txtnave=new Texture("Nave.png");
        txtbomba1=new Texture("f1.png");
        txtbomba2=new Texture("f2.png");
        txtbomba3=new Texture("f3.png");
        txtbomba4=new Texture("f4.png");
        txtbomba5=new Texture("f5.png");


    }
    public Fileira(){

    }

    public void draw(SpriteBatch batch) {



        batch.draw(txtnave, correta * tileWidth, y, tileWidth, tileHeight);

        if (dest) {
            if (ok) {

                for (int i = 0; i <= 5; i++) {

                    int Imagens = new Random().nextInt(600);
                    if (Imagens <= 100) {
                        batch.draw(txtbomba1, correta * tileWidth - 10, y - 20, tileWidth, tileHeight);
                    }
                    if (Imagens > 100 && Imagens <= 200) {
                        batch.draw(txtbomba2, correta * tileWidth, y, tileWidth, tileHeight);
                    }

                    if (Imagens > 200 && Imagens <= 300) {
                        batch.draw(txtbomba3, correta * tileWidth, y, tileWidth, tileHeight);
                    }
                    if (Imagens > 300 && Imagens <= 400) {
                        batch.draw(txtbomba4, correta * tileWidth, y, tileWidth, tileHeight);
                    }
                    if (Imagens > 400 && Imagens <= 500) {
                        batch.draw(txtbomba5, correta * tileWidth, y, tileWidth, tileHeight);
                    }
                }


            }

        }
    }

    public boolean anim(float time){
        if(dest && anim < 1){
            anim += 5*time;
            if(anim >= 1){
                anim = 1;
            }
        }
        return true;
    }

    public int update(float time){
        y -= time*velAtual;
        if(y < 0 - tileHeight){
            if(ok){
                return 1;
            } else {
                erro();
                return 2;
            }
        }
        return 0;
    }

    public int toque(int tx, int ty){

        if(ty >= y && ty <= y + tileHeight){
            pos = tx/tileWidth;


            if(pos == correta){


                ok = true;
                dest = true;
                return 1;
            }
            else {

            }
        }
        return 0;
    }


    public void erro(){
        dest = true;
        pos = correta;
    }

    public void dispose(){
        txtnave.dispose();
        txtbomba1.dispose();
        txtbomba2.dispose();
        txtbomba3.dispose();
        txtbomba4.dispose();
        txtbomba5.dispose();
    }

}
