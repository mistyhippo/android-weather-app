import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class SingleThreadedServer implements Runnable {
	protected ServerSocket serverSocket;
	protected Thread currentThread;
	protected boolean isRunning = true;
	protected final int TIMEOUT = 300000;
	protected int port;

	/* 1 **/
	public SingleThreadedServer(int port) throws IOException {
		this.port = port;
	}
	
	/* 4 **/
	@SuppressWarnings("unused")
	private synchronized boolean isRunning() {
		return this.isRunning;
	}
	
	/* 2 **/
	public void run() {
		synchronized (this) {
			this.currentThread = Thread.currentThread();
		}
		openServerSocket();
		while (isRunning) {
			System.out.println("Running SingleThreadedServer");
			Socket connection;
			try {
				connection = serverSocket.accept();
				
			} catch (IOException e) {
				if(!isRunning){
					System.out.println("Server halted.");
					return;
				}
				throw new RuntimeException("Connection failed.");
			}
			
			try {
			System.out.println("Client connected");
			processRequest(connection);
			} catch (IOException e){
				System.out.println("IOException");
			}
			
		}
		System.out.println("Server Ended");
	}
	
	/* 3 **/
	private void openServerSocket() {
		try {
			this.serverSocket = new ServerSocket(this.port);
		} catch (IOException e) {
			throw new RuntimeException("Cannot open port: " + this.port, e);
		}
	}

	/* 5 **/
	public void processRequest(Socket connection) throws IOException{
			InputStream  input  = connection.getInputStream();
	        OutputStream output = connection.getOutputStream();
	        long time = System.currentTimeMillis();
	        Date date = new Date(time);
	        SimpleDateFormat simpleDate = new SimpleDateFormat("hh:mmaa dd/MM/yy");
	        String t = simpleDate.format(date);
	        output.write(("HTTP/1.1 200 OK\n\n<html><body>" +
	                "Singlethreaded Server: " +
	                t +
	                "</body></html>").getBytes());
	        output.close();
	        input.close();
	        System.out.println("Request processed: " + time);
	}

	/* 6 **/
	public synchronized void stop() {
		this.isRunning = false;
		try {
			this.serverSocket.close();
		} catch (IOException e) {
			throw new RuntimeException("Error closing server", e);
		}
	}
	
	 

	public static void main(String[] args) {
		try {
			SingleThreadedServer server = new SingleThreadedServer(Integer.parseInt(args[0]));
			new Thread(server).start();
			try {
				Thread.sleep(40000);
			} catch (InterruptedException e) {
				System.out.println("Thread Interrupted");
			}
			server.stop();
		} catch (IOException e) {
			System.out.println("Input problem");
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out
					.println("The format of this program is java Server {port}");
		}
	}
}
