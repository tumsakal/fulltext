package com.sakal.fulltext;

import com.sakal.fulltext.store.IndexRequestStore;

public abstract class FullTextEngine implements IndexingOperations, QueryOperations {

  public abstract FullTextPlatformFactory getFullTextFactory();

  public abstract IndexRequestStore getIndexRequestStore();

  public void start() {}

  public void shutdown() {}
}
