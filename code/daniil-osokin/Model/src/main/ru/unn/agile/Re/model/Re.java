package ru.unn.agile.Re.model;

import java.lang.RuntimeException;
import java.lang.String;

public class Re
{
    public static Regex compile(String pattern)
    {
        checkForSingleRegex(pattern);
        return RegexFactory.create(pattern);
    }

    public static int search(String pattern, String text)
    {
        return compile(pattern).search(text);
    }

    private static void checkForSingleRegex(String pattern)
    {
        int regexCounter = 0;
        for (String descriptor: RegexFactory.descriptors)
        {
            String copyPattern = pattern;
            while (copyPattern.length() > 0)
            {
                if (copyPattern.indexOf(descriptor) != Regex.NOT_FOUND_INDEX)
                {
                    copyPattern = copyPattern.substring(copyPattern.indexOf(descriptor) + 1, copyPattern.length());
                    regexCounter++;
                }
                else
                {
                    break;
                }
            }
        }

        if (regexCounter > 1)
        {
            throw new RuntimeException(ReError.MULTIPLE_REGEX);
        }
    }
}
