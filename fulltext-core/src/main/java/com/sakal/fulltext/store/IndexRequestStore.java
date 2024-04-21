package com.sakal.fulltext.store;

import com.sakal.fulltext.index.IndexRequest;

public interface IndexRequestStore {

  void save(IndexRequest request) throws IndexRequestStoreException;

  IndexRequest poll() throws IndexRequestStoreException;
}
