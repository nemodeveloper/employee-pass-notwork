package ru.nemodev.employee.notwork.processor;

import ru.nemodev.employee.notwork.FileUtils;
import ru.nemodev.employee.notwork.entity.Employee;
import ru.nemodev.employee.notwork.entity.EmployeePass;
import ru.nemodev.employee.notwork.formatter.EmployeeNotWorkFormatter;
import ru.nemodev.employee.notwork.parser.EmployeeListExcelParser;
import ru.nemodev.employee.notwork.parser.EmployeePassWorkTimeExcelParser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


public class EmployeeNotWorkProcessor
{
    private final EmployeeListExcelParser employeeListExcelParser;
    private final EmployeePassWorkTimeExcelParser employeePassWorkTimeExcelParser;

    private EmployeeNotWorkFormatter employeeNotWorkFormatter;
    private List<Employee> notWorkEmployeeList;

    private final SimpleDateFormat fileDateFormat;
    private final SimpleDateFormat fileDateParser;

    public EmployeeNotWorkProcessor(EmployeeListExcelParser employeeListExcelParser,
                                    EmployeePassWorkTimeExcelParser employeePassWorkTimeExcelParser)
    {
        this.employeeListExcelParser = employeeListExcelParser;
        this.employeePassWorkTimeExcelParser = employeePassWorkTimeExcelParser;

        this.fileDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.forLanguageTag("ru"));
        this.fileDateParser = new SimpleDateFormat("yyyy-MM-dd");
    }

    public void process()
    {
        if (employeeNotWorkFormatter != null)
            return;

        try
        {
            employeeNotWorkFormatter = new EmployeeNotWorkFormatter(getNotWorkEmployee(), buildFileName());
            employeeNotWorkFormatter.format();
        }
        catch (Exception e)
        {
            System.out.println("Произошла ошибка создания отчета! " + e.getMessage());
            e.printStackTrace();
        }
    }

    private List<Employee> getNotWorkEmployee() {
        if (notWorkEmployeeList == null) {
            notWorkEmployeeList = new ArrayList<>();
            Map<String, List<EmployeePass>> employeePassFioMap = getEmployeePassFioMap();
            employeeListExcelParser.parse().forEach(
                    employee -> {
                        String employeeFio = employee.fio.toLowerCase();
                        boolean isWorkEmployee = false;
                        for (String employeePassFio : employeePassFioMap.keySet()) {
                            if (isSameEmployee(employeeFio, employeePassFio)) {
                                isWorkEmployee = true;
                                break;
                            }
                        }

                        if (!isWorkEmployee)
                            notWorkEmployeeList.add(employee);
                    }
            );
            notWorkEmployeeList.sort(Comparator.comparing(o -> o.fio));
        }
        return notWorkEmployeeList;
    }

    private boolean isSameEmployee(String employeeFio, String employeePassFio) {
        if (employeePassFio.contains(employeeFio)
                || employeeFio.contains(employeePassFio))
            return true;

        return employeePassFio.contains(employeeFio.replace(".", ""));
    }

    private Map<String, List<EmployeePass>> getEmployeePassFioMap()
    {
        return employeePassWorkTimeExcelParser.parse().stream()
                .collect(Collectors.groupingBy(employeePass -> employeePass.fio.toLowerCase(), Collectors.toList()));
    }

    private String buildFileName()
    {
        try
        {
            String[] dateToken =  employeePassWorkTimeExcelParser.getParsedSheet().getRow(2).getCell(5).getStringCellValue().split(" ");
            return FileUtils.getBasePath() + String.format("Отсутствующие сотрудники %s.xls",
                    fileDateFormat.format(fileDateParser.parse(dateToken[0]).getTime()));
        }
        catch (ParseException e)
        {
            throw new RuntimeException(e);
        }
    }
}
