package views;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import controllers.PrecioHabitacionController;
import models.PrecioHabitacion;

public class PreciosHabitacionesView {

    private static final Scanner scanner = new Scanner(System.in);
    private static PrecioHabitacionController precioHabitacionController = new PrecioHabitacionController();

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

}
