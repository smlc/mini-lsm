package com.vonage.api.memtable;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntBinaryOperator;

public class MemTable implements MemoryTable{
    private Map<byte[], byte[]> map;
    AtomicInteger size;
    private int id;
    public MemTable(int id) {
        this.id = id;
        map = new ConcurrentSkipListMap<>(Arrays::compare);
        size = new AtomicInteger(0);
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
        int estimateSize = key.length + value.length;
        map.put(key, value);
        size.accumulateAndGet(estimateSize, Integer::sum);
    }

    @Override
    public int approximateSize() {
        return size.get();
    }
}
