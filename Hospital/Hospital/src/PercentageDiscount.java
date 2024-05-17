package src;

public class PercentageDiscount extends Discount{
    private String userName;
    private int date;
    private String discountId;
    public PercentageDiscount(String id,int date,String userName)
    {
        super(id,date,userName);
    }
}
