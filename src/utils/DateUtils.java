package utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class DateUtils {

    private static final Scanner scanner = new Scanner(System.in);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static LocalDate pedirFecha(String mensaje, LocalDate fechaMinima) {
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

}