import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class BoardFrame extends JFrame {
     private JPanel panel = new JPanel();
     private Map<Integer, BoardComponent> boardComponentMap = new HashMap<>();
     private MouseHandler mouseHandler = new MouseHandler();
     private boolean playerRun = true;
     public int currentPos;

     public BoardFrame(int weight, int height){
         add(panel);
         GridBagLayout layout = new GridBagLayout();
         panel.setLayout(layout);
         addMouseListener(mouseHandler);

         for(int i=0;i<weight;i++){
             for(int j=0;j<height;j++){
                 BoardComponent comp = new BoardComponent();
                 GridBagConstraints constraints = new GridBagConstraints();
                 constraints.weightx=60;
                 constraints.weighty=60;
                 constraints.gridx=i;
                 constraints.gridy=j;
                 JPanel p = new JPanel();
                 p.setBorder(new LineBorder(Color.BLACK));
                 panel.add(p, constraints);
                 p.add(comp);
                 boardComponentMap.put(weight*i+j,comp);
             }
         }
         pack();
     }

     public void setPlayerRun(boolean b){
         playerRun = b;
     }
/*
     public void setPiece(Point pos, int player){
         int index = pos.x*Board.width+pos.y;
         BoardComponent boardComponent = (BoardComponent)boardComponentMap.get(index);
         boardComponent.setState(player);
         playerRun=true;
     }
*/
     private class MouseHandler extends MouseAdapter{
         @Override
         public void mouseClicked(MouseEvent e) {
             Point pos = e.getPoint();
             Point piecePos = getPoint(pos);
             int index = piecePos.x*Board.width+piecePos.y;
             if(Board.checkerboard.board[piecePos.x][piecePos.y]==0 && playerRun) {
                 (boardComponentMap.get(index)).setState(-1);
                 currentPos = index;
                 playerRun = false;
             }
         }
     }

     private Point getPoint(Point mousePos){
         float xindex = (mousePos.x)/(float)this.getWidth();
         float yindex = (mousePos.y-27)/(float)this.getHeight();
         int x = (int)(Board.width*xindex);
         int y = (int)(Board.height*yindex);
         return new Point(x, y);
     }

     public boolean getPlayerRun(){
         return playerRun;
     }

     public void paintBoard(){
         for(int i=0;i<Board.width;i++){
             for(int j=0;j<Board.height;j++){
                 if(Board.checkerboard.board[i][j]==1) boardComponentMap.get(i*Board.width+j).setState(1);
                 else if(Board.checkerboard.board[i][j]==-1) boardComponentMap.get(i*Board.width+j).setState(-1);
                 else boardComponentMap.get(i*Board.width+j).setState(0);
             }
         }
     }

}

/**
 * piece为-1表示白子，为1表示黑子。
 */
class BoardComponent extends JComponent{
    public static final int DEFAULT_WIDTH = 25;
    public static final int DEFAULT_HEIGHT = 25;
    private int state = 0;

    public BoardComponent(){
    }

    public void setState(int state){
        this.state=state;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        switch (state) {
            case 1:g2d.setColor(Color.BLACK);break;
            case -1:g2d.setColor(Color.WHITE);break;
            case 0:g2d.setColor(Color.PINK);break;
        }
        g2d.fillOval(DEFAULT_WIDTH/2,DEFAULT_HEIGHT/2,DEFAULT_WIDTH,DEFAULT_HEIGHT);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(DEFAULT_WIDTH*2, DEFAULT_HEIGHT*2);
    }
}