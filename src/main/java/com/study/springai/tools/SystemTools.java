package com.study.springai.tools;

import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.definition.ToolDefinition;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class SystemTools {

    private final ToolCallbackProvider toolCallbackProvider;

    public SystemTools(ToolCallbackProvider toolCallbackProvider) {
        this.toolCallbackProvider = toolCallbackProvider;
    }

    @Tool(description = "Get what tools are in the system")
    public List<ToolDefinition> getSystemTools() {
        return Arrays.stream(toolCallbackProvider.getToolCallbacks()).map(ToolCallback::getToolDefinition).toList();
    }
}
