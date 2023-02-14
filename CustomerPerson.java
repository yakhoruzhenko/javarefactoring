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
        if (account.getType().isPremium()) {
            switch (customerType) {
                case COMPANY:
                    // we are in overdraft
                    if (account.getMoney() < 0) {
                        // 50 percent discount for overdraft for premium account
                        account.setMoney((account.getMoney() - sum) - sum * account.overdraftFee() * companyOverdraftDiscount / 2);
                    } else {
                        account.setMoney(account.getMoney() - sum);
                    }
                    break;
                case PERSON:
                    // we are in overdraft
                    if (account.getMoney() < 0) {
                        account.setMoney((account.getMoney() - sum) - sum * account.overdraftFee());
                    } else {
                        account.setMoney(account.getMoney() - sum);
                    }
                    break;
            }
        } else {
            switch (customerType) {
                case COMPANY:
                    // we are in overdraft
                    if (account.getMoney() < 0) {
                        // no discount for overdraft for not premium account
                        account.setMoney((account.getMoney() - sum) - sum * account.overdraftFee() * companyOverdraftDiscount);
                    } else {
                        account.setMoney(account.getMoney() - sum);
                    }
                    break;
                case PERSON:
                    // we are in overdraft
                    if (account.getMoney() < 0) {
                        account.setMoney((account.getMoney() - sum) - sum * account.overdraftFee());
                    } else {
                        account.setMoney(account.getMoney() - sum);
                    }
                    break;
            }
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