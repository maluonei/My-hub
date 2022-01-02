import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {
    public static final int width=12;
    public static final int height=12;
    public int[][] board;
    public static Board checkerboard = new Board();
    Random random = new Random();

    public Board(){
        board = new int[width][];
        for(int i=0;i<width;i++){
            board[i]=new int[height];
        }
    }

    public int emptyPosNum(){
        int emptyNum=0;
        for(int i = 0;i<width;i++){
            for(int j=0;j<height;j++){
                if(board[i][j]==0) emptyNum++;
            }
        }
        return emptyNum;
    }

    public Board createNewBoard(int pos, int nextPlayer){
        Board newBoard = new Board();
        for(int i=0;i<width;i++){
            for(int j=0;j<height;j++){
                newBoard.board[i][j]=board[i][j];
            }
        }

        int x = pos/width;
        int y = pos%height;
        newBoard.board[x][y]=nextPlayer;

        return newBoard;
    }

    public Board randomRunNext(int player){
        Board newBoard = new Board();
        List<Integer> possiblePos = new ArrayList<>();
        for(int i=0;i<width;i++){
            for(int j=0;j<height;j++){
                newBoard.board[i][j]=board[i][j];
                if(board[i][j]==0){
                    possiblePos.add(i*width+j);
                }
            }
        }

        int choice = possiblePos.get(random.nextInt(possiblePos.size()));

        int x = choice/width;
        int y = choice%height;
        newBoard.board[x][y]=player*-1;

        return newBoard;
    }

    public int checkWinner(){
        for(int i=0;i<width;i++) {
            for (int j = 0; j < height; j++) {
                if (board[i][j] != 0 && j+4<height && board[i][j] == board[i][j+1] && board[i][j] == board[i][j+2] && board[i][j] == board[i][j+3] && board[i][j] == board[i][j+4]) return board[i][j];
                if (board[i][j] != 0 && i+4<height && board[i][j] == board[i+1][j] && board[i][j] == board[i+2][j] && board[i][j] == board[i+3][j] && board[i+4][j] == board[i][j]) return board[i][j];
                if (board[i][j] != 0 && j+4<height && i+4<width && board[i][j] == board[i+1][j+1] && board[i][j] == board[i+2][j+2] && board[i][j] == board[i+3][j+3] && board[i][j] == board[i+4][j+4]) return board[i][j];
                if (board[i][j] != 0 && i-4>=0 && j+4<height && board[i][j] == board[i-1][j+1] && board[i][j] == board[i-2][j+2] && board[i][j] == board[i-3][j+3] && board[i-4][j+4] == board[i][j]) return board[i][j];
            }
        }
        return 0;
    }

    public boolean checkValidity(int x, int y){
        if(x<0 || x>=width || y<0 || y>=height || board[x][y]!=0){
            return false;
        }
        return true;
    }

    public void showBoard(){
        System.out.print("   ");
        for(int i=0;i<width;i++){
            System.out.print(i + "     ");
        }
        System.out.println();
        for(int i=0;i<width;i++) {
            System.out.print(" "+i+" ");
            for (int j = 0; j < height; j++) {
                System.out.print(board[i][j] + "     ");
            }
            System.out.println();
        }
    }
}
