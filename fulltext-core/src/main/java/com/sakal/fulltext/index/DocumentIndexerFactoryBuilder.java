package com.sakal.fulltext.index;

import com.sakal.fulltext.support.FullTextPlatformHelper;
import com.sakal.fulltext.util.Builder;
import java.util.List;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class DocumentIndexerFactoryBuilder extends Builder<DocumentIndexerFactory> {
  private final String platform;
  private DocumentIndexerFactory documentIndexerFactory;
  private List<DocumentIndexer<? extends IndexRequest, ? extends IndexResponse>> documentIndexers;

  public static DocumentIndexerFactoryBuilder getInstance(String platformName) {
    return new DocumentIndexerFactoryBuilder(platformName);
  }

  public DocumentIndexerFactoryBuilder documentIndexers(
      List<DocumentIndexer<? extends IndexRequest, ? extends IndexResponse>> documentIndexers) {

    var platformDocumentIndexers =
        FullTextPlatformHelper.getFilteredFullTextPlatformObjects(this.platform, documentIndexers);

    this.documentIndexerFactory =
        new DefaultDocumentIndexerFactory(this.platform, platformDocumentIndexers);

    return this;
  }

  @Override
  public DocumentIndexerFactory build() {
    return this.documentIndexerFactory;
  }
}
