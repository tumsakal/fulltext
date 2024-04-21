package com.sakal.fulltext.index;

public interface DocumentIndexer<T extends IndexRequest, R extends IndexResponse> {
  String getIndexKey();

  R index(T indexRequest, IndexContext context);
}
