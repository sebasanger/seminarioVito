package views;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import controllers.ReservaController;
import exceptions.PagoFaltanteException;
import models.EstadoReservaEnum;
import models.Reserva;

public class ReservasDetailView {

    private static final Scanner scanner = new Scanner(System.in);
    private static ReservaController reservaController = new ReservaController();

    public static void generarCheckInReserva() throws SQLException {
        System.out.println("===========================================");
        System.out.println("           GENERACION DE CHECK IN          ");
        System.out.println("===========================================");
        System.out.println("======================================================");
        System.out.println("           RESERVAS DISPONIBLES PARA CHECK IN         ");
        ReservasDetailView.mostrarReservasPorEstadoYFecha(EstadoReservaEnum.PENDIENTE, false);
        System.out.println("======================================================");

        System.out.print("Ingrese el ID de la reserva: ");
        int id = scanner.nextInt();
        try {
            reservaController.generarCheckIn(id);
            System.out.println("Check in generado con éxito.");
        } catch (SQLException e) {
            System.err.println(e);
            System.out.println("Error al generar el check in, Intetelo nuevamente.");
            generarCheckInReserva();
        }

    }

    public static void generarCheckOutReserva() throws SQLException {
        System.out.println("===========================================");
        System.out.println("           GENERACION DE CHECK OUT          ");
        System.out.println("===========================================");
        System.out.println("======================================================");
        System.out.println("           RESERVAS DISPONIBLES PARA CHECK OUT        ");
        ReservasDetailView.mostrarReservasPorEstadoYFecha(EstadoReservaEnum.ACTIVA, true);
        System.out.println("======================================================");

        System.out.print("Ingrese el ID de la reserva: ");
        int id = scanner.nextInt();
        try {
            reservaController.generarCheckOut(id);
            System.out.println("Check in generado con éxito.");
        } catch (SQLException e) {
            System.err.println(e);
            System.out.println("Error al generar el check in, Intetelo nuevamente.");
            generarCheckOutReserva();
        } catch (PagoFaltanteException e) {
            System.out.println("Redirigiendo a generacion de pago...");
            PagosView.crearPago();
        }

    }

    static void mostrarReservasPorEstadoYFecha(EstadoReservaEnum estado, Boolean porFechaFin) throws SQLException {
        List<Reserva> reservas = reservaController.obtenerReservasPorEstadoYFecha(estado.getEstado(), porFechaFin);
        System.out.println("RESERVAS");
        reservas.forEach(reserva -> {
            System.out.println("RESERVA ID " + reserva.getId());
            System.out.println(reserva);
            System.out.println(
                    "============================================================================================");
        });

    }

}
