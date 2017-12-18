import org.junit.Before;
import org.junit.Test;
import ru.sbt.GameOfLife;
import ru.sbt.GameOfLifeImpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;

/**
 * Created by Mintas on 12/10/2017.
 */
public class GameOfLifeTest10 {
    private GameOfLife gameOfLife;

    @Before
    public void before() {
        gameOfLife = new GameOfLifeImpl(10);
    }

    @Test
    public void testGame() throws Exception {
        testOneGame("resources/input.txt", "resources/output.txt");
    }

    @Test
    public void testGame100() throws Exception {
        testOneGame("resources/input100.txt", "resources/output100.txt");
    }

    @Test
    public void testGame1000() throws Exception {
        testOneGame("resources/input1000.txt", "resources/output1000.txt");
    }

    private void testOneGame(String inputFile, String expectedOutputFile) throws FileNotFoundException {
        List<String> result = gameOfLife.play(inputFile);
        assertEquals(readFile(expectedOutputFile), result);
    }

    private static List<String> readFile(String fileName) throws FileNotFoundException {
        ArrayList<String> lines = new ArrayList<>();

        Scanner scan = new Scanner(new File(fileName));
        while (scan.hasNextLine()) {
            lines.add(scan.nextLine());
        }
        scan.close();

        return lines;
    }
}
