import java.time.LocalDateTime;
import java.util.*;

public class Node {
    private static Random random;
    public Board currentBoard;
    private float C;
    private float D;
    private int player; //-1表示玩家，1表示电脑
    private int VistedNumber;
    private int winNumber;
    public int changePos;//相对于上一步新下子的位置
    private Node parent;
    private List<Node> childrenNodes = new ArrayList<Node>();
    private int depth;

    public Node(Board board, float C, float D, int player, int changePos, int depth, Node parent){
        if(random==null) random = new Random();
        this.C = C;
        this.D = D;
        this.currentBoard=board;
        this.player=player;
        this.depth=depth;
        VistedNumber=0;
        winNumber=0;
        this.changePos=changePos;
        this.parent=parent;
        childrenNodes = new ArrayList();
    }

    //寻找最优策略。
    public Node findBestChildNode(){
        if (isleaf()) return this;

        int possibleChildNum = currentBoard.emptyPosNum();
        if(childrenNodes.size()<possibleChildNum){
            Node nextNode=findNewPossiblePos();
            childrenNodes.add(nextNode);
            return nextNode;
        }

        float score = 0;
        float maxscore = 0;
        int randomChoice = random.nextInt(childrenNodes.size());
        Node bestNextNode=childrenNodes.get(randomChoice);

        for (Node node: childrenNodes) {
            float temp = (float)(C*Math.sqrt(((float)Math.log(VistedNumber)/(float)node.VistedNumber)));
            score = (node.winNumber/(float)VistedNumber) + temp - D * (float)Math.sqrt((node.VistedNumber- node.winNumber)/(float)VistedNumber);
            if(score>maxscore){
                maxscore=score;
                bestNextNode=node;
            }
        }

        return bestNextNode.findBestChildNode();
    }

    //还有没有走过的策略，那么寻找可以走的策略。
    public Node findNewPossiblePos(){
        List<Integer> poslist = new ArrayList();
        for(int i=0;i<Board.width;i++){
            for(int j=0;j<Board.height;j++){
                if(currentBoard.board[i][j]==0){
                    poslist.add(i*Board.width+j);
                }
            }
        }

        Iterator iterator = childrenNodes.iterator();
        while(iterator.hasNext()){
            Integer pos = ((Node)iterator.next()).changePos;
            poslist.remove(pos);
        }

        if(poslist.size()!=0) {
            int nextPos = poslist.get(0);
            Board newBoard = currentBoard.createNewBoard(nextPos, player);
            return new Node(newBoard, C, D,player, nextPos, this.depth+1, this);
        }
        else{
            return null;
        }
    }

    public boolean isleaf(){
        return childrenNodes.size()==0 && findNewPossiblePos()==null;
    }

    //快速走子
    public void stimulate(Node nextNode){
        Board board = nextNode.currentBoard;
        int p = nextNode.player;
        while(board.checkWinner()==0 && board.emptyPosNum()>=10){//未决出胜负，继续
            board=board.randomRunNext(p);
            p*=-1;
        }
        int winner = board.checkWinner();
        Node winningNode = nextNode;
        while(winningNode!=null){
            winningNode.VistedNumber++;
            if(winner==this.player) winningNode.winNumber++;
            winningNode = winningNode.parent;
        }
    }

    //模拟完成，寻找胜率最高的下一步下发。
    public void run_next(){
        float max_winning_rate=0;
        Node choiceNode=null;
        for(Node nextNode : childrenNodes){
            int x = nextNode.changePos/Board.width;
            int y = nextNode.changePos%Board.height;
            System.out.println("下子位置:("+x+','+y+"),   总摸拟次数为:"+nextNode.VistedNumber+",    胜率为"+(float)nextNode.winNumber/(float)nextNode.VistedNumber);
            if((float)nextNode.winNumber/(float)nextNode.VistedNumber>=max_winning_rate){
                max_winning_rate = (float)nextNode.winNumber/(float)nextNode.VistedNumber;
                choiceNode = nextNode;
            }
        }
        currentBoard =  choiceNode.currentBoard;
        Main.boardFrame.paintBoard();
        Main.boardFrame.setPlayerRun(true);
        currentBoard.showBoard();
    }

    //模拟以及下子
    public void run(float maxTime, int maxnum){
        LocalDateTime t = LocalDateTime.now();
        float time = t.getHour()*3600+t.getMinute()*60+t.getSecond();
        int simunum=0;
        while(simunum<maxnum && LocalDateTime.now().getHour()*3600+LocalDateTime.now().getMinute()*60+LocalDateTime.now().getSecond()-time<maxTime){ ;
            if(simunum%10000==0) System.out.println("模拟次数："+simunum+"    时间："+(LocalDateTime.now().getHour()*3600+LocalDateTime.now().getMinute()*60+LocalDateTime.now().getSecond()-time));
            simunum++;
            Node bestChoice = findBestChildNode();
            if(bestChoice!=null) stimulate(bestChoice);
            else stimulate(this);
        }
        run_next();
    }

    //调试程序时用
    public void check(Board board){
        board.showBoard();
        Scanner sc = new Scanner(System.in);
        int a = sc.nextInt();
    }
}
