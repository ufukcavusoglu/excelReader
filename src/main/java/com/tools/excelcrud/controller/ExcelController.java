package com.tools.excelcrud.controller;

import com.tools.excelcrud.ExcelWorkBook;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController(value = "/")
public class ExcelController {

    @SneakyThrows
    @RequestMapping(value = "read/workbook", method = RequestMethod.GET)
    public <T> List<List<List<Map<String, String>>>> getExcel(String path) {
        return new ExcelWorkBook(path, null).read();
    }

    @SneakyThrows
    @RequestMapping(value = "read/sheet", method = RequestMethod.GET)
    public <T> List<List<List<Map<String, String>>>> getExcel(String sheet, String path) {
        return new ExcelWorkBook(path,sheet).read();
    }
}
