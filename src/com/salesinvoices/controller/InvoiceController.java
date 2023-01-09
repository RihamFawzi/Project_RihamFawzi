package com.salesinvoices.controller;

import com.salesinvoices.model.SalesInvoice;
import com.salesinvoices.model.InvoiceTableModel;
import com.salesinvoices.model.Line;
import com.salesinvoices.model.LinesTableModell;
import com.salesinvoics.view.InvoiceDialog;
import com.salesinvoics.view.SalesInvoicesFrame;
import com.salesinvoics.view.LineDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class InvoiceController implements ActionListener, ListSelectionListener {

    private SalesInvoicesFrame fraame;
    private InvoiceDialog invoiceDia;
    private LineDialog lineDia;

    public InvoiceController(SalesInvoicesFrame frame) {
        this.fraame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        System.out.println("Action: " + actionCommand);
        switch (actionCommand) {
            case "Load File":
                loadingFile();
                break;
            case "Save File":
                saveFile();
                break;
            case "Create New Invoice":
                createNewInvoic();
                break;
            case "Delete Invoice":
                deleteInvoic();
                break;
            case "Create New Item":
                createNewItem();
                break;
            case "Delete Item":
                deleteItem();
                break;
            case "createInvoiceCancel":
                createInvoicCancel();
                break;
            case "createInvoiceOK":
                createInvoicOK();
                break;
            case "createLineOK":
                createLineeOK();
                break;
            case "createLineCancel":
                createLineeCancel();
                break;
        }
    }

    @Override
   public void valueChanged(ListSelectionEvent e) {
        int selectedIndex = fraame.getInvoiceTablee().getSelectedRow();
        if (selectedIndex != -1) {
            System.out.println("You have selected row: " + selectedIndex);
            SalesInvoice currentInvoice = fraame.getInvoice().get(selectedIndex);
            fraame.getInvoiceNumbLabel().setText("" + currentInvoice.getNumb());
            fraame.getInvoiceDateeLabel().setText(currentInvoice.getDatee());
            fraame.getCustomeerNameLabel().setText(currentInvoice.getCustomeer());
            fraame.getInvoiceTotallLabel().setText("" + currentInvoice.getInvoiceTotal());
            LinesTableModell linesTableModel = new LinesTableModell(currentInvoice.getLine());
            fraame.getLineeTable().setModel(linesTableModel);
            linesTableModel.fireTableDataChanged();
        }
    }
   
    private void loadingFile() {
       JFileChooser fc = new JFileChooser();
        try {
            int result = fc.showOpenDialog(fraame);
            if (result == JFileChooser.APPROVE_OPTION) {
                File headerFile = fc.getSelectedFile();
                Path headerPath = Paths.get(headerFile.getAbsolutePath());
                List<String> headerLines = Files.readAllLines(headerPath);
                System.out.println("Invoices have been read");
                
                // 1,22-11-2020,Ali
                // 2,13-10-2021,Saleh
                
                ArrayList<SalesInvoice> invoicesArray = new ArrayList<>();
                for (String headerLine : headerLines) {
                    try {
                        String[] headerParts = headerLine.split(",");
                        int invoiceNum = Integer.parseInt(headerParts[0]);
                        String invoiceDate = headerParts[1];
                        String customerName = headerParts[2];

                        SalesInvoice invoice = new SalesInvoice(invoiceNum, invoiceDate, customerName);
                        invoicesArray.add(invoice);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(fraame, "Error in line format", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                System.out.println("Check point");
                result = fc.showOpenDialog(fraame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File lineFile = fc.getSelectedFile();
                    Path linePath = Paths.get(lineFile.getAbsolutePath());
                    List<String> lineLines = Files.readAllLines(linePath);
                    System.out.println("Lines have been read");
                    for (String lineLine : lineLines) {
                        try {
                            String lineParts[] = lineLine.split(",");
                            int invoiceNum = Integer.parseInt(lineParts[0]);
                            String itemName = lineParts[1];
                            double itemPrice = Double.parseDouble(lineParts[2]);
                            int count = Integer.parseInt(lineParts[3]);
                            SalesInvoice inv = null;
                            for (SalesInvoice invoice : invoicesArray) {
                                if (invoice.getNumb() == invoiceNum) {
                                    inv = invoice;
                                    break;
                                }
                            }

                            Line line = new Line(itemName, itemPrice, count, inv);
                            inv.getLine().add(line);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(fraame, "Error in line format", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    System.out.println("Check point");
                }
                fraame.setInvoice(invoicesArray);
                InvoiceTableModel invoicesTableModel = new InvoiceTableModel(invoicesArray);
                fraame.setInvoiceTableModel(invoicesTableModel);
                fraame.getInvoiceTablee().setModel(invoicesTableModel);
                fraame.getInvoicesTableModel().fireTableDataChanged();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(fraame, "Cannot read file", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveFile() {
        ArrayList<SalesInvoice> invoices = fraame.getInvoice();
        String headers = "";
        String lines = "";
        for (SalesInvoice invoice : invoices) {
            String invCSV = invoice.getAsCSV();
            headers += invCSV;
            headers += "\n";

            for (Line line : invoice.getLine()) {
                String lineCSV = line.getAsCSV();
                lines += lineCSV;
                lines += "\n";
            }
        }
        System.out.println("Check point");
        try {
            JFileChooser fc = new JFileChooser();
            int result = fc.showSaveDialog(fraame);
            if (result == JFileChooser.APPROVE_OPTION) {
                File headerFile = fc.getSelectedFile();
                FileWriter hfw = new FileWriter(headerFile);
                hfw.write(headers);
                hfw.flush();
                hfw.close();
                result = fc.showSaveDialog(fraame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File lineFile = fc.getSelectedFile();
                    FileWriter lfw = new FileWriter(lineFile);
                    lfw.write(lines);
                    lfw.flush();
                    lfw.close();
                }
            }
        } catch (Exception ex) {

        }
    }

    private void createNewInvoic() {
        invoiceDia = new InvoiceDialog(fraame);
        invoiceDia.setVisible(true);
    }

    private void deleteInvoic() {
        int selectedRow = fraame.getInvoiceTablee().getSelectedRow();
        if (selectedRow != -1) {
            fraame.getInvoice().remove(selectedRow);
            fraame.getInvoicesTableModel().fireTableDataChanged();
        }
    }

    private void createNewItem() {
        lineDia = new LineDialog(fraame);
        lineDia.setVisible(true);
    }

    private void deleteItem() {
        int selectedRow = fraame.getLineeTable().getSelectedRow();

        if (selectedRow != -1) {
            LinesTableModell linesTableModel = (LinesTableModell) fraame.getLineeTable().getModel();
            linesTableModel.getLines().remove(selectedRow);
            linesTableModel.fireTableDataChanged();
            fraame.getInvoicesTableModel().fireTableDataChanged();
        }
    }

    private void createInvoicCancel() {
        invoiceDia.setVisible(false);
        invoiceDia.dispose();
        invoiceDia = null;
    }

    private void createInvoicOK() {
       String date = invoiceDia.getInvDateeField().getText();
        String customer = invoiceDia.getCustNameeField().getText();
        int num = fraame.getNextInvoiceNumb();
        try {
            String[] dateParts = date.split("-");  // "10-08-2015" -> {"10", "08", "2015"} 
            if (dateParts.length < 3) {
                JOptionPane.showMessageDialog(fraame, "Wrong date format", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                int day = Integer.parseInt(dateParts[0]);
                int month = Integer.parseInt(dateParts[1]);
                int year = Integer.parseInt(dateParts[2]);
                if (day > 31 || month > 12 || year > 2100) {
                    JOptionPane.showMessageDialog(fraame, "Wrong date format", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    SalesInvoice invoice = new SalesInvoice(num, date, customer);
                    fraame.getInvoice().add(invoice);
                    fraame.getInvoicesTableModel().fireTableDataChanged();
                    invoiceDia.setVisible(false);
                    invoiceDia.dispose();
                    invoiceDia = null;
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(fraame, "Wrong date format", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }


    private void createLineeOK() {
        String item = lineDia.getItemNameeField().getText();
        String countStr = lineDia.getItemCounttField().getText();
        String priceStr = lineDia.getItemPriceeField().getText();
        int count = Integer.parseInt(countStr);
        double price = Double.parseDouble(priceStr);
        int selectedInvoice = fraame.getInvoiceTablee().getSelectedRow();
        if (selectedInvoice != -1) {
            SalesInvoice invoice = fraame.getInvoice().get(selectedInvoice);
            Line line = new Line(item, price, count, invoice);
            invoice.getLine().add(line);
            LinesTableModell linesTableModel = (LinesTableModell) fraame.getLineeTable().getModel();
            //linesTableModel.getLine().add(line);
            linesTableModel.fireTableDataChanged();
            fraame.getInvoicesTableModel().fireTableDataChanged();
        }
        lineDia.setVisible(false);
        lineDia.dispose();
        lineDia = null;
    }

    private void createLineeCancel() {
        lineDia.setVisible(false);
        lineDia.dispose();
        lineDia = null;
    }

}
