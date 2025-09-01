package com.study.springai.controller;

import com.study.springai.document.reader.DocumentReaderStrategy;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.ExtractedTextFormatter;
import org.springframework.ai.reader.markdown.config.MarkdownDocumentReaderConfig;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

@RestController
public class ReaderTestController {
    @Autowired
    public VectorStore vectorStore;

    @GetMapping(value = "/readerMarkdown")
    public List<Document> readerMarkdown() throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        Resource resource = new ClassPathResource("test.md");
        DocumentReaderStrategy instance = DocumentReaderStrategy.getInstance(resource);
        MarkdownDocumentReaderConfig config = MarkdownDocumentReaderConfig.builder()
                .withHorizontalRuleCreateDocument(true)
                .withIncludeCodeBlock(false)
                .withIncludeBlockquote(false)
                .withAdditionalMetadata("filename", "code.md")
                .build();
        instance.setMarkdownConfig(config);
        return instance.content(resource);
    }

    @GetMapping(value = "/readerPdfPage")
    public List<Document> readerPdfPage() throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        Resource resource = new ClassPathResource("test.md");
        DocumentReaderStrategy instance = DocumentReaderStrategy.getInstance(resource);
        PdfDocumentReaderConfig config = PdfDocumentReaderConfig.builder()
                .withPageTopMargin(0)
                .withPageExtractedTextFormatter(ExtractedTextFormatter.builder()
                        .withNumberOfTopTextLinesToDelete(0)
                        .build())
                .withPagesPerDocument(1)
                .build();
        instance.setPdfDocumentReaderConfig(config);
        return instance.content(resource);
    }


    @GetMapping(value = "/vector")
    public List<Document> embedding() throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
//        // 构建测试数据
        List<Document> documents =
                List.of(new Document("I like Spring Boot"),
                        new Document("I love Java"));
        // 添加到向量数据库
        vectorStore.add(documents);
        return vectorStore.similaritySearch("java");
    }
}
