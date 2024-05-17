package src;


public class PriceBasedDiscount extends Discount {

    public PriceBasedDiscount(String id, int expirationDate, String userName) {
        super(id, expirationDate, userName);
    }

    @Override
    public String toString() {
        return
                id + expiration + userId  ;
    }
}
