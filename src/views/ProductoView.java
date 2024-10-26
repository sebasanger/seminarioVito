package views;

import java.sql.SQLException;
import java.util.Scanner;

import controllers.ProductoController;
import models.Categoria;
import models.Marca;
import models.Producto;

public class ProductoView {

    private static final Scanner scanner = new Scanner(System.in);
    private static ProductoController productoController = new ProductoController();

    public static void mostrarMenuProductos() throws SQLException {
        while (true) {

            System.out.println("===========================================");
            System.out.println("           GESTIÓN DE PRODUCTOS             ");
            System.out.println("===========================================");
            System.out.println();
            System.out.println("1. Crear Producto");
            System.out.println("2. Ver Productos");
            System.out.println("3. Modificar Producto");
            System.out.println("4. Eliminar Producto");
            System.out.println("5. Volver al Menú Principal");
            System.out.println();
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    crearProducto();
                    break;
                case 2:
                    verProductos();
                    break;
                case 3:
                    actualizarProducto();
                    break;
                case 4:
                    eliminarProducto();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Opción no válida, intenta de nuevo.");
            }
        }
    }

    public static Producto crearProducto() throws SQLException {

        Producto producto = new Producto();
        scanner.nextLine();

        System.out.print("Ingrese el nombre del producto :");
        String nombreProducto = scanner.nextLine();
        producto.setNombre(nombreProducto);

        System.out.print("Ingrese la descripcion del producto :");
        String descripcion = scanner.nextLine();
        producto.setDescripcion(descripcion);

        System.out.print("Ingrese el precio :");
        Double precio = scanner.nextDouble();
        producto.setPrecio(precio);

        System.out.print("Ingrese el stock actual :");
        Integer stock = scanner.nextInt();
        producto.setStock(stock);

        Categoria categoria = CategoriasView.obtenerSeleccionCategoria();
        Marca marca = MarcasView.obtenerSeleccionMarca();

        producto.setCategoria(categoria);
        producto.setMarca(marca);

        try {
            productoController.crear(producto);
            System.out.println("Producto generado correctamente");
        } catch (SQLException e) {
            System.out.println("Error al generar el producto, intentelo nuevamente");
            crearProducto();
        }

        return producto;

    }

    public static void verProductos() throws SQLException {
        System.out.println("-------------------------------------------");
        System.out.println("PRODUCTOS");
        productoController.obtenerTodos().forEach(producto -> {
            System.out.println("PRODUCTO ID " + producto.getId());
            System.out.println(producto);
            System.out.println("======================================");
        });
        System.out.println("PRODUCTOS");
        System.out.println("-------------------------------------------");
    }

    private static void eliminarProducto() throws SQLException {
        verProductos();
        System.out.print("Ingrese el id del producto a eliminar: ");
        Integer id = scanner.nextInt();
        Producto producto = productoController.obtenerPorId(id);

        if (producto != null) {
            try {
                productoController.eliminar(id);
                System.out.println("Producto eliminado con éxito.");
            } catch (SQLException e) {
                System.out.println("Error al querer eliminar el Producto, Intentar nuevamente.");
                eliminarProducto();
            }

        } else {
            System.out.println("Producto no encontrado busque nuevamente.");
            eliminarProducto();
        }
    }

    private static void actualizarProducto() throws SQLException {
        verProductos();
        System.out.print("Ingrese el id del producto a actualizar: ");
        Integer idProducto = scanner.nextInt();
        Producto producto = productoController.obtenerPorId(idProducto);
        if (producto != null) {
            scanner.nextLine();

            System.out.print("Ingrese el nombre del producto :");
            String nombreProducto = scanner.nextLine();
            producto.setNombre(nombreProducto);

            System.out.print("Ingrese la descripcion del producto :");
            String descripcion = scanner.nextLine();
            producto.setDescripcion(descripcion);

            System.out.print("Ingrese el precio :");
            Double precio = scanner.nextDouble();
            producto.setPrecio(precio);

            System.out.print("Ingrese el stock actual :");
            Integer stock = scanner.nextInt();
            producto.setStock(stock);

            Categoria categoria = CategoriasView.obtenerSeleccionCategoria();
            Marca marca = MarcasView.obtenerSeleccionMarca();

            producto.setCategoria(categoria);
            producto.setMarca(marca);

            productoController.actualizar(producto);

            System.out.println(producto);
            System.out.println("Producto actualizado correctamente.");
        } else {
            System.out.println("Producto no encontrado busque nuevamente.");
            actualizarProducto();
        }
    }

}
