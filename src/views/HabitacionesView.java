package views;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import controllers.HabitacionController;
import models.Habitacion;

public class HabitacionesView {

    private static final Scanner scanner = new Scanner(System.in);
    private static HabitacionController habitacionController = new HabitacionController();

    public static void mostrarMenuReservas() throws SQLException {
        while (true) {

            System.out.println("===========================================");
            System.out.println("           GESTIÓN DE HABITACIONES             ");
            System.out.println("===========================================");
            System.out.println();
            System.out.println("1. Crear Cliente");
            System.out.println("2. Ver Clientes");
            System.out.println("3. Actualizar Cliente");
            System.out.println("4. Eliminar Cliente");
            System.out.println("5. Volver al Menú Principal");
            System.out.println();
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();

            switch (opcion) {
                case 5:
                    return;
                default:
                    System.out.println("Opción no válida, intenta de nuevo.");
            }
        }
    }

    public static Habitacion obtenerSeleccionHabitacionLibre(Date fechaInicio, Date fechaFin, Integer cantidadHuespedes)
            throws SQLException {

        List<Habitacion> habitacionesLibres = habitacionController.obtenerHabitacionesLibres(fechaInicio,
                fechaFin, cantidadHuespedes);

        for (Habitacion habitacion : habitacionesLibres) {
            System.out.println(habitacion);
        }

        System.out.print("Ingrese el id de la habitacion: \n");
        int habitacionId = scanner.nextInt();

        for (Habitacion habitacion : habitacionesLibres) {
            if (habitacion.getId().equals(habitacionId)) {
                return habitacion;
            }
        }

        System.out.print("Id de la habitacion no valido, Intentar nuevamente: \n");
        return obtenerSeleccionHabitacionLibre(fechaInicio, fechaFin, cantidadHuespedes);
    }

}
