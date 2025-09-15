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
        jFrame.setSize(400, 500);

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
        String[] colNames = {"Col 1", "Col 2", "Col 3", "Col 4", "Col 5"};
        TableModel model = new DefaultTableModel(control.board.getGrid(), colNames) {

            @Override
            public boolean isCellEditable(int row, int column) {

                return false;

            }

        };
        JTable wordleView = new JTable() {

            @Override
            public Class<?> getColumnClass(int column){

                return super.getColumnClass(column);

            }
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) { //I learned how to do this from this thread https://stackoverflow.com/questions/58598303/jtable-cell-render-based-on-content
                Object value = getValueAt(row, column);

                boolean isSelected = false;
                boolean hasFocus = false;

                // Only indicate the selection and focused cell if not printing
                if (!isPaintingForPrint()) {
                    isSelected = isCellSelected(row, column);

                    boolean rowIsLead =
                            (selectionModel.getLeadSelectionIndex() == row);
                    boolean colIsLead =
                            (columnModel.getSelectionModel().getLeadSelectionIndex() == column);

                    hasFocus = (rowIsLead && colIsLead) && isFocusOwner();
                }
                Component component;
                if (value instanceof LetterTile){

                    LetterTile tile = (LetterTile) value;

                    component =  renderer.getTableCellRendererComponent(this, tile.getCharacter(),
                            isSelected, hasFocus,
                            row, column);

                    if (tile.getStatus() == Status.BLACK){

                        component.setForeground(Color.white);
                        component.setBackground(Color.gray);

                    }
                    if (tile.getStatus() == Status.GREEN){

                        component.setForeground(Color.white);
                        component.setBackground(new Color(95, 153, 87));

                    }
                    if (tile.getStatus() == Status.YELLOW){

                        component.setForeground(Color.white);
                        component.setBackground(new Color(217, 180, 59));

                    }


                } else {

                    component = renderer.getTableCellRendererComponent(this, value,
                            isSelected, hasFocus,
                            row, column);
                    component.setBackground(new Color(30, 30, 30));
                    component.setSize(50, 50);
                    component.setForeground(Color.white);

                }

                return component;

            }


        };

        wordleView.setFont(new Font("Arial", Font.BOLD, 50));
        wordleView.setRowHeight(65);

        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    control.submit(textField.getText().toUpperCase());
                    wordleView.setDefaultRenderer(Object.class, renderer);
                    wordleView.setModel(new DefaultTableModel(control.board.getGrid(), colNames) {

                        @Override
                        public boolean isCellEditable(int row, int column){

                            return  false;
                        }

                    });
                    SwingUtilities.updateComponentTreeUI(jFrame);

                    if (control.board.getAttempts() == 6){

                        messages.setText("The answer was " + control.board.getAnswer().toUpperCase());

                    }
                } catch (RuntimeException ex) {
                    messages.setText(ex.getCause().getMessage());
                }

            }

        };
        button.addActionListener(actionListener);
        textField.addActionListener(actionListener);
        wordleView.setModel(model);
        SwingUtilities.updateComponentTreeUI(jFrame);

        jPanel.add(wordleView);
        jPanel.add(textField);
        jPanel.add(button);
        jPanel.add(messages);
        jPanel.setBackground(new Color(30, 30, 30));

        jFrame.add(jPanel);
        jFrame.getContentPane().setBackground(Color.black);
        jFrame.setBackground(Color.black);

        jFrame.setVisible(true);

    }


}
