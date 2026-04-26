package otherexecution;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TestFile
{
    public static void main(String[] args) {
//        String basedir = System.getProperty("user.dir");
//        System.out.println(basedir);

        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("ddMMyyyy_hhmmss");
        String formattedDate = dateTimeFormatter.format(dateTime);
        System.out.println("Date : " + date);
        System.out.println("Time : " + time);
        System.out.println("DateTime: " + dateTime);
        System.out.println("Formatted Date: " + formattedDate);

        System.out.println(TestFile.class.getName());

    }
}
