/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package src.gateflux;

/**
 *
 * @author isaque
 */
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AuditFrame extends JFrame {
    JTable table;
    DefaultTableModel model;

    public AuditFrame() {
        setTitle("Registros de Auditoria");
        setSize(800, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        model = new DefaultTableModel(new String[]{"ID", "Data Criação", "Pessoa", "Serviço", "Início", "Fim"}, 0);
        table = new JTable(model);
        loadData();

        JButton addBtn = new JButton("Adicionar");
        JButton editBtn = new JButton("Editar");
        JButton deleteBtn = new JButton("Excluir");

        addBtn.addActionListener(e -> openForm(null));
        editBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) openForm(model.getValueAt(row, 0).toString());
        });
        deleteBtn.addActionListener(e -> deleteSelected());

        JPanel btnPanel = new JPanel();
        btnPanel.add(addBtn);
        btnPanel.add(editBtn);
        btnPanel.add(deleteBtn);

        add(new JScrollPane(table), BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void loadData() {
        model.setRowCount(0);
        try (Connection conn = DBConnection.getConnection()) {
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM audit");
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getTimestamp("created_at"),
                    rs.getString("person_name"),
                    rs.getString("service"),
                    rs.getTimestamp("start_time"),
                    rs.getTimestamp("end_time")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void openForm(String id) {
        JTextField nome = new JTextField();
        JTextField servico = new JTextField();
        JTextField inicio = new JTextField();
        JTextField fim = new JTextField();

        if (id != null) {
            int row = table.getSelectedRow();
            nome.setText(model.getValueAt(row, 2).toString());
            servico.setText(model.getValueAt(row, 3).toString());
            inicio.setText(model.getValueAt(row, 4).toString());
            fim.setText(model.getValueAt(row, 5).toString());
        }

        JPanel panel = new JPanel(new GridLayout(4, 2));
        panel.add(new JLabel("Pessoa:"));
        panel.add(nome);
        panel.add(new JLabel("Serviço:"));
        panel.add(servico);
        panel.add(new JLabel("Início (YYYY-MM-DD HH:MM:SS):"));
        panel.add(inicio);
        panel.add(new JLabel("Fim (YYYY-MM-DD HH:MM:SS):"));
        panel.add(fim);

        int result = JOptionPane.showConfirmDialog(null, panel,
            id == null ? "Adicionar Registro" : "Editar Registro",
            JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            try (Connection conn = DBConnection.getConnection()) {
                if (id == null) {
                    String sql = "INSERT INTO audit (person_name, service, start_time, end_time) VALUES (?, ?, ?, ?)";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setString(1, nome.getText());
                    stmt.setString(2, servico.getText());
                    stmt.setString(3, inicio.getText());
                    stmt.setString(4, fim.getText());
                    stmt.executeUpdate();
                } else {
                    String sql = "UPDATE audit SET person_name=?, service=?, start_time=?, end_time=? WHERE id=?";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setString(1, nome.getText());
                    stmt.setString(2, servico.getText());
                    stmt.setString(3, inicio.getText());
                    stmt.setString(4, fim.getText());
                    stmt.setInt(5, Integer.parseInt(id));
                    stmt.executeUpdate();
                }
                loadData();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void deleteSelected() {
        int row = table.getSelectedRow();
        if (row == -1) return;

        int confirm = JOptionPane.showConfirmDialog(this, "Excluir este registro?", "Confirmação", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try (Connection conn = DBConnection.getConnection()) {
                int id = Integer.parseInt(model.getValueAt(row, 0).toString());
                PreparedStatement stmt = conn.prepareStatement("DELETE FROM audit WHERE id = ?");
                stmt.setInt(1, id);
                stmt.executeUpdate();
                loadData();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
