package squares;

/**
 * This class is used to create a magicSquare object that can be used for a game.
 * The field choices stores a binary representation of the squares a player has chosen
 * with each of the nine possible values being either a 0 (not chosen) or 1 (chosen).
 * the remaining 7 values of the left byte will remain zeros always. The winning conditions
 * constant holds the integer values of the choices necessary to achieve a win. For instance
 * the choices value 1_0000_1010 represents the selection of 2,4,9 which would be a winning value.
 * the integer representation of 1_0000_1010 is 266 which is a winning value.
 * @Author Devin Leyba-Brown
 * @Version 1.0
 */


public class MagicSquare {



//fields
    private short choices;
    private final byte[] square;
    public static final short[] WINNING_CONDITIONS = {273,266,161,146,140,98,84,56};

//constructor
    public MagicSquare(){
        this.square = new byte[]{2, 7, 6, 9, 5, 1, 4, 3, 8};
        this.choices = 0b0000_0000_0000_0000;
    }

    public MagicSquare(byte[] square){
        this.square = square;
    }

//methods

    /**
     * Allows the player to chose which square out of 1-9 they want to select
     * on the magicSquare object uses a mask to test if selection is valid
     * @param selection is a byte value representing the choice of 1-9
     * @return True if this is the first time that the choice is being made and False otherwise. Useful for validation.
     * @throws IllegalArgumentException if the choice is less than 1 or greater than 9
     */
    public boolean choose (byte selection) throws IllegalArgumentException{

        short index = (short)(selection -1);
        short mask;
        boolean choice = false;

        if(selection < 0 | selection > 9 ){
            throw new IllegalArgumentException("That choice is not between 1 and 9");
        }

        //zero out every number except for the number you are looking for,
        //if it exists in choices that is.
        //mask = (short)(choices & num);
        mask = (short)(1 << (index));

        if((mask & choices) == 0){
            choices^= mask;
            choice = true;
        }

        return choice;
    }

    /**
     * Uses a mask to test if the choice that the player has passed has already
     * been made.
     * @param selection is a choice of square 1-9
     * @return True if square has been chosen and False otherwise
     */

    public boolean hasAlreadyChosen(byte selection) {

        short index = (short)(selection -1);
        short mask;
        boolean answer = false;

        //subtract one to get the index right
        mask = (short)(1 << (index));

        if((mask & choices) == mask){
            answer = true;
        }


            return answer;
    }

    public short getChoices(){
        return this.choices;
    }

    public byte[] getSquare() {
        return this.square;
    }

    public String toString(){

        StringBuilder retStr = new StringBuilder();


        for(int i = 0; i< this.square.length; i++){

            String num;

            if(this.hasAlreadyChosen(this.square[i])){
                num = String.valueOf(this.square[i]) + " ";
            }else{
                num = "_ ";
            }

            // append("|")
            retStr.append(num);
           //         .append("|");

            if(i == 2 || i == 5){
                retStr.append( "\n");
            }

        }


        return retStr.toString();
    }

    /**
     * This method is used to test a players choices against all the
     * winning number combinations. The loop if broken if any of the combinations
     * tests true and true is returned. This will end the game with the player
     * whose choices were tested being declared the winner.
     * @return true if the winners choices are equal to a winning combination
     */

    public boolean checkForWin(){
        boolean winner = false;
        short check;

        for(short con: WINNING_CONDITIONS){

            check = (short)(this.choices & con);

            if(check == con){

                winner = true;
                break;
            }
        }
        return winner;
    }
}
