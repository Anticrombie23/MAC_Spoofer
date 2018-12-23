
import java.io.IOException;
import java.net.MalformedURLException;

import javax.swing.JTextField;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

public class XfinityWebsiteSubmitter {

	XfinityWebsiteSubmitter() {
		client = new WebClient(BrowserVersion.BEST_SUPPORTED);
		client.getOptions().setThrowExceptionOnScriptError(false);
	}

	private WebClient client;
	private static final String XFINITY_URL_2 = "https://wifilogin.comcast.net";
	private static final String XFINITY_URL_1 = "https://login.xfinity.com/login";
	private static final String DEFAULT_USER_ID_1 = "user";
	private static final String DEFAULT_USER_ID_2 = "username";
	private static final String DEFAULT_PASSWORD_ID_1 = "password";
	private static final String DEFAULT_PASSWORD_ID_2 = "passwd";
	private static final String DEFAULT_SUBMIT_BUTTON_VALUE = "sign_in";

	public String completeLogin(JTextField xfinityURLString, JTextField xfinityUserName, JTextField xfinityPassword,
			JTextField html_element_for_ID, JTextField html_element_for_password, JTextField html_element_for_submit)
			throws FailingHttpStatusCodeException, MalformedURLException, IOException {

		// Get Page
		HtmlPage page = getURLToUse(xfinityURLString);

		// Retrieve and set user name field
		HtmlTextInput userNameInput = retrieveUserNameField(page, html_element_for_ID);
		userNameInput.setValueAttribute(xfinityUserName.getText());

		HtmlPasswordInput passwordInput = page.getHtmlElementById(passwordElementID);
		passwordInput.setValueAttribute(xfinityPassword.getText());

		// Retrieve the Form for submission and submit
		HtmlForm form = page.getForms().get(0);
		HtmlSubmitInput submission = form.getInputByName(submissionButtonElementID);

		HtmlPage nextPage;
		nextPage = submission.click();

		if (nextPage != null) {
			return "success";
		} else {
			return "failure";
		}

	}

	private HtmlPage getURLToUse(JTextField xfinityURLString) {

		String urlToTry = null;
		HtmlPage page = null;

		if (null != xfinityURLString) {
			page = attemptConnection(urlToTry);

			if (null != page) {
				return page;
			}
		} else {

			page = attemptConnection(XFINITY_URL_1);

			if (null != page) {
				return page;
			}

			page = attemptConnection(XFINITY_URL_2);

			if (null != page) {
				return page;
			}

			throw new RuntimeException("A URL could not be found that works with Xfinity WIFI");

		}
		return page;

	}

	private HtmlPage attemptConnection(String urlToTry) {
		try {
			return client.getPage(urlToTry);
		} catch (FailingHttpStatusCodeException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private HtmlTextInput retrieveUserNameField(HtmlPage page, JTextField html_element_for_ID) {

		HtmlTextInput userNameInput = null;

		if (null != html_element_for_ID) {

			try {
				userNameInput = page.getHtmlElementById(html_element_for_ID.getText());
				return userNameInput;
			} catch (RuntimeException e) {
				// do nothing and try and continue
			}
		} else {

			try {
				userNameInput = page.getHtmlElementById(DEFAULT_USER_ID_1);
				return userNameInput;
			} catch (RuntimeException e) {
				// do nothing and try and continue
			}

			try {
				userNameInput = page.getHtmlElementById(DEFAULT_USER_ID_2);
				return userNameInput;
			} catch (RuntimeException e) {
				// do nothing and try and continue;
			}

		}

		throw new RuntimeException("A Username html element could not be found within the context of the website provided");
	}

}
