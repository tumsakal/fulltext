package com.sakal.fulltext;

import com.sakal.fulltext.store.IndexRequestStore;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DefaultFullTextEngineFactory
    extends AbstractFullTextEngineFactory<FullTextEngineConfiguration> {

  public DefaultFullTextEngineFactory(
      FullTextEngineConfiguration properties,
      FullTextPlatformFactory fullTextPlatformFactory,
      IndexRequestStore indexRequestStore) {
    super(properties, fullTextPlatformFactory, indexRequestStore);
  }

  @Override
  public FullTextEngine buildIndexingEngine() {
    DefaultFullTextEngine engine =
        new DefaultFullTextEngine(this.getFullTextPlatformFactory(), getIndexRequestStore());
    this.configureExecutor(engine);
    return engine;
  }

  private void configureExecutor(DefaultFullTextEngine indexingEngine) {
    int maxThreads = getConfiguration().getMaxParallel();
    ExecutorService executorService = Executors.newFixedThreadPool(maxThreads);
    indexingEngine.setExecutorService(executorService);
  }
}
