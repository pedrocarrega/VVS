package vvs_dbsetup;

import static org.junit.Assume.*;
import static org.junit.jupiter.api.Assertions.*;
import static vvs_dbsetup.DBSetupUtils.DB_PASSWORD;
import static vvs_dbsetup.DBSetupUtils.DB_URL;
import static vvs_dbsetup.DBSetupUtils.DB_USERNAME;
import static vvs_dbsetup.DBSetupUtils.DELETE_ALL;
import static vvs_dbsetup.DBSetupUtils.INSERT_CUSTOMER_ADDRESS_DATA;
import static vvs_dbsetup.DBSetupUtils.startApplicationDatabaseForTesting;

import java.sql.SQLException;
import java.util.List;

import org.junit.*;

import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.DbSetupTracker;
import com.ninja_squad.dbsetup.Operations;
import com.ninja_squad.dbsetup.destination.Destination;
import com.ninja_squad.dbsetup.destination.DriverManagerDestination;
import com.ninja_squad.dbsetup.operation.Operation;

import webapp.services.ApplicationException;
import webapp.services.CustomerDTO;
import webapp.services.CustomerService;
import webapp.services.SaleDTO;
import webapp.services.SaleService;

public class DBTests {

	private static Destination dataSource;

	// the tracker is static because JUnit uses a separate Test instance for every test method.
	private static DbSetupTracker dbSetupTracker = new DbSetupTracker();

	@BeforeClass
	public static void setupClass() {
		startApplicationDatabaseForTesting();
		dataSource = DriverManagerDestination.with(DB_URL, DB_USERNAME, DB_PASSWORD);
	}

	@Before
	public void setup() throws SQLException {

		Operation initDBOperations = Operations.sequenceOf(
				DELETE_ALL
				, INSERT_CUSTOMER_ADDRESS_DATA
				);

		DbSetup dbSetup = new DbSetup(dataSource, initDBOperations);

		// Use the tracker to launch the DbSetup. This will speed-up tests 
		// that do not not change the BD. Otherwise, just use dbSetup.launch();
		dbSetupTracker.launchIfNecessary(dbSetup);

	}

	@Test
	public void repeatedVATS() throws ApplicationException {

		assumeTrue(DBUtils.hasClient(197672337));
		assertThrows(ApplicationException.class, () -> { CustomerService.INSTANCE.addCustomer(197672337, "New Customer", 123456789);});

	}

	@Test
	public void updateContact() throws ApplicationException {

		final int originalNumber = CustomerService.INSTANCE.getCustomerByVat(197672337).phoneNumber;
		final int expectedNumber = 123456789;

		//To make sure it's a different number
		assertFalse(originalNumber == expectedNumber);
		assertTrue(DBUtils.hasClient(197672337));
		CustomerService.INSTANCE.updateCustomerPhone(197672337, expectedNumber);
		assertFalse(originalNumber == CustomerService.INSTANCE.getCustomerByVat(197672337).phoneNumber);
		assertEquals(expectedNumber, CustomerService.INSTANCE.getCustomerByVat(197672337).phoneNumber);

	}

	@Test
	public void removeAll() throws ApplicationException {

		List<CustomerDTO> customersList = CustomerService.INSTANCE.getAllCustomers().customers;

		assertFalse(customersList.isEmpty());

		for(CustomerDTO c : customersList)
			CustomerService.INSTANCE.removeCustomer(c.vat);

		assertTrue(CustomerService.INSTANCE.getAllCustomers().customers.isEmpty());
	}

	@Test
	public void removeAndAdd() throws ApplicationException {

		assertTrue(DBUtils.hasClient(197672337));
		assertThrows(ApplicationException.class, () -> { CustomerService.INSTANCE.addCustomer(197672337, "New Customer", 123456789);});
		CustomerService.INSTANCE.removeCustomer(197672337);
		assertFalse(DBUtils.hasClient(197672337));
		CustomerService.INSTANCE.addCustomer(197672337, "New Client", 123465798);
		assertTrue(DBUtils.hasClient(197672337));

	}

	@Test
	public void removalOfSales() throws ApplicationException {

		assertTrue(DBUtils.hasClient(197672337));
		//It should be empty
		assertTrue(SaleService.INSTANCE.getAllSales().sales.isEmpty());

		//Adding two sales to the same customer
		SaleService.INSTANCE.addSale(197672337);
		SaleService.INSTANCE.addSale(197672337);
		assertEquals(2, SaleService.INSTANCE.getAllSales().sales.size());

		CustomerService.INSTANCE.removeCustomer(197672337);
		assertTrue(SaleService.INSTANCE.getAllSales().sales.isEmpty());

	}

	@Test
	public void salesNumber() throws ApplicationException {

		final int salesNumber = SaleService.INSTANCE.getAllSales().sales.size();

		assertTrue(DBUtils.hasClient(197672337));
		//It should be empty
		assertEquals(0, salesNumber);

		//Adding one sale
		SaleService.INSTANCE.addSale(197672337);
		assertEquals(salesNumber + 1, SaleService.INSTANCE.getAllSales().sales.size());
	}

	@Test
	public void closeSale() throws ApplicationException {

		assertTrue(DBUtils.hasClient(197672337));

		//Adding one sale
		SaleService.INSTANCE.addSale(197672337);
		assertEquals(1, SaleService.INSTANCE.getAllSales().sales.size());
		
		//getting the sale and verifying
		SaleDTO sale = SaleService.INSTANCE.getAllSales().sales.get(0);
		assertEquals(197672337, sale.customerVat);
		assertEquals("O", sale.statusId);
		
		//closing the sale
		SaleService.INSTANCE.updateSale(sale.id);
		sale = SaleService.INSTANCE.getAllSales().sales.get(0);
		assertEquals(197672337, sale.customerVat);
		assertEquals("C", sale.statusId);
		
	}
	
	@Test
	public void unixistenteClient() throws ApplicationException {
		
		assertFalse(DBUtils.hasClient(197672329));
		assertThrows(ApplicationException.class, () -> { SaleService.INSTANCE.addSale(197672329); });
		
	}
	
	@Test
	public void removalOfDeliveries() throws ApplicationException {

		assertTrue(DBUtils.hasClient(197672337));
		//It should be empty
		assertTrue(SaleService.INSTANCE.getAllSales().sales.isEmpty());

		//Adding two sales to the same customer
		SaleService.INSTANCE.addSale(197672337);
		SaleService.INSTANCE.addSale(197672337);
		assertEquals(2, SaleService.INSTANCE.getAllSales().sales.size());
		
		for(SaleDTO sale : SaleService.INSTANCE.getSaleByCustomerVat(197672337).sales)
			SaleService.INSTANCE.addSaleDelivery(sale.id, CustomerService.INSTANCE.getAllAddresses(sale.customerVat).addrs.get(0).id);

		assertEquals(2, SaleService.INSTANCE.getAllSales().sales.size());
		CustomerService.INSTANCE.removeCustomer(197672337);
		assertTrue(SaleService.INSTANCE.getSalesDeliveryByVat(197672337).sales_delivery.isEmpty());

	}
	
	@Test
	public void deliveryOfClosedSale() throws ApplicationException {
		
		assertTrue(DBUtils.hasClient(197672337));
		//It should be empty
		assertTrue(SaleService.INSTANCE.getAllSales().sales.isEmpty());

		//Adding two sales to the same customer
		SaleService.INSTANCE.addSale(197672337);
		SaleService.INSTANCE.addSale(197672337);
		assertEquals(2, SaleService.INSTANCE.getAllSales().sales.size());
		
		SaleDTO sale = SaleService.INSTANCE.getSaleByCustomerVat(197672337).sales.get(0);
		
		//Verify status
		assertEquals("O", sale.statusId);
		
		//Add delivery
		SaleService.INSTANCE.addSaleDelivery(sale.id, CustomerService.INSTANCE.getAllAddresses(sale.customerVat).addrs.get(0).id);
		assertEquals(1, SaleService.INSTANCE.getSalesDeliveryByVat(197672337).sales_delivery.size());
		
		//Close Sale
		SaleService.INSTANCE.updateSale(sale.id);
		//Updating the sale
		for(SaleDTO s : SaleService.INSTANCE.getSaleByCustomerVat(197672337).sales)
			if(s.id == sale.id)
				sale = s;
		
		//Verifying its closed
		assertEquals("C", sale.statusId);
		
		final SaleDTO auxSale = sale;
		//Trying to add a new delivery to a closed sale
		assertThrows(ApplicationException.class, () -> { SaleService.INSTANCE.addSaleDelivery(auxSale.id, CustomerService.INSTANCE.getAllAddresses(auxSale.customerVat).addrs.get(0).id); });
		
	}

}
