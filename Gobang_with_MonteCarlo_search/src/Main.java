import javax.swing.*;
import java.awt.*;

public class Main {

    public static BoardFrame boardFrame = new BoardFrame(Board.width,Board.height);

    public static void main(String[] args) {
        EventQueue.invokeLater(()->{
            boardFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            boardFrame.setVisible(true);
        });

        Game game = new Game(boardFrame,(float)0.2, 20,30, 500000);
        boardFrame.paintBoard();
        game.start();
    }
}
