package com.study.springai.contant;

import java.util.Arrays;
import java.util.Optional;

public enum FileClass {

    JSON("json", "com.study.springai.document.reader.imp.JsonDocumentReader"),
    MD("md", "com.study.springai.document.reader.imp.MarkdownReader"),
    PDF("pdf", "com.study.springai.document.reader.imp.PdfDocumentReader"),
    TXT("txt", "com.study.springai.document.reader.imp.TextDocumentReader");

    private final String code;
    private final String cla;


    FileClass(String code, String cla) {
        this.code = code;
        this.cla = cla;
    }

    // 根据 code 查找对应的枚举
    public static String fromCode(String code) {
        for (FileClass status : FileClass.values()) {
            if (status.code.equalsIgnoreCase(code)) {
                return status.getCla();
            }
        }
        throw new IllegalArgumentException("Invalid status code: " + code);
    }

    // 安全版本：返回 Optional，避免异常
    public static Optional<FileClass> fromCodeSafe(String code) {
        if (code == null) return Optional.empty();
        return Arrays.stream(values())
                .filter(status -> status.code.equalsIgnoreCase(code.trim()))
                .findFirst();
    }


    public String getCla() {
        return cla;
    }
}
