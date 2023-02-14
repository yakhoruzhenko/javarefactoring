public class CustomerPerson extends BaseCustomer {
    private final String surname;

    public CustomerPerson(String name, String surname, String email, CustomerType customerType, Account account) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.customerType = customerType;
        this.account = account;
    }

    public String getFullName() {
        return name + " " + surname + " ";
    }
}