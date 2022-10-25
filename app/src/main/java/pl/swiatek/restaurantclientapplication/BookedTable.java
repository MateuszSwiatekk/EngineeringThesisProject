package pl.swiatek.restaurantclientapplication;

public class BookedTable {
    public String tableNumber, startDate,endDate,email;

    public BookedTable(){

    }

    public BookedTable(String tableNumber, String startDate,String endDate,String email){
        this.tableNumber=tableNumber;
        this.startDate=startDate;
        this.endDate=endDate;
        this.email=email;
    }
}
