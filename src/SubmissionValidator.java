

import javax.swing.JTextField;

public class SubmissionValidator {

	public String validateSubmission(JTextField macAddressOfDeviceToSpoof, JTextField xfinityURLString,
			JTextField xfinityUserName, JTextField xfinityPassword) {

		// Step 1: Validate MAC Address
		String removedAlphaNum = macAddressOfDeviceToSpoof.getText().replaceAll("[^A-Za-z0-9]", "");
		long test = macAddressOfDeviceToSpoof.getText().chars().filter(ch -> ch == ':').count();

		if (macAddressOfDeviceToSpoof.getText().trim().length() != 17 || removedAlphaNum.trim().length() != 12 || test != 5) {
			return "Bogus MAC Address detected -- should look like: XX:XX:XX:XX:XX:XX";
		}

		// Validate Xfinity URL

		if (xfinityURLString.getText() == null || xfinityURLString.getText().trim().length() == 0) {
			return "Please provide an Xfinity Splash Screen URL";
		}

		// Validate user name
		if (xfinityUserName.getText() == null || xfinityUserName.getText().trim().length() == 0) {
			return "Please provide an XFinity Username!";
		}

		// Validate password
		if (xfinityPassword.getText() == null || xfinityPassword.getText().trim().length() == 0) {
			return "Please provide an XFinity Password!";
		}

		return "true";
		// TODO Auto-generated method stub

	}

}
