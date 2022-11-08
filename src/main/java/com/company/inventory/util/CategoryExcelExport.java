package com.company.inventory.util;

import com.company.inventory.model.Category;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class CategoryExcelExport {

    private final XSSFWorkbook WORKBOOK;
    private XSSFSheet sheet;
    private final List<Category> CATEGORY_LIST;

    public CategoryExcelExport(List<Category> categoryList) {
        this.CATEGORY_LIST = categoryList;
        this.WORKBOOK = new XSSFWorkbook();
    }

    private void writeHeaderLine() {
        sheet = WORKBOOK.createSheet("Result");
        Row row = sheet.createRow(0);
        CellStyle cellStyle = WORKBOOK.createCellStyle();

        XSSFFont font = WORKBOOK.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        cellStyle.setFont(font);
        createCell(row, 0, "ID", cellStyle);
        createCell(row, 1, "Name", cellStyle);
        createCell(row, 2, "Description", cellStyle);

    }

    private void createCell(Row row, int column, Object value, CellStyle cellStyle) {
        sheet.autoSizeColumn(column);
        Cell cell = row.createCell(column);

        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(cellStyle);
    }

    private void writeData() {
        int rowCount = 1;
        CellStyle style = WORKBOOK.createCellStyle();
        XSSFFont font = WORKBOOK.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (Category result : CATEGORY_LIST) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, String.valueOf(result.getId()), style);
            createCell(row, columnCount++, result.getName(), style);
            createCell(row, columnCount, result.getDescription(), style);

        }
    }

    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine(); //Write the header
        writeData(); // Write the data

        ServletOutputStream servletOutputStream = response.getOutputStream();
        WORKBOOK.write(servletOutputStream);
        WORKBOOK.close();
        servletOutputStream.close();
    }
}


