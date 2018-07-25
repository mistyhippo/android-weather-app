import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
	static String host;
	static String command;
	static String reply;
	static int port;

	public static void main(String[] args) {
		try {
			host = args[0];
			port = Integer.parseInt(args[1]);
			Socket client = new Socket(host, port);
			BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));
			PrintWriter output = new PrintWriter(client.getOutputStream());
			
			String httpText;
			while ((httpText = input.readLine()) != null) {
				System.out.println(httpText);
			}
			output.print(client.getInetAddress());

		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("The correct format is java Client {host} {port}");
		} catch (IOException e) {
			System.out.println("Error 404");
		}

	}
}
