package com.study.springai.controller;

import com.alibaba.cloud.ai.graph.*;
import com.alibaba.cloud.ai.graph.checkpoint.config.SaverConfig;
import com.alibaba.cloud.ai.graph.checkpoint.savers.MemorySaver;
import com.alibaba.cloud.ai.graph.exception.GraphStateException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@ResponseBody
public class GraphController {

    private final CompiledGraph compiledGraph;

    public GraphController(StateGraph graph) throws GraphStateException {

        CompileConfig memory = CompileConfig.builder()
                .saverConfig(SaverConfig.builder()
                        .register("memory", new MemorySaver())
                        .build()).build();
        //设置配置，检查边，构建节点
        this.compiledGraph = graph.compile(memory);
    }

    @GetMapping(value = "/expand", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public OverAllState invoke(@RequestParam String query) throws Exception{

        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("query", query);
        OverAllState state = compiledGraph.invoke(objectMap).orElse(null);
        System.out.println(state);
        return state;
    }

}
