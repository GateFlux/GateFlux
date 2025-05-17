/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package src.gateflux;

/**
 *
 * @author isaque
 */
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
