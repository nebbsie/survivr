package Network;

import java.io.IOException;

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
		client = new Client();
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
	}

}
