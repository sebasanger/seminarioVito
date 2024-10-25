package views;

import java.sql.SQLException;
import java.util.Scanner;

import controllers.ClienteController;
import controllers.ConsumoController;
import models.Cliente;
import models.Consumicion;

public class ConsumosView {

    private static final Scanner scanner = new Scanner(System.in);
    private static ClienteController clienteController = new ClienteController();
    private static ConsumoController consumoController = new ConsumoController();

    public static void mostrarMenuConsumos() throws SQLException {
        while (true) {

            System.out.println("===========================================");
            System.out.println("           GESTIÓN DE CONSUMOS             ");
            System.out.println("===========================================");
            System.out.println();
            System.out.println("1. Crear Consumo");
            System.out.println("2. Ver Consumos");
            System.out.println("3. Eliminar Consumo");
            System.out.println("4. Volver al Menú Principal");
            System.out.println();
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    crearConsumo();
                    break;
                case 2:
                    verConsumos();
                    break;
                case 3:
                    eliminarConsumo();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Opción no válida, intenta de nuevo.");
            }
        }
    }

    public static Cliente crearConsumo() throws SQLException {
        System.out.println("Ingrese el documento del huesped : ");

        String documento = scanner.next();
        Cliente cliente = clienteController.obtenerPorDocumento(documento);

        if (cliente == null) {
            System.out.println("No se encontro el cliente en la base de datos");
            cliente = new Cliente();

            System.out.print("Ingrese el nombre del huesped :");
            scanner.nextLine();
            String numbre = scanner.nextLine();

            System.out.print("Ingrese el apellido del huesped :");
            String apellido = scanner.nextLine();

            System.out.print("Ingrese el email del huesped :");
            String email = scanner.nextLine();

            cliente.setDocumento(documento);
            cliente.setEmail(email);
            cliente.setNombre(numbre);
            cliente.setApellido(apellido);

            try {
                clienteController.crear(cliente);
                System.out.println("Huesped generado correctamente");
            } catch (SQLException e) {
                System.out.println("Error al generar el huesped, intentelo nuevamente");
                crearConsumo();
            }

        } else {
            System.out.println("Se obtuvo el cliente de la base de datos por su documento");
            System.out.println(cliente);
        }
        return cliente;

    }

    private static void verConsumos() throws SQLException {
        System.out.println("-------------------------------------------");
        System.out.println("CONSUMOS");
        consumoController.obtenerTodos().forEach(consumo -> {
            System.out.println("CONSUMO ID " + consumo.getId());
            System.out.println(consumo);
            System.out.println("======================================");
        });
        System.out.println("CONSUMOS");
        System.out.println("-------------------------------------------");

    }

    private static void eliminarConsumo() throws SQLException {
        verConsumos();
        System.out.print("Ingrese el id del consumo a eliminar: ");
        Integer id = scanner.nextInt();
        Consumicion consumo = consumoController.obtenerPorId(id);

        if (consumo != null) {
            try {
                consumoController.eliminar(id);
                System.out.println("Consumo eliminado con éxito.");
            } catch (SQLException e) {
                System.out.println("Error al querer eliminar el consumo, Intentar nuevamente.");
                eliminarConsumo();
            }

        } else {
            System.out.println("Consumo no encontrado busque nuevamente.");
            eliminarConsumo();
        }
    }

}
