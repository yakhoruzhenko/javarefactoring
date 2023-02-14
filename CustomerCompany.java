public class CustomerCompany extends BaseCustomer {
    public CustomerCompany(String name, String email, Account account, double companyOverdraftDiscount) {
        this.name = name;
        this.email = email;
        this.customerType = CustomerType.COMPANY;
        this.account = account;
        this.companyOverdraftDiscount = companyOverdraftDiscount;
    }

    public void withdraw(double sum, String currency) {
        checkCurrency(currency);
        double overdraftFee = sum * account.overdraftFee();
        double baseWithdrawCalculation = account.getMoney() - sum;

        if (account.getMoney() < overdraftThreshold) {
            if (account.getType().isPremium()) {
                account.setMoney(baseWithdrawCalculation - overdraftFee * companyOverdraftDiscount
                        * premiumAccountDiscount);
            } else {
                account.setMoney(baseWithdrawCalculation - overdraftFee * companyOverdraftDiscount );
            }
        } else {
            account.setMoney(baseWithdrawCalculation);
        }
    }
}