package com.study.springai.node.edge;

import com.alibaba.cloud.ai.graph.OverAllState;
import com.alibaba.cloud.ai.graph.action.EdgeAction;

public class HumanFeedbackChatModelDispatcher implements EdgeAction {
    @Override
    public String apply(OverAllState state) throws Exception {
        System.out.println("HumanFeedbackChatModelDispatcher  start .........");
        return state.value("humanChooseChat","qwen");
    }
}
