
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
public class LineDialog extends JDialog{
    private JTextField itemNameeField;
    private JTextField itemCounttField;
    private JTextField itemPriceeField;
    private JLabel itemNameLabl;
    private JLabel itemCountLabl;
    private JLabel itemPriceLabl;
    private JButton okBttn;
    private JButton cancelBttn;
    
    public LineDialog(SalesInvoicesFrame frame) {
        itemNameeField = new JTextField(20);
        itemNameLabl = new JLabel("Item Name");
        
        itemCounttField = new JTextField(20);
        itemCountLabl = new JLabel("Item Count");
        
        itemPriceeField = new JTextField(20);
        itemPriceLabl = new JLabel("Item Price");
        
        okBttn = new JButton("OK");
        cancelBttn = new JButton("Cancel");
        
        okBttn.setActionCommand("createLineOK");
        cancelBttn.setActionCommand("createLineCancel");
        
        okBttn.addActionListener(frame.getControlller());
        cancelBttn.addActionListener(frame.getControlller());
        setLayout(new GridLayout(4, 2));
        
        add(itemNameLabl);
        add(itemNameeField);
        add(itemCountLabl);
        add(itemCounttField);
        add(itemPriceLabl);
        add(itemPriceeField);
        add(okBttn);
        add(cancelBttn);
        
        pack();
    }

    public JTextField getItemNameeField() {
        return itemNameeField;
    }

    public JTextField getItemCounttField() {
        return itemCounttField;
    }

    public JTextField getItemPriceeField() {
        return itemPriceeField;
    }
}
