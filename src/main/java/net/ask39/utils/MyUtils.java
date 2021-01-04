package net.ask39.utils;

import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author zhangzheng
 * @date 2021-01-03
 **/
public class MyUtils {
    private static final DateTimeFormatter LOCAL_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static String ldt2Str(LocalDateTime localDateTime){
        if(localDateTime == null){
            return null;
        }
        return LOCAL_DATE_TIME_FORMATTER.format(localDateTime);
    }

    public static Date ldt2Date(LocalDateTime localDateTime){
        if(localDateTime == null){
            return null;
        }
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDateTime date2Ldt(Date date){
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    public static LocalDate str2Ld(String str){
        if(StringUtils.isEmpty(str)){
            return null;
        }
        return LocalDate.parse(str, DATE_TIME_FORMATTER);
    }
}
