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
        if (account.getType().isPremium()) {
            if (account.getMoney() < OVERDRAFT_THRESHOLD) {
                account.setMoney((account.getMoney() - sum) - sum * account.overdraftFee() * companyOverdraftDiscount
                        * PREMIUM_ACCOUNT_DISCOUNT);
            } else {
                account.setMoney(account.getMoney() - sum);
            }
        } else {
            if (account.getMoney() < OVERDRAFT_THRESHOLD) {
                account.setMoney((account.getMoney() - sum) - sum * account.overdraftFee() * companyOverdraftDiscount);
            } else {
                account.setMoney(account.getMoney() - sum);
            }
        }
    }
}