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

        JFrame jFrame = new JFrame("Wordle");
        jFrame.setSize(950, 700);

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(JLabel.CENTER);

        JPanel jPanel = new JPanel();

        JTextField textField = new JTextField(5);
        JTextField messages = new JTextField(40);
        messages.setEditable(false);
        messages.setBackground(new Color(30, 30, 30));
        messages.setForeground(Color.white);
        messages.setHorizontalAlignment(JTextField.CENTER);

        JButton button = new JButton("Submit");
        JButton saveButton = new JButton("Save & Quit");
        JButton loadSaveButton = new JButton("Load Previous Save");

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


        wordleView.setFont(new Font("Arial", Font.BOLD, 50));
        wordleView.setRowHeight(65);
        keyboard.setFont(new Font("Arial", Font.BOLD, 30));
        keyboard.setRowHeight(55);


        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(e.getActionCommand());
                if (e.getActionCommand().equals("Submit")) {
                    try {
                        control.submit(textField.getText().toUpperCase());
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
                    } catch (RuntimeException ex) {
                        messages.setText(ex.getCause().getMessage());
                    }
                }
                if (e.getActionCommand().equals("Save & Quit")){

                    control.saveGame();
                    System.exit(0);
                }
                if (e.getActionCommand().equals("Load Previous Save")){

                    control.loadPrevGame();
                    wordleView.setDefaultRenderer(Object.class, renderer);
                    wordleView.setModel(new DefaultTableModel(control.board.getGrid(), colNames) {

                        @Override
                        public boolean isCellEditable(int row, int column) {

                            return false;
                        }

                    });
                    SwingUtilities.updateComponentTreeUI(jFrame);


                }

            }

        };
        button.addActionListener(actionListener);
        textField.addActionListener(actionListener);
        saveButton.addActionListener(actionListener);
        loadSaveButton.addActionListener(actionListener);
        wordleView.setModel(model);
        SwingUtilities.updateComponentTreeUI(jFrame);

        jPanel.add(wordleView);
        jPanel.add(textField);
        jPanel.add(button);
        jPanel.add(saveButton);
        jPanel.add(loadSaveButton);
        jPanel.add(keyboard);
        jPanel.add(messages);
        jPanel.setBackground(new Color(30, 30, 30));

        jFrame.add(jPanel);
        jFrame.getContentPane().setBackground(Color.black);
        jFrame.setBackground(Color.black);

        jFrame.setVisible(true);

    }


}
