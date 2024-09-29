import com.vonage.api.memtable.LSMStorage;
import com.vonage.api.memtable.MemTable;
import com.vonage.api.memtable.MemoryTable;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WeekOneDayOneTests {

    @Test
    void testMemTableGet() {
        MemoryTable memTable = new MemTable(0);

        memTable.put("key1".getBytes(), "value1".getBytes());
        memTable.put("key2".getBytes(), "value2".getBytes());
        memTable.put("key3".getBytes(), "value3".getBytes());

        assertArrayEquals("value1".getBytes(), memTable.get("key1".getBytes()).get());
        assertArrayEquals("value2".getBytes(), memTable.get("key2".getBytes()).get());
        assertArrayEquals("value3".getBytes(), memTable.get("key3".getBytes()).get());
    }

    @Test
    void testMemTableOverwrite() {
        MemoryTable memTable = new MemTable(0);

        // Initial puts
        memTable.put("key1".getBytes(), "value1".getBytes());
        memTable.put("key2".getBytes(), "value2".getBytes());
        memTable.put("key3".getBytes(), "value3".getBytes());

        // Overwrite values
        memTable.put("key1".getBytes(), "value11".getBytes());
        memTable.put("key2".getBytes(), "value22".getBytes());
        memTable.put("key3".getBytes(), "value33".getBytes());

        // Assert overwritten values
        assertArrayEquals("value11".getBytes(), memTable.get("key1".getBytes()).get());
        assertArrayEquals("value22".getBytes(), memTable.get("key2".getBytes()).get());
        assertArrayEquals("value33".getBytes(), memTable.get("key3".getBytes()).get());
    }


    @Test
    void testStorageIntegration() throws Exception {
        LSMStorage storage = new LSMStorage();

        // Test get on non-existent key
        assertEquals(Optional.empty(), storage.get("0".getBytes()));

        // Test puts
        storage.put("1".getBytes(), "233".getBytes());
        storage.put("2".getBytes(), "2333".getBytes());
        storage.put("3".getBytes(), "23333".getBytes());

        // Test gets after puts
        assertArrayEquals("233".getBytes(), storage.get("1".getBytes()).get());
        assertArrayEquals("2333".getBytes(), storage.get("2".getBytes()).get());
        assertArrayEquals("23333".getBytes(), storage.get("3".getBytes()).get());

        // Test delete
        storage.delete("2".getBytes());
        assertTrue(storage.get("2".getBytes()).isEmpty());

        // Test delete of non-existent key (should not throw exception)
        assertDoesNotThrow(() -> storage.delete("0".getBytes()));
    }
}
