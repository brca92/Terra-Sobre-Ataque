package com.bruno.gamenaves;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Array;

import java.util.Random;

import static com.bruno.gamenaves.Cons.*;


public class MainClass extends ApplicationAdapter  {



    private SpriteBatch batch;

    private Array<Fileira> fileiras;

    private float tempoTotal;

    public int pontos;

    public int stages=1;

    private Random rand;

    public int estado;

    private Texture texIniciar;

    private Texture fundo;

    private Texture pausa;

    private Texture fundo1;

    private BitmapFont fonte;

    private Texture estrela;

    public int higscore;

    Som p;

    Fileira f=new Fileira();

    private int ptestrela;

    private BitmapFont revive;
    private BitmapFont stage;

    private BitmapFont Gameover;

    private int font;

    private int imagem;

    float posy1;

    float posy2;

    static Thread T=new Thread();



    private Texture btnrestar;
    private Texture btniniciar;
    private Texture btnsair;
    private Texture btnranking;
    private Texture menuprincipal;
    boolean highoutput =false;
    boolean higranking=false;
    boolean higrestart=false;
    boolean higsair=false;
    boolean higpause=false;
    boolean higresume=false;
    private Texture trofeu;
    private Texture comemoracao;
  //  private PlayServices playServices;

 //   public MainClass(PlayServices playServices){
 //       this.playServices=playServices;
   // }



    @Override
    public void create () {


        FreeTypeFontGenerator.setMaxTextureSize(2048);
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = (int)(0.07f*screeny);
        parameter.color = Color.YELLOW;

        FreeTypeFontGenerator.setMaxTextureSize(2048);
        FreeTypeFontGenerator generator1 = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter1 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter1.size = (int)(0.03f*screeny);
        parameter1.color = Color.YELLOW;

        stage=generator1.generateFont(parameter1);
        fonte = generator.generateFont(parameter);
        revive = generator.generateFont(parameter);
        Gameover= generator.generateFont(parameter);
        generator.dispose();

        batch = new SpriteBatch();

        fundo = new Texture("Fundo.jpg");

        pausa=new Texture("pausa.png");

        fundo1 = new Texture("Fundo1.jpg");

        fileiras = new Array<Fileira>();

        rand = new Random();

        texIniciar = new Texture("Ini.PNG");

        p=new Som();

        estrela= new Texture("star.png");

        ptestrela=2;

        font=0;

        imagem=pontos/50;

        posy1=0;

        posy2=screeny;


        btnrestar=new Texture("restart.png");

        btniniciar=new Texture("btn_iniciar.png");

        btnsair=new Texture("btn_sair.png");

        menuprincipal=new Texture("Fundo_principal.png");

        btnranking=new Texture("Ranking.png");

        trofeu =new Texture("Trofeu.png");
        comemoracao =new Texture("Comemoracao.jpg");


   telaPrincipal();

    }

    public void higScoreSalvar(int pontos,int higscore) {

        this.pontos=pontos;
        this.higscore=higscore;

        Preferences preferences= Gdx.app.getPreferences("Preferencias");

        if (estado ==2 && this.pontos>=this.higscore) {
            preferences.putInteger("higscore",this.pontos);
            preferences.flush();
        }

        this.higscore=preferences.getInteger("higscore",this.pontos);
    }


    @Override
    public void render () {

        input();

        update(Gdx.graphics.getDeltaTime());
        higScoreSalvar(pontos,higscore);
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();


        if(estado==1) {

            batch.draw(fundo1, 0, posy1, screenx, screeny);
            batch.draw(fundo1, 0, posy2, screenx, screeny);

            updateFundo(Gdx.graphics.getDeltaTime());

        }
        if(estado==4||estado==0||estado==3||estado==2||estado==6){
            batch.draw(fundo, 0, 0, screenx, screeny);


        }
        if (estado==5){
            batch.draw(comemoracao,0,0,screenx,screeny);
        }

        for(Fileira f:fileiras){

            f.draw(batch);

        }

        if(estado == 0){

            batch.draw(texIniciar, 0,  tileHeight/4, screenx, tileHeight/2);
            fonte.draw(batch, String.valueOf("HigScore"), screenx/2/4, screeny /2);
            fonte.draw(batch, String.valueOf(higscore), screenx/2/2, screeny /2-150);

        }
        if(estado!=0&&estado!=3&&estado!=4){

            fonte.draw(batch, String.valueOf(pontos), screenx/2-50, screeny-30);

        }
        if (estado==0||estado==1){
          //  batch.draw(estrela, screenx-screenx/8, screeny-90,0.05f*screenx,0.03f*screeny );
            stage.draw(batch, String.valueOf("Stage "+stages), 50, screeny-30);
            stage.draw(batch, String.valueOf("X "+ptestrela), screenx-screenx/8, screeny-30);
            batch.draw(pausa,50,screeny-200);
        }
        if(estado==3) {

            if (ptestrela >= 1 ) {

                batch.draw(btnrestar,btnxstar,btny,0.3f*screenx,0.2f*screeny);
                revive.draw(batch, String.valueOf("Revive"), screenx/2/2, screeny - 300);
                batch.draw(btnsair,btnx1,screeny/2/2,0.4f*screenx,0.1f*screeny);

            }



        }
        if(estado==6) {



           // batch.draw(texIniciar, 0,  tileHeight/4, screenx, tileHeight/2);
            fonte.draw(batch, String.valueOf("Pause"), screenx/2/4, screeny /2);
           // fonte.draw(batch, String.valueOf("Toque na tela para continuar"), screenx/2/2, screeny /2-150);


       }
        if(estado==2){

            Gameover.draw(batch, String.valueOf("Game Over"), 40, screeny - 300);
            batch.draw(btnsair,btnx1,btny,0.4f*screenx,0.1f*screeny);
        }


        if(estado==1){
            p.tocarmusica();
        }
        else{
            p.music.stop();
        }

        if(estado==4){

            batch.draw(fundo1, 0, 0, screenx, screeny);
            Gameover.draw(batch, String.valueOf("Terra sob \n  Ataque"), screenx/2/4, screeny - 200);
            batch.draw(btniniciar,btnx1,btny,0.4f*screenx,0.1f*screeny);
            batch.draw(btnsair,btnx1,screeny/2/2,0.4f*screenx,0.1f*screeny);
          //  batch.draw(btnranking,btnx1,btny-100,300,100);

        }

        if (estado==5){
          //  stage.draw(batch, String.valueOf("Bom trabalho \nAgora a Terra está salva"), 70, tileHeight/3);
            batch.draw(trofeu,btnx1,btny,0.4f*screenx,0.2f*screeny);
            batch.draw(btnsair,btnx1,screeny/2/2,0.4f*screenx,0.1f*screeny);
            p.musicaVitoria();
        }
        else{
            p.vitoria.stop();
        }
        if(estado==1){
            if(pontos>=100&&pontos<=200) {

                stage.draw(batch, String.valueOf("+10"), screenx-120, screeny-120);
            }

            if(pontos>=300&&pontos<=400) {

                stage.draw(batch, String.valueOf("+30"), screenx-120, screeny-120);
            }
            if(pontos>=600&&pontos<=700) {

                stage.draw(batch, String.valueOf("+60"), screenx-120, screeny-120);
            }
            if(pontos>=1000&&pontos<=1100) {

                stage.draw(batch, String.valueOf("+100"), screenx-120, screeny-120);
            }
            if(pontos>=1500&&pontos<=1600) {

                stage.draw(batch, String.valueOf("+150"), screenx-120, screeny-120);
            }

            if(pontos>=2000&&pontos<=2100) {

                stage.draw(batch, String.valueOf("+200"), screenx-120, screeny-120);
            }
        }

        batch.end();
    }

    private void update(float deltaTime){

        if(estado == 1){


            tempoTotal += deltaTime;


            velAtual = velIni + tileHeight*tempoTotal/45f;

            for(int i=0;i<fileiras.size;i++){

                int retorno = fileiras.get(i).update(deltaTime);
                fileiras.get(i).anim(deltaTime);

                if(retorno != 0){

                    if(retorno == 1){

                        fileiras.removeIndex(i);
                        i--;


                        adicionar();
                    } else if(retorno == 2){
                        finalizar(1);
                    }
                }
            }
        } else if(estado == 2){

      //      playServices.SubmitScore(pontos);
            if(pontos>=5000){
         //       playServices.unlockAchievement("5000");
            }
            if(pontos>=1000){
        //        playServices.unlockAchievement("1000");
            }
            if(pontos>=600){
         //       playServices.unlockAchievement("600");
            }
            if(pontos>=300){
          //      playServices.unlockAchievement("300");
            }
            if(pontos>=100){
          //      playServices.unlockAchievement("100");
            }
            if(pontos>=1){
          //      playServices.unlockAchievement("1");
            }



            for(Fileira f:fileiras){
                f.anim(deltaTime);
            }
        }

    }

    public boolean verifpause(int x, int y){


        if(x>=50&&x<=50+0.1f*screenx&&
                screeny-200<=y&&screeny-200+0.1f*screeny>=y){
           higpause=true;

        }
        else{
            higpause=false;
        }
        return higpause  ;
    }
    public boolean verifresume(int x, int y){


        if(x>=btnxstar&&x<=btnxstar+0.4f*screenx&&
                btny<=y&&btny+0.2f*screeny>=y){
            higresume=true;

        }
        else{
            higresume=false;
        }
        return higresume  ;
    }

    public boolean verifrestart(int x, int y){


        if(x>=btnxstar&&x<=btnxstar+0.4f*screenx&&
                btny<=y&&btny+0.2f*screeny>=y){
           higrestart=true;

        }
        else{
            higrestart=false;
        }
        return higrestart  ;
    }

    public boolean verifsair(int x, int y){


        if(x>=btnx&&x<=btnx+0.4f*screenx&&
                btny1<=y&&btny1+0.1f*screeny>=y){
            higsair=true;
        }
        else{
            higsair=false;
        }
        return higsair;
    }

    public boolean verifranking(int x, int y){


        if(x>=screenx/2-150&&x<=screenx-200&&
                y>=screeny-800&&y<=screeny-700){
            higranking=true;
        }
        else{
            higranking=false;
        }
        return higranking;
    }
    public boolean verifoutput(int x, int y){


        if(x>=btnx1&&x<=btnx1+0.4f*screenx&&
                btny<=y&&btny+0.1f*screeny>=y){
            highoutput=true;
        }
        else{
            highoutput=false;
        }
        return highoutput;
    }

    private void input(){

        if(Gdx.input.justTouched()){
            int x = Gdx.input.getX();
            int y = screeny - Gdx.input.getY();

            if (estado==3){
                verifrestart(x,y);
                verifsair(x,y);
            }


            if(estado==2){
                verifoutput(x,y);
            }
            if (estado==4){
                verifoutput(x,y);
                verifsair(x,y);
               // verifranking(x,y);

            }
            if (estado==5){
                verifoutput(x,y);
                verifsair(x,y);
                // verifranking(x,y);

            }

            if(estado == 0||estado==6) {
                p.tocarsonbtn();
                estado = 1;
            }
            if(estado == 2) {
                p.tocarsonbtn();

            }
            if(estado == 1){
                p.tocarson();
                verifpause(x,y);

                if(pontos==100) {
                    ptestrela++;
                    pontos += 10;
                    stages++;
                }

                if(pontos==300){
                    ptestrela++;
                    pontos+=30;
                    stages++;
                }
                if(pontos==600){
                    ptestrela++;
                    pontos+=60;
                    stages++;
                }
                if(pontos==1000){
                    ptestrela++;
                    pontos+=100;
                    stages++;
                }
                if(pontos==1500){
                    ptestrela++;
                    pontos+=150;
                    stages++;
                }

                if(pontos==2000){
                    ptestrela++;
                    pontos+=200;
                    stages++;
                }
                if(pontos==3000){
                   telaFinal();
                }



                for(int i=0;i<fileiras.size;i++){
                    int retorno = fileiras.get(i).toque(x, y);
                    if(retorno != 0){
                        if( retorno==1){

                            pontos++;

                        }

                    }
                }
            }
        }else if(!Gdx.input.isTouched()){
            if(higrestart&&estado==3){
                estado=1;
                higrestart=false;
            }

            if(highoutput&&estado==2){
                telaPrincipal();
                highoutput=false;
            }
            if(highoutput&&estado==4){
                iniciar();
                highoutput=false;
            }
            if(higsair&&estado==4){
                System.exit(0);
                higsair=false;
            }
            if(higsair&&estado==3){
                estado=4;
                higsair=false;
            }

            if(higsair&&estado==5){
                estado=4;
                higsair=false;
            }
            if(higpause&&estado==1){
                estado=6;
                higsair=false;
            }

           // if(higranking&&estado==4){
             //   playServices.showScore();
             //   higranking=false;
           // }


        }

    }

    private void adicionar(){
        float y = fileiras.get(fileiras.size-1).y + tileHeight;
        fileiras.add(new Fileira(y, rand.nextInt(4)));
    }




    private void iniciar(){
        tempoTotal = 0;

        pontos = 0;

        fileiras.clear();
        fileiras.add(new Fileira(tileHeight, rand.nextInt(4)));
        adicionar();
        adicionar();
        adicionar();
        adicionar();

        estado = 0;

        velAtual = 0;
        ptestrela=2;


    }
    public void telaPrincipal(){
        fileiras.clear();
        estado=4;
    }
    public void telaFinal(){
        fileiras.clear();
        estado=5;
    }





    private void reset(){

        fileiras.clear();
        fileiras.add(new Fileira(tileHeight, rand.nextInt(4)));
        adicionar();
        adicionar();
        adicionar();
        adicionar();

        for(Fileira f:fileiras){
            f.y += tileHeight*2;
        }
        stages=1;

        velAtual=1;

    }

    void finalizar(int opt){
        Gdx.input.vibrate(200);
        p.tocarsonbtn();

        if(ptestrela>1){
            reset();
            estado=3;

            ptestrela--;
            if(opt == 1){
                for(Fileira f:fileiras){
                    f.y += tileHeight;
                }

            }
        }

        else{
            estado = 2;

            if(opt == 1){
                for(Fileira f:fileiras){
                    f.y += tileHeight;
                }
            }
        }


    }

    public void updateFundo(float time){
        posy1+=time * canovely;
        posy2+=time * canovely;

        if (posy1+ screeny<=0){
            posy1=screeny;
            posy2=0;
        }
        if(posy2+screeny<=0){
            posy2=screeny;
            posy1=0;
        }
    }

    @Override
    public void dispose () {
        batch.dispose();
        texIniciar.dispose();
        fundo.dispose();
        fundo1.dispose();
        estrela.dispose();
        fonte.dispose();
        Gameover.dispose();
        revive.dispose();
        p.dispose();
        f.dispose();
        btnrestar.dispose();
        btniniciar.dispose();
        btnranking.dispose();
        btnsair.dispose();
        stage.dispose();
        trofeu.dispose();
        comemoracao.dispose();

    }

}
