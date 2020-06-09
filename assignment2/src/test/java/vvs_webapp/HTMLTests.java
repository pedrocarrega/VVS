package vvs_webapp;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

import org.junit.*;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;

public class HTMLTests {

	private final HTMLUtils utils = new HTMLUtils();
	private static final String APPLICATION_URL = "http://localhost:8080/VVS_webappdemo/";
	private final int CLIENTS_TO_ADD = 2;
	private int customersUsed = 0;
	private String[][] clients = utils.fillClients();;

	private static HtmlPage page;

	@BeforeClass
	public static void setUpClass() throws Exception {
		try (final WebClient webClient = new WebClient(BrowserVersion.getDefault())) { 

			// possible configurations needed to prevent JUnit tests to fail for complex HTML pages
			webClient.setJavaScriptTimeout(15000);
			webClient.getOptions().setThrowExceptionOnScriptError(false);
			webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
			webClient.getOptions().setCssEnabled(false);
			webClient.setAjaxController(new NicelyResynchronizingAjaxController());
			webClient.getOptions().setJavaScriptEnabled(true);
			webClient.getOptions().setThrowExceptionOnScriptError(false);

			page = webClient.getPage(APPLICATION_URL);
			assertEquals(200, page.getWebResponse().getStatusCode()); // OK status
		}

	}

	@Before
	public void populateClient() throws FailingHttpStatusCodeException, IOException {

		utils.addCustomer(clients[0][0], clients[0][1], clients[0][2]);
		customersUsed++;

	}

	@After
	public void tearDown() throws FailingHttpStatusCodeException, IOException {

		for(int i = 0; i < customersUsed; i++)
			utils.removeCustomer(clients[i][0]);

		customersUsed = 0;

	}

	@Test
	public void addressInserting() throws Exception {

		int expected_value;
		String[][] input = {{"Street 1", "1", "133-119", "Lisboa"}, {"Street 2", "2", "133-118", "Lisboa"}};

		//in case the client has no addresses the table doesn't exist throwing a nullpointer exception
		try {
			expected_value = utils.getTable(utils.getClient(clients[0][0]).getBody()).getRows().size() + 2;
		} catch (NullPointerException e) {
			expected_value = 2;
		}

		// get a specific link
		HtmlAnchor addAddressLink = page.getAnchorByHref("addAddressToCustomer.html");
		// click on it
		HtmlPage nextPage = (HtmlPage) addAddressLink.openLinkInNewWindow();
		// check if title is the one expected
		assertEquals("Enter Address", nextPage.getTitleText());

		// get the page first form:
		HtmlForm addCustomerForm = nextPage.getForms().get(0);

		// check if form has the excepted fields
		addCustomerForm.getInputByName("vat").setValueAttribute("");
		addCustomerForm.getInputByName("address").setValueAttribute("");
		addCustomerForm.getInputByName("door").setValueAttribute("");
		addCustomerForm.getInputByName("postalCode").setValueAttribute("");
		addCustomerForm.getInputByName("locality").setValueAttribute("");

		//submit page
		utils.addAddress(clients[0][0], input[0][0], input[0][1], input[0][2], input[0][3]);

		//submit page
		utils.addAddress(clients[0][0], input[1][0], input[1][1], input[1][2], input[1][3]);

		// get a specific link
		addAddressLink = page.getAnchorByHref("getCustomerByVAT.html");
		// click on it
		nextPage = (HtmlPage) addAddressLink.openLinkInNewWindow();
		// check if title is the one expected
		assertEquals("Enter Name", nextPage.getTitleText());

		// get the page first form:
		addCustomerForm = nextPage.getForms().get(0);

		// check if form has the excepted fields
		addCustomerForm.getInputByName("vat").setValueAttribute("");

		//get table from page
		HtmlTable table = utils.getTable(utils.getClient(clients[0][0]).getBody());

		int i = 0;
		for(HtmlTableRow row : table.getRows().subList(table.getRowCount()-2, table.getRowCount())) {
			assertTrue(row.asText().equals(input[i][0] + "\t" + input[i][1] + "\t" + input[i][2] + "\t" + input[i][3]));
			i++;
		}

		assertEquals(expected_value, table.getRowCount()-1);

	}


	@Test
	public void addClients() throws Exception {

		// get a specific link
		HtmlAnchor link = page.getAnchorByHref("GetAllCustomersPageController");
		// click on it
		HtmlPage nextPage = (HtmlPage) link.openLinkInNewWindow();
		// check if title is the one expected
		assertEquals("Customers Info", nextPage.getTitleText());

		HtmlTable original_table = utils.getTable(nextPage.getBody());

		// get a specific link
		link = page.getAnchorByHref("addAddressToCustomer.html");
		// click on it
		nextPage = (HtmlPage) link.openLinkInNewWindow();
		// check if title is the one expected
		assertEquals("Enter Address", nextPage.getTitleText());

		// get the page first form:
		HtmlForm addCustomerForm = nextPage.getForms().get(0);

		// check if form has the excepted fields
		addCustomerForm.getInputByName("vat").setValueAttribute("");
		addCustomerForm.getInputByName("address").setValueAttribute("");
		addCustomerForm.getInputByName("door").setValueAttribute("");
		addCustomerForm.getInputByName("postalCode").setValueAttribute("");
		addCustomerForm.getInputByName("locality").setValueAttribute("");

		//addClients
		for(int i = 0; i < CLIENTS_TO_ADD; i++) {
			utils.addCustomer(clients[i+1][0], clients[i+1][1], clients[i+1][2]);
			customersUsed++;
		}

		// get a specific link
		link = page.getAnchorByHref("GetAllCustomersPageController");
		// click on it
		nextPage = (HtmlPage) link.openLinkInNewWindow();
		// check if title is the one expected
		assertEquals("Customers Info", nextPage.getTitleText());

		String[][] temp = clients.clone();
		Collections.reverse(Arrays.asList(temp));

		HtmlTable new_table = utils.getTable(nextPage.getBody());

		int expected_size = original_table.getRowCount() + 2;

		assertEquals(expected_size, new_table.getRowCount());

		int i = 1;

		for(HtmlTableRow row : new_table.getRows().subList(1, new_table.getRowCount())) {
			if(i < original_table.getRowCount()) { //in case it's not a new client
				assertEquals(original_table.getRow(i).asText(), row.asText());
			}else {
				assertEquals(temp[new_table.getRowCount() - 1 - i][1] + "\t" + temp[new_table.getRowCount() - 1 - i][2] + "\t" + temp[new_table.getRowCount() - 1 - i][0], row.asText());
			}
			i++;
		}
	}

	@Test
	public void addSale() throws FailingHttpStatusCodeException, IOException {

		// get a specific link
		HtmlAnchor addSaleLink = page.getAnchorByHref("addSale.html");
		// click on it
		HtmlPage nextPage = (HtmlPage) addSaleLink.openLinkInNewWindow();
		// check if title is the one expected
		assertEquals("New Sale", nextPage.getTitleText());

		// get form
		HtmlForm addSaleForm = nextPage.getForms().get(0);

		// check if form has the excepted fields
		addSaleForm.getInputByName("customerVat").setValueAttribute("");

		nextPage = utils.addSale(clients[0][0]);

		assertEquals("Sales Info", nextPage.getTitleText());

		HtmlTable table = utils.getTable(nextPage.getBody());

		// The new sale is in the last row
		assertTrue(table.getRow(table.getRowCount()-1).asText().contains("O"));
		assertTrue(table.getRow(table.getRowCount()-1).asText().contains(clients[0][0]));

	}

	@Test
	public void closeSale() throws FailingHttpStatusCodeException, IOException {

		// get a specific link
		HtmlAnchor link = page.getAnchorByHref("addSale.html");
		// click on it
		HtmlPage nextPage = (HtmlPage) link.openLinkInNewWindow();
		// check if title is the one expected
		assertEquals("New Sale", nextPage.getTitleText());

		// get form
		HtmlForm addSaleForm = nextPage.getForms().get(0);

		// check if form has the excepted fields
		addSaleForm.getInputByName("customerVat").setValueAttribute("");

		nextPage = utils.addSale(clients[0][0]);

		assertEquals("Sales Info", nextPage.getTitleText());

		HtmlTable table = utils.getTable(nextPage.getBody());

		// The new sale is in the last row
		assertTrue(table.getRow(table.getRowCount()-1).asText().contains("O"));

		//get ID
		String id = utils.getSaleId(table.getRow(table.getRowCount()-1).asText());

		// get a specific link
		link = page.getAnchorByHref("UpdateSaleStatusPageControler");
		// click on it
		nextPage = (HtmlPage) link.openLinkInNewWindow();
		// check if title is the one expected
		assertEquals("Enter Sale Id", nextPage.getTitleText());

		// get form
		HtmlForm closeSaleForm = nextPage.getForms().get(0);

		// check if form has the excepted fields
		closeSaleForm.getInputByName("id").setValueAttribute("");

		//Submit request
		nextPage = utils.closeSale(id);

		//Verify status
		table = utils.getTable(nextPage.getBody());

		assertTrue(table.getRow(table.getRowCount()-1).asText().contains("C"));

	}

	@Test
	public void checkPageContent() throws FailingHttpStatusCodeException, IOException {

		String[] address = {"Street 1", "1", "133-119", "Lisboa"};

		HtmlPage nextPage = utils.addCustomer(clients[1][0], clients[1][1], clients[1][2]);
		customersUsed++;

		assertEquals("Customer Info", nextPage.getTitleText());
		assertTrue(nextPage.asText().contains("Client Info"));
		assertTrue(nextPage.asText().contains("Identifier: "));
		assertTrue(nextPage.asText().contains("Name: " + clients[1][1]));
		assertTrue(nextPage.asText().contains("Contact Info: " + clients[1][2]));

		// verify it has a home button
		assertEquals(com.gargoylesoftware.htmlunit.html.HtmlButton.class, nextPage.getElementById("botao_home").getClass());

		HtmlTable original_table = utils.getTable(utils.getClient(clients[1][0]).getBody());

		// get a specific link
		HtmlAnchor link = page.getAnchorByHref("addAddressToCustomer.html");
		// click on it
		nextPage = (HtmlPage) link.openLinkInNewWindow();
		// check if title is the one expected
		assertEquals("Enter Address", nextPage.getTitleText());

		// get the page first form:
		HtmlForm addCustomerForm = nextPage.getForms().get(0);

		// check if form has the excepted fields
		addCustomerForm.getInputByName("vat").setValueAttribute("");
		addCustomerForm.getInputByName("address").setValueAttribute("");
		addCustomerForm.getInputByName("door").setValueAttribute("");
		addCustomerForm.getInputByName("postalCode").setValueAttribute("");
		addCustomerForm.getInputByName("locality").setValueAttribute("");

		// verify it has a submit button
		assertEquals(com.gargoylesoftware.htmlunit.html.HtmlSubmitInput.class, nextPage.getElementById("botao").getClass());

		//submit page
		nextPage = utils.addAddress(clients[1][0], address[0], address[1], address[2], address[3]);

		assertEquals("Customer Info", nextPage.getTitleText());

		assertTrue(nextPage.asText().contains("Client Info"));
		assertTrue(nextPage.asText().contains("Identifier: "));
		assertTrue(nextPage.asText().contains("Name: " + clients[1][1]));
		assertTrue(nextPage.asText().contains("Contact Info: " + clients[1][2]));

		HtmlTable table = utils.getTable(utils.getClient(clients[1][0]).getBody());

		for(int i = 0; i < table.getRowCount(); i++) {
			if(original_table != null && i < original_table.getRowCount()) {
				assertEquals(original_table.getRow(i), table.getRow(i));
			}else if(i == 0){
				assertEquals("Address\tDoor\tPostal Code\tLocality", table.getRow(i).asText());
			}else {
				assertEquals(address[0] + "\t" + address[1] + "\t" + address[2] + "\t" + address[3], table.getRow(i).asText());
			}
		}

		// verify it has a home button
		assertEquals(com.gargoylesoftware.htmlunit.html.HtmlButton.class, nextPage.getElementById("botao_home").getClass());

		original_table = utils.getTable(utils.getSales(clients[1][0]).getBody());

		//Adding new sale
		nextPage = utils.addSale(clients[1][0]);

		assertEquals("Sales Info", nextPage.getTitleText());

		assertTrue(nextPage.asText().contains("Sale Info"));

		table = utils.getTable(utils.getSales(clients[1][0]).getBody());

		for(int i = 0; i < table.getRowCount(); i++) {
			if(original_table != null && i < original_table.getRowCount()) {
				assertEquals(original_table.getRow(i), table.getRow(i));
			}else if(i == 0){
				assertEquals("Id\tDate\tTotal\tStatus\tCustomer Vat Number", table.getRow(i).asText());
			}else {
				assertTrue(table.getRow(i).asText().contains("\t" + utils.getCurrentDate() + "\t0.0\tO\t" + clients[1][0]));
			}
		}

		link = page.getAnchorByHref("saleDeliveryVat.html");
		nextPage = (HtmlPage) link.openLinkInNewWindow();

		assertEquals("Enter Name", nextPage.getTitleText());
		assertTrue(nextPage.asText().contains("Get Customer Info"));
		assertTrue(nextPage.asText().contains("Please enter customer's vat number:"));

		// verify it has a home button
		assertEquals(com.gargoylesoftware.htmlunit.html.HtmlButton.class, nextPage.getElementById("botao_home").getClass());

		nextPage = utils.saleDelivery(clients[1][0]);

		assertEquals("Enter Name", nextPage.getTitleText());
		assertTrue(nextPage.asText().contains("Add SaleDelivery"));
		assertTrue(nextPage.asText().contains("Please enter address id:"));
		assertTrue(nextPage.asText().contains("Please enter sale's id:"));

		// verify it has a home button
		assertEquals(com.gargoylesoftware.htmlunit.html.HtmlButton.class, nextPage.getElementById("botao_home").getClass());

		table = utils.getTable(nextPage.getBody());
		assertNotNull(table);
		//check header
		assertEquals("Id\tAddress\tDoor\tPostal Code\tLocality", table.getRow(0).asText());
		//check if the insert address is present
		String addressId = utils.getId(table);
		assertEquals(addressId + "\t" + address[0] + "\t" + address[1] + "\t" + address[2] + "\t" + address[3], table.getRow(1).asText());

		table = (HtmlTable) nextPage.getElementById("botao_home").getPreviousElementSibling();
		assertNotNull(table);
		//check header
		assertEquals("Id\tDate\tTotal\tStatus\tCustomer Vat Number", table.getRow(0).asText());
		//check if the insert address is present
		String saleId = utils.getId(table);
		assertEquals(saleId + "\t" + utils.getCurrentDate() + "\t0.0\tO\t" + clients[1][0], table.getRow(1).asText());

		nextPage = utils.addSaleDelivery(addressId, saleId);

		assertEquals("Sales Info", nextPage.getTitleText());
		assertTrue(nextPage.asText().contains("Sale Info"));

		table = utils.getTable(nextPage.getBody());
		String deliveryId = utils.getId(table);

		assertEquals("Id\tSale Id\tAddress id", table.getRow(0).asText());
		assertEquals(deliveryId + "\t" + saleId + "\t" + addressId, table.getRow(1).asText());

		// verify it has a home button
		assertEquals(com.gargoylesoftware.htmlunit.html.HtmlButton.class, nextPage.getElementById("botao_home").getClass());

		link = page.getAnchorByHref("showDelivery.html");
		nextPage = (HtmlPage) link.openLinkInNewWindow();

		assertEquals("Enter Name", nextPage.getTitleText());
		assertTrue(nextPage.asText().contains("Customer Info"));
		assertTrue(nextPage.asText().contains("Please enter customer's vat number:"));

		// verify it has a submit button
		assertEquals(com.gargoylesoftware.htmlunit.html.HtmlSubmitInput.class, nextPage.getElementById("botao").getClass());

		// verify it has a home button
		assertEquals(com.gargoylesoftware.htmlunit.html.HtmlButton.class, nextPage.getElementById("botao_home").getClass());

		nextPage = utils.getSaleDelivery(clients[1][0]);

		assertEquals("Sales Info", nextPage.getTitleText());
		assertTrue(nextPage.asText().contains("Sale Info"));

		table = utils.getTable(nextPage.getBody());
		assertEquals("Id\tSale Id\tAddress id", table.getRow(0).asText());
		assertEquals(deliveryId + "\t" + saleId + "\t" + addressId, table.getRow(1).asText());
	}

}
