package com.sakal.fulltext.store;

import com.sakal.fulltext.index.IndexRequest;
import lombok.extern.slf4j.Slf4j;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Slf4j
public class InMemoryIndexRequestStore implements IndexRequestStore {
    private final Queue<IndexRequest> requestStore = new ConcurrentLinkedQueue<>();

    @Override
    public void save(IndexRequest request) {
        requestStore.add(request);
    }

    @Override
    public IndexRequest poll() {
        return requestStore.poll();
    }
}
