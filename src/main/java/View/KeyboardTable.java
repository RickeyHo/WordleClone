package View;

import Model.LetterTile;
import Model.Status;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class KeyboardTable extends JTable {
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
}
