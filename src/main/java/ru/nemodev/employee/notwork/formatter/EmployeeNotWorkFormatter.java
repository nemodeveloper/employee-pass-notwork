package ru.nemodev.employee.notwork.formatter;

import ru.nemodev.employee.notwork.FileUtils;
import ru.nemodev.employee.notwork.entity.Employee;
import ru.nemodev.employee.notwork.entity.EmployeePass;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import ru.nemodev.employee.notwork.parser.EmployeeListExcelParser;
import ru.nemodev.employee.notwork.parser.EmployeePassWorkTimeExcelParser;

import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


public class EmployeeNotWorkFormatter
{
    private final List<Employee> notWorkEmployeeList;
    private final String fileName;

    public EmployeeNotWorkFormatter(List<Employee> notWorkEmployeeList, String fileName)
    {
        this.notWorkEmployeeList = notWorkEmployeeList;
        this.fileName = fileName;
    }

    public void format()
    {
        try
        {
            writeToFile(buildWorkbook(), fileName);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    private Workbook buildWorkbook()
    {
        Workbook book = new HSSFWorkbook();
        Sheet sheet = book.createSheet("Worksheet");

        CellStyle dataCellStyle = book.createCellStyle();
        fillDataCellStyle(dataCellStyle);

        sheet.createRow(0).createCell(0).setCellValue("Отсутствующие сотрудники");
        sheet.getRow(0).getCell(0).setCellStyle(dataCellStyle);

        int index = 1;

        for (Employee employee : notWorkEmployeeList)
        {
            Row row = sheet.createRow(index);
            row.createCell(0).setCellValue(employee.fio);

            for (int i = 0; i < 1; ++i)
            {
                row.getCell(i).setCellStyle(dataCellStyle);
            }

            ++index;
        }

        sheet.setColumnWidth(0, 8000);

        return book;
    }

    private void writeToFile(Workbook workbook, String fileName) throws Exception
    {
        workbook.write(new FileOutputStream(fileName));
        workbook.close();
        System.out.println(String.format("Данные отформатированы и записаны в файл %s!", fileName));
    }

    private void fillDataCellStyle(CellStyle cellStyle)
    {
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);

        cellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
    }

}
