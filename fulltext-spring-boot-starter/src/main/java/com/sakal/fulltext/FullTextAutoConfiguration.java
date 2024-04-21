package com.sakal.fulltext;

import com.sakal.fulltext.index.DocumentIndexer;
import com.sakal.fulltext.index.DocumentIndexerFactory;
import com.sakal.fulltext.index.DocumentIndexerFactoryBuilder;
import com.sakal.fulltext.index.IndexRequest;
import com.sakal.fulltext.index.IndexResponse;
import com.sakal.fulltext.query.DocumentQuery;
import com.sakal.fulltext.query.DocumentQueryFactory;
import com.sakal.fulltext.query.DocumentQueryFactoryBuilder;
import com.sakal.fulltext.query.QueryRequest;
import com.sakal.fulltext.query.QueryResponse;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(FullTextEngineConfigurationProperties.class)
@RequiredArgsConstructor
@Slf4j
public class FullTextAutoConfiguration {

    @Bean
    public DocumentIndexerFactory documentIndexerFactory(
            FullTextEngineConfigurationProperties engineConfiguration,
            List<DocumentIndexer<? extends IndexRequest, ? extends IndexResponse>> documentIndexers) {

        return DocumentIndexerFactoryBuilder.getInstance(engineConfiguration.getPlatform())
                .documentIndexers(documentIndexers)
                .build();
    }

    @Bean
    public DocumentQueryFactory getLuceneSearcherFactory(
            FullTextEngineConfigurationProperties engineConfiguration,
            List<DocumentQuery<? extends QueryRequest, ? extends QueryResponse>> documentQueries) {

        return DocumentQueryFactoryBuilder.getInstance(engineConfiguration.getPlatform())
                .documentQueries(documentQueries)
                .build();
    }

    @Bean
    public FullTextEngineFactoryBean fullTextEngineFactoryBean(
            FullTextEngineConfigurationProperties engineConfiguration,
            DocumentIndexerFactory documentIndexerFactory,
            DocumentQueryFactory documentQueryFactory) {

        FullTextEngineFactoryBean fullTextEngineFactoryBean = new FullTextEngineFactoryBean();

        fullTextEngineFactoryBean.setPlatform(engineConfiguration.getPlatform());
        fullTextEngineFactoryBean.setMaxParallel(engineConfiguration.getMaxParallel());
        fullTextEngineFactoryBean.setDocumentIndexerFactory(documentIndexerFactory);
        fullTextEngineFactoryBean.setDocumentQueryFactory(documentQueryFactory);

        return fullTextEngineFactoryBean;
    }
}
