import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class AccountTest {

    @Test
    public void testBankchargePremiumLessThanAWeek() {
        Account account = getPremiumAccount(5);
        assertThat(account.bankcharge(), is(14.5));
    }

    @Test
    public void testBankchargePremiumMoreThanAWeek() {
        Account account = getPremiumAccount(9);
        assertThat(account.bankcharge(), is(16.5));
    }

    @Test
    public void testOverdraftFeePremium() {
        Account account = getPremiumAccount(9);
        assertThat(account.overdraftFee(), is(0.10));
    }

    @Test
    public void testOverdraftFeeNotPremium() {
        Account account = getNormalAccount();
        assertThat(account.overdraftFee(), is(0.20));
    }

    @Test
    public void testPrintCustomer() {
        Account account = getNormalAccount();
        BaseCustomer customer = new CustomerPerson("xxx", "xxx", "xxx@mail.com", CustomerType.PERSON, account);
        account.setCustomer(customer);
        assertThat(account.getCustomer().printCustomer(), is("xxx xxx@mail.com"));
    }

    @Test
    public void testWithdrawPersonWithNormalAccount() throws Exception {
        Account account = getAccountByTypeAndMoney(false, 34.0);
        CustomerPerson customer = getPersonCustomer(account);
        account.withdrawPerson(10, "EUR");
        assertThat(account.getBalance().getMoney(), is(24.0));
    }

    @Test
    public void testWithdrawPersonWithNormalAccountAndOverdraft() throws Exception {
        Account account = getAccountByTypeAndMoney(false, -10.0);
        CustomerPerson customer = getPersonCustomer(account);
        account.withdrawPerson(10, "EUR");
        assertThat(account.getBalance().getMoney(), is(-22.0));
    }

    @Test
    public void testWithdrawPersonWithPremiumAccount() throws Exception {
        Account account = getAccountByTypeAndMoney(true, 34.0);
        CustomerPerson customer = getPersonCustomer(account);
        account.withdrawPerson(10, "EUR");
        assertThat(account.getBalance().getMoney(), is(24.0));
    }

    @Test
    public void testWithdrawPersonWithPremiumAccountAndOverdraft() throws Exception {
        Account account = getAccountByTypeAndMoney(true, -10.0);
        CustomerPerson customer = getPersonCustomer(account);
        account.withdrawPerson(10, "EUR");
        assertThat(account.getBalance().getMoney(), is(-21.0));
    }

    @Test
    public void testWithdrawCompanyWithNormalAccount() throws Exception {
        Account account = getAccountByTypeAndMoney(false, 34);
        CustomerCompany customer = getCompanyCustomer(account);
        account.withdrawCompany(10, "EUR");
        assertThat(account.getBalance().getMoney(), is(24.0));
    }

    @Test
    public void testWithdrawCompanyWithNormalAccountAndOverdraft() throws Exception {
        Account account = getAccountByTypeAndMoney(false, -10);
        CustomerCompany customer = getCompanyCustomer(account);
        account.withdrawCompany(10, "EUR");
        assertThat(account.getBalance().getMoney(), is(-21.0));
    }

    @Test
    public void testWithdrawCompanyWithPremiumAccount() throws Exception {
        Account account = getAccountByTypeAndMoney(true, 34);
        CustomerCompany customer = getCompanyCustomer(account);
        account.withdrawCompany(10, "EUR");
        assertThat(account.getBalance().getMoney(), is(24.0));
    }

    @Test
    public void testWithdrawCompanyWithPremiumAccountAndOverdraft() throws Exception {
        Account account = getAccountByTypeAndMoney(true, -10);
        CustomerCompany customer = getCompanyCustomer(account);
        account.withdrawCompany(10, "EUR");
        assertThat(account.getBalance().getMoney(), is(-20.25));
    }

    @Test
    public void testPrintAccountNormal() throws Exception {
        BaseCustomer customer = getPersonWithAccount(false);
        assertThat(customer.account.printAccount(), is("Account: IBAN: RO023INGB434321431241, Balance: 34.0, Account type: normal"));
    }

    @Test
    public void testPrintAccountPremium() throws Exception {
        BaseCustomer customer = getPersonWithAccount(true);
        assertThat(customer.account.printAccount(), is("Account: IBAN: RO023INGB434321431241, Balance: 34.0, Account type: premium"));
    }

    @Test
    public void testPrintDaysOverdrawn() throws Exception {
        CustomerPerson customer = getPersonWithAccount(false);
        assertThat(customer.account.printDaysOverdrawn(), is("danix dan Account: IBAN: RO023INGB434321431241, Days Overdrawn: 9"));
    }

    @Test
    public void testPrintMoney() throws Exception {
        CustomerPerson customer = getPersonWithAccount(false);
        assertThat(customer.account.printMoney(), is("danix dan Account: IBAN: RO023INGB434321431241, Balance: 34.0"));
    }

    private Account getNormalAccount() {
        return new Account(9, false);
    }

    private Account getPremiumAccount(int daysOverdrawn) {
        return new Account(daysOverdrawn, true);
    }

    private Account getAccountByTypeAndMoney(boolean premium, double money) {
        Account account = new Account(9, premium);
        account.setIban("RO023INGB434321431241");
        account.setBalance(money, "EUR");
        return account;
    }

    private CustomerCompany getCompanyCustomer(Account account) {
        CustomerCompany customer = new CustomerCompany("company", "company@mail.com", account, 0.50);
        account.setCustomer(customer);
        return customer;
    }

    private CustomerPerson getPersonWithAccount(boolean premium) {
        Account account = new Account(9, premium);
        CustomerPerson customer = getPersonCustomer(account);
        account.setIban("RO023INGB434321431241");
        account.setBalance(34.0, "EUR");
        return customer;
    }

    private CustomerPerson getPersonCustomer(Account account) {
        CustomerPerson customer = new CustomerPerson("danix", "dan", "dan@mail.com", CustomerType.PERSON, account);
        account.setCustomer(customer);
        return customer;
    }

}