package com.sakal.fulltext.query;

import com.sakal.fulltext.support.FullTextPlatformHelper;
import com.sakal.fulltext.util.Builder;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class DocumentQueryFactoryBuilder extends Builder<DocumentQueryFactory> {
  private final String platform;
  private DocumentQueryFactory documentQueryFactory;

  public static DocumentQueryFactoryBuilder getInstance(String platformName) {
    return new DocumentQueryFactoryBuilder(platformName);
  }

  public DocumentQueryFactoryBuilder documentQueries(
      List<DocumentQuery<? extends QueryRequest, ? extends QueryResponse>> documentQueries) {

    var platformDocumentQueries =
        FullTextPlatformHelper.getFilteredFullTextPlatformObjects(platform, documentQueries);

    this.documentQueryFactory =
        new DefaultDocumentQueryFactory(this.platform, platformDocumentQueries);

    return this;
  }

  @Override
  public DocumentQueryFactory build() {
    return this.documentQueryFactory;
  }
}
