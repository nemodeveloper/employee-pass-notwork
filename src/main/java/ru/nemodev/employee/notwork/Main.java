package ru.nemodev.employee.notwork;

import ru.nemodev.employee.notwork.parser.EmployeeListExcelParser;
import ru.nemodev.employee.notwork.parser.EmployeePassWorkTimeExcelParser;
import ru.nemodev.employee.notwork.processor.EmployeeNotWorkProcessor;

public class Main
{
    public static void main(String[] args)
    {
        new EmployeeNotWorkProcessor(
                new EmployeeListExcelParser(getFileName(EmployeeListExcelParser.BASE_FILE_NAME)),
                new EmployeePassWorkTimeExcelParser(getFileName(EmployeePassWorkTimeExcelParser.BASE_FILE_NAME)))
            .process();
    }

    private static String getFileName(String fileName)
    {
        return FileUtils.getBasePath() + fileName;
    }

}
