public class Account {
    private String iban;
    private int daysOverdrawn;
    private double money;
    private String currency;
    private BaseCustomer customer;
    private boolean premium;

    public Account(int daysOverdrawn, boolean premium) {
        super();
        this.daysOverdrawn = daysOverdrawn;
        this.premium = premium;
    }

    public double bankcharge() {
        double result = 4.5;

        result += overdraftCharge();

        return result;
    }

    private double overdraftCharge() {
        if (isPremium()) {
            double result = 10;
            if (getDaysOverdrawn() > 7)
                result += (getDaysOverdrawn() - 7) * 1.0;
            return result;
        } else
            return getDaysOverdrawn() * 1.75;
    }

    public double overdraftFee() {
        if (isPremium()) {
            return 0.10;
        } else {
            return 0.20;
        }
    }


    public int getDaysOverdrawn() {
        return daysOverdrawn;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public double getMoney() {
        return money;
    }

    public BaseCustomer getCustomer() {
        return customer;
    }

    public void setCustomer(BaseCustomer customer) {
        this.customer = customer;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String printCustomerDaysOverdrawn() {
        String accountDescription = "Account: IBAN: " + getIban() + ", Days Overdrawn: " + getDaysOverdrawn();
        if (customer instanceof CustomerPerson customerPerson) {
            return customerPerson.getFullName() + accountDescription;
        } else {
            return customer.getName() + accountDescription;
        }
    }

    public String printCustomerMoney() {
        String accountDescription = "";
        accountDescription += "Account: IBAN: " + getIban() + ", Money: " + getMoney();
        if (customer instanceof CustomerPerson customerPerson) {
            return customerPerson.getFullName() + accountDescription;
        } else {
            return customer.getName() + accountDescription;
        }
    }

    public boolean isPremium() {
        return premium;
    }

    public String getType() {
        return premium ? "premium" : "normal";
    }
}
