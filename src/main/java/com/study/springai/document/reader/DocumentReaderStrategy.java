package com.study.springai.document.reader;

import com.study.springai.utils.FileUtil;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.markdown.config.MarkdownDocumentReaderConfig;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.core.io.Resource;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public abstract class DocumentReaderStrategy {


    protected String[] jsonKeysToUse = new String[]{};
    protected Map<String, Object> meta = Collections.emptyMap();
    protected MarkdownDocumentReaderConfig markdownConfig = MarkdownDocumentReaderConfig.builder().build();
    protected PdfDocumentReaderConfig pdfDocumentReaderConfig = PdfDocumentReaderConfig.builder().build();
    protected boolean isPdfParagraph = false;


    public abstract List<Document> content(Resource resource);

    public static DocumentReaderStrategy getInstance(Resource resource) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Object newInstance = Class.forName(FileUtil.type(Objects.requireNonNull(resource.getFilename()))).getConstructor().newInstance();
        if (newInstance instanceof DocumentReaderStrategy) {
            return (DocumentReaderStrategy) newInstance;
        }
        throw new RuntimeException("config is error, no class:" + newInstance);
    }

    public void setJsonKeysToUse(String... jsonKeysToUse) {
        this.jsonKeysToUse = jsonKeysToUse;
    }

    public void setMate(Map<String, Object> meta) {
        this.meta = meta;
    }

    public void setMarkdownConfig(MarkdownDocumentReaderConfig markdownConfig) {
        this.markdownConfig = markdownConfig;
    }

    public void setPdfDocumentReaderConfig(PdfDocumentReaderConfig pdfDocumentReaderConfig) {
        this.pdfDocumentReaderConfig = pdfDocumentReaderConfig;
    }

    public void setIsPdfParagraph(boolean isPdfParagraph) {
        this.isPdfParagraph = isPdfParagraph;
    }
}
