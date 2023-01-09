
package com.salesinvoices.model;

import java.util.ArrayList;

/**
 *
 * @author Riham Fawzi
 */

public class SalesInvoice {
    private int num;
    private String datee;
    private String customeer;
    private ArrayList<Line> line;
    
    public SalesInvoice() {
    }

    public SalesInvoice(int num, String date, String customer) {
        this.num = num;
        this.datee = date;
        this.customeer = customer;
    }

    public double getInvoiceTotal() {
        double total = 0.0;
        if(line == null)  { total = 0.0;}
        else
        {
        for (Line line : getLine()) {
            total += line.getLineTotal();
        }
    }
                return total;
    }   
    
    public ArrayList<Line> getLine() {
        if (line == null) {
            line = new ArrayList<>();
        }
        return line;
    }

    public String getCustomeer() {
        return customeer;
    }

    public void setCustomeer(String customer) {
        this.customeer = customer;
    }

    public int getNumb() {
        return num;
    }

    public void setNumb(int num) {
        this.num = num;
    }

    public String getDatee() {
        return datee;
    }

    public void setDatee(String date) {
        this.datee = date;
    }

    @Override
    public String toString() {
        return "Invoice{" + "num=" + num + ", date=" + datee + ", customer=" + customeer + '}';
    }
    
    public String getAsCSV() {
        return num + "," + datee + "," + customeer;
    }
    
}
