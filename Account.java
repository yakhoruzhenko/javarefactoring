public class Account {
    private String iban;
    private int daysOverdrawn;
    private BaseCustomer customer;
    private boolean premium;
    private double premiumAccountDiscount = 0.5;
    private double overdraftThreshold = 0;
    private Balance balance;


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

    public Balance getBalance() {
        return balance;
    }

    public void setBalance(double money, String currency) {
        this.balance = new Balance(money, currency);
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

    public BaseCustomer getCustomer() {
        return customer;
    }

    public void setCustomer(BaseCustomer customer) {
        this.customer = customer;
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
        accountDescription += "Account: IBAN: " + getIban() + ", Balance: " + balance.getMoney();
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
        if (!balance.getCurrency().equals(currency)) {
            throw new RuntimeException("Can't extract withdraw " + currency);
        }
    }

    public void withdrawPerson(double sum, String currency) {
        checkCurrency(currency);
        double baseWithdrawCalculation = balance.getMoney() - sum;
        if (balance.getMoney() < overdraftThreshold) {
            balance.setMoney(baseWithdrawCalculation - sum * overdraftFee());
        } else {
            balance.setMoney(baseWithdrawCalculation);
        }
    }

    public void withdrawCompany(double sum, String currency) {
        checkCurrency(currency);
        double baseWithdrawCalculation = balance.getMoney() - sum;
        double overdraftFee = sum * overdraftFee();
        CustomerCompany customerCompany = (CustomerCompany) customer;
        if (balance.getMoney() < overdraftThreshold) {
            if (isPremium()) {
                balance.setMoney(baseWithdrawCalculation - overdraftFee * customerCompany.companyOverdraftDiscount
                        * premiumAccountDiscount);
            } else {
                balance.setMoney(baseWithdrawCalculation - overdraftFee * customerCompany.companyOverdraftDiscount );
            }
        } else {
            balance.setMoney(baseWithdrawCalculation);
        }
    }

    public String printAccount() {
        return "Account: IBAN: " + getIban() + ", Balance: " + balance.getMoney() + ", Account type: " + getType();
    }

}
