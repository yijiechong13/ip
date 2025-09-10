import Nailong.Parser;
import Nailong.Storage;
import Nailong.Task;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.*;
import java.nio.file.Path;
import java.util.ArrayList;

public class OctopusTest {
    @TempDir
    Path tempDir;

    @Test
    void testParseEventArgs() {
        // Valid case
        String[] result = Parser.parseEventArgs("meeting /from 2pm /to 4pm");
        assertNotNull(result);
        assertEquals("meeting", result[0]);
        assertEquals("2pm", result[1]);
        assertEquals("4pm", result[2]);

        // Invalid case - missing /to
        assertNull(Parser.parseEventArgs("meeting /from 2pm"));

        // Invalid case - empty input
        assertNull(Parser.parseEventArgs(""));
    }

    @Test
    void testStorageLoad() throws Exception {
        // Test loading valid tasks
        String testData = "T | 0 | read book\nD | 1 | homework | 2023-12-25 14:00";
        Path testFile = tempDir.resolve("test.txt");
        java.nio.file.Files.write(testFile, testData.getBytes());

        Storage storage = new Storage(testFile.toString());
        ArrayList<Task> tasks = storage.load();

        assertEquals(2, tasks.size());
        assertEquals("read book", tasks.get(0).getDescription());
        assertFalse(tasks.get(0).isDone());
        assertEquals("homework", tasks.get(1).getDescription());
        assertTrue(tasks.get(1).isDone());

        // Test loading from non-existent file
        Storage emptyStorage = new Storage(tempDir.resolve("nonexistent.txt").toString());
        ArrayList<Task> emptyTasks = emptyStorage.load();
        assertEquals(0, emptyTasks.size());
    }
}