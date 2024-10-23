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
import utils.DateUtils;

public class ReservasViewActualizacion {

    private static ReservaController reservaController = new ReservaController();
    private static final Scanner scanner = new Scanner(System.in);
    Reserva reserva = new Reserva();

    public void actualizarReserva(Reserva reserva) throws SQLException {
        boolean continuar = true;
        this.reserva = reserva;
        while (continuar) {
            System.out.println("\n--- Menú de Actualización de Reserva ---");
            System.out.println(
                    "\n--- Realice los cambios y presione 8 cuando tenga todos los cambios que desea sobre la reserva para actualizarla ---");
            System.out.println("1. Actualizar huéspedes");
            System.out.println("2. Actualizar fechas (fecha de creación, inicio, fin)");
            System.out.println("3. Actualizar precio de habitación");
            System.out.println("4. Actualizar habitación");
            System.out.println("5. Actualizar destino");
            System.out.println("6. Actualizar origen");
            System.out.println("7. Actualizar precio");
            System.out.println("8. Ejecutar actualizacion");

            System.out.print("\nSeleccione una opción: ");
            int opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    actualizarHuespedes();
                    break;
                case 2:
                    actualizarFechas();
                    break;
                case 3:
                    actualizarPrecio();
                    break;
                case 4:
                    actualizarHabitacion();
                    break;
                case 5:
                    actualizarDestino();
                    break;
                case 6:
                    actualizarOrigen();
                    break;
                case 7:
                    actualizarPrecio();
                    break;
                case 8:
                    continuar = false;
                    System.out.println("Saliendo del menú de actualización...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }

        System.out.println("Ejecutando actualizacion.");
        System.out.println(reserva);
        reservaController.actualizar(this.reserva);
    }

    private void actualizarHuespedes() throws SQLException {
        List<Cliente> huespedes = new ArrayList<>();
        System.out.print("Ingrese la cantidad de huéspedes: ");
        int cantidadHuespedes = scanner.nextInt();

        for (int i = 0; i < cantidadHuespedes; i++) {
            System.out.println("Huésped " + (i + 1));
            Cliente huesped = ClientesView.crearCliente();
            huespedes.add(huesped);
            System.out.println("============================");
        }
        this.reserva.setClientes(huespedes);
        System.out.println("Huéspedes actualizados correctamente.");
    }

    private void actualizarFechas() {
        LocalDate fechaInicio = DateUtils.pedirFecha("Ingrese la fecha de inicio estimada");
        LocalDate fechaFin = DateUtils.pedirFechaConMinimo("Ingrese la fecha de fin estimada", fechaInicio);

        this.reserva.setFechaInicio(Date.valueOf(fechaInicio));
        this.reserva.setFechaFin(Date.valueOf(fechaFin));

        System.out.println("Fechas actualizadas correctamente.");
    }

    private void actualizarHabitacion() throws SQLException {
        int cantidadHuespedes = this.reserva.getClientes().size();
        LocalDate fechaInicio = DateUtils.convertirADate(this.reserva.getFechaInicio());
        LocalDate fechaFin = DateUtils.convertirADate(this.reserva.getFechaFin());

        Habitacion habitacion = HabitacionesView.obtenerSeleccionHabitacionLibre(
                Date.valueOf(fechaInicio), Date.valueOf(fechaFin), cantidadHuespedes);
        this.reserva.setHabitacion(habitacion);
        System.out.println("Habitación actualizada correctamente.");
    }

    private void actualizarDestino() {
        System.out.print("Ingrese el nuevo destino: ");
        String destino = scanner.next();
        this.reserva.setDestino(destino);
        System.out.println("Destino actualizado correctamente.");
    }

    private void actualizarOrigen() {
        System.out.print("Ingrese el nuevo origen: ");
        String origen = scanner.next();
        this.reserva.setOrigen(origen);
        System.out.println("Origen actualizado correctamente.");
    }

    private void actualizarPrecio() throws SQLException {
        PrecioHabitacion precioHabitacion = PreciosHabitacionesView.obtenerSeleccionPrecio();
        this.reserva.setPrecioHabitacion(precioHabitacion);
        System.out.println("Precio de habitación actualizado correctamente.");
    }
}
