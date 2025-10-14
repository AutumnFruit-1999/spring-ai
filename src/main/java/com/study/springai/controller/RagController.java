package com.study.springai.controller;

import com.study.springai.document.reader.DocumentReaderStrategy;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.ExtractedTextFormatter;
import org.springframework.ai.reader.markdown.config.MarkdownDocumentReaderConfig;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class RagController {
    @Autowired
    public VectorStore vectorStore;
    @Autowired
    public VectorStore simpleVectorStore;

    @GetMapping(value = "/readerMarkdown")
    public List<Document> readerMarkdown(String filePath) throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        Resource resource = new FileSystemResource(filePath);
        DocumentReaderStrategy instance = DocumentReaderStrategy.getInstance(resource);
        MarkdownDocumentReaderConfig config = MarkdownDocumentReaderConfig.builder()
                .withHorizontalRuleCreateDocument(true)
                .withIncludeCodeBlock(false)
                .withIncludeBlockquote(false)
                .withAdditionalMetadata("filename", Optional.of(resource.getFilename()).orElse("default.md"))
                .build();
        instance.setMarkdownConfig(config);
        List<Document> content = instance.content(resource);
        List<Document> tmpContent = new ArrayList<>();
        for (int i = 0; i < content.size(); i++) {
            tmpContent.add(content.get(i));
            if (tmpContent.size() == 9) {
                simpleVectorStore.add(tmpContent);
                tmpContent = new ArrayList<>();
            }
        }
        if (!tmpContent.isEmpty()) {
            simpleVectorStore.add(tmpContent);
        }
        SearchRequest request = new SearchRequest();
        simpleVectorStore.similaritySearch(request);
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
}
