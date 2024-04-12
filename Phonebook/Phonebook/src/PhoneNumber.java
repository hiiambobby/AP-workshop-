public class PhoneNumber {
    private String countryCode;
    private Character[] number = new Character[12];

    public PhoneNumber(String countryCode, String number) {
        this.countryCode = countryCode;
        for (int i = 0; i < number.length(); i++) {
            this.number[i] = number.charAt(i);
        }
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

    public void setNumber(String number) {
        for (int i = 0; i < number.length(); i++) {
            this.number[i] = number.charAt(i);
        }
        ;
    }

    @Override
    public String toString() {
        return (countryCode + " " + number).toLowerCase();
    }
}
