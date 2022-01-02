import java.util.Scanner;

public class Game {
    public BoardFrame boardFrame;
    public float C;
    public float D;
    public float max_Time;
    public int maxnum;
    public int currenPos;
    public static Scanner sc = new Scanner(System.in);
    public Game(BoardFrame boardFrame, float C, float D, float max_Time, int maxnum){
        this.boardFrame=boardFrame;
        this.C=C;
        this.D=D;
        this.max_Time=max_Time;
        this.maxnum=maxnum;
    }

    public void start(){
        while(Board.checkerboard.checkWinner()==0){
            playerRun();
            if(Board.checkerboard.checkWinner()==-1){
                System.out.println("游戏结束，你获胜了");
                break;
            }
            Board.checkerboard.showBoard();
            Node node = new Node(Board.checkerboard, C, D,1, currenPos, 1, null);
            node.run(max_Time, maxnum);
            Board.checkerboard = node.currentBoard;
            boardFrame.paintBoard();
        }
        if(Board.checkerboard.checkWinner()==1){
            System.out.println("游戏结束，你失败了");
        }
    }

    public void playerRun(){
//        board.showBoard();
//        System.out.println("轮到你下子了，请选择下子位置:");
//        int x = sc.nextInt();
//        int y = sc.nextInt();
            /*
            while(!board.checkValidity(x, y)){
                System.out.println("不合法的输入，请重新输入。");
                System.out.println("轮到你下子了，请选择下子位置:");
                x = sc.nextInt();
                y = sc.nextInt();
            }
            System.out.println("您选择的落子位置为:("+ x +","+ y +")");
            board.board[x][y]=-1;
            board.showBoard();
            currenPos = x*Board.width+y;
        }
        */
        while(boardFrame.getPlayerRun()){
            try{
                Thread.sleep(1);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        currenPos = boardFrame.currentPos;
        int x = currenPos/Board.width;
        int y = currenPos%Board.height;
        Board.checkerboard.board[x][y] = -1;
    }
}
