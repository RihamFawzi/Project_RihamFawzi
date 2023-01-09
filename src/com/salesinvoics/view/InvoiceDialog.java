
package com.salesinvoics.view;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author Riham Fawzi
 */
public class InvoiceDialog extends JDialog {
    private JTextField custoNameField;
    private JTextField invDateeField;
    private JLabel custNameLabl;
    private JLabel invDateLabl;
    private JButton okBttn;
    private JButton cancelBttn;

    public InvoiceDialog(SalesInvoicesFrame frame) {
        custNameLabl = new JLabel("Customer Name:");
        custoNameField = new JTextField(20);
        invDateLabl = new JLabel("Invoice Date:");
        invDateeField = new JTextField(20);
        okBttn = new JButton("OK");
        cancelBttn = new JButton("Cancel");
        
        okBttn.setActionCommand("createInvoiceOK");
        cancelBttn.setActionCommand("createInvoiceCancel");
        
        okBttn.addActionListener(frame.getControlller());
        cancelBttn.addActionListener(frame.getControlller());
        setLayout(new GridLayout(3, 2));
        
        add(invDateLabl);
        add(invDateeField);
        add(custNameLabl);
        add(custoNameField);
        add(okBttn);
        add(cancelBttn);
        
        pack();
        
    }

    public JTextField getCustNameeField() {
        return custoNameField;
    }

    public JTextField getInvDateeField() {
        return invDateeField;
    }
    
}
