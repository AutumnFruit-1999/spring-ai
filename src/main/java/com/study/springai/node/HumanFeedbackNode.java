package com.study.springai.node;

import com.alibaba.cloud.ai.graph.OverAllState;
import com.alibaba.cloud.ai.graph.action.NodeAction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class HumanFeedbackNode implements NodeAction {
    @Override
    public Map<String, Object> apply(OverAllState state) throws Exception {

        System.out.println("HumanFeedbackNode start....... ");

        List<String> chatList = new ArrayList<>();
        chatList.add("qwen");
        chatList.add("deepseek");
        Random random = new Random();
        int i = random.nextInt(chatList.size());

        return Map.of("humanChooseChat",chatList.get(i));
    }
}
