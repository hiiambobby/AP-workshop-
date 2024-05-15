package src;

public abstract class Discount {
    String id;
    int expiration;
    public Discount(String id,int expiration)
    {
        this.id = id;
        this.expiration = expiration;
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
}
