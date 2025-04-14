import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class TaskMate extends JFrame {
    private DefaultListModel<String> taskListModel;
    private JList<String> taskList;
    private JTextField taskInput;
    private JButton addButton, deleteButton, saveButton, loadButton;

    public TaskMate() {
        setTitle("TaskMate - To-Do List");
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        taskListModel = new DefaultListModel<>();
        taskList = new JList<>(taskListModel);
        JScrollPane scrollPane = new JScrollPane(taskList);

        taskInput = new JTextField();
        addButton = new JButton("Add Task");
        deleteButton = new JButton("Delete Task");
        saveButton = new JButton("Save");
        loadButton = new JButton("Load");

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 4));
        panel.add(addButton);
        panel.add(deleteButton);
        panel.add(saveButton);
        panel.add(loadButton);

        add(taskInput, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> {
            String task = taskInput.getText().trim();
            if (!task.isEmpty()) {
                taskListModel.addElement(task);
                taskInput.setText("");
            }
        });

        deleteButton.addActionListener(e -> {
            int selected = taskList.getSelectedIndex();
            if (selected != -1) {
                taskListModel.remove(selected);
            }
        });

        saveButton.addActionListener(e -> saveTasks());
        loadButton.addActionListener(e -> loadTasks());
    }

    private void saveTasks() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("tasks.txt"))) {
            for (int i = 0; i < taskListModel.size(); i++) {
                writer.write(taskListModel.getElementAt(i));
                writer.newLine();
            }
            JOptionPane.showMessageDialog(this, "Tasks saved successfully.");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error saving tasks.");
        }
    }

    private void loadTasks() {
        try (BufferedReader reader = new BufferedReader(new FileReader("tasks.txt"))) {
            taskListModel.clear();
            String line;
            while ((line = reader.readLine()) != null) {
                taskListModel.addElement(line);
            }
            JOptionPane.showMessageDialog(this, "Tasks loaded successfully.");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error loading tasks.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TaskMate().setVisible(true));
    }
}