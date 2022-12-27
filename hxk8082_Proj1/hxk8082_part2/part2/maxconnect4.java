
import java.io.*;
import java.util.Scanner;

/**
 *
 * @author James Spargo This class controls the game play for the Max
 * Connect-Four game. To compile the program, use the following command from the
 * maxConnectFour directory: javac *.java
 *
 * the usage to run the program is as follows: ( again, from the maxConnectFour
 * directory )
 *
 * -- for interactive mode: java MaxConnectFour interactive [ input_file ] [
 * computer-next / human-next ] [ search depth]
 *
 * -- for one move mode java maxConnectFour.MaxConnectFour one-move [ input_file
 * ] [ output_file ] [ search depth]
 *
 * description of arguments: [ input_file ] -- the path and filename of the
 * input file for the game
 *
 * [ computer-next / human-next ] -- the entity to make the next move. either
 * computer or human. can be abbreviated to either C or H. This is only used in
 * interactive mode
 *
 * [ output_file ] -- the path and filename of the output file for the game.
 * this is only used in one-move mode
 *
 * [ search depth ] -- the depth of the minimax search algorithm
 *
 *
 */
public class maxconnect4 {

    public static void main(String[] args) {
        // check for the correct number of arguments
        if (args.length != 4) {
            System.out.println("Four command-line arguments are needed:\n"
                    + "Usage: java [program name] interactive [input_file] [computer-next / human-next] [depth]\n"
                    + " or:  java [program name] one-move [input_file] [output_file] [depth]\n");

            exit_function(0);
        }

        // parse the input arguments
        String game_mode = args[0].toString();
        String input = args[1].toString();
        int depthLevel = Integer.parseInt(args[3]); 
        
        
        //create and initialize gameboard
        GameBoard currentGame = new GameBoard(input);
        
        if (game_mode.equalsIgnoreCase("interactive")) {
            String start_mode =  args[2];
            interactive(currentGame, start_mode, depthLevel);
        } else if (game_mode.equalsIgnoreCase("one-move")) {
            String output = args[2];
            one_move(currentGame, output, depthLevel);
        } else{
             System.out.println("\n" + game_mode + " is an unrecognized game mode \n try again. \n");
        }
        //************************** end computer play
    } // end of main()
    
    private static void one_move(GameBoard currentGame, String output, int depth){
        // create the Ai Player
        AiPlayer calculon = new AiPlayer(depth);
        //  variables to keep up with the game
        int playColumn = 99;				//  the players choice of column to play
        boolean playMade = false;			//  set to true once a play has been made
        
          /////////////   one-move mode ///////////
        // get the output file name
        //String output = args[2].toString(); // the output game file
        System.out.print("\nMaxConnect-4 game\n");
        System.out.print("game state before move:\n");

        //print the current game board
        currentGame.printGameBoard();
        // print the current scores
        System.out.println("Score: Player 1 = " + currentGame.getScore(1)
                + ", Player2 = " + currentGame.getScore(2) + "\n ");

        // ****************** this chunk of code makes the computer play
        if (currentGame.getPieceCount() < 42) {
            int current_player = currentGame.getCurrentTurn();
            // AI play - random play
            playColumn = calculon.findBestActionPlay(currentGame, current_player);

            // play the piece
            currentGame.playPiece(playColumn);

            // display the current game board
            System.out.println("move " + currentGame.getPieceCount()
                    + ": Player " + current_player
                    + ", column " + playColumn);
            System.out.print("game state after move:\n");
            currentGame.printGameBoard();

            // print the current scores
            System.out.println("Score: Player 1 = " + currentGame.getScore(1)
                    + ", Player2 = " + currentGame.getScore(2) + "\n ");

            currentGame.printGameBoardToFile(output);
        } else {
            System.out.println("\nI can't play.\nThe Board is Full\n\nGame Over");
        }

    }
    /**
     * Interactive mode
     * @param currentGame
     * @param first_start
     * @param depth 
     */
    private static void interactive(GameBoard currentGame, String first_start, int depth) {
        System.out.print("\nMaxConnect-4 game\n");
        AiPlayer calculon = new AiPlayer(depth);
        String computer_out = "computer.txt";
        String human_out = "human.txt";
        int start_mode = 0;
        Scanner scanner = new Scanner(System.in);
        
        if (first_start.equalsIgnoreCase("computer-next")) {
            start_mode = 0;
            //print game board
            System.out.print("game state before move:\n");
            currentGame.printGameBoard();

        } else if (first_start.equalsIgnoreCase("human-next")) {
            start_mode = 1;
            System.out.print("game state before move:\n");
            currentGame.printGameBoard();
        } else {
            System.out.println("Invalid argument !");
            exit_function(0);
        }

        while (currentGame.getPieceCount() < 42) {
            int current_player = currentGame.getCurrentTurn();
            if (start_mode == 0) {
                //computer is first
                if (current_player == 1) {
                    playComputer(currentGame, calculon, current_player, computer_out);
                } else {
                    playHuman(currentGame, scanner, current_player, human_out);
                }
            } else if (start_mode == 1) {
                //human is first
                if (current_player == 1) {
                    playHuman(currentGame, scanner, current_player, human_out);
                } else {
                    playComputer(currentGame, calculon, current_player, computer_out);
                }
            }
        }
        System.out.println("\nI can't play.\nThe Board is Full\n\nGame Over");
    }
    /**
     * Computer player
     * @param currentGame
     * @param calculon
     * @param current_player
     * @param computer_out 
     */
    private static void playComputer(GameBoard currentGame, AiPlayer calculon, int current_player, String computer_out) {
        int playColumn = 99;
        // AI play - random play
        playColumn = calculon.findBestActionPlay(currentGame, current_player);
        //playColumn = calculon.findBestPlay(currentGame);

        // play the piece
        currentGame.playPiece(playColumn);

        // display the current game board
        System.out.println("move " + currentGame.getPieceCount()
                + ": Player " + current_player
                + ", column " + playColumn);
        System.out.print("game state after move:\n");
        currentGame.printGameBoard();

        // print the current scores
        System.out.println("Score: Player 1 = " + currentGame.getScore(1)
                + ", Player2 = " + currentGame.getScore(2) + "\n ");
        //save computer movement
        currentGame.printGameBoardToFile(computer_out);
    }
    
    /**
     * Human Player
     * @param currentGame
     * @param scanner
     * @param current_player
     * @param human_out 
     */
    private static void playHuman(GameBoard currentGame, Scanner scanner, int current_player, String human_out) {
        
        int column_num = 0;
        while (true) {
            System.out.print("Human: Please enter column: ");
            String column_str = scanner.nextLine();
            if (column_str.trim().isEmpty()) {
                System.out.println("Invalid input.");
                continue;
            }
            try {
                column_num = Integer.valueOf(column_str);
            } catch (Exception e) {
                System.out.println("Invalid input.");
                continue;
            }
            if (!currentGame.playPiece(column_num)) {
                System.out.println("Invalid column.");
                continue;
            }
            break;
        }
        // display the current game board
        System.out.println("move " + currentGame.getPieceCount()
                + ": Player " + current_player
                + ", column " + column_num);
        System.out.print("game state after move:\n");
        currentGame.printGameBoard();

        // print the current scores
        System.out.println("Score: Player 1 = " + currentGame.getScore(1)
                + ", Player2 = " + currentGame.getScore(2) + "\n ");
        //save human movement
        currentGame.printGameBoardToFile(human_out);
    }

    /**
     * This method is used when to exit the program prematurly.
     *
     * @param value an integer that is returned to the system when the program
     * exits.
     */
    private static void exit_function(int value) {
        System.out.println("exiting from MaxConnectFour.java!\n\n");
        System.exit(value);
    }
} // end of class connectFour
