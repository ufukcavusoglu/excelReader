package com.tools.excelcrud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.tools.excelcrud.controller")
public class ExcelReaderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExcelReaderApplication.class, args);
    }

}
