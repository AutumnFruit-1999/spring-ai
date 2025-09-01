package com.study.springai.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.study.springai.utils.FileUtil;
import io.micrometer.common.lang.NonNullApi;
import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.util.Assert;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@NonNullApi
public class FileChatMemoryRepository implements ChatMemoryRepository {

    private static final ObjectMapper jackson = new ObjectMapper()
            .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);


    @Override
    public List<String> findConversationIds() {
       return List.of();
    }

    @Override
    public List<Message> findByConversationId(String conversationId) {

        List<Message> messages = new ArrayList<>();
        List<String> contents = FileUtil.FileContent(conversationId);
        for (String content : contents){
            try {
                Map map = jackson.readValue(content, Map.class);
                if (map.get("messageType").equals("user")){
                    messages.add(new UserMessage(String.valueOf(map.get("text"))));
                }
                if (map.get("messageType").equals("assistant")){
                    messages.add(new AssistantMessage(String.valueOf(map.get("text"))));
                }
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        return messages;
    }

    @Override
    public void saveAll(String conversationId, List<Message> messages) {
        Assert.hasText(conversationId, "conversationId cannot be null or empty");
        Assert.notNull(messages, "messages cannot be null");
        Assert.noNullElements(messages, "messages cannot contain null elements");

        List<String> maps = this.MessageToMap(List.of(messages.getLast()));

        FileUtil.writeLinesToFile(conversationId,maps);
    }

    @Override
    public void deleteByConversationId(String conversationId) {
        FileUtil.deleteFile(conversationId);
    }

    private List<String> MessageToMap(List<Message> messages) {
        return messages.stream()
                .map(message -> {
                    try {
                        ConcurrentHashMap<String, String> messageMap = new ConcurrentHashMap<>();
                        messageMap.put("messageType", message.getMessageType().getValue());
                        messageMap.put("text", message.getText());
                        messageMap.put("time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                        return jackson.writeValueAsString(messageMap);
                    } catch (Exception e) {
                        throw new RuntimeException("序列化失败", e);
                    }
                })
                .toList();
    }
}
