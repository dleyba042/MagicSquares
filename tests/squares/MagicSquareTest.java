package squares;

import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.Assert.*;
public class MagicSquareTest {
    private static byte[] selections = {2, 7, 6, 9, 5, 1, 4, 3, 8};
    private static final short[] CHOICES = {0b0_0000_0010,
            0b0_0100_0010,
            0b0_0110_0010,
            0b1_0110_0010,
            0b1_0111_0010,
            0b1_0111_0011,
            0b1_0111_1011,
            0b1_0111_1111,
            0b1_1111_1111};


    @Test
    public void testGetChoices(){


        //choices will be a equal to this binary number after being added
        short choiceTest = 0b0_0010_1010;

        MagicSquare ms = new MagicSquare(selections);

        assertFalse("should be false since choices is instantiated as zero",(ms.getChoices() > 0));


        ms.choose((byte)2);
        ms.choose((byte)4);
        ms.choose((byte)6);

        assertEquals("choices should equal those in choiceTest variable",choiceTest,ms.getChoices());
    }


    @Test
    public void testGetSquare(){

        byte[] testSquare = new byte[9];

        MagicSquare ms = new MagicSquare(selections);

        assertEquals("should return a byte array of values",testSquare.getClass(),ms.getSquare().getClass());

    }




    @Test
    public void testHasAlreadyChosen(){

        MagicSquare ms = new MagicSquare(selections);

        ms.choose((byte)2);

        assertTrue("should return true",ms.hasAlreadyChosen((byte)2));

        assertFalse("should return false",ms.hasAlreadyChosen((byte)4));


    }



    @Test
    public void testPrintChoicesEmptySquare() {
        MagicSquare ms = new MagicSquare(selections);
        // redirect output from console window into a PrintStream
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        // invoke toString() which now prints to the PrintStream instead of the
        //console window
        System.out.println(ms);
        String expectedConsoleOutput = "_ _ _ "+ System.lineSeparator()+"_ _ _ "+System.lineSeparator()+"_ _ _ "+System.lineSeparator();
        assertEquals("print choices incorrect output", expectedConsoleOutput,
                out.toString());
    }
    @Test
    public void testChoose() {
        MagicSquare sq = new MagicSquare(selections);
        // for every selection choice in values array
        for (int i = 0; i < selections.length; i++) {
            // choose num
            sq.choose((byte) selections[i]);
            // verify that getChoices returns proper cummulative selections
            assertEquals("choice was noted incorrectly", CHOICES[i],
                    sq.getChoices());
        }
    }
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidChoice() {
        MagicSquare ms = new MagicSquare(selections);
        ms.choose((byte) -7);
        ms.choose((byte) 0);
        ms.choose((byte) 10);
    }
    @Test
    public void testPrintChoicesFullSquare() {
        MagicSquare ms = new MagicSquare(selections);
        // choose every selection in the values array
        for (byte selection : selections) {
            ms.choose((byte) selection);
        }
        // redirect output from console window into a PrintStream
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        // invoke toString() which now prints to the PrintStream instead of the
        //console window
        System.out.println(ms);
        String expectedConsoleOutput = "2 7 6 "+
                System.lineSeparator()+"9 5 1 "+
                System.lineSeparator()+"4 3 8 "+
                System.lineSeparator();
        assertEquals("print choices incorrect output", expectedConsoleOutput,
                out.toString());
    }
}