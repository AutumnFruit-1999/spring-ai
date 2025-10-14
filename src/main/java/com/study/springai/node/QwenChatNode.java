package com.study.springai.node;

import com.alibaba.cloud.ai.graph.OverAllState;
import com.alibaba.cloud.ai.graph.action.NodeAction;
import org.springframework.ai.chat.client.ChatClient;

import java.util.Map;

public class QwenChatNode implements NodeAction {

    private final ChatClient qwenChatClient;

    public QwenChatNode(ChatClient qwenChatClient) {
        this.qwenChatClient = qwenChatClient;
    }

    @Override
    public Map<String, Object> apply(OverAllState state) throws Exception {
        System.out.println("QwenChatNode start......");
        String call = qwenChatClient.prompt().user(state.value("query", "token a joke")).call().content();
        return Map.of("RagReadNodeResult",call);
    }
}
