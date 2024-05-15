package src;

public abstract class Discount {
    String id;
    int expiration;
    String userId;
    public Discount(String id,int expiration,String userId)
    {
        this.id = id;
        this.expiration = expiration;
        this.userId = userId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setExpiration(int expiration) {
        this.expiration = expiration;
    }

    public String getId() {
        return id;
    }

    public int getExpiration() {
        return expiration;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
