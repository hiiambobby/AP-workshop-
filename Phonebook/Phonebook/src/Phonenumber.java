public class Phonenumber {
    private String countryCode;
    private Character[] number = new Character[12];
    public Phonenumber(String countryCode,Character number)
    {
        this.countryCode = countryCode;
        this.number = new Character[]{number};
    }

    public String getCountryCode() {
        return countryCode.toLowerCase();
    }

    public Character[] getNumber() {
        return number;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode.toLowerCase();
    }

    public void setNumber(Character number) {
        this.number = new Character[]{number};
    }

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}


