package com.arnor4eck.worklog.gigachat;

import chat.giga.client.GigaChatClient;
import chat.giga.client.auth.AuthClient;
import chat.giga.client.auth.AuthClientBuilder;
import chat.giga.model.Scope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GigaChatConfig {

    @Value("${api.giga-chat-api}")
    private String gigaChatApiKey;

    @Bean
    public GigaChatClient gigaChatClient(){
        return GigaChatClient.builder()
                .authClient(AuthClient.builder()
                        .withOAuth(AuthClientBuilder.OAuthBuilder.builder()
                                .scope(Scope.GIGACHAT_API_PERS)
                                .authKey(this.gigaChatApiKey)
                                .build())
                        .build())
                .build();
    }
}
