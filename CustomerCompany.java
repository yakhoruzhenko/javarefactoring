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
}