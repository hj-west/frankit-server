package com.frankit.config.properties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("jwt")
@Getter
@Setter
@NoArgsConstructor
public class JwtTokenProperties {
    private String secret;
    private String tokenValidTime;
}
