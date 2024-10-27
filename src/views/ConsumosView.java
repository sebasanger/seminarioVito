package views;

import java.sql.SQLException;
import java.util.Scanner;

import controllers.ConsumoController;
import controllers.ProductoController;
import controllers.ReservaController;
import controllers.UsuarioController;
import exceptions.StockInsuficienteException;
import models.Consumicion;
import models.EstadoReservaEnum;
import models.Producto;
import models.Reserva;

public class ConsumosView {

    private static final Scanner scanner = new Scanner(System.in);
    private static ConsumoController consumoController = new ConsumoController();
    private static ProductoController productoController = new ProductoController();
    private static ReservaController reservaController = new ReservaController();
    private static UsuarioController usuarioController = new UsuarioController();

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

    public static void crearConsumo() throws SQLException {
        ReservasView.mostrarReservasPorEstado(EstadoReservaEnum.ACTIVA);
        System.out.println("Seleccionar el ID de la reserva : ");
        Integer idReserva = scanner.nextInt();
        Reserva reserva = reservaController.obtenerPorId(idReserva);

        ProductoView.verProductos();
        System.out.println("Seleccionar el ID del producto : ");
        Integer idProducto = scanner.nextInt();
        Producto producto = productoController.obtenerPorId(idProducto);

        if (producto != null && reserva != null) {
            Consumicion consumicion = new Consumicion();

            System.out.print("Ingrese la cantidad :");
            Integer cantidad = scanner.nextInt();

            consumicion.setCantidad(cantidad);
            consumicion.setPrecioUnitario(producto.getPrecio());
            consumicion.setReserva(reserva);
            consumicion.setProducto(producto);

            consumicion.setUsuario(usuarioController.getUsuarioLogueado());
            try {
                consumoController.crearConsumo(consumicion);
                System.out.println("Consumo generado correctamente");
            } catch (SQLException e) {
                System.out.println("Error al generar el consumo, intentelo nuevamente");
                crearConsumo();
            } catch (StockInsuficienteException e) {
                System.out.println("Intente nuevamente con una cantidad menor de producto a comprar.");
                crearConsumo();
            }

        } else {
            System.out.println("No se encontro producto o reserva con ese ID");
            crearConsumo();
        }

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
