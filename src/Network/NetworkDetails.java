package Network;

import com.esotericsoftware.kryonet.Connection;

import java.net.InetSocketAddress;
import java.util.ArrayList;

public class NetworkDetails {
	
	public int clients = 0;
	public int ping = 0;
	public InetSocketAddress address;
	public ArrayList<NetworkPlayer> players = new ArrayList<>();
	public Connection connection;
}
