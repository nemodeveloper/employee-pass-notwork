package ru.nemodev.employee.notwork.parser;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public abstract class ListExcelParser<T>
{
    private final Sheet sheet;
    private List<T> data;
    protected final String fileName;

    public ListExcelParser(String fileName)
    {
        this.fileName = fileName;
        this.sheet = getSheet();
    }

    public List<T> parse()
    {
        if (data == null)
        {
            data = parseSheet();
        }

        return data;
    }

    public Sheet getParsedSheet()
    {
        return sheet;
    }

    public String getFileName()
    {
        return fileName;
    }

    private Sheet getSheet()
    {
        try
        {
            Workbook workbook = WorkbookFactory.create(new File(fileName));
            Sheet sheet = workbook.getSheetAt(0);
            workbook.close();

            return sheet;
        }
        catch (FileNotFoundException e)
        {
            System.out.println(String.format("Не удалось открыть файл %s !", fileName));
            throw new RuntimeException(e);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    protected List<T> parseSheet()
    {
        List<T> employeePassList = new ArrayList<>();
        int index = 0;
        int firstDataRowIndex = getFirstDataRowIndex();
        for (Row cells : getParsedSheet())
        {
            // пропускаем заголовки
            if (index >= firstDataRowIndex)
            {
                employeePassList.add(parseRow(cells));
            }
            ++index;
        }

        return employeePassList;
    }

    protected int getFirstDataRowIndex()
    {
        return 0;
    }

    protected abstract T parseRow(Row row);
}
