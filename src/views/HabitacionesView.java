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

    public static void mostrarMenuHabitaciones() throws SQLException {
        while (true) {

            System.out.println("===========================================");
            System.out.println("           GESTIÓN DE HABITACIONES             ");
            System.out.println("===========================================");
            System.out.println();
            System.out.println("1. Crear Habitacion");
            System.out.println("2. Ver Habitaciones");
            System.out.println("3. Actualizar Habitacion");
            System.out.println("4. Eliminar Habitacion");
            System.out.println("5. Volver al Menú Principal");
            System.out.println();
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    crearHabitacion();
                    break;
                case 2:
                    verHabitaciones();
                    break;
                case 3:
                    actualizarHabitacion();
                    break;
                case 4:
                    eliminarHabitacion();
                    break;
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

    public static Habitacion crearHabitacion() throws SQLException {

        Habitacion habitacion = new Habitacion();

        System.out.print("Ingrese el numero de la habitacion :");
        String numeroHabitacion = scanner.next();
        habitacion.setNumeroHabitacion(numeroHabitacion);

        System.out.print("Ingrese el piso :");
        Integer piso = scanner.nextInt();
        habitacion.setPiso(piso);

        System.out.print("Ingrese la cantidad de camas sigles:");
        Integer camasSingles = scanner.nextInt();
        habitacion.setCamasSingles(camasSingles);

        System.out.print("Ingrese la cantidad de camas matrimoñales:");
        Integer camasDobles = scanner.nextInt();
        habitacion.setCamasDobles(camasDobles);

        System.out.print("Ingrese la capacidad de la habitacion:");
        Integer capacidad = scanner.nextInt();
        habitacion.setCapacidad(capacidad);

        habitacion.setHabilitada(true);
        habitacion.setdisponible(true);

        try {
            habitacionController.crear(habitacion);
            System.out.println("Habitacion generada correctamente");
        } catch (SQLException e) {
            System.out.println("Error al generar la habitacion, intentelo nuevamente");
            crearHabitacion();
        }
        return habitacion;

    }

    public static void verHabitaciones() throws SQLException {
        System.out.println("-------------------------------------------");
        System.out.println("HABITACIONES");
        habitacionController.obtenerTodos().forEach(habitacion -> {
            System.out.println("HABITACION ID " + habitacion.getId());
            System.out.println(habitacion);
            System.out.println("======================================");
        });
        System.out.println("HABITACIONES");
        System.out.println("-------------------------------------------");
    }

    private static void eliminarHabitacion() throws SQLException {
        verHabitaciones();
        System.out.print("Ingrese el id de la marca a eliminar: ");
        Integer id = scanner.nextInt();
        Habitacion habitacion = habitacionController.obtenerPorId(id);

        if (habitacion != null) {
            try {
                habitacionController.eliminar(id);
                System.out.println("Habitacion eliminada con éxito.");
            } catch (SQLException e) {
                System.out.println("Error al querer eliminar la habitacion, Intentar nuevamente.");
                eliminarHabitacion();
            }

        } else {
            System.out.println("Habitacion no encontrada, busque nuevamente.");
            eliminarHabitacion();
        }
    }

    private static void actualizarHabitacion() throws SQLException {
        verHabitaciones();
        System.out.print("Ingrese el id de la marca a actualizar: ");
        Integer id = scanner.nextInt();
        Habitacion habitacion = habitacionController.obtenerPorId(id);
        if (habitacion != null) {

            System.out.print("Ingrese el numero de la habitacion :");
            String numeroHabitacion = scanner.next();
            habitacion.setNumeroHabitacion(numeroHabitacion);

            System.out.print("Ingrese el piso :");
            Integer piso = scanner.nextInt();
            habitacion.setPiso(piso);

            System.out.print("Ingrese la cantidad de camas sigles:");
            Integer camasSingles = scanner.nextInt();
            habitacion.setCamasSingles(camasSingles);

            System.out.print("Ingrese la cantidad de camas matrimoñales:");
            Integer camasDobles = scanner.nextInt();
            habitacion.setCamasDobles(camasDobles);

            System.out.print("Ingrese la capacidad de la habitacion:");
            Integer capacidad = scanner.nextInt();
            habitacion.setCapacidad(capacidad);

            habitacionController.actualizar(habitacion);

            System.out.println(habitacion);
            System.out.println("Habitacion actualizada correctamente.");
        } else {
            System.out.println("Habitacion no encontrada busque nuevamente.");
            actualizarHabitacion();
        }
    }

}
