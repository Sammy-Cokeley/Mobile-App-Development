package com.c323proj9.sammycokeley;

public class Purchase {

    private String expense;
    private String cost;
    private String date;
    private String category;
    private int image;

    public Purchase(String expense, String cost, String date, String category, int image) {
        this.expense = expense;
        this.cost = cost;
        this.date = date;
        this.category = category;
        this.image = image;
    }

    public String getExpense() {
        return expense;
    }

    public void setExpense(String expense) {
        this.expense = expense;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
