package views;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import controllers.ReservaController;
import models.Cliente;
import models.Habitacion;
import models.PrecioHabitacion;
import models.Reserva;
import models.Usuario;
import utils.DateUtils;

public class ReservasView {

    private static final Scanner scanner = new Scanner(System.in);
    private static ReservaController reservaController = new ReservaController();

    public static void mostrarMenuReservas() throws SQLException {
        while (true) {

            System.out.println("===========================================");
            System.out.println("           GESTIÓN DE RESERVAS             ");
            System.out.println("===========================================");
            System.out.println();
            System.out.println("1. Crear Reserva");
            System.out.println("2. Ver Reservas");
            System.out.println("3. Actualizar Reserva");
            System.out.println("4. Eliminar Reserva");
            System.out.println("5. Volver al Menú Principal");
            System.out.println();
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    crearReserva();
                    break;
                case 2:
                    verReservas();
                    break;
                case 3:
                    actualizarReserva();
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

    private static void crearReserva() throws SQLException {
        limpiarConsola();

        System.out.println("===========================================");
        System.out.println("           CREACION DE RESERVA             ");
        System.out.println("===========================================");

        Reserva reserva = new Reserva();
        List<Cliente> huespedes = new ArrayList<Cliente>();

        System.out.print("Ingrese la cantidad de huespedes: \n");
        int cantidadHuespedes = scanner.nextInt();

        LocalDate fechaCreacion = LocalDate.now();
        LocalDate fechaInicio = DateUtils.pedirFecha("Ingrese la fecha de inicio estimada", fechaCreacion);
        LocalDate fechaFin = DateUtils.pedirFecha("Ingrese la fecha de fin estimada", fechaInicio);

        reserva.setFechaCreacion(Date.valueOf(fechaCreacion));
        reserva.setFechaInicio(Date.valueOf(fechaInicio));
        reserva.setFechaFin(Date.valueOf(fechaFin));

        if (fechaInicio.isEqual(fechaCreacion)) {
            System.out.println("Agregando check-in en la reserva");
            reserva.setCheckIn(Date.valueOf(fechaInicio));
        }

        for (int i = 0; i < cantidadHuespedes; i++) {
            System.out.println("HUESPED " + (i + 1));
            Cliente huesped = ClientesView.crearCliente();
            huespedes.add(huesped);
            System.out.println("============================");
        }
        reserva.setClientes(huespedes);

        Habitacion habitacion = HabitacionesView.obtenerSeleccionHabitacionLibre(Date.valueOf(fechaInicio),
                Date.valueOf(fechaFin), cantidadHuespedes);

        reserva.setHabitacion(habitacion);

        System.out.print("Ingrese el destino: \n");
        String destino = scanner.next();
        reserva.setDestino(destino);

        System.out.print("Ingrese el origen: \n");
        String origen = scanner.next();
        reserva.setOrigen(origen);

        PrecioHabitacion precioHabitacion = PreciosHabitacionesView.obtenerSeleccionPrecio();
        reserva.setPrecioHabitacion(precioHabitacion);

        // TODO: faltante de agregar el usuario cuando se tenga el login
        reserva.setUsuario(new Usuario(1));

        reservaController.crear(reserva);
        System.out.println("Reserva creada con éxito.");
        System.out.println(reserva);
    }

    private static void verReservas() throws SQLException {

        System.out.println("-------------------------------------------");
        System.out.println("RESERVAS");
        reservaController.obtenerTodos().forEach(reserva -> {
            System.out.println("RESERVA ID " + reserva.getId());
            System.out.println(reserva);
            System.out.println("======================================");
        });
        System.out.println("RESERVAS");
        System.out.println("-------------------------------------------");

    }

    private static void actualizarReserva() throws SQLException {
        System.out.print("Ingrese el ID de la reserva a actualizar: ");
        int id = scanner.nextInt();
        Reserva reserva = reservaController.obtenerPorId(id);
        if (reserva != null) {
            // Actualizar campos
            System.out.print("Ingrese el nuevo precio diario: ");
            double nuevoPrecio = scanner.nextDouble();
            reserva.setPrecioDiario(nuevoPrecio);
            reservaController.actualizar(reserva);
            System.out.println("Reserva actualizada con éxito.");
        } else {
            System.out.println("Reserva no encontrada.");
        }
    }

    private static void eliminarReserva() throws SQLException {
        System.out.print("Ingrese el ID de la reserva a eliminar: ");
        int id = scanner.nextInt();
        reservaController.eliminar(id);
        System.out.println("Reserva eliminada con éxito.");
    }

    private static void limpiarConsola() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
