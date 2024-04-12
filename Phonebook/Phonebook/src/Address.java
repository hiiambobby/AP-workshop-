public class Address {
    private String zipCode;
    private String country;
    private String city;

    public Address(String zipCode, String country, String city) {
        this.city = city;
        this.zipCode = zipCode;
        this.country = country;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city.toLowerCase();
    }

    public String getCountry() {
        return country.toLowerCase();
    }

    public String getZipCode() {
        return zipCode.toLowerCase();
    }

    @Override
    public String toString() {
        return (zipCode + ", " + country + ", " + city).toLowerCase();
    }

}
