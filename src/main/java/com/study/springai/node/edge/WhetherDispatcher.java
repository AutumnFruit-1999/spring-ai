package com.study.springai.node.edge;

import com.alibaba.cloud.ai.graph.OverAllState;
import com.alibaba.cloud.ai.graph.action.EdgeAction;

public class WhetherDispatcher implements EdgeAction {
    @Override
    public String apply(OverAllState state) throws Exception {
        System.out.println("state = " + state);
        return String.valueOf(state.value("RagReadNodeNext",false));
    }
}
