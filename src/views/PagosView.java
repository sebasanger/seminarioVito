package views;

import java.sql.SQLException;
import java.util.Scanner;

import controllers.PagoController;
import controllers.ReservaController;
import controllers.UsuarioController;
import exceptions.CajaNoAbiertaException;
import exceptions.PagoExcedidoException;
import models.EstadoReservaEnum;
import models.Pago;
import models.Reserva;

public class PagosView {

    private static final Scanner scanner = new Scanner(System.in);
    private static PagoController pagoController = new PagoController();
    private static ReservaController reservaController = new ReservaController();
    private static UsuarioController usuarioController = new UsuarioController();

    public static void mostrarMenuPagos() throws SQLException {
        while (true) {

            System.out.println("===========================================");
            System.out.println("           GESTIÓN DE PAGOS             ");
            System.out.println("===========================================");
            System.out.println();
            System.out.println("1. Crear pago");
            System.out.println("2. Ver pagos");
            System.out.println("3. Eliminar pago");
            System.out.println("4. Volver al Menú Principal");
            System.out.println();
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    crearPago();
                    break;
                case 2:
                    verPagos();
                    break;
                case 3:
                    eliminarPago();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Opción no válida, intenta de nuevo.");
            }
        }
    }

    public static void crearPago() throws SQLException {

        ReservasView.mostrarReservasPorEstado(EstadoReservaEnum.ACTIVA);

        System.out.println("Ingrese el id de la reserva : ");
        Integer reservaId = scanner.nextInt();

        Reserva reserva = reservaController.obtenerPorId(reservaId);

        if (reserva != null) {
            agregarPagoReserva(reserva);

        } else {

            System.out.println("No se encontro reserva con ese ID intentalo nuevamente");
            crearPago();
        }

    }

    private static void agregarPagoReserva(Reserva reserva) throws SQLException {
        Pago pago = new Pago();
        System.out.println(
                "Faltante a pagar en la reserva " + (reserva.getPrecioTotal() - reserva.getPagadoTotal()) + " $");

        System.out.print("Ingrese el monto :");
        Double monto = scanner.nextDouble();

        scanner.nextLine();

        System.out.print("Ingrese la descripcion :");
        String descripcion = scanner.nextLine();
        pago.setUsuario(usuarioController.getUsuarioLogueado());

        pago.setCantidad(monto);
        pago.setDescripcion(descripcion);
        pago.setReserva(reserva);

        try {
            pagoController.crearPago(pago);
            System.out.println("Pago generado correctamente");
        } catch (SQLException e) {
            System.out.println(e);
            System.out.println("Error al generar el pago, intentelo nuevamente");
            crearPago();
        } catch (CajaNoAbiertaException e) {
            // TODO: redirigir a abrir la caja y luego terminar de generar el pago
            e.printStackTrace();
            agregarPagoReserva(reserva);
        } catch (PagoExcedidoException e) {
            agregarPagoReserva(reserva);
        }

        mostrarMenuPagos();
    }

    private static void verPagos() throws SQLException {

        System.out.println("-------------------------------------------");
        System.out.println("PAGOS");
        pagoController.obtenerTodos().forEach(pago -> {
            System.out.println("PAGO ID " + pago.getId());
            System.out.println(pago);
            System.out.println("======================================");
        });
        System.out.println("PAGOS");
        System.out.println("-------------------------------------------");

    }

    private static void eliminarPago() throws SQLException {
        verPagos();
        System.out.print("Ingrese el pago a eliminar: ");
        Integer pagoId = scanner.nextInt();
        Pago pago = pagoController.obtenerPorId(pagoId);

        if (pago != null) {
            try {
                pagoController.eliminar(pago.getId());
                System.out.println("Pago eliminado con éxito.");
            } catch (SQLException e) {
                System.out.println("Error al eliminar el pago, Intentar nuevamente.");
                eliminarPago();
            }

        } else {
            System.out.println("Pago no encontrado ingrese el id nuevamente.");
            eliminarPago();
        }
    }

}
