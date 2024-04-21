package com.sakal.fulltext;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FullTextEngineConfiguration {
    private String platform;
    private int maxParallel;
    private Map<String, Object> properties;
}
