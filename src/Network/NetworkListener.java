package Network;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import Game.Survivr;
import Network.Packets.Packet01Connect;
import Network.Packets.Packet02Message;
import Network.Packets.Packet03Update;

public class NetworkListener extends Listener {
	
	private Client client;
	public String name;
	
	public void init(Client client){
		this.client = client;
	}
	
	public NetworkListener(String name) {
		this.name = name;
	}
	
	@Override
	public void connected(Connection c){

		Survivr.details.connection = c;

		System.out.println("[CLIENT] >>  You Have Connected To Server");

		Packet01Connect p1 = new Packet01Connect();
		
		p1.username = name;
		
		client.sendTCP(p1);
	}
	
	@Override
	public void disconnected(Connection c){
		System.out.println("[CLIENT] >>  You Have Disconected From Server");
	}
	
	@Override
	public void received(Connection connection, Object packet) {
		if(packet instanceof Packet02Message){
			Packet02Message p = (Packet02Message) packet;
			System.out.println(p.message);
		}
		
		if(packet instanceof Packet03Update){
			Packet03Update p = (Packet03Update) packet;
			Survivr.details.clients = p.players;
			Survivr.details.ping = connection.getReturnTripTime();
			Survivr.details.address = connection.getRemoteAddressTCP();
			Survivr.details.players = p.clients;
		}
	}

}
