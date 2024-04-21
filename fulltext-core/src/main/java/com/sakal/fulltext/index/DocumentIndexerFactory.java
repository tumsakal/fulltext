package com.sakal.fulltext.index;

public abstract class DocumentIndexerFactory {
  public abstract String getType();

  public abstract DocumentIndexer<IndexRequest, IndexResponse> getDocumentIndexer(String indexKey);
}
