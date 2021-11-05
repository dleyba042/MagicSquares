package game;

import squares.MagicSquare;

import java.util.Scanner;

/**
 * This class uses a game loop to play at least one game of magic squares. The user is
 * asked for input if they want to play another game. The main class utilizes one static constant
 * MagicSquare object as a means of keeping track of the squares available for player selection.
 * @Author Devin Leyba-Brown
 * @Version 1.0
 */

public class MagicSquareGame {

    private static final MagicSquare Example = new MagicSquare();
    private static String p1Name;
    private static String p2Name;
    private static final Scanner input = new Scanner(System.in);
    private static final short TIE_GAME_SCORE = 0b1_1111_1111;

    public static void main(String[] args) {

        boolean playAgain = false;
        String answer;

        //init example square
        for(byte i = 0; i<10; i++){
            Example.choose(i);
        }

        System.out.println("Welcome to Magic Squares!");
        System.out.println("Rules:");
        System.out.println("Two Players play the game");
        System.out.println("Each player takes turns picking a number from 1-9.");
        System.out.println("No number can be chosen twice.");
        System.out.println("The first player to have 3 numbers that sum to 15 wins!");
        System.out.println(Example);
        System.out.println("*******************************************************\n");

        System.out.println("Enter Player Ones name : ");
        p1Name = input.nextLine();

        System.out.println("Welcome : " + p1Name + "!\n");

        System.out.println("And Your Challenger is??\n");

        System.out.println("Enter name: ");
        p2Name = input.nextLine();

        do{
            playGame();
            System.out.println("Would you like to play again? (1.)Yes (2.)No");
            answer = input.next();

            if(answer.equals("1")){
                playAgain = true;
            }else if(answer.equals("2")){
                playAgain = false;
            }

        }while (playAgain);

        System.out.println("Thanks for playing Magic Squares!!!");

    }

    /**
     * The playGame function loops for the duration of one magic squares game.
     * Booleans are used to keep track of the current state of the game and the game
     * can end in either a tie or a draw. A win message string is used to display the
     * proper message if the game ends in either player winning. Otherwise, a
     * separate message is output when the game ends in a draw. A do while loop is
     * used to validate that the player selects a valid square as a choice for their turn.
     */

    public static void playGame(){

        byte choice;
        boolean winState;
        boolean drawState = false;
        boolean validChoice = false;
        String winMessage = "";
        MagicSquare GAME_SQUARE = new MagicSquare();
        MagicSquare P1_SQUARE = new MagicSquare();
        MagicSquare P2_SQUARE = new MagicSquare();


        do{

            System.out.println("\n");

            //P1 input here
            System.out.println(p1Name + "'s turn, which number do you choose??");
            //This validates that a choice is neither an illegal number
            //or has already been chosen,that is why the game square is used
            do{
                choice = input.nextByte();

                //Use the game square to determine if this square has been chosen yet
                try{ validChoice =  GAME_SQUARE.choose(choice);
                    if(!validChoice){
                        System.out.println("That number has already been chosen");
                        System.out.println("Please choose again.");
                    }
                }catch (IllegalArgumentException e){
                    System.out.println(e.getMessage());
                    System.out.println("Choose again please");
                }
            }while(!validChoice);

            P1_SQUARE.choose(choice);
            System.out.println("Game square now looks like this");
            System.out.println(GAME_SQUARE);

            //check p1 for winner
            winState = P1_SQUARE.checkForWin();
            if(winState) {
                winMessage = p1Name + " Wins!!";
            }

            //check that the game has not ended in a win or tie
            if(!winState && !(GAME_SQUARE.getChoices() == TIE_GAME_SCORE)) {

                //p2 input here
                System.out.println(p2Name + "'s turn, which number do you choose??");

                //input validation
                do{
                    choice = input.nextByte();

                    //Use the game square to determine if this square has been chosen yet
                    try{ validChoice =  GAME_SQUARE.choose(choice);
                        if(!validChoice){
                            System.out.println("That number has already been chosen");
                            System.out.println("Please choose again.");
                        }

                    }catch (IllegalArgumentException e){
                        System.out.println(e.getMessage());
                        System.out.println("Choose again please");
                    }
                }while(!validChoice);


                //GAME_SQUARE.choose(choice);
                P2_SQUARE.choose(choice);
                System.out.println("Game square now looks like this");
                System.out.println(GAME_SQUARE);

                //check for winner after player twos turn
                winState = P2_SQUARE.checkForWin();
                if(winState) {
                    winMessage = p2Name + " Wins!!";
                }
            }

            //if player two did not win when entering their last number then check for a tie
            if(GAME_SQUARE.getChoices() == TIE_GAME_SCORE){
                drawState = true;// in order to skip the win message
                System.out.println("GAME ENDS IN A DRAW!!!");
            }

        }while(!winState && !drawState);

        if(!drawState) {
            System.out.println(winMessage);
        }
    }
}
