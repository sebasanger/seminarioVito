import java.sql.SQLException;
import java.util.Scanner;

import views.CategoriasView;
import views.ClientesView;
import views.ConsumosView;
import views.EntradaProductosView;
import views.HabitacionesView;
import views.MarcasView;
import views.PagosView;
import views.PreciosHabitacionesView;
import views.ProductoView;
import views.ReservasView;

public class App {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws SQLException {
        mostrarMenuPrincipal();
    }

    private static void mostrarMenuPrincipal() throws SQLException {
        while (true) {
            limpiarConsola();

            System.out.println("===========================================");
            System.out.println("           SISTEMA DE GESTIÓN HOTELERA     ");
            System.out.println("===========================================");
            System.out.println("\033[0;34m--- Menú Principal ---\033[0m");
            System.out.println("0. Salir");
            System.out.println("1. Gestionar Reservas");
            System.out.println("2. Gestionar Clientes");
            System.out.println("3. Gestionar Pagos");
            System.out.println("4. Gestionar Consumiciones");
            System.out.println("5. Gestionar Productos");
            System.out.println("6. Gestionar Habitaciones");
            System.out.println("7. Gestionar Usuarios");
            System.out.println("8. Gestionar Precios Habitaciones");
            System.out.println("9. Gestionar Categorias de Productos");
            System.out.println("10. Gestionar Marcas de Productos");
            System.out.println("11. Gestionar Entradas de Productos");

            System.out.println();
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();

            switch (opcion) {
                case 0:
                    System.out.println("Saliendo...");
                    System.exit(0);
                    break;
                case 1:
                    ReservasView.mostrarMenuReservas();
                    break;
                case 2:
                    ClientesView.mostrarMenuClientes();
                    break;
                case 3:
                    PagosView.mostrarMenuPagos();
                    break;
                case 4:
                    ConsumosView.mostrarMenuConsumos();
                    break;
                case 5:
                    ProductoView.mostrarMenuProductos();
                    break;
                case 6:
                    HabitacionesView.mostrarMenuHabitaciones();
                    break;
                case 7:
                    ProductoView.mostrarMenuProductos();
                    break;
                case 8:
                    PreciosHabitacionesView.mostrarMenuPreciosHabitaciones();
                    break;
                case 9:
                    CategoriasView.mostrarMenuCategorias();
                    break;
                case 10:
                    MarcasView.mostrarMenuMarcas();
                    break;
                case 11:
                    EntradaProductosView.mostrarMenuEntradasProductos();
                    break;
                default:
                    System.out.println("Opción no válida, intenta de nuevo.");
            }
        }
    }

    private static void limpiarConsola() {
        // Este método funciona dependiendo del terminal. Simula limpiar la consola.
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}
