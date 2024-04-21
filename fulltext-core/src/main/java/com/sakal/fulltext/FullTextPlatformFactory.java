package com.sakal.fulltext;

import com.sakal.fulltext.index.DocumentIndexerFactory;
import com.sakal.fulltext.query.DocumentQueryFactory;
import lombok.Getter;

@Getter
public class FullTextPlatformFactory {
  private final String platform;
  private final DocumentIndexerFactory documentIndexerFactory;
  private final DocumentQueryFactory documentQueryFactory;

  public FullTextPlatformFactory(
      String platform,
      DocumentIndexerFactory documentIndexerFactory,
      DocumentQueryFactory documentQueryFactory) {
    this.platform = platform;
    this.documentIndexerFactory = documentIndexerFactory;
    this.documentQueryFactory = documentQueryFactory;
  }
}
