package com.ejemplo;

import java.sql.*;
import java.util.Scanner;

public class App
{
    private static final String URL = "jdbc:postgresql://localhost:5432/PrimerBaseDeDatos"; // Cambia el nombre de la base de datos si es necesario
    private static final String USER = "postgres"; // Usuario por defecto
    private static final String PASSWORD = "123qweasdzxc"; // Cambia esto por la contraseña que elegiste

    public static void main( String[] args ){
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            System.out.println("Conexión exitosa a la base de datos!");

            // Insertar un nuevo usuario
            //insertarUsuario(connection);
            // Leer usuarios
            System.out.println("Usuarios en la base de datos:");
            leerUsuarios(connection);
        } catch (SQLException e) {
            System.out.println("Error en la conexión: " + e.getMessage());
        }
    }
    private static void insertarUsuario(Connection connection) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingresa el nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Ingresa el correo: ");
        String correo = scanner.nextLine();

        System.out.print("Ingresa la fecha de nacimiento (YYYY-MM-DD): ");
        String fechaNacimiento = scanner.nextLine();

        String sql = "INSERT INTO usuarios (nombre, correo, fecha_nacimiento) VALUES (?, ?, ?::date)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            pstmt.setString(2, correo);
            pstmt.setString(3, fechaNacimiento);
            pstmt.executeUpdate();
            System.out.println("Usuario insertado exitosamente!");
        } catch (SQLException e) {
            System.out.println("Error al insertar el usuario: " + e.getMessage());
        }
    }

    public static void leerUsuarios(Connection connection) {
        String sql = "SELECT * FROM usuarios"; // Asegúrate de que este nombre coincide con el de tu tabla
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nombre = resultSet.getString("nombre");
                String correo = resultSet.getString("correo");
                Date fechaNacimiento = resultSet.getDate("fecha_nacimiento");
                System.out.printf("ID: %d, Nombre: %s, Correo: %s, Fecha de Nacimiento: %s\n",
                        id, nombre, correo, fechaNacimiento);
            }
        } catch (SQLException e) {
            System.out.println("Error al leer usuarios: " + e.getMessage());
        }
    }

}
