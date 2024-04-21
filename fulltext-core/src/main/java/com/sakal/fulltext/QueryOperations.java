package com.sakal.fulltext;

import com.sakal.fulltext.query.DocumentQueryFactory;
import com.sakal.fulltext.query.QueryRequest;
import com.sakal.fulltext.query.QueryResponse;

public interface QueryOperations {
  DocumentQueryFactory getDocumentQueryFactory();

  <T extends QueryRequest, R extends QueryResponse> R query(T queryRequest);
}
