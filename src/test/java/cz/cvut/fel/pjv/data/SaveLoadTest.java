package cz.cvut.fel.pjv.data;
import cz.cvut.fel.pjv.GamePanel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.File;

public class SaveLoadTest {
    // Constants
    private static final String EXISTING_SAVE_FILE_PATH = "D:/study/2022-2023-leto/pjv/00/pro/save.dat";
    private static final String NON_EXISTENT_SAVE_FILE_PATH = "D:/study/2022-2023-leto/pjv/00/pro/save_not_exist.dat";
    private static final int DELAY_MILLISECONDS = 1000;
    private static final String ERROR_MESSAGE = "Expected Result: %s, Actual Result: %s";

    @Test
    public void testSaveFileExists() {
        // Check that the existing save file exists
        File saveFile = new File(EXISTING_SAVE_FILE_PATH);
        boolean saveFileExists = saveFile.exists();

        // Define expected and actual results
        boolean expectedResult = true;
        boolean actualResult = saveFileExists;

        // Assert with custom message
        String errorMessage = String.format(ERROR_MESSAGE, expectedResult, actualResult);
        Assertions.assertEquals(expectedResult, actualResult, errorMessage);
    }

    @Test
    public void testNonExistentSaveFile() {
        // Check that a non-existent save file does not exist
        File saveFile = new File(NON_EXISTENT_SAVE_FILE_PATH);
        boolean saveFileExists = saveFile.exists();

        // Define expected and actual results
        boolean expectedResult = false;
        boolean actualResult = saveFileExists;

        // Assert with custom message
        String errorMessage = String.format(ERROR_MESSAGE, expectedResult, actualResult);
        Assertions.assertEquals(expectedResult, actualResult, errorMessage);
    }
}
