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

    public String getTableNumber() { return tableNumber; }

    public void setTableNumber(String tableNumber) {
        this.tableNumber = tableNumber;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
