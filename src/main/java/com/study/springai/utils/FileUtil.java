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
        return FileClass.fromCode(fileName.split("\\.")[1]);
    }


//    public static void main(String[] args) throws IOException {
//        ObjectMapper map = new ObjectMapper();
//
//
//        List<String> strings = FileContent("test_self_mode.txt");
//        List<SelfModel> list = strings.stream()
//                .filter(s -> {
//                    String regex = s.split("\b")[1];
//                    Integer i = Integer.valueOf(regex);
//
//                    return i<1754918537;
//                })
//                .map(s -> {
//                    try {
//
//                        String s1 = s.split("\b")[8];
//                        Map map2 = map.readValue(s1, Map.class);
//                        Map map1 = (Map) map2.get("outer_ext_info");
//                        Object o1 = map2.get("item");
//                        Object o = map1.get("solutionName");
//                        SelfModel selfModel = new SelfModel(String.valueOf(o1), String.valueOf(o), map.readValue(s.split("\b")[9], List.class));
//                        return selfModel;
//                    } catch (JsonProcessingException e) {
//                        throw new RuntimeException(e);
//                    }
//                }).toList();
//
//
//        Map<Object, Map<Object, Map<Object, Object>>> result = new HashMap<>();
//
//        for (int i = 0; i < list.size(); i++) {
//            SelfModel selfModel = list.get(i);
//            List<Object> feature = selfModel.getFeature();
//
//            Map<Object, Map<Object, Object>> resultTmp = new HashMap<>();
//            Map<Object, Object> mapjs = new HashMap<>();
//
//            for (int j = 0; j < feature.size(); j++) {
//                Map mapj = (Map) feature.get(j);
//                mapjs.put(mapj.get("tag_name"), mapj.get("value"));
//
//            }
//            resultTmp.put(selfModel.getAlgorithm(), mapjs);
//            if (result.containsKey(selfModel.getUrl())) {
//                Map<Object, Map<Object, Object>> objectMapMap = result.get(selfModel.getUrl());
//                objectMapMap.put(selfModel.getAlgorithm(), mapjs);
//            } else {
//                result.put(selfModel.getUrl(), resultTmp);
//
//            }
//        }
//        System.out.println("result = " + result);
//
//
////
////        // 文件路径
//
////        String filePath = "output.txt";
////
////        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
////            for (Map.Entry<Object, Map<Object, Map<Object, Object>>> entry : result.entrySet()) {
////                // 构造单个顶层条目：{ "topKey": { ... } }
////                Map<Object, Map<Object, Map<Object, Object>>> singleEntry = new HashMap<>();
////                singleEntry.put(entry.getKey(), entry.getValue());
////
////                // 序列化为 JSON 字符串
////                String jsonLine = map.writeValueAsString(singleEntry);
////
////                // 写入文件并换行
////                writer.write(jsonLine);
////                writer.newLine(); // 换行
////            }
////            System.out.println("JSON 数据已写入文件: " + filePath);
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
//
//        Workbook workbook = new XSSFWorkbook();
//        Sheet sheet = workbook.createSheet("Nested Data");
//
//        // 创建表头样式
//        CellStyle headerStyle = workbook.createCellStyle();
//        Font font = workbook.createFont();
//        font.setBold(true);
//        headerStyle.setFont(font);
//
//        // 创建表头
//        Row headerRow = sheet.createRow(0);
//        String[] headers = {"图片", "算法", "标签", "标签值"};
//        for (int i = 0; i < headers.length; i++) {
//            Cell cell = headerRow.createCell(i);
//            cell.setCellValue(headers[i]);
//            cell.setCellStyle(headerStyle);
//        }
//
//        // 填充数据
//        int rowNum = 1;
//        for (Map.Entry<Object, Map<Object, Map<Object, Object>>> topEntry : result.entrySet()) {
//            Object topKey = topEntry.getKey();
//            Map<Object, Map<Object, Object>> secondLevel = topEntry.getValue();
//
//            for (Map.Entry<Object, Map<Object, Object>> secondEntry : secondLevel.entrySet()) {
//                Object subKey = secondEntry.getKey();
//                Map<Object, Object> thirdLevel = secondEntry.getValue();
//
//                for (Map.Entry<Object, Object> thirdEntry : thirdLevel.entrySet()) {
//                    Object innerKey = thirdEntry.getKey();
//                    Object value = thirdEntry.getValue();
//
//                    Row row = sheet.createRow(rowNum++);
//                    row.createCell(0).setCellValue(topKey.toString());
//                    row.createCell(1).setCellValue(subKey.toString());
//                    row.createCell(2).setCellValue(innerKey.toString());
//                    row.createCell(3).setCellValue(valueToString(value));
//                }
//            }
//        }
//
//        // 自动调整列宽
//        for (int i = 0; i < headers.length; i++) {
//            sheet.autoSizeColumn(i);
//        }
//
//        // 写入文件
//        String excelFilePath = "nested_map_output.xlsx";
//        try (FileOutputStream outputStream = new FileOutputStream(excelFilePath)) {
//            workbook.write(outputStream);
//            System.out.println("Excel 文件已生成: " + excelFilePath);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                workbook.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//
//    }
//    private static String valueToString(Object value) {
//        if (value == null) return "";
//        return value.toString();
//    }

}
