package ru.nemodev.employee.notwork;

import java.io.File;

public class FileUtils
{
    public static String getBasePath()
    {
        try
        {
            File jarFile = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
            return jarFile.getParent() + File.separator;
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
}
