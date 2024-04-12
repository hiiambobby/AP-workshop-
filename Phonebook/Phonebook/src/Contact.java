public class Contact {
    private String group;
    private String email;
    private String firstName;
    private String lastName;
    private Address address;
    private Phonenumber phoneNumber;

    public Contact() {

    }

    public Contact(String group, String email, String firstName, String lastName, Address address,
            Phonenumber phoneNumber) {
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.group = group;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void setGroup(String group) {
        this.group = group;

    }

    public void setEmail(String email) {
        this.email = email;

    }

    public void setFirstname(String firstName) {
        this.firstName = firstName;
    }

    public void setLasttname(String lastName) {
        this.lastName = lastName;
    }

}