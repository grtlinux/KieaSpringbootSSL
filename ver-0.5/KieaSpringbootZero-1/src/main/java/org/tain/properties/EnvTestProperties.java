package org.tain.properties;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import lombok.Getter;

@Component
@ConfigurationProperties(prefix = "env.test")
@Validated
@Getter
public class EnvTestProperties {

	private List<String> servers = new ArrayList<>();
	private UserObject user = new UserObject();
}
