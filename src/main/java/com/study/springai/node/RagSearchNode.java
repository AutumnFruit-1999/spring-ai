package com.study.springai.node;

import com.alibaba.cloud.ai.graph.OverAllState;
import com.alibaba.cloud.ai.graph.action.NodeAction;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.evaluation.RelevancyEvaluator;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.deepseek.DeepSeekChatModel;
import org.springframework.ai.evaluation.EvaluationRequest;
import org.springframework.ai.evaluation.EvaluationResponse;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.vectorstore.VectorStore;

import java.util.Map;

public class RagSearchNode implements NodeAction {

    private static final PromptTemplate DEFAULT_PROMPT_TEMPLATE = new PromptTemplate("\tYour task is to evaluate if the response for the query\n\tis in line with the context information provided.\n\n\tYou have two options to answer. Either YES or NO.\n\n\tAnswer YES, if the response for the query\n\tis in line with context information otherwise NO. If the response is I don't know, etc., meaningless answers also return no\n\n\tQuery:\n\t{query}\n\n\tResponse:\n\t{response}\n\n\tContext:\n\t{context}\n\n\tAnswer:\n");


    public final VectorStore simpleVectorStore;

    private DeepSeekChatModel deepSeekChatModel;

    public RagSearchNode(VectorStore simpleVectorStore, DeepSeekChatModel deepSeekChatModel) {
        this.simpleVectorStore = simpleVectorStore;
        this.deepSeekChatModel = deepSeekChatModel;
    }

    @Override
    public Map<String, Object> apply(OverAllState state) throws Exception {
        boolean over = true;
        String reslut = "";
        int count = 0;
        String message = state.value("query", "");

        while (over && count < 2) {
            RetrievalAugmentationAdvisor ragAdvisor = RetrievalAugmentationAdvisor.builder()
                    .documentRetriever(VectorStoreDocumentRetriever.builder()
                            .vectorStore(simpleVectorStore).topK(state.value("topK", 4))
                            .build())
                    .build();

            ChatResponse chatResponse = ChatClient.builder(deepSeekChatModel).build().prompt(message)
                    .advisors(ragAdvisor)
                    .call().chatResponse();

            reslut = chatResponse.getResult().getOutput().getText();
            EvaluationRequest evaluationRequest = new EvaluationRequest(
                    message,
                    chatResponse.getMetadata().get(RetrievalAugmentationAdvisor.DOCUMENT_CONTEXT),
                    reslut
            );


            RelevancyEvaluator evaluator = RelevancyEvaluator.builder().chatClientBuilder(ChatClient.builder(deepSeekChatModel)).promptTemplate(DEFAULT_PROMPT_TEMPLATE).build();
            EvaluationResponse evaluationResponse = evaluator.evaluate(evaluationRequest);
            over = !evaluationResponse.isPass();
            count++;
        }

        if (over) {
            return Map.of("RagReadNodeResult", "该问题无法在知识库中查询","RagReadNodeNext", over);
        }

        return Map.of("RagReadNodeResult", reslut );
    }
}
