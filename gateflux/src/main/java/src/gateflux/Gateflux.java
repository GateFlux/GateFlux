/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package src.gateflux;

/**
 *
 * @author isaque
 */
public class Gateflux {
    public static void main(String[] args) {
        // Garante que a interface gráfica será criada na thread de eventos do Swing
        javax.swing.SwingUtilities.invokeLater(() -> {
            new LoginFrame(); // Abre a tela de login
        });
    }
}
