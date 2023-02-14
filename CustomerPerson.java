public class CustomerPerson extends BaseCustomer {

    private String surname;

    public CustomerPerson(String name, String surname, String email, CustomerType customerType, Account account) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.customerType = customerType;
        this.account = account;
    }

    public void withdraw(double sum, String currency) {
        checkCurrency(currency);
        if (account.getMoney() < 0) {
            account.setMoney((account.getMoney() - sum) - sum * account.overdraftFee());
        } else {
            account.setMoney(account.getMoney() - sum);
        }
    }

    public String printCustomerDaysOverdrawn() {
        String accountDescription = "Account: IBAN: " + account.getIban() + ", Days Overdrawn: " + account.getDaysOverdrawn();
        return getFullName() + accountDescription;
    }

    public String printCustomerMoney() {
        String accountDescription = "";
        accountDescription += "Account: IBAN: " + account.getIban() + ", Money: " + account.getMoney();
        return getFullName() + accountDescription;
    }

    public String getFullName() {
        return name + " " + surname + " ";
    }
}