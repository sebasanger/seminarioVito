package views;

import java.sql.SQLException;
import java.util.Scanner;

import controllers.ClienteController;
import models.Cliente;

public class ClientesView {

    private static final Scanner scanner = new Scanner(System.in);
    private static ClienteController clienteController = new ClienteController();

    public static void mostrarMenuReservas() throws SQLException {
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
                    eliminarReserva();
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
            String numbre = scanner.next();

            System.out.print("Ingrese el apellido del huesped :");
            String apellido = scanner.next();

            System.out.print("Ingrese el email del huesped :");
            String email = scanner.next();

            cliente.setDocumento(documento);
            cliente.setEmail(email);
            cliente.setNombre(numbre);
            cliente.setApellido(apellido);

            System.out.println("Huesped generado correctamente");
            try {
                clienteController.crear(cliente);
            } catch (SQLException e) {
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
        clienteController.obtenerTodos().forEach(reserva -> {
            System.out.println("CLIENTE ID " + reserva.getId());
            System.out.println(reserva);
            System.out.println("======================================");
        });
        System.out.println("CLIENTES");
        System.out.println("-------------------------------------------");

    }

    private static void actualizarCliente() throws SQLException {
        System.out.print("Ingrese el ID de la reserva a actualizar: ");
        int id = scanner.nextInt();
        Cliente reserva = clienteController.obtenerPorId(id);
        if (reserva != null) {
            // Actualizar campos
            System.out.print("Ingrese el nuevo precio diario: ");
            String nuevoNombre = scanner.next();
            reserva.setNombre(nuevoNombre);
            // reservaController.actualizar(reserva);
            System.out.println("Cliente actualizado.");
        } else {
            System.out.println("Cliente no encontrado.");
        }
    }

    private static void eliminarReserva() throws SQLException {
        System.out.print("Ingrese el ID del cliente a eliminar: ");
        int id = scanner.nextInt();
        clienteController.eliminar(id);
        System.out.println("Cliente eliminado con éxito.");
    }

}
