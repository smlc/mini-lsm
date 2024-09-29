package com.vonage.api.memtable;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentSkipListMap;

public class MemTable implements MemoryTable{
    private Map<byte[], byte[]> map;
    private int id;
    public MemTable(int id) {
        this.id = id;
        map = new ConcurrentSkipListMap<>(Arrays::compare);
    }


    @Override
    public Optional<MemTable> recoverFromWal(int id, String path) {
        return Optional.empty();
    }

    @Override
    public Optional<byte[]> get(byte[] key) {
        return Optional.ofNullable(map.get(key));
    }

    @Override
    public void put(byte[] key, byte[] value) {
        map.put(key, value);
    }
}
