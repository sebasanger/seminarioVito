import java.sql.SQLException;
import java.util.Scanner;

import exceptions.CredencialesInvalidasException;
import services.UsuarioService;
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
import views.UsuarioView;

public class App {

    private static final Scanner scanner = new Scanner(System.in);
    private static UsuarioService usuarioService = new UsuarioService();

    public static void main(String[] args) throws SQLException {
        // mostrarMenuPrincipal();
        ejecutarLogin();
    }

    public static void ejecutarLogin() {
        System.out.print("Ingrese su email: ");
        String email = scanner.nextLine();

        System.out.print("Ingrese su contraseña: ");
        String password = scanner.nextLine();

        try {
            usuarioService.loginUsuario(email, password);
            mostrarMenuPrincipal();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            System.out.println("Intentelo nuevamente");
            ejecutarLogin();
        } catch (CredencialesInvalidasException e) {
            System.out.println("Intentelo nuevamente");
            ejecutarLogin();
        }
    }

    private static void mostrarMenuPrincipal() throws SQLException {
        while (true) {
            limpiarConsola();
            if (UsuarioService.getUsuarioActual() != null) {
                System.out.println("Bienvenido, " + UsuarioService.getUsuarioActual().getNombre() + " "
                        + UsuarioService.getUsuarioActual().getApellido());
            }

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
                    UsuarioView.mostrarMenuUsuarios();
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
                    System.out.println("Opcion no valida, intenta de nuevo.");
            }
        }
    }

    private static void limpiarConsola() {
        // Este método funciona dependiendo del terminal. Simula limpiar la consola.
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}
