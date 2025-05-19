/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package src.gateflux;

//Este código define uma classe chamada DBConnection que fornece um método estático getConnection() para estabelecer uma conexão com um banco de dados MySQL
//usando JDBC. O método utiliza os dados de URL, usuário e senha (que devem ser preenchidos) para retornar uma instância de Connection. Ele é útil para 
//centralizar e reutilizar a lógica de conexão com o banco de dados em uma aplicação Java.

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://<IP>:3306/<DATABASE>";
        String user = "<USER>";
        String password = "<SENHA>";
        return DriverManager.getConnection(url, user, password);
    }
}
