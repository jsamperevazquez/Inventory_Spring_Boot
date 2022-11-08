package com.company.inventory.util;

import com.company.inventory.model.Product;
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

public class ProductExcelExport {

    private final XSSFWorkbook WORKBOOK;
    private XSSFSheet sheet;
    private final List<Product> PRODUCT_LIST;

    public ProductExcelExport(List<Product> productList) {
        this.PRODUCT_LIST = productList;
        this.WORKBOOK = new XSSFWorkbook();
    }

    public void writeHeaderLine(){
        sheet = WORKBOOK.createSheet("Result");
        Row row = sheet.createRow(0);
        CellStyle cellStyle = WORKBOOK.createCellStyle();
        XSSFFont font = WORKBOOK.createFont();

        font.setFontHeight(16);
        font.setBold(true);
        cellStyle.setFont(font);

        createCell(row,0,"ID",cellStyle);
        createCell(row,1,"NAME",cellStyle);
        createCell(row,2,"PRICE",cellStyle);
        createCell(row,3,"AMOUNT",cellStyle);
        createCell(row,4,"CATEGORY",cellStyle);
    }

    private void createCell(Row row, int column, Object value, CellStyle cellStyle){
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

    private void writeData(){
        int rowCount = 1;

        CellStyle style = WORKBOOK.createCellStyle();
        XSSFFont font = WORKBOOK.createFont();

        font.setFontHeight(14);
        style.setFont(font);

        for (Product product: PRODUCT_LIST
             ) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row,columnCount++,String.valueOf(product.getId()),style);
            createCell(row,columnCount++,product.getName(),style);
            createCell(row,columnCount++,String.valueOf(product.getPrice()),style);
            createCell(row,columnCount++,String.valueOf(product.getAmount()),style);
            createCell(row,columnCount,String.valueOf(product.getCategory().getName()),style);

        }
    }
    public void export(HttpServletResponse response) throws IOException {
        //Write header and data
        writeHeaderLine();
        writeData();


        ServletOutputStream outputStream = response.getOutputStream();
        WORKBOOK.write(outputStream);

        WORKBOOK.close();
        outputStream.close();
    }
}
