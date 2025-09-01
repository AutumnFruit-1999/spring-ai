package com.study.springai.config;

import com.study.springai.contant.Prompt;
import com.study.springai.repository.FileChatMemoryRepository;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
import org.springframework.ai.deepseek.DeepSeekChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfig {

    @Bean
    public ChatClient deepSeekChatClient(DeepSeekChatModel deepSeekChatModel,ToolCallbackProvider toolCallbackProvider) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        return ChatClient.builder(deepSeekChatModel)
                .defaultAdvisors(MessageChatMemoryAdvisor
                        .builder(MessageWindowChatMemory.builder()
                                .maxMessages(10).chatMemoryRepository(new FileChatMemoryRepository())
                                .build()).build())
                .defaultToolCallbacks(toolCallbackProvider)
                .build();
    }
    @Bean
    public ChatClient qwenChatClient(OpenAiChatModel openAiChatModel, JdbcChatMemoryRepository jdbcChatMemoryRepository) {
        return ChatClient.builder(openAiChatModel)
                .defaultAdvisors(MessageChatMemoryAdvisor
                        .builder(MessageWindowChatMemory.builder()
                                .maxMessages(10).chatMemoryRepository(jdbcChatMemoryRepository)
                                .build()).build())
                .build();
    }

    @Bean
    public ChatClient deepSeekLearningPlannerChatClient(DeepSeekChatModel deepSeekChatModel) {
        return ChatClient.builder(deepSeekChatModel)
                .defaultSystem(Prompt.LearningPlanner)
                .defaultAdvisors(MessageChatMemoryAdvisor
                        .builder(MessageWindowChatMemory.builder()
                                .maxMessages(10).chatMemoryRepository(new FileChatMemoryRepository())
                                .build()).build())
                .build();
    }
    @Bean
    public ChatClient deepSeekAuditChatClient(DeepSeekChatModel deepSeekChatModel,JdbcChatMemoryRepository jdbcChatMemoryRepository) {
        return ChatClient.builder(deepSeekChatModel)
                .defaultSystem(Prompt.Audit)
                .defaultAdvisors(MessageChatMemoryAdvisor
                        .builder(MessageWindowChatMemory.builder()
                                .maxMessages(10).chatMemoryRepository(jdbcChatMemoryRepository)
                                .build()).build())
                .build();
    }
    @Bean
    public ChatClient deepSeekArthasTeacherChatClient(DeepSeekChatModel deepSeekChatModel,JdbcChatMemoryRepository jdbcChatMemoryRepository) {
        return ChatClient.builder(deepSeekChatModel)
                .defaultSystem(Prompt.ArthasTeacher)
                .defaultAdvisors(MessageChatMemoryAdvisor
                        .builder(MessageWindowChatMemory.builder()
                                .maxMessages(10).chatMemoryRepository(jdbcChatMemoryRepository)
                                .build()).build())
                .build();
    }
}
