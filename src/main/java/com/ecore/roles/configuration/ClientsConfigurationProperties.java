package com.ecore.roles.configuration;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Data
@Component
@ConfigurationProperties(prefix = "clients")
public class ClientsConfigurationProperties {

    private String usersApiHost;

    private String teamsApiHost;

}
