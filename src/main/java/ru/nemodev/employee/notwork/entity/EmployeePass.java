package ru.nemodev.employee.notwork.entity;

import java.util.Calendar;

public class EmployeePass
{
    public enum Direction
    {
        IN,
        OUT;

        public static Direction fromString(String value)
        {
            if ("In".equals(value))
                return IN;

            if ("Out".equals(value))
                return OUT;

            throw new IllegalArgumentException("Не известный тип Direction - " + value);
        }
    }

    public String id;
    public String fio;
    public Calendar date;
    public Direction direction;
    public String object;
}
