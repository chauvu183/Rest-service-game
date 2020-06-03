package hawhamburg;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;

import hawhamburg.controller.GameController;
import hawhamburg.service.HeroService;
import static spark.Spark.*;

public class Start {
	private static final int MIN_PORT_NUMBER = 4567;
	private static final int MAX_PORT_NUMBER = 5567;
	static HeroService heroService;
	static GameController g1;
	static GameController g2;
	public static void main(String[] args) throws IOException {
		int portToUse = 4567;
		while(!available(portToUse)) {
			portToUse++;
		}
		port(portToUse);
		System.out.println("port used = " + portToUse);
		GameController gameController = new GameController(portToUse);
//		Thread t = new Thread(() ->  {try {
//			g1 = new GameController();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}});
//		t.start();
//		
//		Thread t2 = new Thread(() ->  {try {
//			g1 = new GameController();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}});
//		t2.start();

	}
	
	public static boolean available(int port) {
	    if (port < MIN_PORT_NUMBER || port > MAX_PORT_NUMBER) {
	        throw new IllegalArgumentException("Invalid start port: " + port);
	    }

	    ServerSocket ss = null;
	    DatagramSocket ds = null;
	    try {
	        ss = new ServerSocket(port);
	        ss.setReuseAddress(true);
	        ds = new DatagramSocket(port);
	        ds.setReuseAddress(true);
	        return true;
	    } catch (IOException e) {
	    } finally {
	        if (ds != null) {
	            ds.close();
	        }

	        if (ss != null) {
	            try {
	                ss.close();
	            } catch (IOException e) {
	                /* should not be thrown */
	            }
	        }
	    }

	    return false;
	}

}
