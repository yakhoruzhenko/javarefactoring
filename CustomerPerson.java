public class CustomerPerson extends BaseCustomer {
    private final String surname;

    public CustomerPerson(String name, String surname, String email, CustomerType customerType, Account account) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.customerType = customerType;
        this.account = account;
    }

    public void withdraw(double sum, String currency) {
        checkCurrency(currency);
        if (account.getMoney() < overdraftThreshold) {
            account.setMoney((account.getMoney() - sum) - sum * account.overdraftFee());
        } else {
            account.setMoney(account.getMoney() - sum);
        }
    }

    public String getFullName() {
        return name + " " + surname + " ";
    }
}