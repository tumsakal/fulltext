package com.sakal.fulltext.query;

public abstract class DocumentQueryFactory {
  public abstract String getType();

  public abstract DocumentQuery<QueryRequest, QueryResponse> getDocumentQuery(String key);
}
