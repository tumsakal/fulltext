package com.sakal.fulltext;

import com.sakal.fulltext.index.DocumentIndexerFactory;
import com.sakal.fulltext.index.IndexRequest;
import com.sakal.fulltext.index.IndexResponse;

public interface IndexingOperations {

  DocumentIndexerFactory getDocumentIndexerFactory();

  <T extends IndexRequest, R extends IndexResponse> R index(T indexingRequest);
}
