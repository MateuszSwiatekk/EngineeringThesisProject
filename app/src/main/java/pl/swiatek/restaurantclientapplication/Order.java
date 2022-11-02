package pl.swiatek.restaurantclientapplication;

import java.util.List;

public class Order {

    public String table,email;

    public double total;

    public boolean finished;


    public List<FoodItem> items;

    public Order(){

    }

    public Order(String table, String email,double total,boolean finished){
        this.table=table;
        this.total=total;
        this.finished = finished;
        this.email=email;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public List<FoodItem> getItems() {
        return items;
    }

    public void setItems(List<FoodItem> items) {
        this.items = items;
    }
}
