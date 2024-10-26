package views;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import controllers.CategoriaController;
import models.Categoria;

public class CategoriasView {

    private static final Scanner scanner = new Scanner(System.in);
    private static CategoriaController categoriaController = new CategoriaController();

    public static void mostrarMenuCategorias() throws SQLException {
        while (true) {

            System.out.println("===========================================");
            System.out.println("           GESTIÓN DE HABITACIONES             ");
            System.out.println("===========================================");
            System.out.println();
            System.out.println("1. Crear Caregoria");
            System.out.println("2. Ver Caregorias");
            System.out.println("3. Actualizar Caregoria");
            System.out.println("4. Eliminar Caregoria");
            System.out.println("5. Volver al Menú Principal");
            System.out.println();
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    crearCategoria();
                    break;
                case 2:
                    verCategorias();
                    break;
                case 3:
                    actualizarCategoria();
                    break;
                case 4:
                    eliminarCategoria();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Opción no válida, intenta de nuevo.");
            }
        }
    }

    public static Categoria obtenerSeleccionCategoria() throws SQLException {

        List<Categoria> categorias = categoriaController.obtenerTodos();

        for (Categoria categoria : categorias) {
            System.out.println(categoria);
        }

        System.out.print("Ingrese el id del categoria: \n");
        int categoriaId = scanner.nextInt();

        for (Categoria categoria : categorias) {
            if (categoria.getId().equals(categoriaId)) {
                return categoria;
            }
        }

        System.out.print("Id no valido, Intentar nuevamente: \n");
        return obtenerSeleccionCategoria();
    }

    public static Categoria crearCategoria() throws SQLException {

        Categoria categoria = new Categoria();
        scanner.nextLine();

        System.out.print("Ingrese la categoria :");
        String descripcion = scanner.nextLine();
        categoria.setCategoria(descripcion);

        try {
            categoriaController.crear(categoria);
            System.out.println("Categoria generada correctamente");
        } catch (SQLException e) {
            System.out.println("Error al generar la categoria, intentelo nuevamente");
            crearCategoria();
        }
        return categoria;

    }

    public static void verCategorias() throws SQLException {
        System.out.println("-------------------------------------------");
        System.out.println("CATEGORIAS");
        categoriaController.obtenerTodos().forEach(categoria -> {
            System.out.println("CATEGORIA ID " + categoria.getId());
            System.out.println(categoria);
            System.out.println("======================================");
        });
        System.out.println("CATEGORIAS");
        System.out.println("-------------------------------------------");
    }

    private static void eliminarCategoria() throws SQLException {
        verCategorias();
        System.out.print("Ingrese el id de la categoria a eliminar: ");
        Integer id = scanner.nextInt();
        Categoria categoria = categoriaController.obtenerPorId(id);

        if (categoria != null) {
            try {
                categoriaController.eliminar(id);
                System.out.println("Categoria eliminada con éxito.");
            } catch (SQLException e) {
                System.out.println("Error al querer eliminar la categoria, Intentar nuevamente.");
                eliminarCategoria();
            }

        } else {
            System.out.println("Categoria no encontrada, busque nuevamente.");
            eliminarCategoria();
        }
    }

    private static void actualizarCategoria() throws SQLException {
        verCategorias();
        System.out.print("Ingrese el id de la categoria a actualizar: ");
        Integer idPrecio = scanner.nextInt();
        Categoria categoria = categoriaController.obtenerPorId(idPrecio);
        if (categoria != null) {
            scanner.nextLine();

            System.out.print("Ingrese la categoria :");
            String categoriaNueva = scanner.nextLine();
            categoria.setCategoria(categoriaNueva);

            categoriaController.actualizar(categoria);

            System.out.println(categoria);
            System.out.println("Categoria actualizada correctamente.");
        } else {
            System.out.println("Categoria no encontrada busque nuevamente.");
            actualizarCategoria();
        }
    }

}
