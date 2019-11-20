package com.bruno.gamenaves;

import com.badlogic.gdx.Gdx;


public class Cons {
    public static int screenx = Gdx.graphics.getWidth();
    public static int screeny = Gdx.graphics.getHeight();

    public static int tileWidth = screenx/4;

    public static int tileHeight = screeny/4;

    public static float velIni = 1f*tileHeight/1f;
    public static float velAtual = 0;

    public static float canovely=-0.1f*screeny;

    public static int btnsize=(int)(0.1f*screenx);
  //  public static int btnsize1=(int)(0.3f*screenx);
    public static int btnx=(int)(0.3f*screenx);
    public static int btnx3=(int)(0.2f*screenx);
    public static int btnx1=(int)(0.3f*screenx);
    public static int btnxstar=screenx/2/2+btnsize;
    public static int btny=(screeny/2);
    public static int btny1=(screeny/2/2);


}
