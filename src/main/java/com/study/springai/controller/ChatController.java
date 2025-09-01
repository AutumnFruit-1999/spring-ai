package com.study.springai.controller;

import com.study.springai.advisor.CustomSimpleLoggerAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class ChatController {

    @Autowired
    public ChatClient deepSeekChatClient;

    @Autowired
    public ChatClient deepSeekLearningPlannerChatClient;

    @Autowired
    public ChatClient deepSeekAuditChatClient;


    @Autowired
    public ChatClient qwenChatClient;


    @GetMapping(value = "/deepSeekChat", produces = MediaType.TEXT_HTML_VALUE + ";charset=UTF-8")
    public Flux<String> deepSeekChatStream(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message
            , @RequestParam(value = "conversationId", defaultValue = "1000") String conversationId) {

        return deepSeekChatClient.prompt().user(message)
                .advisors(new CustomSimpleLoggerAdvisor())
                .advisors(advisorSpec -> {
                    advisorSpec.param(ChatMemory.CONVERSATION_ID, conversationId);
                })
                .stream().content();
    }

    @GetMapping(value = "/qwenChat", produces = MediaType.TEXT_HTML_VALUE + ";charset=UTF-8")
    public Flux<String> qwenChatClientStream(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message
            , @RequestParam(value = "conversationId", defaultValue = "1000") String conversationId) {
        return qwenChatClient.prompt().user(message)
                .advisors(new SimpleLoggerAdvisor())
                .advisors(advisorSpec -> {
                    advisorSpec.param(ChatMemory.CONVERSATION_ID, conversationId);
                })
                .stream().content();
    }

    @GetMapping(value = "/deepSeekLearningPlannerChat", produces = MediaType.TEXT_HTML_VALUE + ";charset=UTF-8")
    public Flux<String> deepSeekLearningPlannerChatStream(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message
            , @RequestParam(value = "conversationId", defaultValue = "1000") String conversationId) {
        return deepSeekLearningPlannerChatClient.prompt().user(message)
                .advisors(new CustomSimpleLoggerAdvisor())
                .advisors(advisorSpec -> {
                    advisorSpec.param(ChatMemory.CONVERSATION_ID, conversationId);
                })
                .stream().content();
    }

    @GetMapping(value = "/deepSeekAuditChat", produces = MediaType.TEXT_HTML_VALUE + ";charset=UTF-8")
    public Flux<String> deepSeekAuditChatStream(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message
            , @RequestParam(value = "conversationId", defaultValue = "1000") String conversationId) {
        return deepSeekAuditChatClient.prompt().user(message)
                .advisors(new CustomSimpleLoggerAdvisor())
                .advisors(advisorSpec -> {
                    advisorSpec.param(ChatMemory.CONVERSATION_ID, conversationId);
                })
                .stream().content();
    }

}
