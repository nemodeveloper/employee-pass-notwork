package ru.nemodev.employee.notwork.parser;

import ru.nemodev.employee.notwork.entity.Employee;
import org.apache.poi.ss.usermodel.Row;


public class EmployeeListExcelParser extends ListExcelParser<Employee>
{
    public static final String BASE_FILE_NAME = "employee_list.xls";

    public EmployeeListExcelParser(String fileName)
    {
        super(fileName);
    }

    @Override
    protected int getFirstDataRowIndex()
    {
        return 1;
    }

    protected Employee parseRow(Row row)
    {
        try
        {
            Employee employee = new Employee();
            employee.fio = row.getCell(0).getStringCellValue();
            return employee;
        }
        catch (Exception e)
        {
            throw new RuntimeException(String.format("Ошибка парсинга строки %s файла %s ", row.getRowNum(), fileName) , e);
        }
    }
}
