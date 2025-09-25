package View;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import Control.GameControl;
import Model.LetterTile;
import Model.Status;

public class WordleView {

    public static void main(String[] args) throws Exception {

        GameControl control = new GameControl();
        boolean accessibilityMode = false;

        JFrame jFrame = new JFrame("Wordle");
        jFrame.setSize(550, 800);

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(JLabel.CENTER);

        JPanel tableKeyboardPanel = new JPanel();
        JPanel buttonPanel = new JPanel(new GridLayout());

        JTextField inputField = new JTextField(5);
        inputField.setFont(new Font("Arial", Font.BOLD, 50));
        inputField.setHorizontalAlignment(JTextField.CENTER);
        JTextField messages = new JTextField(40);
        messages.setEditable(false);
        messages.setBackground(new Color(30, 30, 30));
        messages.setForeground(Color.white);
        messages.setHorizontalAlignment(JTextField.CENTER);

        JButton button = new JButton("Submit");
        JButton saveButton = new JButton("Save & Quit");
        JButton loadSaveButton = new JButton("Load Previous Save");
        JButton accessButton = new JButton("Accessibility");


        String[] colNames = {"Col 1", "Col 2", "Col 3", "Col 4", "Col 5"};
        String[] KBcolNames = {"Col 1", "Col 2", "Col 3", "Col 4", "Col 5", "Col 6", "Col 7", "Col 8", "Col 9", "Col 10"};

        TableModel model = new DefaultTableModel(control.board.getGrid(), colNames) {

            @Override
            public boolean isCellEditable(int row, int column) {

                return false;

            }

        };


        WordleTable wordleView = new WordleTable();



        KeyboardTable keyboard = new KeyboardTable();
        keyboard.setModel(new DefaultTableModel(control.board.getKeyboard(), KBcolNames) {

            @Override
            public boolean isCellEditable(int row, int column) {

                return false;
            }

        });

        wordleView.setFont(new Font("Arial", Font.BOLD, 50));
        wordleView.setRowHeight(65);
        keyboard.setFont(new Font("Arial", Font.BOLD, 30));
        keyboard.setRowHeight(55);


        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("Submit")) {
                    try {
                        control.submit(inputField.getText().toUpperCase());
                        wordleView.setDefaultRenderer(Object.class, renderer);
                        wordleView.setModel(new DefaultTableModel(control.board.getGrid(), colNames) {

                            @Override
                            public boolean isCellEditable(int row, int column) {

                                return false;
                            }

                        });
                        keyboard.setModel(new DefaultTableModel(control.board.getKeyboard(), KBcolNames) {

                            @Override
                            public boolean isCellEditable(int row, int column) {

                                return false;
                            }

                        });
                        SwingUtilities.updateComponentTreeUI(jFrame);

                        if (control.board.getAttempts() == 6 && !control.board.isWinner()) {

                            messages.setText("GAME OVER - The answer was " + control.board.getAnswer().toUpperCase());


                        }

                        if (control.board.isWinner()){

                            messages.setText("Congratulations, you win.");

                        }

                    } catch (RuntimeException ex) {
                        messages.setText(ex.getCause().getMessage());
                    }
                }
                if (e.getActionCommand().equals("Save & Quit")){

                    control.saveGame();
                    System.exit(0);
                }
                if (e.getActionCommand().equals("Load Previous Save")){

                    try {
                        control.loadPrevGame();
}
                    catch (Exception ex) {
                        messages.setText("No previous save");
                        throw new RuntimeException(ex);
                    }
                    wordleView.setDefaultRenderer(Object.class, renderer);
                    wordleView.setModel(new DefaultTableModel(control.board.getGrid(), colNames) {

                        @Override
                        public boolean isCellEditable(int row, int column) {

                            return false;
                        }

                    });
                    SwingUtilities.updateComponentTreeUI(jFrame);


                }

                if (e.getActionCommand().equals("Accessibility")){

                    wordleView.isAccessible = !wordleView.isAccessible;
                    wordleView.setModel(new DefaultTableModel(control.board.getGrid(), colNames) {

                        @Override
                        public boolean isCellEditable(int row, int column) {

                            return false;
                        }

                    });
                }

            }

        };
        button.addActionListener(actionListener);
        inputField.addActionListener(actionListener);
        saveButton.addActionListener(actionListener);
        loadSaveButton.addActionListener(actionListener);
        accessButton.addActionListener(actionListener);
        wordleView.setModel(model);
        SwingUtilities.updateComponentTreeUI(jFrame);

        tableKeyboardPanel.setBorder(BorderFactory.createEmptyBorder(35, 35, 0, 35));
        tableKeyboardPanel.setLayout(new BoxLayout(tableKeyboardPanel, BoxLayout.Y_AXIS));
        tableKeyboardPanel.add(wordleView);
        tableKeyboardPanel.add(inputField);
        buttonPanel.setBackground(new Color(30, 30, 30));

        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10,10));
        buttonPanel.add(button);
        buttonPanel.add(saveButton);
        buttonPanel.add(loadSaveButton);
        buttonPanel.add(accessButton);
        tableKeyboardPanel.add(keyboard);
        tableKeyboardPanel.add(messages);
        tableKeyboardPanel.setBackground(new Color(30, 30, 30));

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
        jPanel.add(tableKeyboardPanel);
        jPanel.add(buttonPanel);

        jFrame.add(jPanel);
        jFrame.getContentPane().setBackground(Color.black);
        jFrame.setBackground(Color.black);

        jFrame.setVisible(true);

    }


}
