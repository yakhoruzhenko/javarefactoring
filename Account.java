public class Account {
    private String iban;
    private int daysOverdrawn;
    private double money;
    private String currency;
    private BaseCustomer customer;
    private boolean premium;
    protected double premiumAccountDiscount = 0.5;
    protected double overdraftThreshold = 0;

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

    public String printDaysOverdrawn() {
        String accountDescription = "Account: IBAN: " + getIban() + ", Days Overdrawn: " + getDaysOverdrawn();
        if (customer instanceof CustomerPerson customerPerson) {
            return customerPerson.getFullName() + accountDescription;
        } else {
            return customer.getName() + accountDescription;
        }
    }

    public String printMoney() {
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

    public void checkCurrency(String currency) {
        if (!getCurrency().equals(currency)) {
            throw new RuntimeException("Can't extract withdraw " + currency);
        }
    }

    public void withdrawPerson(double sum, String currency) {
        checkCurrency(currency);
        double baseWithdrawCalculation = getMoney() - sum;
        if (getMoney() < overdraftThreshold) {
            setMoney(baseWithdrawCalculation - sum * overdraftFee());
        } else {
            setMoney(baseWithdrawCalculation);
        }
    }

    public void withdrawCompany(double sum, String currency) {
        checkCurrency(currency);
        double baseWithdrawCalculation = getMoney() - sum;
        double overdraftFee = sum * overdraftFee();
        CustomerCompany customerCompany = (CustomerCompany) customer;
        if (getMoney() < overdraftThreshold) {
            if (isPremium()) {
                setMoney(baseWithdrawCalculation - overdraftFee * customerCompany.companyOverdraftDiscount
                        * premiumAccountDiscount);
            } else {
                setMoney(baseWithdrawCalculation - overdraftFee * customerCompany.companyOverdraftDiscount );
            }
        } else {
            setMoney(baseWithdrawCalculation);
        }
    }

    public String printAccount() {
        return "Account: IBAN: " + getIban() + ", Money: " + getMoney() + ", Account type: " + getType();
    }

}
