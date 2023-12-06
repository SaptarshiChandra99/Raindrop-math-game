package RainDropMaths;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class GameComponents extends JPanel implements ActionListener {

    //states is play is when u can actually play the game
    //menu is main menu is active
    //rules is rules page is active
    //gameover is game over
    //pause is when game is paused in game
    //gamemode chose between easy,normal,hard
    //options is when option menu is open

    static final int S_HEIGHT=600,S_WIDTH=600;
    static final int DELAY=30;  //milisecs
    static int VEL_Y=2;
   // static final int NO_OF_PROBLEMS=10;

    int noOfDropsPerWave=10;
    int totalNoOfDrops=noOfDropsPerWave;
    int noOfObjects =noOfDropsPerWave;
    boolean ifnegative=false;
    boolean isFirstwave=true;
   // boolean running=false;
    public int noOfpops=0;
    int noPressed=0;
    Timer timer;
    String state="menu";
    Random r=new Random();
    ImageDirectory imageDirectoryobj=new ImageDirectory();
    DataBase db=new DataBase();
    int[] cloudPos={-370,500};
    int[] waveposlist={0,-598};
    int sunY=-70; //Game over screen sun
    int[] menuCloud={-600,-1800};

    ArrayList<Circle> objList=new ArrayList<>(noOfObjects);
    Rectangle playButton=new Rectangle((S_WIDTH-150)/2,150,150,70);
    Rectangle helpButton=new Rectangle((S_WIDTH-150)/2,250,150,70);
    Rectangle exitButton=new Rectangle((S_WIDTH-150)/2,350,150,70);
    Rectangle backButton=new Rectangle(0,0,50,20);


    public void setState(String state){ this.state=state;    }


    public GameComponents(){
        this.addMouseListener(new MyMouseAdapter());
        this.addKeyListener( new MyKeyAdapter());
        this.setPreferredSize(new Dimension(S_WIDTH,S_HEIGHT));
        this.setFocusable(true);
        this.startGame();
        initializeArr();
    }

    public void startGame(){
        timer=new Timer(DELAY, this);
        timer.start();
    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void initializeArr(){
        String operations="+-*/";
        int y=-40,x=100;
        if(!isFirstwave){
            y=30;
        }
        for(int i = 0; i< noOfDropsPerWave; i++){
            if(x>=550)  x=r.nextInt(150,550);
            else  if(x<=50)  x=r.nextInt(110,500);
            int minDistPxY=r.nextInt(-70,-60);
            int minDistPxX=r.nextInt(100,250);
            String ops=Character.toString(operations.charAt(r.nextInt(0,3)));
            int a=r.nextInt(10),b=r.nextInt(10);
            int ans=switch (ops){
                case "+" -> a+b;
                case "-"->   a-b;
                case "*"->  a*b;
                case "/"-> a/b;
                default -> 0;
            };
            System.out.println(ans+" ="+a+ops+b);
           objList.add(new Circle(i,x,y,ans,a+ops+b));
           y+=minDistPxY;
           ops=Character.toString(operations.charAt(r.nextInt(0,2)));
            //System.out.println(ops);
           x=switch (ops){
               case "+" ->x+minDistPxX;
               case "-" ->x-minDistPxX;
               default -> 0;
           };
        }
    }

   public void draw(Graphics g){

       if(state.equals("gameover")){
           gameOver(g);
           return;
       }
       if(state.equals("menu")){
          drawMenu(g);
           return;
       }
       if(state.equals("options")){
           options(g);
           return;
       }
       if(state.equals("pause")){
           pause(g);
           return;
       }
       if(state.equals("gamemode")){
           gameMode(g);
           return;
       }
       if(state.equals("rules")){
           rules(g);
           return;
       }
       this.setBackground(new Color(0x0676A9));
       for(Circle c: objList){
           drawCircle(g,c.getX(),c.getY(),c.getId(),c);
       }
       clouds(g);
       water(g);
       drawBackButton(g);

   }
    public void drawBackButton(Graphics g){
       g.drawImage(imageDirectoryobj.getBackButtonImage(),0,0,null );
    }

    public void move(){
       // for(int i=0;i<noOfObjects;i++){   circleY.set(i,circleY.get(i)+VEL_Y);}
        for(Circle c:objList)   {            c.setY(c.getY()+VEL_Y);        }
    }

    public void gameMode(Graphics g){

        setBackground(new Color(0x992525BD, true));
        Graphics2D g2=(Graphics2D) g;
        Font textFont=new Font("arial",Font.BOLD,27);
        g.setFont(textFont);
        g.setColor(Color.WHITE);
        g.drawString("Easy", playButton.x+40, playButton.y+45);
        g2.draw(playButton);
        g.drawString("Normal", helpButton.x+25, helpButton.y+45);
        g2.draw(helpButton);
        g.drawString("Hard", exitButton.x+40, exitButton.y+45);
        g2.draw(exitButton);

        drawBackButton(g);

    }

    public void pause(Graphics g){

        setBackground(new Color(0x992525BD, true));
        Graphics2D g2=(Graphics2D) g;
        Font textFont=new Font("arial",Font.BOLD,27);
        g.setFont(textFont);
        g.setColor(Color.WHITE);
        g.drawString("Continue", playButton.x+15, playButton.y+45);
        g2.draw(playButton);
        g.drawString("Restart", helpButton.x+25, helpButton.y+45);
        g2.draw(helpButton);
        g.drawString("Main Menu", exitButton.x+7, exitButton.y+45);
        g2.draw(exitButton);

    }


    public void drawMenu(Graphics g){

        g.drawImage(imageDirectoryobj.getMainMenuSkyImage(),0,0,null);
        Graphics2D g2d= (Graphics2D) g;

        if(menuCloud[0]<=600 )  {
            if(menuCloud[0]==600)   menuCloud[0]=-1800;
            g.drawImage(imageDirectoryobj.getMainMenuCloudsImage(),menuCloud[0]++,0,null);
        }

        if(menuCloud[1]<=600){
            if(menuCloud[1]==600)   menuCloud[1]=-1800;
            g.drawImage(imageDirectoryobj.getMainMenuCloudsImage(),menuCloud[1]++,0,null);
        }

        Font textFont=new Font("arial",Font.BOLD,40);
        g.setFont(textFont);
        g.setColor(new Color(0xE5000000, true));
        g.drawString("Play", playButton.x+35, playButton.y+50);
        g2d.draw(playButton);
        g.drawString("Option", helpButton.x+12, helpButton.y+50);
        g2d.draw(helpButton);
        g.drawString("Exit", exitButton.x+35, exitButton.y+50);
        g2d.draw(exitButton);
    }

    public void water(Graphics g){

        int posY=475;
        if(waveposlist[0]<=598){
            g.drawImage(imageDirectoryobj.getGameSeaWavesImage(),waveposlist[0]=waveposlist[0]+2,posY,null);
            if(waveposlist[0]==598)            waveposlist[0]=-598;
        }

        if(waveposlist[1]<=598){
            g.drawImage(imageDirectoryobj.getGameSeaWavesImage(),waveposlist[1]=waveposlist[1]+2,posY,null);
            if(waveposlist[1]==598 ) waveposlist[1]=-598;
        }
    }

    public void clouds(Graphics g){

        int cloudposY=-80,cloudVel=10;
        if(cloudPos[0]<=-70 ){   cloudPos[0]+=cloudVel;        }
        if(cloudPos[1]!=200)        cloudPos[1]-=cloudVel;
        g.drawImage(imageDirectoryobj.getGameRainyCloudImage(),cloudPos[1],cloudposY,null);
        g.drawImage(imageDirectoryobj.getGameRainyCloudImage(),cloudPos[0], cloudposY,null);
    }

   public void drawCircle(Graphics g,int x,int y,int objectId,Circle c){
       g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN,  18));
       FontMetrics fm = g.getFontMetrics();
       double textWidth = fm.getStringBounds(c.getProblem(), g).getWidth();
       int ovalWidth = (int)textWidth+30, ovalHeight = (int)textWidth+30;

      // g.setColor(Color.WHITE);       //snow effect
     //  g.fillOval(x - (ovalWidth / 2)-2, y - (ovalHeight / 2)-3,ovalWidth+5, ovalHeight+5);

       g.setColor(new Color(0,0,0, 48));
       g.fillOval(x - ovalWidth / 2, y - ovalHeight / 2,ovalWidth, ovalHeight);

       g.setColor(new Color(4, 39, 145));
       g.drawString(c.getProblem(), (int) (x - textWidth/2), (y + fm.getMaxAscent() / 2));
   }

    public void gameOver(Graphics g){

        int sunVelY=2,cloudvelx=20;
        g.drawImage(imageDirectoryobj.getGameOverBeachBGImage(),0,0,null);

        if(sunY<0) sunY+=sunVelY;
        g.drawImage(imageDirectoryobj.getGameOverSunImage(),0,sunY,null);

        g.setColor(Color.red);
        g.setFont( new Font("Ink Free",Font.BOLD, 40));
        String currentscore="Score: "+noOfpops;
        FontMetrics fm1=getFontMetrics(g.getFont());
        g.drawString(currentscore, (S_WIDTH-fm1.stringWidth(currentscore)) /2, (S_HEIGHT/2)-20);
        int maxscore=0;
        try {
            maxscore= db.getMaxScore();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String maxScore="Max Score: "+maxscore;
        g.drawString(maxScore, (S_WIDTH- fm1.stringWidth(maxScore)) /2, (S_HEIGHT/2)-70);

        if(cloudPos[0]>=-500 && cloudPos[1]<=850){
            cloudPos[0]-=cloudvelx;
            cloudPos[1]+=cloudvelx;
            g.drawImage(imageDirectoryobj.getGameRainyCloudImage(),cloudPos[1], -80,null);
            g.drawImage(imageDirectoryobj.getGameRainyCloudImage(),cloudPos[0],-80,null);
        }
        drawBackButton(g);

    }

    public void options(Graphics g){

        g.drawImage(imageDirectoryobj.getRulesBeachwithSunBGImage(),0,0,null);
        Graphics2D g2d=(Graphics2D)g;
        g.setColor(Color.red);
        g.setFont( new Font("arial",Font.BOLD, 50));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("-: Options :-",(S_WIDTH-metrics2.stringWidth("-: Options :-"))/2, 70);

        g.setFont( new Font("arial",Font.BOLD, 40));
        g.setColor(Color.BLACK);
        g.drawString("Rules", playButton.x+25, playButton.y+50);
        g2d.draw(playButton);
        g.drawString("Mode", helpButton.x+25, helpButton.y+50);
        g2d.draw(helpButton);
        drawBackButton(g);

    }
    public void rules(Graphics g){

        g.drawImage(imageDirectoryobj.getRulesBeachwithSunBGImage(),0,0,null);
        g.setColor(Color.red);
        g.setFont( new Font("arial",Font.BOLD, 50));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("-: RULES :-",(S_WIDTH-metrics2.stringWidth("-: RULES :-"))/2, 70);

        drawBackButton(g);

    }

   public void checkPositionOfObjs(){
        int i=-1;
        var removalList=new ArrayList<Integer>();
        for(Circle c:objList){
            if(c.getY()>S_HEIGHT-120){
                removalList.add(++i);
            }
        }
       for(i=0;i<removalList.size();i++){

               objList.remove(i);
                noOfObjects--;

       }
       if(noOfObjects ==0){
            newWave();
          // running=false;
       }
      // System.out.println(noOfObjOutOfBounds+" "+noOfObjects);
   }

    public void checkAnswers(int input){
        int i=-1;
        for(Circle c:objList){
            ++i;
            if(c.getAns()==input){
                objList.remove(i);
                noOfObjects--;
                noOfpops++;
                break;
            }

        }
        noPressed=0;
        if(noOfObjects ==0){
            newWave();
            // running=false;
        }

    }

    public void newWave(){
        if(noOfpops<totalNoOfDrops-(totalNoOfDrops/3)){
            state="gameover";
            try{        db.insertData(noOfpops);  }
            catch (Exception e){    System.out.println("problem occured");            }
            return;
        }
        isFirstwave=false;
        initializeArr();
        totalNoOfDrops+=noOfDropsPerWave;
        noOfObjects=noOfDropsPerWave;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(state=="play" ){
            move();
            checkPositionOfObjs();
        }
        repaint();
    }

    public void input(int input){
        System.out.println(input+" input");
       noPressed=(noPressed*10)+input;
        System.out.println(noPressed);

    }

    public void newGame(){
        objList.clear();
        noOfObjects=noOfDropsPerWave;
        noOfpops=0;
        totalNoOfDrops=noOfDropsPerWave;
        isFirstwave=true;
        initializeArr();
        sunY=-70;
        cloudPos[0]=-370;
        cloudPos[1]=500;
    }

    public class MyKeyAdapter extends KeyAdapter {

        @Override
        public void keyPressed(java.awt.event.KeyEvent e) {
            if(e.getKeyCode()==KeyEvent.VK_P)   state="menu";
            if( e.getKeyCode()==109)    ifnegative=true;
            int n=e.getKeyCode()-(2*48);
            if(n>=0 && n<=9)
                input(n);

            if(e.getKeyCode() == KeyEvent.VK_ENTER) {
               // System.out.println("in");
                if(ifnegative)  {
                    checkAnswers(noPressed * -1);
                    ifnegative=false;
                }
                else  checkAnswers(noPressed);
            }

        }
    }

    class MyMouseAdapter extends MouseAdapter{
        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);
            int mx=e.getX();
            int my=e.getY();

            if(mx>=backButton.x && mx<backButton.x+20){
                if(my>=backButton.y && my<=backButton.y+20){
                    if( state=="play") setState("pause");
                    else if(state.equals("gameover")) setState("menu");
                    else if(state.equals("options")) setState("menu");
                    else if(state.equals("gamemode")) setState("options");
                    else if(state.equals("rules"))  setState("options");

                }
            }
            if(mx>= playButton.x && mx<= playButton.x+150){
                if(my>= playButton.y && my<=playButton.y+70){
                    //play button is pressed call the game

//                   if(state.equals("menu")){
//                      newGame();
//                    }
                   if(state.equals("menu") || state.equals("pause")) setState("play");
                   else if(state.equals("options")) setState("rules");
                   else if(state.equals("gamemode"))    VEL_Y=1;
                    if(noOfObjects==0)  newGame();
                }
            }
            if(mx>= helpButton.x && mx<= helpButton.x+150){
                if(my>= helpButton.y && my<=helpButton.y+70){
                    //rules page
                    if(state.equals("pause"))    {
                        setState("play");
                        newGame();
                        return;
                    }
                    else if(state.equals("menu"))   setState("options");
                    else if(state.equals("options")) state="gamemode";
                    else if(state.equals("gamemode"))   VEL_Y=2;
                }
            }
            if(mx>= exitButton.x && mx<= exitButton.x+150){
                if(my>= exitButton.y && my<=exitButton.y+70){
                    //exit the game
                    if(state.equals("pause")){
                        setState("menu");
                        noOfObjects=0;
                        return;
                    }
                    else if(state.equals("menu"))   System.exit(1);
                    else if(state.equals("gamemode"))   VEL_Y=3;
                }
            }
        }
    }

}
