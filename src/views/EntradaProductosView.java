package views;

import java.sql.SQLException;
import java.util.Scanner;

import controllers.EntradaProductoController;
import controllers.ProductoController;
import controllers.UsuarioController;
import models.EntradaProducto;
import models.Producto;

public class EntradaProductosView {

    private static final Scanner scanner = new Scanner(System.in);
    private static EntradaProductoController entradaProductoController = new EntradaProductoController();
    private static UsuarioController usuarioController = new UsuarioController();
    private static ProductoController productoController = new ProductoController();

    public static void mostrarMenuEntradasProductos() throws SQLException {
        while (true) {

            System.out.println("========================================================");
            System.out.println("           GESTIÓN DE ENTRADAS DE PRODUCTOS             ");
            System.out.println("========================================================");
            System.out.println();
            System.out.println("1. Crear Entrada de producto");
            System.out.println("2. Ver Entradas de productos");
            System.out.println("3. Eliminar Entrada de producto");
            System.out.println("4. Volver al Menú Principal");
            System.out.println();
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    crearEntradaDeProducto();
                    break;
                case 2:
                    verEntradasDeProductos();
                    break;
                case 3:
                    eliminarEntradaDeProducto();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Opción no válida, intenta de nuevo.");
            }
        }
    }

    public static EntradaProducto crearEntradaDeProducto() throws SQLException {

        EntradaProducto entradaProducto = new EntradaProducto();

        ProductoView.verProductos();
        System.out.println("Seleccionar el ID del producto : ");
        Integer idProducto = scanner.nextInt();
        Producto producto = productoController.obtenerPorId(idProducto);
        if (producto != null) {
            entradaProducto.setProducto(producto);

            System.out.print("Ingrese la cantidad :");
            Integer descripcion = scanner.nextInt();
            entradaProducto.setCantidad(descripcion);

            System.out.print("Ingrese el precio unitario :");
            Double precioUnitario = scanner.nextDouble();
            entradaProducto.setPrecioUnitario(precioUnitario);

            entradaProducto.setUsuario(usuarioController.getUsuarioLogueado());

            try {
                entradaProductoController.crear(entradaProducto);
                System.out.println("Entrada de producto generada correctamente");
            } catch (SQLException e) {
                System.out.println(e);
                System.out.println("Error al generar la entrada de producto, intentelo nuevamente");
                crearEntradaDeProducto();
            }
            return entradaProducto;
        } else {
            System.out.println("Producto no encontrado, intentar nuevamente");
            return crearEntradaDeProducto();
        }

    }

    public static void verEntradasDeProductos() throws SQLException {
        System.out.println("-------------------------------------------");
        System.out.println("ENTRADAS DE PRODUCTOS");
        entradaProductoController.obtenerTodos().forEach(entradaProducto -> {
            System.out.println("ENTRADA DE PRODUCTO ID " + entradaProducto.getId());
            System.out.println(entradaProducto);
            System.out.println("======================================");
        });
        System.out.println("ENTRADAS DE PRODUCTOS");
        System.out.println("-------------------------------------------");
    }

    private static void eliminarEntradaDeProducto() throws SQLException {
        verEntradasDeProductos();
        System.out.print("Ingrese el id de la entrada de producto a eliminar: ");
        Integer id = scanner.nextInt();
        EntradaProducto entradaProducto = entradaProductoController.obtenerPorId(id);

        if (entradaProducto != null) {
            try {
                entradaProductoController.eliminar(id);
                System.out.println("Entrada de producto eliminada con éxito.");
            } catch (SQLException e) {
                System.out.println("Error al querer eliminar la Entrada de producto, Intentar nuevamente.");
                eliminarEntradaDeProducto();
            }

        } else {
            System.out.println("Entrada de producto no encontrada, busque nuevamente.");
            eliminarEntradaDeProducto();
        }
    }

}
