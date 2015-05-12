package Personal_Project.main;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.Timer;

import org.apache.commons.io.FileUtils;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.webapp.WebAppContext;

import edu.ycp.cs320.sqldemo.SQLDemo;

public class Main {
	private static Scanner keyboard;

	public static void main(String[] args) throws Exception {
		Server server = new Server(8080);

		// Create and register a webapp context
		WebAppContext handler = new WebAppContext();
		handler.setContextPath("/pegasuslandscaping");
		handler.setWar("./war"); // web app is in the war directory of the project
		handler.setInitParameter("org.eclipse.jetty.servlet.Default.dirAllowed", "false");
		server.setHandler(handler);
		
		// Use 20 threads to handle requests
		server.setThreadPool(new QueuedThreadPool(20));
		
		// Start the server
		server.start();
		Timer starty = new Timer();
		File source = new File("C:/Users/Michael/Desktop/workspace/Personal_Project/main.db");
		File dest = new File("C:/Users/Michael/Desktop/workspace/main.db");
		starty.schedule(
		    new java.util.TimerTask() {
		        @Override
		        public void run() {
		        	System.out.println("Saved: " + new java.util.Date().toString());
		        	try {
		        	    FileUtils.copyFile(source, dest);
		        	} catch (IOException e) {
		        	    e.printStackTrace();
		        	}
		        }
		    }, 
		    0, 300000
		);
		
		// Wait for the user to type "quit"
		System.out.println("Web server started, type help for more options");
		keyboard = new Scanner(System.in);
		int stop = 0;
		while (keyboard.hasNextLine()) {
			String line = keyboard.nextLine();
			if (line.trim().toLowerCase().equals("close")) {
				break;
			}
			else if (line.trim().toLowerCase().equals("restart")) {
				System.out.println("Restarting server...");
				server.stop();
				server.join();
				main(args);
			}
			else if (line.trim().toLowerCase().equals("stop")) {
				System.out.println("Stopping server...");
				server.stop();
				server.join();
				stop = 1;
				System.out.println("Server stopped");
			}
			else if (line.trim().toLowerCase().equals("start") && stop == 1) {
				System.out.println("Stating server...");
				main(args);
			}
			else if (line.trim().toLowerCase().equals("db")) {
				System.out.println("Opening access to the database...");
				System.out.println("Database opened.  Type 'quit;' to close database.");
				SQLDemo.main(args);
				System.out.println("Database closed");
			}
			else if (line.trim().toLowerCase().equals("help")) {
				System.out.println("Type close to exit");
				System.out.println("     backup to back up the database data");
				System.out.println("     restore to restore database data");
				System.out.println("     restart to restart the server");
				System.out.println("     stop to stop server");
				System.out.println("     start to start server");
				System.out.println("     db to look at the database");
			}
		}
		
		System.out.println("Shutting down...");
		server.stop();
		server.join();
		System.out.println("Server has shut down, exiting");
		starty.cancel();
	}
}
