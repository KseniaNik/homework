package homework.util;

/**
 * Created on 29.04.2017.
 */
public class Util {

    public static java.sql.Date toSqlDate(java.util.Date date) {
        return new java.sql.Date(date.getTime());
    }

    public static java.sql.Timestamp toSqlTimestamp(java.util.Date date) {
        return new java.sql.Timestamp(date.getTime());
    }

}
