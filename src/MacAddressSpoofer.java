
import javax.swing.JComboBox;
import javax.swing.JTextField;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

public class MacAddressSpoofer {

	InetAddress ip;
	String os;

	public String getOldMacAddress() {

		try {

			ip = InetAddress.getLocalHost();
			System.out.println("Current IP address : " + ip.getHostAddress());

			NetworkInterface network = NetworkInterface.getByInetAddress(ip);

			byte[] mac = network.getHardwareAddress();

			System.out.print("Current MAC address : ");

			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < mac.length; i++) {
				sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
			}
			System.out.println(sb.toString());

			return sb.toString();

		} catch (UnknownHostException e) {

			return "error";

		} catch (SocketException e) {

			return "error";

		}
	}

	public void spoofAddress(JTextField macAddressOfDeviceToSpoof, JComboBox operatingSystem) throws IOException {

		String macAddress = macAddressOfDeviceToSpoof.getText().trim();

		if (operatingSystem.getSelectedItem().toString().equals("MAC")) {
			System.out.println("MAC selected");
			os = "MAC";

			String interfaceName = determineMacNetworkInterfaceName();
			Process p = Runtime.getRuntime().exec("sudo ifconfig " + interfaceName + " ether " + macAddress);

		} else if (operatingSystem.getSelectedItem().toString().equals("WINDOWS")) {
			System.out.println("WINDOWS selected");
			os = "WINDOWS";

			String oLevel = determineWindowsOLevel();

			Process p = Runtime.getRuntime().exec(
					"cmd /c start cmd.exe /K \"  \"reg add HKEY_LOCAL_MACHINE\\SYSTEM\\CurrentControlSet\\Control\\Class\\{4D36E972-E325-11CE-BFC1-08002BE10318}\\"
							+ oLevel + " /t REG_SZ /v NetworkAddress /d " + macAddress + " /f ");
		}

	}

	private String determineMacNetworkInterfaceName() {
		// TODO Auto-generated method stub
		return null;
	}

	private String determineWindowsOLevel() {
		// TODO Auto-generated method stub
		return null;
	}

	public void resetMacAddressToPrevious(String oldMacAddress) throws IOException {

		if (os.equals("MAC")) {
			Process p = Runtime.getRuntime().exec("sudo ifconfig en0 ether " + oldMacAddress);

		} else if (os.equals("WINDOWS")) {
			Process p = Runtime.getRuntime().exec(
					"cmd /c start cmd.exe /K \"  \"reg add HKEY_LOCAL_MACHINE\\SYSTEM\\CurrentControlSet\\Control\\Class\\{4D36E972-E325-11CE-BFC1-08002BE10318}\\0001 /t REG_SZ /v NetworkAddress /d "
							+ oldMacAddress + " /f ");
		}

	}

}
