package vvs_webapp;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.util.NameValuePair;

public class HTMLUtils {

	private static final String APPLICATION_URL = "http://localhost:8080/VVS_webappdemo/";


	HtmlPage addAddress(String VAT, String address, String door, String postalCode, String local) throws FailingHttpStatusCodeException, IOException {

		HtmlPage reportPage;
		String formData = String.format("vat=%s&address=%s&door=%s&postalCode=%s&locality=%s", VAT, address, door, postalCode, local);

		WebRequest req = new WebRequest(new java.net.URL(APPLICATION_URL+"GetCustomerPageController"), HttpMethod.POST);
		req.setRequestBody(formData);

		try (final WebClient webClient = new WebClient(BrowserVersion.getDefault())) {
			reportPage = (HtmlPage) webClient.getPage(req);
		}

		return reportPage;
	}

	HtmlPage addCustomer(String VAT, String designation, String phone) throws FailingHttpStatusCodeException, IOException {

		HtmlPage resultPage;
		String formData = String.format("vat=%s&designation=%s&phone=%s", VAT, designation, phone);

		WebRequest req = new WebRequest(new java.net.URL(APPLICATION_URL+"AddCustomerPageController"), HttpMethod.POST);
		req.setRequestBody(formData);

		try (final WebClient webClient = new WebClient(BrowserVersion.getDefault())) {
			resultPage = (HtmlPage) webClient.getPage(req);
		}
		
		return resultPage;
	}


	HtmlPage getClient(String VAT) throws FailingHttpStatusCodeException, IOException {

		HtmlPage reportPage;

		WebRequest req = new WebRequest(new java.net.URL(APPLICATION_URL+"GetCustomerPageController"), HttpMethod.GET);
		req.setRequestParameters(new ArrayList<NameValuePair>());
		req.getRequestParameters().add(new NameValuePair("vat", VAT));
		req.getRequestParameters().add(new NameValuePair("submit", "Get Customer"));

		try (final WebClient webClient = new WebClient(BrowserVersion.getDefault())) {
			reportPage = (HtmlPage) webClient.getPage(req);
		}

		return reportPage;
	}

	//gets the first table
	HtmlTable getTable(DomElement element) {

		HtmlTable table = null;

		for(DomNode s : element.getDescendants()) 
			if(s instanceof HtmlTable){
				table = (HtmlTable) s;
				break;
			}

		return table;

	}

	void removeCustomer(String VAT) throws FailingHttpStatusCodeException, IOException {

		String formData = String.format("vat=%s", VAT);

		WebRequest req = new WebRequest(new java.net.URL(APPLICATION_URL+"RemoveCustomerPageController"), HttpMethod.POST);
		req.setRequestBody(formData);

		try (final WebClient webClient = new WebClient(BrowserVersion.getDefault())) {
			webClient.getPage(req);
		}

	}

	String[][] fillClients() {

		String[][] result = {{"197672329", "New Client 1", "123456798"}, {"503183504", "New Client 2", "123456798"}, {"197672302", "New Client 3", "123456798"}};

		return result;
	}

	HtmlPage addSale(String VAT) throws FailingHttpStatusCodeException, IOException {

		HtmlPage reportPage;
		String formData = String.format("customerVat=%s", VAT);

		WebRequest req = new WebRequest(new java.net.URL(APPLICATION_URL+"AddSalePageController"), HttpMethod.POST);
		req.setRequestBody(formData);

		try (final WebClient webClient = new WebClient(BrowserVersion.getDefault())) {
			reportPage = (HtmlPage) webClient.getPage(req);
		}

		return reportPage;

	}

	String getSaleId(String row) {
		
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0; i < row.length(); i++) {
			if(row.charAt(i) == '\t')
				break;
			sb.append(row.charAt(i));
		}
		
		return sb.toString();
	}

	HtmlPage closeSale(String id) throws FailingHttpStatusCodeException, IOException {
		
		HtmlPage reportPage;
		String formData = String.format("id=%s", id);

		WebRequest req = new WebRequest(new java.net.URL(APPLICATION_URL+"UpdateSaleStatusPageControler"), HttpMethod.POST);
		req.setRequestBody(formData);

		try (final WebClient webClient = new WebClient(BrowserVersion.getDefault())) {
			reportPage = (HtmlPage) webClient.getPage(req);
		}
		
		return reportPage;
	}

	HtmlPage getSales(String VAT) throws FailingHttpStatusCodeException, IOException {
		
		HtmlPage reportPage;

		WebRequest req = new WebRequest(new java.net.URL(APPLICATION_URL+"GetSalePageController"), HttpMethod.GET);
		req.setRequestParameters(new ArrayList<NameValuePair>());
		req.getRequestParameters().add(new NameValuePair("customerVat", VAT));
		req.getRequestParameters().add(new NameValuePair("submit", "Get Sales"));

		try (final WebClient webClient = new WebClient(BrowserVersion.getDefault())) {
			reportPage = (HtmlPage) webClient.getPage(req);
		}
		
		return reportPage;
	}

	String getCurrentDate() {
		LocalDate now = LocalDate.now();
		int year = now.getYear();
		String month = String.valueOf(now.getMonthValue());
		if(month.length() == 1)
			month = "0" + month;
		String day = String.valueOf(now.getDayOfMonth());
		if(day.length() == 1)
			day = "0" + day;
		
		return year + "-" + month + "-" + day;
	}

	HtmlPage saleDelivery(String VAT) throws FailingHttpStatusCodeException, IOException {
		
		HtmlPage reportPage;

		WebRequest req = new WebRequest(new java.net.URL(APPLICATION_URL+"AddSaleDeliveryPageController"), HttpMethod.GET);
		req.setRequestParameters(new ArrayList<NameValuePair>());
		req.getRequestParameters().add(new NameValuePair("vat", VAT));
		req.getRequestParameters().add(new NameValuePair("submit", "Get Customer"));

		try (final WebClient webClient = new WebClient(BrowserVersion.getDefault())) {
			reportPage = (HtmlPage) webClient.getPage(req);
		}
		
		return reportPage;
	}

	String getId(HtmlTable table) {
		
		StringBuilder sb = new StringBuilder();
		
		for(char c : table.getRow(table.getRowCount()-1).asText().toCharArray()) {
			if(c == '\t')
				break;
			else
				sb.append(c);
		}
		return sb.toString();
	}

	HtmlPage addSaleDelivery(String addressId, String saleId) throws FailingHttpStatusCodeException, IOException {
		
		HtmlPage reportPage;
		String formData = String.format("addr_id=%s&sale_id=%s", addressId, saleId);

		WebRequest req = new WebRequest(new java.net.URL(APPLICATION_URL+"AddSaleDeliveryPageController"), HttpMethod.POST);
		req.setRequestBody(formData);

		try (final WebClient webClient = new WebClient(BrowserVersion.getDefault())) {
			reportPage = (HtmlPage) webClient.getPage(req);
		}
		
		return reportPage;
	}

	HtmlPage getSaleDelivery(String VAT) throws FailingHttpStatusCodeException, IOException {

		HtmlPage reportPage;

		WebRequest req = new WebRequest(new java.net.URL(APPLICATION_URL+"GetSaleDeliveryPageController"), HttpMethod.GET);
		req.setRequestParameters(new ArrayList<NameValuePair>());
		req.getRequestParameters().add(new NameValuePair("vat", VAT));
		req.getRequestParameters().add(new NameValuePair("submit", "Get Customer"));

		try (final WebClient webClient = new WebClient(BrowserVersion.getDefault())) {
			reportPage = (HtmlPage) webClient.getPage(req);
		}
		
		return reportPage;
	}

}
