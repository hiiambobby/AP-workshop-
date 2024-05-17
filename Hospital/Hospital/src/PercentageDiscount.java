package src;

public class PercentageDiscount extends Discount{
    public PercentageDiscount(String id,int date,String userName)
    {
        super(id,date,userName);
    }
    @Override
    public String toString() {
        return
                id + expiration + userId  ;
    }
}
