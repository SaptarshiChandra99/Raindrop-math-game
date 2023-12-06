package RainDropMaths;

import javax.swing.*;

public class GameFrame extends JFrame {
    public GameFrame(){
        this.add(new GameComponents());
      //this.add(new Menu());
        this.setTitle("Rain Drop Math");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
