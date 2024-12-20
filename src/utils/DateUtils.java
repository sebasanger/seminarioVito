package utils;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.Scanner;

//para trabajar con las fechas
public class DateUtils {

    private static final Scanner scanner = new Scanner(System.in);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // solicita al usuario una fecha en formato dd/MM/yyyy y la valida
    public static LocalDate pedirFecha(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje + " (formato: dd/MM/yyyy): ");
                String input = scanner.nextLine();
                return LocalDate.parse(input, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Formato de fecha incorrecto. Por favor, intenta de nuevo.");
            }
        }
    }

    // Solicita al usuario una fecha en formato dd/MM/yyyy y valida que no sea
    // anterior a una fecha especificada
    public static LocalDate pedirFechaConMinimo(String mensaje, LocalDate fechaMinima) {
        while (true) {
            try {
                System.out.print(mensaje + " (formato: dd/MM/yyyy): ");
                String input = scanner.nextLine();
                LocalDate fecha = LocalDate.parse(input, formatter);

                if (fecha.isBefore(fechaMinima)) {
                    System.out.println("La fecha no puede ser anterior a " + fechaMinima.format(formatter)
                            + " intentar de nuevo.");
                } else {
                    return fecha;
                }
            } catch (DateTimeParseException e) {
                System.out.println("Formato de fecha incorrecto. Por favor, intenta de nuevo.");
            }
        }
    }

    // pasa de util a sql tipo de fecha
    public static Date transformDateUtilToSql(java.util.Date fecha) {
        if (fecha == null) {
            return null;
        }
        Date date = new Date(fecha.getTime());

        return date;
    }

    // Calcula la diferencia en días entre dos fechas
    public static long getDiffInDays(java.util.Date fechaInicio, java.util.Date fechaFin) {

        LocalDate inicio = DateUtils.convertirADate(fechaInicio);
        LocalDate fin = DateUtils.convertirADate(fechaFin);

        return ChronoUnit.DAYS.between(inicio, fin);
    }

    // convierte a LocalDate siendo sql o util la fecha inicial
    public static LocalDate convertirADate(Object fecha) {
        Objects.requireNonNull(fecha, "La fecha no puede ser nula");

        if (fecha instanceof java.sql.Date) {
            return ((java.sql.Date) fecha).toLocalDate();
        } else if (fecha instanceof java.util.Date) {
            return ((java.util.Date) fecha).toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
        } else {
            throw new IllegalArgumentException(
                    "El tipo de fecha no es compatible. Se esperaba java.util.Date o java.sql.Date");
        }
    }

    // evalua si son dos fechas iguales
    public static boolean mismaFecha(java.util.Date fecha1, java.util.Date fecha2) {
        LocalDate localDate1 = convertirADate(fecha1);
        LocalDate localDate2 = convertirADate(fecha2);

        return localDate1.equals(localDate2);
    }

}