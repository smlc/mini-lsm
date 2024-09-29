package com.vonage.api.memtable;

import java.util.Arrays;
import java.util.Optional;

public class LSMStorage {
    private MemoryTable memTable;
    private MemoryTable immutableMemTable;

    public LSMStorage() {
        this.memTable = new MemTable(0);
    }

    public Optional<byte[]> get(byte[] key) {
        return memTable.get(key)
                .filter(value -> !Arrays.equals(value, "".getBytes()));
    }

    public void put(byte[] key, byte[] value) {
        memTable.put(key, value);
    }

    /// Remove a key from the storage by writing an empty value.
    public void delete(byte[] key) {
        memTable.put(key, "".getBytes());
    }
}
