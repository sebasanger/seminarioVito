package views;

import java.sql.SQLException;
import java.util.Scanner;

import controllers.UsuarioController;
import models.Usuario;

public class UsuarioView {

    private static final Scanner scanner = new Scanner(System.in);
    private static UsuarioController usuarioController = new UsuarioController();

    public static void mostrarMenuUsuarios() throws SQLException {
        while (true) {

            System.out.println("===========================================");
            System.out.println("           GESTIÓN DE USUARIOS             ");
            System.out.println("===========================================");
            System.out.println();
            System.out.println("1. Crear usuario");
            System.out.println("2. Ver usuarios");
            System.out.println("3. Actualizar usuario");
            System.out.println("4. Eliminar usuario");
            System.out.println("5. Volver al Menú Principal");
            System.out.println();
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    crearUsuario();
                    break;
                case 2:
                    verUsuarios();
                    break;
                case 3:
                    actualizarUsuario();
                    break;
                case 4:
                    eliminarUsuario();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Opción no válida, intenta de nuevo.");
            }
        }
    }

    public static Usuario crearUsuario() throws SQLException {

        Usuario usuario = new Usuario();
        scanner.nextLine();

        System.out.print("Ingrese el nombre :");
        String nombre = scanner.nextLine();
        usuario.setNombre(nombre);

        System.out.print("Ingrese el apellido :");
        String apellido = scanner.nextLine();
        usuario.setApellido(apellido);

        System.out.print("Ingrese el email :");
        String email = scanner.nextLine();
        usuario.setEmail(email);

        System.out.print("Ingrese la contraseña :");
        String password = scanner.nextLine();
        usuario.setPassword(password);

        System.out.println("Ingrese [0] para que sea rol administrador :");
        System.out.println("Ingrese [1] para que sea rol recepcionista :");
        System.out.println("Ingrese [2] para que sea rol de mantenimiento :");
        Integer rol = scanner.nextInt();

        switch (rol) {
            case 0:
                usuario.setRol("admin");
                break;
            case 1:
                usuario.setRol("recepcionista");
                break;
            default:
                usuario.setRol("limpieza");
                break;
        }

        try {
            usuarioController.crear(usuario);
            System.out.println("Usuario generada correctamente");
        } catch (SQLException e) {
            System.out.println("Error al generar el Usuario, intentelo nuevamente");
            crearUsuario();
        }
        return usuario;

    }

    public static void verUsuarios() throws SQLException {
        System.out.println("-------------------------------------------");
        System.out.println("USUARIOS");
        usuarioController.obtenerTodos().forEach(usuario -> {
            System.out.println("USUARIO ID " + usuario.getId());
            System.out.println(usuario);
            System.out.println("======================================");
        });
        System.out.println("USUARIOS");
        System.out.println("-------------------------------------------");
    }

    private static void eliminarUsuario() throws SQLException {
        verUsuarios();
        System.out.print("Ingrese el id del usuario a eliminar: ");
        Integer id = scanner.nextInt();
        Usuario usuario = usuarioController.obtenerPorId(id);

        if (usuario != null) {
            try {
                usuarioController.eliminar(id);
                System.out.println("Usuario eliminada con éxito.");
            } catch (SQLException e) {
                System.out.println("Error al querer eliminar el Usuario, Intentar nuevamente.");
                eliminarUsuario();
            }

        } else {
            System.out.println("Usuario no encontrado, busque nuevamente.");
            eliminarUsuario();
        }
    }

    private static void actualizarUsuario() throws SQLException {
        verUsuarios();
        System.out.print("Ingrese el id del usuario a actualizar: ");
        Integer id = scanner.nextInt();
        Usuario usuario = usuarioController.obtenerPorId(id);
        if (usuario != null) {
            scanner.nextLine();

            System.out.print("Ingrese el nombre :");
            String nombre = scanner.nextLine();
            usuario.setNombre(nombre);

            System.out.print("Ingrese el apellido :");
            String apellido = scanner.nextLine();
            usuario.setApellido(apellido);

            System.out.print("Ingrese el email :");
            String email = scanner.nextLine();
            usuario.setEmail(email);

            System.out.print("Ingrese la contraseña :");
            String password = scanner.nextLine();
            usuario.setPassword(password);

            try {
                usuarioController.actualizar(usuario);
                System.out.println("Usuario actualizado correctamente");
            } catch (SQLException e) {
                System.out.println("Error al actualizar el usuario");
            }
        } else {
            System.out.println("Usuario no encontrada busque nuevamente.");
            actualizarUsuario();
        }
    }

}
