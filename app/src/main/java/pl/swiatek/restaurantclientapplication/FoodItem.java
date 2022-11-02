package pl.swiatek.restaurantclientapplication;

public class FoodItem {

    public String foodName,type;
    public double price;
    public int quantity;

    public FoodItem(){

    }

    public FoodItem(String foodName, String type, double price, int quantity){
        this.foodName=foodName;
        this.type=type;
        this.price=price;
        this.quantity=quantity;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
