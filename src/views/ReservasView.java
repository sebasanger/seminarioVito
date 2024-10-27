package views;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import controllers.ReservaController;
import controllers.UsuarioController;
import models.Cliente;
import models.EstadoReservaEnum;
import models.Habitacion;
import models.PrecioHabitacion;
import models.Reserva;
import utils.DateUtils;

//menu de reservas que genera la interaccion con las reservas
public class ReservasView {

    private static final Scanner scanner = new Scanner(System.in);
    private static ReservaController reservaController = new ReservaController();
    private static UsuarioController usuarioController = new UsuarioController();
    private static ReservasViewActualizacion reservasViewActualizacion = new ReservasViewActualizacion();

    // pide al usuario que quiere realizar
    public static void mostrarMenuReservas() throws SQLException {
        while (true) {

            System.out.println("===========================================");
            System.out.println("           GESTIÓN DE RESERVAS             ");
            System.out.println("===========================================");
            System.out.println();
            System.out.println("1. Crear Reserva");
            System.out.println("2. Ver todas las Reservas");
            System.out.println("3. Actualizar Reserva");
            System.out.println("4. Eliminar Reserva");
            System.out.println("5. Ver reservas por estado");
            System.out.println("6. Generar check in");
            System.out.println("7. Generar check out");
            System.out.println("8. Ver detalle de reserva");
            System.out.println("9. Volver al Menú Principal");
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
                    verReservasPorEstado();
                case 6:
                    ReservasDetailView.generarCheckInReserva();
                    break;
                case 7:
                    ReservasDetailView.generarCheckOutReserva();
                    break;
                case 8:
                    ReservasDetailView.mostrarDetalleReservaPorId();
                    break;
                case 9:
                    return;
                default:
                    System.out.println("Opción no válida, intenta de nuevo.");
            }
        }
    }

    // pide los datos para generar una reserva
    private static void crearReserva() throws SQLException {
        limpiarConsola();

        System.out.println("===========================================");
        System.out.println("           CREACION DE RESERVA             ");
        System.out.println("===========================================");

        Reserva reserva = new Reserva(); // se genera el objeto reserva
        List<Cliente> huespedes = new ArrayList<Cliente>(); // se genera una lista de clientes

        System.out.print("Ingrese la cantidad de huespedes: \n");
        int cantidadHuespedes = scanner.nextInt();// se pide la cantidad de huespedes

        // se piden y cargan a la reserva las fechas con un formato amigable
        LocalDate fechaCreacion = LocalDate.now();
        LocalDate fechaInicio = DateUtils.pedirFechaConMinimo("Ingrese la fecha de inicio estimada", fechaCreacion);
        LocalDate fechaFin = DateUtils.pedirFechaConMinimo("Ingrese la fecha de fin estimada", fechaInicio);

        reserva.setFechaCreacion(Date.valueOf(fechaCreacion));
        reserva.setFechaInicio(Date.valueOf(fechaInicio));
        reserva.setFechaFin(Date.valueOf(fechaFin));

        // se evalua para agregar el check in
        if (fechaInicio.isEqual(fechaCreacion)) {
            System.out.println("Agregando check-in en la reserva");
            reserva.setCheckIn(Date.valueOf(fechaInicio));
        }

        // se pide por la cantidad de huespedes sus datos
        // si se encuentra el huesped por su documento ya no se piden los datos sino que
        // son tomandos de la base de datos
        for (int i = 0; i < cantidadHuespedes; i++) {
            System.out.println("HUESPED " + (i + 1));
            Cliente huesped = ClientesView.crearCliente();
            huespedes.add(huesped);
            System.out.println("============================");
        }
        reserva.setClientes(huespedes);

        // busca todas las habitaciones libres por las fechas que se indicaron de inicio
        // y fin, se listan y se le da a elegir al usuario las disponibles por su id
        Habitacion habitacion = HabitacionesView.obtenerSeleccionHabitacionLibre(Date.valueOf(fechaInicio),
                Date.valueOf(fechaFin), cantidadHuespedes);

        reserva.setHabitacion(habitacion);

        // se piden origen y destino
        scanner.nextLine();
        System.out.print("Ingrese el destino: \n");
        String destino = scanner.nextLine();
        reserva.setDestino(destino);

        System.out.print("Ingrese el origen: \n");
        String origen = scanner.nextLine();
        reserva.setOrigen(origen);

        // se le pide la seleccion del precio dandole el listado de las posibilidades al
        // usuario
        PrecioHabitacion precioHabitacion = PreciosHabitacionesView.obtenerSeleccionPrecio();
        reserva.setPrecioHabitacion(precioHabitacion);

        // se guarda el usuario actual
        reserva.setUsuario(usuarioController.getUsuarioLogueado());

        // se genera la reserva
        reservaController.crear(reserva);
    }

    public static void verReservas() throws SQLException {
        // se muestra el listado de las reservas
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
        // se redirige a la vista de actualizacion de la reserva, ya que se puede
        // cambiar algunos datos sin tener que agregarlos todos nuevamente
        System.out.println("===========================================");
        System.out.println("           ACTUALIZACION DE RESERVA        ");
        System.out.println("===========================================");
        verReservas();

        // se muestra las reservas y se le pide que reserva quiere actualizar
        System.out.print("Ingrese el ID de la reserva a actualizar: ");
        int id = scanner.nextInt();
        Reserva reserva = reservaController.obtenerPorId(id);
        if (reserva != null) {
            // se redirige a la vista de actualizacion de la reserva
            reservasViewActualizacion.actualizarReserva(reserva);
        } else {
            System.out.println("Reserva no encontrada.");
        }
    }

    private static void eliminarReserva() throws SQLException {
        // se muestra las reservas y se le pide que reserva quiere eliminar
        System.out.print("Ingrese el ID de la reserva a eliminar: ");
        int id = scanner.nextInt();
        reservaController.eliminar(id);
        System.out.println("Reserva eliminada con éxito.");
    }

    // se da opciones de por que estado se quiere ver las reservas
    protected static void verReservasPorEstado() throws SQLException {

        System.out.println("===========================================");
        System.out.println("           VSUALIZACION DE RESERVAS             ");
        System.out.println("===========================================");
        System.out.println();
        System.out.println("1. Reservas activas");
        System.out.println("2. Reservas pendientes");
        System.out.println("3. Reservas finalizadas");
        System.out.println("4. Reservas eliminadas");
        System.out.println("5. Volver al menu anterior");
        System.out.println();
        System.out.print("Seleccione una opción: ");
        int opcion = scanner.nextInt();

        switch (opcion) {
            case 1:
                mostrarReservasPorEstado(EstadoReservaEnum.ACTIVA);
                break;
            case 2:
                mostrarReservasPorEstado(EstadoReservaEnum.PENDIENTE);
                break;
            case 3:
                mostrarReservasPorEstado(EstadoReservaEnum.FINALIZADA);
                break;
            case 4:
                mostrarReservasPorEstado(EstadoReservaEnum.ELIMINADA);
                break;
            case 5:
                mostrarMenuReservas();
            default:
                System.out.println("Opción no válida, intenta de nuevo.");
                verReservasPorEstado();
                break;
        }
        verReservasPorEstado();

    }

    // muestra las reservas que se filtran por un estado indicado
    public static void mostrarReservasPorEstado(EstadoReservaEnum estado) throws SQLException {
        List<Reserva> reservas = reservaController.obtenerReservasPorEstado(estado.getEstado());
        System.out.println("--------------- reservas " + estado.getEstado() + " ----------------------------");
        System.out.println("RESERVAS");
        reservas.forEach(reserva -> {
            System.out.println("RESERVA ID " + reserva.getId());
            System.out.println(reserva);
            System.out.println("======================================");
        });
        System.out.println("----------------------------------------------------------------------------");

    }

    private static void limpiarConsola() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
