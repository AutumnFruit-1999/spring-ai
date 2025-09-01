package com.study.springai.document.reader.imp;

import com.study.springai.document.reader.DocumentReaderStrategy;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.core.io.Resource;

import java.util.List;

public class TikaReader extends DocumentReaderStrategy {
    @Override
    public List<Document> content(Resource resource) {
        TikaDocumentReader tikaDocumentReader = new TikaDocumentReader(resource);
        return tikaDocumentReader.read();
    }
}
