package tools;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class MyTableModel extends AbstractTableModel {
    private String[] columnNames = {"idofcomplaint",
            "username",
            "Product",
            "Company",
            "Status",
            "RegistrationDate",
            "ForwardDate",
            "ResponseDate",
            "PickupDate",
            "CloseDate",
            "Description"
    };
    public Object[][] data = null;


    public MyTableModel(){
        data=new Object[15][15];
        for(int i=0;i<=14;i++){
            for(int j=0;j<=14;j++){
                data[i][j]= new String("");
            }
        }
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return data.length;
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col) {
        return data[row][col];
    }


    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    public boolean isCellEditable(int row, int col) {
            return true;
    }
    public void setValueAt(Object value, int row, int col) {
        data[row][col] = value;
        fireTableCellUpdated(row+1, col);
    }
}
