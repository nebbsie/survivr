package Network;
//
import java.io.IOException;

import Network.Packets.Packet04ClientUpdate;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;

import Network.Packets.Packet01Connect;
import Network.Packets.Packet02Message;
import Network.Packets.Packet03Update;

public class NetworkClient {
	
	int portNumber = 49152;
	String IPAddress = "92.15.200.237";
	
	public Client client;
	private NetworkListener nl;
	
	public NetworkClient(String name){
		client = new Client(8192, 2048);
		nl = new NetworkListener(name);
		nl.init(client);
		registerPackets();
		client.addListener(nl);
		
		new Thread(client).start();
		
		try {
			client.connect(5000, IPAddress, portNumber);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void registerPackets(){
		Kryo kyro = client.getKryo();
		kyro.register(Packet01Connect.class);
		kyro.register(Packet02Message.class);
		kyro.register(Packet03Update.class);
		kyro.register(Packet04ClientUpdate.class);
		kyro.register(java.util.ArrayList.class);
		kyro.register(NetworkPlayer.class);
	}

}
