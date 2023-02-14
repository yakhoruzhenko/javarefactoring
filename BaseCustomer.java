public class BaseCustomer {
    public static final int OVERDRAFT_THRESHOLD = 0;
    public static final double PREMIUM_ACCOUNT_DISCOUNT = 0.5;
    protected String name;
    protected String email;
    protected CustomerType customerType;
    protected Account account;
    protected double companyOverdraftDiscount = 1;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

    public void setCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }

    public String printCustomerAccount() {
        return "Account: IBAN: " + account.getIban() + ", Money: "
                + account.getMoney() + ", Account type: " + account.getType();
    }

    public void checkCurrency(String currency) {
        if (!account.getCurrency().equals(currency)) {
            throw new RuntimeException("Can't extract withdraw " + currency);
        }
    }
}
