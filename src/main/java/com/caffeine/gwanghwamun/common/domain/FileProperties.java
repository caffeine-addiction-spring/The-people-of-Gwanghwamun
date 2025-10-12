package com.caffeine.gwanghwamun.common.domain;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "file.upload")
public class FileProperties {
	private String url;
	private String path;
}
