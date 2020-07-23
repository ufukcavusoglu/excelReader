package com.tools.excelcrud;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.IntStream.rangeClosed;

public class ExcelSheet {

    Sheet sheet;
    Path path;

    public ExcelSheet(Sheet sheet, Path path) {
        this.sheet = sheet;
        this.path = Paths.get("/home/ufuk/Downloads/SampleData.xlsx");
    }

    public List<List<Map<String, String>>> reader() {
        return rangeClosed(1, sheet.getLastRowNum())
                .parallel()
                .mapToObj(sheet::getRow)
                .map(this::readRow)
                .collect(Collectors.toList());
    }

    List<Map<String, String>> readRow(Row row) {
        return rangeClosed(row.getFirstCellNum(), row.getLastCellNum())
                .mapToObj(row::getCell)
                .parallel()
                .map(cell -> Map.of(getColumnNameOf(cell), cell.getStringCellValue()))
                .collect(Collectors.toList());
    }

    String getColumnNameOf(Cell cell) {
        return sheet.getRow(0)
                .getCell(cell.getColumnIndex())
                .getStringCellValue();
    }

}

