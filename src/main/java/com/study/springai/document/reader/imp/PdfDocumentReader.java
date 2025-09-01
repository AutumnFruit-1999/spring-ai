package com.study.springai.document.reader.imp;

import com.study.springai.document.reader.DocumentReaderStrategy;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.ParagraphPdfDocumentReader;
import org.springframework.core.io.Resource;

import java.util.List;

public class PdfDocumentReader extends DocumentReaderStrategy {


    @Override
    public List<Document> content(Resource resource) {
        return isPdfParagraph ? new PagePdfDocumentReader(resource, pdfDocumentReaderConfig).get() : new ParagraphPdfDocumentReader(resource, pdfDocumentReaderConfig).get();
    }
}
