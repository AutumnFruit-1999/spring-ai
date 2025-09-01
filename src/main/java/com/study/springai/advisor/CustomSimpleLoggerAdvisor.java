package com.study.springai.advisor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClientMessageAggregator;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;
import org.springframework.ai.chat.client.advisor.api.StreamAdvisor;
import org.springframework.ai.chat.client.advisor.api.StreamAdvisorChain;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.prompt.Prompt;
import reactor.core.publisher.Flux;

public class CustomSimpleLoggerAdvisor implements CallAdvisor, StreamAdvisor {

    private static final Logger logger= LoggerFactory.getLogger(CustomSimpleLoggerAdvisor.class);

    private  final Integer order;

    public CustomSimpleLoggerAdvisor() {
        this.order=0;
    }

    public CustomSimpleLoggerAdvisor(Integer order) {
        this.order = order;
    }

    @Override
    public ChatClientResponse adviseCall(ChatClientRequest chatClientRequest, CallAdvisorChain callAdvisorChain) {
        return null;
    }

    @Override
    public Flux<ChatClientResponse> adviseStream(ChatClientRequest chatClientRequest, StreamAdvisorChain streamAdvisorChain) {

        Prompt prompt = chatClientRequest.prompt();
        logger.info("user message: {}",prompt.getUserMessage().getText());

        Flux<ChatClientResponse> chatClientResponse = streamAdvisorChain.nextStream(chatClientRequest);
        return (new ChatClientMessageAggregator()).aggregateChatClientResponse(chatClientResponse, this::logResponse);
    }

    private void logResponse(ChatClientResponse chatClientResponse) {
        AssistantMessage output = chatClientResponse.chatResponse().getResult().getOutput();
        logger.info("response: {}", output.getText());
    }

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public int getOrder() {
        return this.order;
    }
}
