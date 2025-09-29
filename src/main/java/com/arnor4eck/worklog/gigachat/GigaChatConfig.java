package com.arnor4eck.worklog.gigachat;

import chat.giga.client.GigaChatClient;
import chat.giga.client.auth.AuthClient;
import chat.giga.client.auth.AuthClientBuilder;
import chat.giga.model.Scope;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GigaChatConfig {

    @Value("${api.giga-chat-api}")
    private String gigaChatApiKey;

    @PostConstruct
    public void validateConfig(){
        System.out.println(gigaChatApiKey);
    }

    @Bean
    public GigaChatClient gigaChatClient(){
        return GigaChatClient.builder()
                .verifySslCerts(false)
                .authClient(AuthClient.builder()
                        .withOAuth(AuthClientBuilder.OAuthBuilder.builder()
                                .scope(Scope.GIGACHAT_API_PERS)
                                .authKey(this.gigaChatApiKey)
                                .build())
                        .build())
                .build();
    }
}
