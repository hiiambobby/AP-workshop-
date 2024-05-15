package src;

public class PriceBasedDiscount extends Discount{
    private int date;
    private String id;
    public PriceBasedDiscount(String id,int date)
    {
        super(id,date);
    }

}
