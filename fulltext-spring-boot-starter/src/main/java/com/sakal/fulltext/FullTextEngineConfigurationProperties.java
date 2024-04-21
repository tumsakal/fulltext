package com.sakal.fulltext;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ConfigurationProperties(prefix = "fulltext")
public class FullTextEngineConfigurationProperties {
    private String platform;
    private int maxParallel;
}
