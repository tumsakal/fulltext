package com.sakal.fulltext;

import com.sakal.fulltext.store.IndexRequestStore;
import lombok.Getter;

@Getter
public abstract class AbstractFullTextEngineFactory<T extends FullTextEngineConfiguration>
    implements FullTextEngineFactory {
  private final T configuration;
  private final FullTextPlatformFactory fullTextPlatformFactory;
  private final IndexRequestStore indexRequestStore;

  protected AbstractFullTextEngineFactory(
      T configuration,
      FullTextPlatformFactory fullTextPlatformFactory,
      IndexRequestStore indexRequestStore) {
    // assert not null
    this.configuration = configuration;
    this.fullTextPlatformFactory = fullTextPlatformFactory;
    this.indexRequestStore = indexRequestStore;
  }
}
