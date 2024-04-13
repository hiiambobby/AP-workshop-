public class Person {
    private String userName;
    private int money;
    private Hospital hospital;
    private String passWord;

    public Person() {

    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public int getMoney() {
        return money;
    }

    public Hospital geHospital() {
        return hospital;
    }

}