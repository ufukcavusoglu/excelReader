package com.tools.excelcrud;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Repository
public class ExcelWorkBook {

    Path path;
    String sheetName;
    Sheet sheet;

    @Autowired
    public ExcelWorkBook(String path, String sheet) {
        this.path = Paths.get(path);
        this.sheetName = sheet;
    }

    public List<List<Map<String, String>>> read() throws IOException {
        FileInputStream inputStream = new FileInputStream(path.toFile());
        Workbook workbook = new XSSFWorkbook(inputStream);
        this.sheet = workbook.getSheet(sheetName);
        List<List<Map<String, String>>> result = readSheets(workbook);
        workbook.close();
        inputStream.close();
        return result;
    }

    private List<List<Map<String, String>>> readSheets(Workbook workbook) {
        return IntStream.range(0, workbook.getNumberOfSheets())
                .parallel()
                .mapToObj(workbook::getSheetAt)
                .filter(sheet1 -> Objects.isNull(sheet) || sheet1.equals(sheet))
                .map(sheet -> new ExcelSheet(sheet, path).reader())
                .collect(Collectors.toList());
    }

}
