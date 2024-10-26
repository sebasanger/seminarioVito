package views;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import controllers.PrecioHabitacionController;
import models.PrecioHabitacion;

public class PreciosHabitacionesView {

    private static final Scanner scanner = new Scanner(System.in);
    private static PrecioHabitacionController precioHabitacionController = new PrecioHabitacionController();

    public static void mostrarMenuPreciosHabitaciones() throws SQLException {
        while (true) {

            System.out.println("===========================================");
            System.out.println("           GESTIÓN DE HABITACIONES             ");
            System.out.println("===========================================");
            System.out.println();
            System.out.println("1. Crear Precio");
            System.out.println("2. Ver Precios");
            System.out.println("3. Actualizar Precio");
            System.out.println("4. Eliminar Precio");
            System.out.println("5. Volver al Menú Principal");
            System.out.println();
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    crearPrecioHabitacion();
                    break;
                case 2:
                    verPreciosHabitaciones();
                    break;
                case 3:
                    actualizarPrecioHabitacion();
                    break;
                case 4:
                    eliminarPrecioHabitacion();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Opción no válida, intenta de nuevo.");
            }
        }
    }

    public static PrecioHabitacion obtenerSeleccionPrecio()
            throws SQLException {

        List<PrecioHabitacion> precios = precioHabitacionController.obtenerTodos();

        for (PrecioHabitacion precio : precios) {
            System.out.println(precio);
        }

        System.out.print("Ingrese el id del precio: \n");
        int precioHabitacionId = scanner.nextInt();

        for (PrecioHabitacion precio : precios) {
            if (precio.getId().equals(precioHabitacionId)) {
                return precio;
            }
        }

        System.out.print("Id del precio no valido, Intentar nuevamente: \n");
        return obtenerSeleccionPrecio();
    }

    public static PrecioHabitacion crearPrecioHabitacion() throws SQLException {

        PrecioHabitacion precioHabitacion = new PrecioHabitacion();
        scanner.nextLine();

        System.out.print("Ingrese la descripcion :");
        String descripcion = scanner.nextLine();
        precioHabitacion.setDescripcion(descripcion);

        System.out.print("Ingrese el precio :");
        Double precio = scanner.nextDouble();
        precioHabitacion.setPrecio(precio);

        precioHabitacion.setDisponible(true);

        LocalDate fechaActual = LocalDate.now();
        precioHabitacion.setFechaCreacion(Date.valueOf(fechaActual));

        try {
            precioHabitacionController.crear(precioHabitacion);
            System.out.println("Precio generado correctamente");
        } catch (SQLException e) {
            System.out.println("Error al generar el Precio, intentelo nuevamente");
            crearPrecioHabitacion();
        }

        return precioHabitacion;

    }

    public static void verPreciosHabitaciones() throws SQLException {
        System.out.println("-------------------------------------------");
        System.out.println("PRECIOS");
        precioHabitacionController.obtenerTodos().forEach(precio -> {
            System.out.println("PRECIO ID " + precio.getId());
            System.out.println(precio);
            System.out.println("======================================");
        });
        System.out.println("PRECIOS");
        System.out.println("-------------------------------------------");
    }

    private static void eliminarPrecioHabitacion() throws SQLException {
        verPreciosHabitaciones();
        System.out.print("Ingrese el id del precio a eliminar: ");
        Integer id = scanner.nextInt();
        PrecioHabitacion precioHabitacion = precioHabitacionController.obtenerPorId(id);

        if (precioHabitacion != null) {
            try {
                precioHabitacionController.eliminar(id);
                System.out.println("Precio de habitacion eliminado con éxito.");
            } catch (SQLException e) {
                System.out.println("Error al querer eliminar el Precio de habitacio, Intentar nuevamente.");
                eliminarPrecioHabitacion();
            }

        } else {
            System.out.println("Precio no encontrado busque nuevamente.");
            eliminarPrecioHabitacion();
        }
    }

    private static void actualizarPrecioHabitacion() throws SQLException {
        verPreciosHabitaciones();
        System.out.print("Ingrese el id del precio: ");
        Integer idPrecio = scanner.nextInt();
        PrecioHabitacion precio = precioHabitacionController.obtenerPorId(idPrecio);
        if (precio != null) {
            scanner.nextLine();

            System.out.print("Ingrese la descripcion :");
            String descripcionNuevo = scanner.nextLine();
            precio.setDescripcion(descripcionNuevo);

            System.out.print("Ingrese el precio :");
            Double precioNuevo = scanner.nextDouble();
            precio.setPrecio(precioNuevo);

            precioHabitacionController.actualizar(precio);

            System.out.println(precio);
            System.out.println("Precio de habitacion actualizado correctamente.");
        } else {
            System.out.println("Cliente no encontrado busque nuevamente.");
            actualizarPrecioHabitacion();
        }
    }

}
