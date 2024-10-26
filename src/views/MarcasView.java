package views;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import controllers.MarcaController;
import models.Marca;

public class MarcasView {

    private static final Scanner scanner = new Scanner(System.in);
    private static MarcaController marcaController = new MarcaController();

    public static void mostrarMenuMarcas() throws SQLException {
        while (true) {

            System.out.println("======================================================");
            System.out.println("           GESTIÓN DE MARCAS DE PRODUCTOS             ");
            System.out.println("======================================================");
            System.out.println();
            System.out.println("1. Crear Marca");
            System.out.println("2. Ver Marcas");
            System.out.println("3. Actualizar Marca");
            System.out.println("4. Eliminar Marca");
            System.out.println("5. Volver al Menú Principal");
            System.out.println();
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    crearMarca();
                    break;
                case 2:
                    verMarcas();
                    break;
                case 3:
                    actualizarMarca();
                    break;
                case 4:
                    eliminarMarca();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Opción no válida, intenta de nuevo.");
            }
        }
    }

    public static Marca obtenerSeleccionMarca() throws SQLException {

        List<Marca> marcas = marcaController.obtenerTodos();

        for (Marca marca : marcas) {
            System.out.println(marca);
        }

        System.out.print("Ingrese el id de la marca: \n");
        int marcaId = scanner.nextInt();

        for (Marca marca : marcas) {
            if (marca.getId().equals(marcaId)) {
                return marca;
            }
        }

        System.out.print("Id no valido, Intentar nuevamente: \n");
        return obtenerSeleccionMarca();
    }

    public static Marca crearMarca() throws SQLException {

        Marca marca = new Marca();
        scanner.nextLine();

        System.out.print("Ingrese la marca :");
        String des = scanner.nextLine();
        marca.setMarca(des);

        try {
            marcaController.crear(marca);
            System.out.println("Marca generada correctamente");
        } catch (SQLException e) {
            System.out.println("Error al generar la marca, intentelo nuevamente");
            crearMarca();
        }
        return marca;

    }

    public static void verMarcas() throws SQLException {
        System.out.println("-------------------------------------------");
        System.out.println("MARCAS");
        marcaController.obtenerTodos().forEach(marca -> {
            System.out.println("MARCA ID " + marca.getId());
            System.out.println(marca);
            System.out.println("======================================");
        });
        System.out.println("MARCAS");
        System.out.println("-------------------------------------------");
    }

    private static void eliminarMarca() throws SQLException {
        verMarcas();
        System.out.print("Ingrese el id de la marca a eliminar: ");
        Integer id = scanner.nextInt();
        Marca marca = marcaController.obtenerPorId(id);

        if (marca != null) {
            try {
                marcaController.eliminar(id);
                System.out.println("marca eliminada con éxito.");
            } catch (SQLException e) {
                System.out.println("Error al querer eliminar la marca, Intentar nuevamente.");
                eliminarMarca();
            }

        } else {
            System.out.println("marca no encontrada, busque nuevamente.");
            eliminarMarca();
        }
    }

    private static void actualizarMarca() throws SQLException {
        verMarcas();
        System.out.print("Ingrese el id de la marca a actualizar: ");
        Integer idPrecio = scanner.nextInt();
        Marca marca = marcaController.obtenerPorId(idPrecio);
        if (marca != null) {
            scanner.nextLine();

            System.out.print("Ingrese la marca :");
            String marcaNueva = scanner.nextLine();
            marca.setMarca(marcaNueva);

            marcaController.actualizar(marca);

            System.out.println(marca);
            System.out.println("Marca actualizada correctamente.");
        } else {
            System.out.println("Marca no encontrada busque nuevamente.");
            actualizarMarca();
        }
    }

}
