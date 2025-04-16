package coursework_final;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AdminPanelPage {
    private JFrame frame;
    private JButton addQuestionButton, deleteQuestionButton, viewReportsButton;

    public AdminPanelPage() {
        frame = new JFrame("Admin Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(4, 1));

        JLabel label = new JLabel("Admin Panel", SwingConstants.CENTER);
        frame.add(label);

        addQuestionButton = new JButton("Add Question");
        deleteQuestionButton = new JButton("Delete Question");
        viewReportsButton = new JButton("View Reports");

        addQuestionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Logic to add questions (You can open another frame for adding questions)
                JOptionPane.showMessageDialog(frame, "Add Question Functionality");
            }
        });

        deleteQuestionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Logic to delete questions
                JOptionPane.showMessageDialog(frame, "Delete Question Functionality");
            }
        });

        viewReportsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Logic to view reports
                JOptionPane.showMessageDialog(frame, "View Reports Functionality");
            }
        });

        frame.add(addQuestionButton);
        frame.add(deleteQuestionButton);
        frame.add(viewReportsButton);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
