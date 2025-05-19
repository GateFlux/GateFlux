/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package src.gateflux;

// A classe Session armazena, de forma estática, o nome de usuário (username) e o nível de acesso (role) do usuário logado, permitindo que esses dados sejam
//acessados de qualquer parte do sistema sem a necessidade de instanciar a classe.

public class Session {
    public static String username;
    public static int role;
}