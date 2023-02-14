import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CustomerTest {
    @Test
    public void testPrintCustomerDaysOverdrawn() throws Exception {
        CustomerPerson customer = getPersonWithAccount(false);
        assertThat(customer.account.printCustomerDaysOverdrawn(), is("danix dan Account: IBAN: RO023INGB434321431241, Days Overdrawn: 9"));
    }

    @Test
    public void testPrintCustomerMoney() throws Exception {
        CustomerPerson customer = getPersonWithAccount(false);
        assertThat(customer.account.printCustomerMoney(), is("danix dan Account: IBAN: RO023INGB434321431241, Money: 34.0"));
    }

    @Test
    public void testPrintCustomerAccountNormal() throws Exception {
        BaseCustomer customer = getPersonWithAccount(false);
        assertThat(customer.printCustomerAccount(), is("Account: IBAN: RO023INGB434321431241, Money: 34.0, Account type: normal"));
    }

    @Test
    public void testPrintCustomerAccountPremium() throws Exception {
        BaseCustomer customer = getPersonWithAccount(true);
        assertThat(customer.printCustomerAccount(), is("Account: IBAN: RO023INGB434321431241, Money: 34.0, Account type: premium"));
    }

    private CustomerPerson getPersonWithAccount(boolean premium) {
        Account account = new Account(9, premium);
        CustomerPerson customer = getPersonCustomer(account);
        account.setIban("RO023INGB434321431241");
        account.setMoney(34.0);
        account.setCurrency("EUR");
        return customer;
    }

    private CustomerPerson getPersonCustomer(Account account) {
        CustomerPerson customer = new CustomerPerson("danix", "dan", "dan@mail.com", CustomerType.PERSON, account);
        account.setCustomer(customer);
        return customer;
    }
}