package com.vonage.api.memtable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LSMStorage {

    private static final int TARGET_SST_SIZE = 2 << 20; // 2 MB
    private MemoryTable memTable;
    private List<MemoryTable> immutableMemTable;
    private Lock write;
    private Lock stateLock;

    public LSMStorage() {
        this.memTable = new MemTable(0);
        immutableMemTable = new ArrayList<>();
        write = new ReentrantLock();
        stateLock = new ReentrantLock();

    }

    public Optional<byte[]> get(byte[] key) {
        return memTable.get(key)
                .filter(value -> !Arrays.equals(value, "".getBytes()));
    }

    public void put(byte[] key, byte[] value) {
        if (memTable.approximateSize() >= TARGET_SST_SIZE) {
            stateLock.lock();
            if (memTable.approximateSize() >= TARGET_SST_SIZE) {
                try_freeze();
            }
        }
        memTable.put(key, value);

    }

    /// Remove a key from the storage by writing an empty value.
    public void delete(byte[] key) {
        memTable.put(key, "".getBytes());

        try_freeze();
    }

    private void try_freeze() {
        write.lock();
        immutableMemTable.add(memTable);
        memTable = new MemTable(0);
        write.unlock();
    }
}
