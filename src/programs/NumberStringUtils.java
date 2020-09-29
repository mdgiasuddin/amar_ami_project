package programs;

import java.util.regex.Pattern;

public class NumberStringUtils {

    private static Pattern PATTERN = Pattern.compile( "^(-?0|-?[1-9]\\d*)(\\.\\d+)?(E\\d+)?$" );

    public static boolean isNumeric( String value )
    {
        return value != null && PATTERN.matcher( value ).matches();
    }
}
