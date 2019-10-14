package ru.nemodev.employee.notwork.parser;

import org.apache.poi.ss.usermodel.Row;
import ru.nemodev.employee.notwork.entity.EmployeePass;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class EmployeePassWorkTimeExcelParser extends ListExcelParser<EmployeePass>
{
    public static final String BASE_FILE_NAME = "access_zone.xls";

    private final SimpleDateFormat dateParser;
    private List<EmployeePass> employeePassList;

    public EmployeePassWorkTimeExcelParser(String fileName)
    {
        super(fileName);
        this.dateParser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    public List<EmployeePass> parse()
    {
        if (employeePassList == null)
        {
            employeePassList = parseSheet();
        }

        return employeePassList;
    }

    @Override
    protected int getFirstDataRowIndex()
    {
        return 1;
    }

    protected EmployeePass parseRow(Row row)
    {
        try
        {
            EmployeePass employeePass = new EmployeePass();
            employeePass.id = row.getCell(3).getStringCellValue();
            employeePass.fio = row.getCell(4).getStringCellValue();
            employeePass.date = Calendar.getInstance();
            employeePass.date.setTime(dateParser.parse(row.getCell(5).getStringCellValue()));
            employeePass.direction = row.getCell(6).getStringCellValue();
            employeePass.object = row.getCell(1).getStringCellValue();

            return employeePass;
        }
        catch (Exception e)
        {
            throw new RuntimeException(String.format("Ошибка парсинга строки %s файла %s ", row.getRowNum(), fileName), e);
        }
    }

}
