package views;

import java.sql.SQLException;
import java.util.Scanner;

import controllers.ClienteController;
import models.Cliente;

public class ClientesView {

    private static final Scanner scanner = new Scanner(System.in);
    private static ClienteController clienteController = new ClienteController();

    public static void mostrarMenuClientes() throws SQLException {
        while (true) {

            System.out.println("===========================================");
            System.out.println("           GESTIÓN DE CLIENTES             ");
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
                case 1:
                    crearCliente();
                    break;
                case 2:
                    verClientes();
                    break;
                case 3:
                    actualizarCliente();
                    break;
                case 4:
                    eliminarCliente();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Opción no válida, intenta de nuevo.");
            }
        }
    }

    public static Cliente crearCliente() throws SQLException {
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
                cliente = clienteController.obtenerPorDocumento(documento);
                System.out.println("Huesped generado correctamente");
            } catch (SQLException e) {
                System.out.println("Error al generar el huesped, intentelo nuevamente");
                crearCliente();
            }

        } else {
            System.out.println("Se obtuvo el cliente de la base de datos por su documento");
            System.out.println(cliente);
        }
        return cliente;

    }

    private static void verClientes() throws SQLException {

        System.out.println("-------------------------------------------");
        System.out.println("CLIENTES");
        clienteController.obtenerTodos().forEach(cliente -> {
            System.out.println("CLIENTE ID " + cliente.getId());
            System.out.println(cliente);
            System.out.println("======================================");
        });
        System.out.println("CLIENTES");
        System.out.println("-------------------------------------------");

    }

    private static void eliminarCliente() throws SQLException {
        verClientes();
        System.out.print("Ingrese el documento del cliente a eliminar: ");
        String documento = scanner.next();
        Cliente cliente = clienteController.obtenerPorDocumento(documento);

        if (cliente != null) {
            try {
                clienteController.eliminar(cliente.getId());
                System.out.println("Cliente eliminado con éxito.");
            } catch (SQLException e) {
                System.out.println("Error al querer eliminar el cliente, Intentar nuevamente.");
                System.out.println("Si el cliente esta asociado a una reserva no se permite su eliminacion. ");
                eliminarCliente();
            }

        } else {
            System.out.println("Cliente no encontrado busque nuevamente.");
            eliminarCliente();
        }
    }

    private static void actualizarCliente() throws SQLException {
        System.out.print("Ingrese el documento del cliente: ");
        String documento = scanner.next();
        Cliente cliente = clienteController.obtenerPorDocumento(documento);
        if (cliente != null) {
            System.out.println(cliente);
            menuActualizarCliente(cliente);

        } else {
            System.out.println("Cliente no encontrado busque nuevamente.");
            actualizarCliente();
        }
    }

    public static void menuActualizarCliente(Cliente cliente) throws SQLException {
        boolean continuar = true;
        while (continuar) {
            System.out.println("\n--- Menú de Actualización de Cliente ---");
            System.out.println(
                    "\n--- Realice los cambios y presione 5 para actualizar ---");
            System.out.println("0. Cancelar");
            System.out.println("1. Actualizar nombre");
            System.out.println("2. Actualizar apellido");
            System.out.println("3. Actualizar email");
            System.out.println("4. Actualizar documento");
            System.out.println("5. Ejecutar actualizacion");

            System.out.print("\nSeleccione una opción: ");
            int opcion = scanner.nextInt();

            switch (opcion) {
                case 0:
                    continuar = false;
                    System.out.println("Saliendo del menú de actualización...");
                    break;
                case 1:
                    actualizarNombre(cliente);
                    break;
                case 2:
                    actualizarApellido(cliente);
                    break;
                case 3:
                    actualizarEmail(cliente);
                    break;
                case 4:
                    actualizarDocumento(cliente);
                    break;
                case 5:
                    System.out.println("Ejecutando actualizacion.");
                    System.out.println(cliente);
                    clienteController.actualizar(cliente);
                    continuar = false;
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
                    menuActualizarCliente(cliente);
            }
        }

    }

    private static void actualizarNombre(Cliente cliente) {
        System.out.print("Ingrese el nuevo nombre: ");
        scanner.nextLine();
        String nuevoNombre = scanner.nextLine();
        cliente.setNombre(nuevoNombre);
    }

    private static void actualizarApellido(Cliente cliente) {
        System.out.print("Ingrese el nuevo apellido: ");
        scanner.nextLine();
        String nuevoNombre = scanner.nextLine();
        cliente.setApellido(nuevoNombre);
    }

    private static void actualizarEmail(Cliente cliente) {
        System.out.print("Ingrese el nuevo email: ");
        scanner.nextLine();
        String nuevoNombre = scanner.nextLine();
        cliente.setEmail(nuevoNombre);
    }

    private static void actualizarDocumento(Cliente cliente) {
        System.out.print("Ingrese el nuevo documento: ");
        scanner.nextLine();
        String nuevoNombre = scanner.nextLine();
        cliente.setDocumento(nuevoNombre);
    }

}
