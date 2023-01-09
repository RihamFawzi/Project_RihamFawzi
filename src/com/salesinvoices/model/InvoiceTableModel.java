
package com.salesinvoices.model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Riham Fawzi
 */

public class InvoiceTableModel extends AbstractTableModel {
    private ArrayList<SalesInvoice> invoices;
    private String[] columns = {"No.", "Date", "Customer", "Total"};
    
    public InvoiceTableModel(ArrayList<SalesInvoice> invoices) {
        this.invoices = invoices;
    }
    
    @Override
    public int getRowCount() {
        return invoices.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        SalesInvoice invoice = invoices.get(rowIndex);
        
        switch (columnIndex) {
            case 0: return invoice.getNumb();
            case 1: return invoice.getDatee();
            case 2: return invoice.getCustomeer();
            case 3: return invoice.getInvoiceTotal();
            default : return "";
        }
    }
}
