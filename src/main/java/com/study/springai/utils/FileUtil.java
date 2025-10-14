package com.study.springai.utils;

import com.study.springai.contant.FileClass;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

public class FileUtil {


    public static List<String> FileContent(String file) {
        return FileContent(file, "ChatMemory/");
    }


    public static List<String> FileContent(String file, String filePath) {

        ClassPathResource resource = new ClassPathResource(filePath);
        try {
            if (!resource.exists()) {
                throw new RuntimeException("resource not found");
            }

            File fileTmp = new File(resource.getFile(), file);

            if (!fileTmp.exists() && !fileTmp.isFile()) {
                new File(resource.getFile(), file).createNewFile();
            }
            return Files.readAllLines(fileTmp.toPath(), StandardCharsets.UTF_8);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void writeLinesToFile(String fileName, List<String> lines) {
        writeLinesToFile(fileName, lines, "ChatMemory/");
    }

    public static void writeLinesToFile(String fileName, List<String> lines, String filePath) {
        CompletableFuture.runAsync(() -> {
            ClassPathResource resource = new ClassPathResource(filePath);
            try {
                if (!resource.exists()) {
                    throw new RuntimeException("ChatMemory directory not found");
                }

                File file = new File(resource.getFile(), fileName);

                Files.write(file.toPath(), lines, StandardCharsets.UTF_8,
                        StandardOpenOption.CREATE, StandardOpenOption.APPEND);

            } catch (IOException e) {
                throw new RuntimeException("Failed to write lines to file: " + fileName, e);
            }
        }, Executors.newVirtualThreadPerTaskExecutor());
    }

    public static void deleteFile(String fileName) {
        deleteFile(fileName, "ChatMemory/");
    }

    public static void deleteFile(String fileName, String filePath) {
        ClassPathResource resource = new ClassPathResource(filePath);
        if (!resource.exists()) {
            throw new RuntimeException("ChatMemory directory not found");
        }
        // 获取目录
        File directory = null;
        try {
            directory = resource.getFile();

            // 构建要删除的文件路径
            File fileToDelete = new File(directory, fileName);

            // 检查文件是否存在
            if (!fileToDelete.exists()) {
                throw new RuntimeException("File not found: " + fileName);
            }

            // 删除文件
            if (!fileToDelete.delete()) {
                throw new IOException("Failed to delete file: " + fileName);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static String type(String fileName) {
        return FileClass.fromCode(fileName.split("\\.", 2)[1]);
    }
}
