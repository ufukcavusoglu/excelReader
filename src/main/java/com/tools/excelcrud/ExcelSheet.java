package com.tools.excelcrud;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.IntStream.range;
import static java.util.stream.IntStream.rangeClosed;

@Repository
public class ExcelSheet {

    Sheet sheet;
    Path path;

    @Autowired
    public ExcelSheet(Sheet sheet, Path path) {
        this.sheet = sheet;
        this.path = path;
    }

    public List<Map<String, String>> reader() {
        return rangeClosed(1, sheet.getLastRowNum())
                .parallel()
                .mapToObj(sheet::getRow)
                .map(this::readRow)
                .collect(Collectors.toList());
    }

    Map<String, String> readRow(Row row) {
        Map<String,String> map = new HashMap<>();
        range(row.getFirstCellNum(), row.getLastCellNum())
                .parallel()
                .mapToObj(row::getCell)
                .forEach(cell -> addToMap(map, cell));
        return map;
    }

    private void addToMap(Map<String,String> map, Cell b) {
        map.put(getColumnNameOf(b), stringMapper(b));
    }

    String stringMapper(Cell cell) {
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            case _NONE, BLANK, ERROR -> "";
            case NUMERIC -> String.valueOf(cell.getNumericCellValue());
            case FORMULA -> cell.getCellFormula();
        };
    }

    String getColumnNameOf(Cell cell) {
        return sheet.getRow(0)
                .getCell(cell.getColumnIndex())
                .getStringCellValue();
    }

}

