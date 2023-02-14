import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CustomerTest {
    @Test
    public void testPrintCustomer() throws Exception {
        CustomerPerson customer = getPersonWithAccount(false);
        assertThat(customer.printCustomer(), is("danix dan@mail.com"));
    }

    @Test
    public void testGetFullName() throws Exception {
        CustomerPerson customer = getPersonWithAccount(false);
        assertThat(customer.getFullName(), is("danix dan "));
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