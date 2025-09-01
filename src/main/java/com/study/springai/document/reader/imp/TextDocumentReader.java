package com.study.springai.document.reader.imp;

import com.study.springai.document.reader.DocumentReaderStrategy;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.TextReader;
import org.springframework.core.io.Resource;

import java.util.List;

public class TextDocumentReader extends DocumentReaderStrategy {
    @Override
    public List<Document> content(Resource resource) {
        TextReader textReader = new TextReader(resource);
        textReader.getCustomMetadata().putAll(meta);
        return textReader.get();
    }
}
