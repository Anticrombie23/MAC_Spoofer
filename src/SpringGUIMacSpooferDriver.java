
import java.awt.Button;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.Enumeration;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class SpringGUIMacSpooferDriver {

	private JFrame frame;
	private JTextField macAddressOfDeviceToSpoof;
	private JTextField xfinityURLString;
	private JTextField html_element_for_user_name;
	private JTextField html_element_for_password;
	private JTextField xfinityUserName;
	private JTextField xfinityPassword;
	private MacAddressSpoofer spoofer = new MacAddressSpoofer();
	private XfinityWebsiteSubmitter submitter = new XfinityWebsiteSubmitter();
	private SubmissionValidator v = new SubmissionValidator();
	private JTextField html_element_for_submit;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SpringGUIMacSpooferDriver window = new SpringGUIMacSpooferDriver();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SpringGUIMacSpooferDriver() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 425, 651);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		macAddressOfDeviceToSpoof = new JTextField();
		macAddressOfDeviceToSpoof.setText("B8:3E:59:3B:23:F5");
		macAddressOfDeviceToSpoof.setBounds(223, 74, 146, 20);
		frame.getContentPane().add(macAddressOfDeviceToSpoof);
		macAddressOfDeviceToSpoof.setColumns(10);

		Label label = new Label("MAC Address to Spoof:");
		label.setBounds(25, 72, 146, 22);
		frame.getContentPane().add(label);

		xfinityURLString = new JTextField();
		xfinityURLString.setText("https://login.xfinity.com/login");
		xfinityURLString.setBounds(223, 105, 146, 20);
		frame.getContentPane().add(xfinityURLString);
		xfinityURLString.setColumns(10);

		Label label_1 = new Label("Website URL:");
		label_1.setBounds(25, 103, 120, 22);
		frame.getContentPane().add(label_1);

		Label label_2 = new Label("Enter the following info below: ");
		label_2.setFont(new Font("Dialog", Font.BOLD, 12));
		label_2.setBounds(25, 27, 186, 22);
		frame.getContentPane().add(label_2);

		Label label_3 = new Label("HTML Element ID for User Name: ");
		label_3.setBounds(25, 265, 170, 22);
		frame.getContentPane().add(label_3);

		Label label_4 = new Label("HTML Element ID for Password: ");
		label_4.setBounds(25, 293, 170, 22);
		frame.getContentPane().add(label_4);

		Label label_5 = new Label("If below left empty, will attempt to parse via REGEX");
		label_5.setFont(new Font("Dialog", Font.BOLD, 12));
		label_5.setBounds(25, 237, 299, 22);
		frame.getContentPane().add(label_5);

		html_element_for_user_name = new JTextField();
		html_element_for_user_name.setColumns(10);
		html_element_for_user_name.setBounds(223, 265, 146, 20);
		frame.getContentPane().add(html_element_for_user_name);

		html_element_for_password = new JTextField();
		html_element_for_password.setColumns(10);
		html_element_for_password.setBounds(223, 295, 146, 20);
		frame.getContentPane().add(html_element_for_password);

		Label extra_label = new Label("XFinity User Name:");
		extra_label.setBounds(25, 145, 146, 22);
		frame.getContentPane().add(extra_label);

		Label label_6 = new Label("XFinity Password:");
		label_6.setBounds(25, 183, 146, 22);
		frame.getContentPane().add(label_6);

		xfinityUserName = new JTextField();
		xfinityUserName.setText("spodle200");
		xfinityUserName.setColumns(10);
		xfinityUserName.setBounds(223, 147, 146, 20);
		frame.getContentPane().add(xfinityUserName);

		xfinityPassword = new JTextField();
		xfinityPassword.setText("Belfore03!");
		xfinityPassword.setColumns(10);
		xfinityPassword.setBounds(223, 185, 146, 20);
		frame.getContentPane().add(xfinityPassword);

		Label label_7 = new Label("What type of OS are you currently on? (select one)");
		label_7.setFont(new Font("Dialog", Font.BOLD, 12));
		label_7.setBounds(25, 369, 299, 22);
		frame.getContentPane().add(label_7);

		Label resultLabel = new Label("");
		resultLabel.setBackground(Color.LIGHT_GRAY);
		resultLabel.setBounds(10, 506, 389, 76);
		frame.getContentPane().add(resultLabel);

		JComboBox operatingSystem = new JComboBox();
		operatingSystem.setModel(new DefaultComboBoxModel(new String[] { "WINDOWS", "MAC" }));
		operatingSystem.setBounds(25, 397, 95, 20);
		frame.getContentPane().add(operatingSystem);

		Button submitButton = new Button("Spoof MAC Address and Login");
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// Step 1: Validate no one was a moron
				String isValidText = v.validateSubmission(macAddressOfDeviceToSpoof, xfinityURLString, xfinityUserName,
						xfinityPassword);

				if (!isValidText.equals("true")) {
					// Provide the error
					returnErrorLabel(resultLabel, isValidText);
				} else {

					try {
						
						// Step 0: test
						printInterfaces();
						

						// Step 2: Store old mac address for reset
						String oldMacAddress = spoofer.getOldMacAddress();

						if (oldMacAddress.equals("error")) {
							returnErrorLabel(resultLabel, "MAC Address could not be retrieved from your computer.");
						}

						// Step 3: Spoof to mac address of device you're attempting to connect to
						// internet with
						// spoofer.spoofAddress(macAddressOfDeviceToSpoof, operatingSystem);
						
						
						//Step 4 disable and re-enable interface or wireless adapter
						

						// Step 5: Navigate to XFinity Hotspot website -- might use a default if not
						// provided??
						submitter.completeLogin(xfinityURLString, xfinityUserName, xfinityPassword,
								html_element_for_user_name, html_element_for_password, html_element_for_submit);

						// Step 6: Reset MAC address to old one
						// spoofer.resetMacAddressToPrevious(oldMacAddress);

						// Step 7: Return success if successful
						resultLabel.setText(
								"Login should be successful if MAC address supplied was good -- all steps completed successfully");
						resultLabel.setAlignment(Label.CENTER);
						resultLabel.setBackground(Color.GREEN);

						

					} catch (Exception ex) {

						ex.printStackTrace();

						returnErrorLabel(resultLabel, "Something unrecoverable happened: " + ex.getCause());

					}

				}

			}

			private void printInterfaces() {
				Enumeration<NetworkInterface> networkInterfaces;
				try {
					networkInterfaces = NetworkInterface.getNetworkInterfaces();
					Collections.list(networkInterfaces).forEach(networkInterface -> {

						try {
							StringBuilder sb = new StringBuilder();
							if (networkInterface.getHardwareAddress() != null) {
								byte[] mac = networkInterface.getHardwareAddress();
								for (int i = 0; i < mac.length; i++) {
									sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
								}
							} else {
								sb.append("Interface has no MAC");
							}
							System.out.println(String.format("Interface: %s  MAC: %s",
									networkInterface.getDisplayName(), sb.toString()));
						} catch (SocketException e) {
							e.printStackTrace();
						}
					});
				} catch (SocketException e1) {
					e1.printStackTrace();
				}

			}

			private void returnErrorLabel(Label resultLabel, String errorText) {
				resultLabel.setText(errorText);
				resultLabel.setAlignment(Label.CENTER);
				resultLabel.setBackground(Color.RED);
			}
		});
		submitButton.setBounds(25, 434, 344, 22);
		frame.getContentPane().add(submitButton);

		Label label_8 = new Label("HTML Element Name for Submit: ");
		label_8.setBounds(25, 321, 170, 22);
		frame.getContentPane().add(label_8);

		html_element_for_submit = new JTextField();
		html_element_for_submit.setColumns(10);
		html_element_for_submit.setBounds(223, 323, 146, 20);
		frame.getContentPane().add(html_element_for_submit);

	}
}
