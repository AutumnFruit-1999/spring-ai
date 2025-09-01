package com.study.springai.document.reader.imp;

import com.study.springai.document.reader.DocumentReaderStrategy;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.JsonReader;
import org.springframework.core.io.Resource;

import java.util.List;

public class JsonDocumentReader extends DocumentReaderStrategy {


    @Override
    public List<Document> content(Resource resource) {
        return new JsonReader(resource, jsonKeysToUse).get();
    }
}
