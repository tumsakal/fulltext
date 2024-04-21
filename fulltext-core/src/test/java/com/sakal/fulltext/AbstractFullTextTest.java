package com.sakal.fulltext;

import com.sakal.fulltext.index.DocumentIndexer;
import com.sakal.fulltext.index.IndexRequest;
import com.sakal.fulltext.index.IndexResponse;
import com.sakal.fulltext.query.DocumentQuery;
import com.sakal.fulltext.query.QueryRequest;
import com.sakal.fulltext.query.QueryResponse;
import java.util.List;
import java.util.Objects;

import lombok.Getter;
import org.junit.jupiter.api.BeforeEach;

@Getter
public abstract class AbstractFullTextTest {
  protected static final String PLATFORM_NAME = "testPlatform";
  private FullTextEngine fullTextEngine;

  protected List<DocumentIndexer<? extends IndexRequest, ? extends IndexResponse>>
      getDocumentIndexers() {
    return List.of();
  }

  protected List<DocumentQuery<? extends QueryRequest, ? extends QueryResponse>> documentQueries() {
    return List.of();
  }

  @BeforeEach
  void setup() {
    var docIndexers =
        Objects.requireNonNullElse(
            getDocumentIndexers(),
            List.<DocumentIndexer<? extends IndexRequest, ? extends IndexResponse>>of());

    var docQueries =
        Objects.requireNonNullElse(
            documentQueries(),
            List.<DocumentQuery<? extends QueryRequest, ? extends QueryResponse>>of());

    fullTextEngine =
        FullTextEngineBuilder.getInstance(PLATFORM_NAME, 2)
            .indexerFactoryBuilder(
                indexerFactoryCustomizer -> indexerFactoryCustomizer.documentIndexers(docIndexers))
            .queryFactoryBuilder(
                queryFactoryCustomizer -> queryFactoryCustomizer.documentQueries(docQueries))
            .build();
  }
}
