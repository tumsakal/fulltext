package com.sakal.fulltext;

import com.sakal.fulltext.index.DocumentIndexerFactory;
import com.sakal.fulltext.index.DocumentIndexerFactoryBuilder;
import com.sakal.fulltext.query.DocumentQueryFactory;
import com.sakal.fulltext.query.DocumentQueryFactoryBuilder;
import com.sakal.fulltext.store.IndexRequestStore;
import com.sakal.fulltext.store.IndexRequestStoreBuilder;
import com.sakal.fulltext.util.Customizer;

import java.util.Objects;

public class FullTextEngineBuilder {
  private final FullTextEngineConfiguration configurationProperties;
  private DocumentIndexerFactory documentIndexerFactory;
  private DocumentQueryFactory documentQueryFactory;
  private IndexRequestStore indexRequestStore;
  private FullTextPlatformFactory fullTextPlatformFactory;

  private FullTextEngineBuilder(FullTextEngineConfiguration configurationProperties) {
    this.configurationProperties = configurationProperties;
  }

  public static FullTextEngineBuilder getInstance(String platformName, int maxParallel) {
    return new FullTextEngineBuilder(new FullTextEngineConfiguration())
        .platform(platformName)
        .maxParallel(maxParallel);
  }

  public static FullTextEngineBuilder getInstance(
      FullTextEngineConfiguration fullTextEngineConfiguration) {
    return new FullTextEngineBuilder(fullTextEngineConfiguration);
  }

  public FullTextEngineBuilder platform(String platformName) {
    this.configurationProperties.setPlatform(platformName);
    return this;
  }

  public FullTextEngineBuilder maxParallel(int max) {
    this.configurationProperties.setMaxParallel(max);
    return this;
  }

  public FullTextEngineBuilder indexerFactory(DocumentIndexerFactory documentIndexerFactory) {
    this.documentIndexerFactory = documentIndexerFactory;
    return this;
  }

  public FullTextEngineBuilder indexerFactoryBuilder(
      Customizer<DocumentIndexerFactoryBuilder> documentIndexerFactoryCustomizer) {

    DocumentIndexerFactoryBuilder indexerFactoryBuilder =
        DocumentIndexerFactoryBuilder.getInstance(this.configurationProperties.getPlatform());

    documentIndexerFactoryCustomizer.customize(indexerFactoryBuilder);

    this.documentIndexerFactory = indexerFactoryBuilder.build();

    return this;
  }

  public FullTextEngineBuilder queryFactory(DocumentQueryFactory documentQueryFactory) {
    this.documentQueryFactory = documentQueryFactory;
    return this;
  }

  public FullTextEngineBuilder queryFactoryBuilder(
      Customizer<DocumentQueryFactoryBuilder> documentQueryFactoryCustomizer) {
    DocumentQueryFactoryBuilder documentQueryFactoryBuilder =
        DocumentQueryFactoryBuilder.getInstance(this.configurationProperties.getPlatform());

    documentQueryFactoryCustomizer.customize(documentQueryFactoryBuilder);

    this.documentQueryFactory = documentQueryFactoryBuilder.build();

    return this;
  }

  public FullTextEngineBuilder indexRequestStore(IndexRequestStore indexRequestStore) {
    this.indexRequestStore = indexRequestStore;
    return this;
  }

  public FullTextEngineBuilder indexRequestStoreBuilder(
      Customizer<IndexRequestStoreBuilder> indexRequestStoreCustomizer) {
    IndexRequestStoreBuilder requestStoreBuilder = IndexRequestStoreBuilder.getInstance();

    indexRequestStoreCustomizer.customize(requestStoreBuilder);

    this.indexRequestStore = requestStoreBuilder.build();

    return this;
  }

  public FullTextEngineBuilder fullTextPlatformFactory(
      FullTextPlatformFactory fullTextPlatformFactory) {
    this.fullTextPlatformFactory = fullTextPlatformFactory;
    return this;
  }

  // TODO: 4/19/2024 Add documentation
  public FullTextEngine build() {
    FullTextPlatformFactory platformFactory =
        Objects.requireNonNullElse(
            this.fullTextPlatformFactory,
            new FullTextPlatformFactory(
                this.configurationProperties.getPlatform(),
                this.documentIndexerFactory,
                this.documentQueryFactory));

    IndexRequestStore idxReqStore =
        Objects.requireNonNullElse(
            this.indexRequestStore, IndexRequestStoreBuilder.getDefault().build());

    DefaultFullTextEngineFactory fullTextEngineFactory =
        new DefaultFullTextEngineFactory(
            this.configurationProperties, platformFactory, idxReqStore);

    return fullTextEngineFactory.buildIndexingEngine();
  }
}
