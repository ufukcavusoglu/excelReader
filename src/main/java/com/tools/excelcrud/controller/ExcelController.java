package com.tools.excelcrud.controller;

import com.tools.excelcrud.ExcelWorkBook;
import lombok.SneakyThrows;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

@RestController(value = "/")
public class ExcelController {

    @RequestMapping(value = "read", method = RequestMethod.GET)
    public String getExcel() throws URISyntaxException {
        return new URIBuilder().addParameter("path", "/home/ufuk/Downloads/SampleData.xlsx").setPort(8080).setHost("localhost").setPath("read/workbook").build().toString();
    }

    @SneakyThrows
    @RequestMapping(value = "read/workbook", method = RequestMethod.GET)
    public List<List<Map<String, String>>> getExcel(@RequestParam String path) {
        return new ExcelWorkBook(path, null).read();
    }

    @SneakyThrows
    @RequestMapping(value = "read/sheet", method = RequestMethod.GET)
    public <T> List<List<Map<String, String>>> getExcel(String sheet, String path) {
        return new ExcelWorkBook(path, sheet).read();
    }
}
