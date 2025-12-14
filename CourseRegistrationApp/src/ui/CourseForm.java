package ui;

import model.Course;
import service.CourseService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.regex.Pattern;

public class CourseForm extends JFrame {

    JTextField txtId, txtName, txtCode, txtCredit;
    JTable table;
    DefaultTableModel model;
    CourseService service = new CourseService();

    public CourseForm() {

        setTitle("Course Registration System");
        setSize(750, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // ===== Form Panel =====
        JPanel panel = new JPanel(new GridLayout(5, 2));

        panel.add(new JLabel("Course ID"));
        txtId = new JTextField();
        txtId.setEditable(false);
        panel.add(txtId);

        panel.add(new JLabel("Course Name"));
        txtName = new JTextField();
        panel.add(txtName);

        panel.add(new JLabel("Course Code"));
        txtCode = new JTextField();
        panel.add(txtCode);

        panel.add(new JLabel("Credit"));
        txtCredit = new JTextField();
        panel.add(txtCredit);

        // ===== Buttons =====
        JButton btnAdd = new JButton("Add");
        JButton btnUpdate = new JButton("Update");
        JButton btnDelete = new JButton("Delete");
        JButton btnClear = new JButton("Clear");

        JPanel btnPanel = new JPanel();
        btnPanel.add(btnAdd);
        btnPanel.add(btnUpdate);
        btnPanel.add(btnDelete);
        btnPanel.add(btnClear);

        // ===== Table =====
        model = new DefaultTableModel(
                new String[]{"ID", "Name", "Code", "Credit"}, 0);
        table = new JTable(model);

        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);

        // ===== Button Actions =====
        btnAdd.addActionListener(e -> addCourse());
        btnUpdate.addActionListener(e -> updateCourse());
        btnDelete.addActionListener(e -> deleteCourse());
        btnClear.addActionListener(e -> clearFields());

        // ===== Table Click Event =====
        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                txtId.setText(model.getValueAt(row, 0).toString());
                txtName.setText(model.getValueAt(row, 1).toString());
                txtCode.setText(model.getValueAt(row, 2).toString());
                txtCredit.setText(model.getValueAt(row, 3).toString());
            }
        });

        loadTable();
        setVisible(true);
    }

    // ===== Methods =====

    private void addCourse() {

        if (!Pattern.matches("^[A-Za-z ]+$", txtName.getText())) {
            JOptionPane.showMessageDialog(this, "Invalid Course Name");
            return;
        }

        if (!Pattern.matches("^[A-Z]{3}[0-9]{3}$", txtCode.getText())) {
            JOptionPane.showMessageDialog(this, "Course Code must be like CSC101");
            return;
        }

        if (!Pattern.matches("^[0-9]+$", txtCredit.getText())) {
            JOptionPane.showMessageDialog(this, "Credit must be a number");
            return;
        }

        service.addCourse(new Course(
                0,
                txtName.getText(),
                txtCode.getText(),
                Integer.parseInt(txtCredit.getText())
        ));

        loadTable();
        clearFields();
    }

    private void updateCourse() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Select a course to update");
            return;
        }

        service.updateCourse(new Course(
                Integer.parseInt(txtId.getText()),
                txtName.getText(),
                txtCode.getText(),
                Integer.parseInt(txtCredit.getText())
        ));

        loadTable();
        clearFields();
    }

    private void deleteCourse() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            service.deleteCourse((int) model.getValueAt(row, 0));
            loadTable();
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Select a course to delete");
        }
    }

    private void loadTable() {
        model.setRowCount(0);
        for (Course c : service.getAllCourses()) {
            model.addRow(new Object[]{
                    c.getCourseId(),
                    c.getCourseName(),
                    c.getCourseCode(),
                    c.getCredit()
            });
        }
    }

    private void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtCode.setText("");
        txtCredit.setText("");
    }

    public static void main(String[] args) {
        new CourseForm();
    }
}