
import java.util.*;

/**
 * This is the AiPlayer class. It simulates a minimax player for the max connect
 * four game. The constructor essentially does nothing.
 *
 * @author james spargo
 *
 */
public class AiPlayer {

    /**
     * The constructor essentially does nothing except instantiate an AiPlayer
     * object.
     *
     */
    public AiPlayer() {
        // nothing to do here
    }

    /**
     * This method plays a piece randomly on the board
     *
     * @param currentGame The GameBoard object that is currently being used to
     * play the game.
     * @return an integer indicating which column the AiPlayer would like to
     * play in.
     */
    public int findBestPlay(GameBoard currentGame) {
        // start random play code
        Random randy = new Random();
        int playChoice = 99;

        playChoice = randy.nextInt(7);

        while (!currentGame.isValidPlay(playChoice)) {
            playChoice = randy.nextInt(7);
        }

        // end random play code
        return playChoice;
    }
    private int maxDepth;

    /**
     * The constructor essentially does nothing except instantiate an AiPlayer
     * object.
     *
     */
    public AiPlayer(int depth) {
        this.maxDepth = depth;
        // nothing to do here
    }

    /**
     * This method plays a piece randomly on the board
     *
     * @param currentGame The GameBoard object that is currently being used to
     * play the game.
     * @return an integer indicating which column the AiPlayer would like to
     * play in.
     */
    public int findBestActionPlay(GameBoard currentGame, int maxPlayerVal) {
        int[][] gameArray;
        int best = 0;
        long val = -922337203;
        int playChoice = 99;
        long gameVal;
        gameArray = currentGame.getGameBoard();
        GameBoard currentGame1 = new GameBoard(gameArray);
        if (maxDepth == 0) {
            Random randy = new Random();

            playChoice = randy.nextInt(7);

            while (!currentGame.isValidPlay(playChoice)) {
                playChoice = randy.nextInt(7);
            }

            // end random play code
            return playChoice;
        }
        Node root = new Node(currentGame1, 0, -1);
        root.sucNode = root.getSuccessors();
        for (Node successor : root.sucNode) {
            gameVal = minVal(successor, -922337203, 922337203, maxPlayerVal);
            if (val < gameVal) {
                val = gameVal;
                best = successor.choice;
            }
        }

        return best;
    }

    public long minVal(Node minCurrentGame, long alpha, long beta, int maxPlayerVal) {
        long val = 0;
        int player1 = 1;
        int player2 = 2;
        if (maxPlayerVal == 1) {
            player1 = 1;
            player2 = 2;
        } else {
            player1 = 2;
            player2 = 1;
        }
        if (minCurrentGame.state.getPieceCount() == 42 || minCurrentGame.depth == maxDepth) {
            minCurrentGame.value = (minCurrentGame.state.getScore(player1) - minCurrentGame.state.getScore(player2));
            return minCurrentGame.value;
        } else {
            val = 922337203;
            minCurrentGame.sucNode = minCurrentGame.getSuccessors();
            for (Node successor : minCurrentGame.sucNode) {
                val = Math.min(val, maxVal(successor, alpha, beta, maxPlayerVal));
                if (val < alpha) {
                    minCurrentGame.value = val;
                    return val;
                }
                beta = Math.min(beta, val);
            }
            minCurrentGame.value = val;
            return val;
        }

    }

    public long maxVal(Node maxCurrentGame, long alpha, long beta, int maxPlayerVal) {
        long val = 0;
        int player1 = 1;
        int player2 = 2;
        if (maxPlayerVal == 1) {
            player1 = 1;
            player2 = 2;
        } else {
            player1 = 2;
            player2 = 1;
        }
        if (maxCurrentGame.state.getPieceCount() == 42 || maxCurrentGame.depth == maxDepth) {
            maxCurrentGame.value = (maxCurrentGame.state.getScore(player1) - maxCurrentGame.state.getScore(player2));
            return maxCurrentGame.value;
        } else {
            val = -922337203;
            maxCurrentGame.sucNode = maxCurrentGame.getSuccessors();
            for (Node successor : maxCurrentGame.sucNode) {
                val = Math.max(val, minVal(successor, alpha, beta, maxPlayerVal));
                if (val >= beta) {
                    maxCurrentGame.value = val;
                    return val;
                }
                alpha = Math.max(alpha, val);
            }
            maxCurrentGame.value = val;
            return val;
        }

    }

    public GameBoard[] successorFunction(GameBoard currentGame1) {
        int[][] succGameArray = currentGame1.getGameBoard();
        GameBoard nextGame = new GameBoard(succGameArray);
        int playChoice = 0;
        int loopChoice = 0;
        while (loopChoice < 7) {
            if (nextGame.isValidPlay(loopChoice)) {
                playChoice = playChoice + 1;
            }
            loopChoice = loopChoice + 1;
        }
        GameBoard[] GameArray = new GameBoard[playChoice];
        loopChoice = 0;
        playChoice = 0;
        while (loopChoice < 7) {
            if (nextGame.isValidPlay(loopChoice)) {
                nextGame.playPiece(loopChoice);
                int[][] loopSuccGameArray = nextGame.getGameBoard();
                GameArray[playChoice] = new GameBoard(loopSuccGameArray);
                nextGame.removePiece(loopChoice);
                playChoice = playChoice + 1;
            }
            loopChoice = loopChoice + 1;
        }
        return GameArray;
    }

    public int successorStateNumber(GameBoard currentGame1) {
        int[][] succGameArray = currentGame1.getGameBoard();
        GameBoard nextGameCount = new GameBoard(succGameArray);
        int playChoice = 0;
        int loopChoice = 0;
        while (loopChoice < 7) {
            if (nextGameCount.isValidPlay(loopChoice)) {
                playChoice = playChoice + 1;
            }
            loopChoice = loopChoice + 1;
        }
        return playChoice;
    }
}
