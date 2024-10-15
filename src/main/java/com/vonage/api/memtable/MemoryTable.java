package com.vonage.api.memtable;

import java.util.Optional;

public interface MemoryTable {

    Optional<MemTable> recoverFromWal(int id, String path);

    Optional<byte[]> get(byte[] key);

    void put(byte[] key, byte[] value);

    int approximateSize();
}
