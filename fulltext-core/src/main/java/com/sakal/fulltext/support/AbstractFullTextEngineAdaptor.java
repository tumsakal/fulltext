package com.sakal.fulltext.support;

import com.sakal.fulltext.FullTextEngine;
import com.sakal.fulltext.FullTextPlatformFactory;
import com.sakal.fulltext.index.DocumentIndexerFactory;
import com.sakal.fulltext.index.IndexRequest;
import com.sakal.fulltext.index.IndexResponse;
import com.sakal.fulltext.query.DocumentQueryFactory;
import com.sakal.fulltext.query.QueryRequest;
import com.sakal.fulltext.query.QueryResponse;
import lombok.AccessLevel;
import lombok.Getter;

public abstract class AbstractFullTextEngineAdaptor extends FullTextEngine {

  @Getter(AccessLevel.PROTECTED)
  private final FullTextEngine delegate;

  protected AbstractFullTextEngineAdaptor(FullTextEngine delegate) {
    this.delegate = delegate;
  }

  @Override
  public FullTextPlatformFactory getFullTextFactory() {
    return delegate.getFullTextFactory();
  }

  @Override
  public void shutdown() {
    delegate.shutdown();
  }

  @Override
  public DocumentIndexerFactory getDocumentIndexerFactory() {
    return delegate.getDocumentIndexerFactory();
  }

  @Override
  public DocumentQueryFactory getDocumentQueryFactory() {
    return delegate.getDocumentQueryFactory();
  }

  @Override
  public <T extends IndexRequest, R extends IndexResponse> R index(T indexingRequest) {
    return delegate.index(indexingRequest);
  }

  @Override
  public <T extends QueryRequest, R extends QueryResponse> R query(T queryRequest) {
    return delegate.query(queryRequest);
  }
}
