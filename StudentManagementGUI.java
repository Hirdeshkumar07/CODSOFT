import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

class Student implements Serializable {
    private String name;
    private String rollNumber;
    private String grade;

    public Student(String name, String rollNumber, String grade) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.grade = grade;
    }

    public String getName() { return name; }
    public String getRollNumber() { return rollNumber; }
    public String getGrade() { return grade; }

    public void setName(String name) { this.name = name; }
    public void setRollNumber(String rollNumber) { this.rollNumber = rollNumber; }
    public void setGrade(String grade) { this.grade = grade; }
}

class StudentManagementSystem {
    private List<Student> students;
    private final String FILE_NAME = "students.dat";

    public StudentManagementSystem() {
        students = new ArrayList<>();
        loadFromFile();
    }

    public void addStudent(Student student) {
        students.add(student);
        saveToFile();
    }

    public void removeStudent(String rollNumber) {
        Student toRemove = searchStudent(rollNumber);
        if (toRemove != null) {
            students.remove(toRemove);
            saveToFile();
        }
    }

    public Student searchStudent(String rollNumber) {
        for (Student s : students) {
            if (s.getRollNumber().equalsIgnoreCase(rollNumber)) {
                return s;
            }
        }
        return null;
    }

    public List<Student> getAllStudents() {
        return students;
    }

    public void editStudent(String rollNumber, String newName, String newGrade) {
        Student student = searchStudent(rollNumber);
        if (student != null) {
            if (!newName.trim().isEmpty()) student.setName(newName);
            if (!newGrade.trim().isEmpty()) student.setGrade(newGrade);
            saveToFile();
        }
    }

    private void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(students);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            students = (List<Student>) ois.readObject();
        } catch (FileNotFoundException e) {
            students = new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            students = new ArrayList<>();
        }
    }
}

public class StudentManagementGUI extends JFrame {
    private StudentManagementSystem sms;
    private DefaultTableModel tableModel;
    private JTable studentTable;

    public StudentManagementGUI() {
        sms = new StudentManagementSystem();

        setTitle("Student Management System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Table
        String[] columnNames = {"Name", "Roll Number", "Grade"};
        tableModel = new DefaultTableModel(columnNames, 0);
        studentTable = new JTable(tableModel);
        loadTableData();
        JScrollPane scrollPane = new JScrollPane(studentTable);

        // Buttons
        JButton addButton = new JButton("Add Student");
        JButton editButton = new JButton("Edit Student");
        JButton removeButton = new JButton("Remove Student");
        JButton searchButton = new JButton("Search Student");
        JButton refreshButton = new JButton("Refresh");

        // Panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(refreshButton);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Button actions
        addButton.addActionListener(e -> addStudentDialog());
        editButton.addActionListener(e -> editStudentDialog());
        removeButton.addActionListener(e -> removeStudentDialog());
        searchButton.addActionListener(e -> searchStudentDialog());
        refreshButton.addActionListener(e -> loadTableData());

        setVisible(true);
    }

    private void loadTableData() {
        tableModel.setRowCount(0); // Clear table
        for (Student s : sms.getAllStudents()) {
            tableModel.addRow(new Object[]{s.getName(), s.getRollNumber(), s.getGrade()});
        }
    }

    private void addStudentDialog() {
        JTextField nameField = new JTextField();
        JTextField rollField = new JTextField();
        JTextField gradeField = new JTextField();
        Object[] message = {
            "Name:", nameField,
            "Roll Number:", rollField,
            "Grade:", gradeField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Add Student", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            if (nameField.getText().isEmpty() || rollField.getText().isEmpty() || gradeField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            sms.addStudent(new Student(nameField.getText(), rollField.getText(), gradeField.getText()));
            loadTableData();
        }
    }

    private void editStudentDialog() {
        String roll = JOptionPane.showInputDialog(this, "Enter Roll Number of student to edit:");
        if (roll == null) return;
        Student student = sms.searchStudent(roll);
        if (student == null) {
            JOptionPane.showMessageDialog(this, "Student not found!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        JTextField nameField = new JTextField(student.getName());
        JTextField gradeField = new JTextField(student.getGrade());
        Object[] message = {
            "New Name:", nameField,
            "New Grade:", gradeField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Edit Student", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            sms.editStudent(roll, nameField.getText(), gradeField.getText());
            loadTableData();
        }
    }

    private void removeStudentDialog() {
        String roll = JOptionPane.showInputDialog(this, "Enter Roll Number to remove:");
        if (roll == null) return;
        sms.removeStudent(roll);
        loadTableData();
    }

    private void searchStudentDialog() {
        String roll = JOptionPane.showInputDialog(this, "Enter Roll Number to search:");
        if (roll == null) return;
        Student student = sms.searchStudent(roll);
        if (student != null) {
            JOptionPane.showMessageDialog(this,
                    "Name: " + student.getName() + "\nRoll Number: " + student.getRollNumber() + "\nGrade: " + student.getGrade(),
                    "Student Found", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Student not found!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(StudentManagementGUI::new);
    }
}
