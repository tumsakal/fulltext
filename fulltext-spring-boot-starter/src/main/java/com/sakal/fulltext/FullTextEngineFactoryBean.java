package com.sakal.fulltext;

import com.sakal.fulltext.index.DocumentIndexerFactory;
import com.sakal.fulltext.query.DocumentQueryFactory;
import com.sakal.fulltext.store.IndexRequestStore;
import com.sakal.fulltext.store.IndexRequestStoreBuilder;
import lombok.Setter;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.StringUtils;

import java.util.Objects;

@Setter
public class FullTextEngineFactoryBean
        implements FactoryBean<FullTextEngine>, InitializingBean, DisposableBean {

    private String platform;
    private int maxParallel;
    private DocumentIndexerFactory documentIndexerFactory;
    private DocumentQueryFactory documentQueryFactory;
    private IndexRequestStore indexRequestStore;
    private FullTextPlatformFactory fullTextPlatformFactory;
    private FullTextEngine engine;

    @Override
    public void afterPropertiesSet() {
        this.fullTextPlatformFactory = this.getFullTextPlatformFactory();
        this.indexRequestStore =
                Objects.requireNonNullElse(
                        this.indexRequestStore, IndexRequestStoreBuilder.getDefault().build());

        this.engine =
                FullTextEngineBuilder.getInstance(this.platform, this.maxParallel)
                        .fullTextPlatformFactory(this.fullTextPlatformFactory)
                        .indexRequestStore(indexRequestStore)
                        .build();

        this.engine.start();
    }

    private FullTextPlatformFactory getFullTextPlatformFactory() {
        if (this.fullTextPlatformFactory == null) {
            return getDefaultFullTextFactory();
        }

        return this.fullTextPlatformFactory;
    }

    private FullTextPlatformFactory getDefaultFullTextFactory() {
        if (!StringUtils.hasText(this.platform)) {
            throw new NullPointerException("Platform is required");
        }
        if (this.documentIndexerFactory == null && this.documentQueryFactory == null) {
            throw new IllegalStateException("Factory not fully setup");
        }

        return new FullTextPlatformFactory(
                this.platform,
                this.documentIndexerFactory,
                this.documentQueryFactory);
    }

    @Override
    public FullTextEngine getObject() {

        return this.engine;
    }

    @Override
    public Class<?> getObjectType() {
        return FullTextEngine.class;
    }

    @Override
    public void destroy() {
        this.engine.shutdown();
    }
}
