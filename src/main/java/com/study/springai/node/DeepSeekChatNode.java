package com.study.springai.node;

import com.alibaba.cloud.ai.graph.OverAllState;
import com.alibaba.cloud.ai.graph.action.NodeAction;
import org.springframework.ai.chat.client.ChatClient;

import java.util.Map;

public class DeepSeekChatNode implements NodeAction {

    private final ChatClient deepSeekClient;

    public DeepSeekChatNode(ChatClient deepSeekClient) {
        this.deepSeekClient = deepSeekClient;
    }

    @Override
    public Map<String, Object> apply(OverAllState state) throws Exception {
        System.out.println("DeepSeekChatNode start......");
        String call = deepSeekClient.prompt().user(state.value("query", "token a joke")).call().content();
        return Map.of("RagReadNodeResult",call);
    }
}
