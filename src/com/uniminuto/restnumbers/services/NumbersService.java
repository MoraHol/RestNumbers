package com.uniminuto.restnumbers.services;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.uniminuto.restnumbers.model.Neighbors;
import com.uniminuto.restnumbers.model.ResponseNumbers;

/**
 * 
 * @author Alexis Holguin github:MoraHol
 *
 */
@Path("/numbers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class NumbersService {
	static ArrayList<Integer> numbers = new ArrayList<>();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getNumbers() {
		try {
			String ip;
			final DatagramSocket socket = new DatagramSocket();
			socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
			ip = socket.getLocalAddress().getHostAddress();
			socket.close();
			ResponseNumbers res = new ResponseNumbers(ip, numbers);
			return Response.ok(res).build();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.serverError().build();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.serverError().build();
		}

	}

	@POST
	@Path("/add/{number}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addNumber(@PathParam("number") String n) {
		System.out.println(n);
		numbers.add(Integer.parseInt(n));
		return Response.ok().build();
	}

	@GET
	@Path("/neighbors")
	@Produces(MediaType.APPLICATION_JSON)
	public Response sumNeighbors() {
		Gson gson = new Gson();
		Neighbors n = new Neighbors();
		ArrayList<String> neighbors = n.getNeighbors();
		ArrayList<ResponseNumbers> neightList = new ArrayList<>();
		
		NetClientNeighbor client = new NetClientNeighbor();	
		for (int i = 0; i < neighbors.size(); i++) {
			neightList.add(gson.fromJson(client.getNumsNeightbor(neighbors.get(i)), ResponseNumbers.class));
		}
		neightList.add(gson.fromJson(client.getNumsNeightbor("localhost"), ResponseNumbers.class));
		return Response.ok(neightList).build();
	}
}
