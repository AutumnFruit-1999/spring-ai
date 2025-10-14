package com.study.springai.config;

import com.alibaba.cloud.ai.graph.GraphRepresentation;
import com.alibaba.cloud.ai.graph.KeyStrategy;
import com.alibaba.cloud.ai.graph.KeyStrategyFactory;
import com.alibaba.cloud.ai.graph.StateGraph;
import com.alibaba.cloud.ai.graph.action.AsyncEdgeAction;
import com.alibaba.cloud.ai.graph.state.strategy.ReplaceStrategy;
import com.study.springai.node.DeepSeekChatNode;
import com.study.springai.node.HumanFeedbackNode;
import com.study.springai.node.QwenChatNode;
import com.study.springai.node.RagSearchNode;
import com.study.springai.node.edge.HumanFeedbackChatModelDispatcher;
import com.study.springai.node.edge.WhetherDispatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.deepseek.DeepSeekChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

import static com.alibaba.cloud.ai.graph.action.AsyncNodeAction.node_async;

@Configuration
public class RagGraphConfig {

    private static final Logger log = LoggerFactory.getLogger(RagGraphConfig.class);
    @Autowired
    public VectorStore simpleVectorStore;
    @Autowired
    private DeepSeekChatModel deepSeekChatModel;
    @Autowired
    private OpenAiChatModel openAiChatModel;

    @Bean
    public StateGraph humanGraph() {
        StateGraph stateGraph = new StateGraph();
        try {
            KeyStrategyFactory keyStrategyFactory = () -> {
                HashMap<String, KeyStrategy> keyStrategyHashMap = new HashMap<>();
                // 用户输入
                keyStrategyHashMap.put("query", new ReplaceStrategy());
                keyStrategyHashMap.put("threadid", new ReplaceStrategy());

                //rag返回结果
                keyStrategyHashMap.put("RagReadNodeNext",new ReplaceStrategy());
                keyStrategyHashMap.put("RagReadNodeResult", new ReplaceStrategy());

                //
                keyStrategyHashMap.put("humanChooseChat", new ReplaceStrategy());


                return keyStrategyHashMap;
            };
            stateGraph = new StateGraph(keyStrategyFactory)
                    .addNode("RagSearch", node_async(new RagSearchNode(simpleVectorStore, deepSeekChatModel)))
                    .addNode("HumanFeedbackNode", node_async(new HumanFeedbackNode()))
                    .addNode("DeepSeekChat", node_async(new DeepSeekChatNode(ChatClient.builder(deepSeekChatModel).build())))
                    .addNode("QwenChat", node_async(new QwenChatNode(ChatClient.builder(openAiChatModel).build())));

            stateGraph.addEdge(StateGraph.START, "RagSearch")
                    .addConditionalEdges("RagSearch", AsyncEdgeAction.edge_async((new WhetherDispatcher())), Map.of(
                            "true", "HumanFeedbackNode", "false", StateGraph.END
                    ))
                    .addConditionalEdges("HumanFeedbackNode", AsyncEdgeAction.edge_async((new HumanFeedbackChatModelDispatcher())), Map.of(
                            "deepseek", "DeepSeekChat", "qwen", "QwenChat"
                    ))
                    .addEdge("DeepSeekChat", StateGraph.END)
                    .addEdge("QwenChat", StateGraph.END);
        } catch (Exception e) {
            log.error("error");
        } finally {
            System.out.println("stateGraph.getGraph(GraphRepresentation.Type.PLANTUML, \"Observability Demo\") = " + stateGraph.getGraph(GraphRepresentation.Type.PLANTUML, "Observability Demo"));
        }
        return stateGraph;
    }
}
