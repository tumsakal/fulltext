package com.sakal.fulltext;

import com.sakal.fulltext.index.DefaultIndexingContext;
import com.sakal.fulltext.index.DocumentIndexer;
import com.sakal.fulltext.index.DocumentIndexerFactory;
import com.sakal.fulltext.index.IndexContext;
import com.sakal.fulltext.index.IndexRequest;
import com.sakal.fulltext.index.IndexResponse;
import com.sakal.fulltext.query.DocumentQuery;
import com.sakal.fulltext.query.DocumentQueryFactory;
import com.sakal.fulltext.query.QueryRequest;
import com.sakal.fulltext.query.QueryResponse;
import com.sakal.fulltext.store.IndexRequestStore;
import com.sakal.fulltext.store.IndexRequestStoreException;

import java.util.UUID;

public abstract class AbstractFullTextEngine extends FullTextEngine {
  private final FullTextPlatformFactory fullTextPlatformFactory;
  private final IndexRequestStore indexRequestStore;

  protected AbstractFullTextEngine(
      FullTextPlatformFactory fullTextPlatformFactory, IndexRequestStore indexRequestStore) {
    this.fullTextPlatformFactory = fullTextPlatformFactory;
    this.indexRequestStore = indexRequestStore;
  }

  @Override
  public FullTextPlatformFactory getFullTextFactory() {
    return fullTextPlatformFactory;
  }

  @Override
  public DocumentIndexerFactory getDocumentIndexerFactory() {
    return getFullTextFactory().getDocumentIndexerFactory();
  }

  @Override
  public DocumentQueryFactory getDocumentQueryFactory() {
    return getFullTextFactory().getDocumentQueryFactory();
  }

  @Override
  public IndexRequestStore getIndexRequestStore() {
    return this.indexRequestStore;
  }

  /**
   *
   * @return null if failed.
   */
  protected abstract <T extends IndexRequest, R extends IndexResponse> R doIndexInternal(
          DocumentIndexer<T, R> documentIndexer, T indexRequest, DefaultIndexingContext context);

  @SuppressWarnings("unchecked")
  @Override
  public <T extends IndexRequest, R extends IndexResponse> R index(T indexingRequest) {
    DocumentIndexerFactory documentIndexerFactory = getDocumentIndexerFactory();

    String requestID = UUID.randomUUID().toString();
    IndexContext rootContext = new IndexContext();
    DefaultIndexingContext context = new DefaultIndexingContext(rootContext);
    context.setReference(requestID);

    DocumentIndexer<T, R> documentIndexer =
        (DocumentIndexer<T, R>)
            documentIndexerFactory.getDocumentIndexer(indexingRequest.getIndexKey());

    if (documentIndexer == null) {
      throw new FullTextOperationException(
          "There is no DocumentIndexer configured for the index key: "
              + indexingRequest.getIndexKey());
    }

    return doIndexInternal(documentIndexer, indexingRequest, context);
  }

  @SuppressWarnings("unchecked")
  @Override
  public <T extends QueryRequest, R extends QueryResponse> R query(T queryRequest) {
    DocumentQueryFactory queryFactory = getDocumentQueryFactory();
    DocumentQuery<T, R> documentQuery =
        (DocumentQuery<T, R>) queryFactory.getDocumentQuery(queryRequest.getQueryName());

    if (documentQuery == null) {
      throw new FullTextOperationException(
          "There is no DocumentQuery configured for the request: " + queryRequest.getQueryName());
    }

    return documentQuery.query(queryRequest);
  }

  protected <T extends IndexRequest> void onIndexingFailed(T failedRequest) {
    try {
      getIndexRequestStore().save(failedRequest);
    } catch (IndexRequestStoreException e) {
      throw new FullTextOperationException(
          "Failed to save an indexing request after the operation failed", e);
    }
  }
}
