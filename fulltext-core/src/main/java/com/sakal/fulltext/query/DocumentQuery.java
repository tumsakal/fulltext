package com.sakal.fulltext.query;

public interface DocumentQuery<T extends QueryRequest, R extends QueryResponse> {
  String getName();

  R query(T searchRequest);
}
