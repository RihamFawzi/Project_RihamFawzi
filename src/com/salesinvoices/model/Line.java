
package com.salesinvoices.model;

/**
 *
 * @author Riham Fawzi
 */

public class Line {
    private String item;
    private double price;
    private int count;
    private SalesInvoice invoice;

    public Line() {
    }

    public Line(String item, double price, int count, SalesInvoice invoice) {
        this.item = item;
        this.price = price;
        this.count = count;
        this.invoice = invoice;
    }

    public double getLineTotal() {
        return price * count;
    }
    
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Line{" + "num=" + invoice.getNumb() + ", item=" + item + ", price=" + price + ", count=" + count + '}';
    }

    public SalesInvoice getInvoice() {
        return invoice;
    }
    
    public String getAsCSV() {
        return invoice.getNumb() + "," + item + "," + price + "," + count;
    }
    
}
