package com.uniminuto.restnumbers.services;

import java.lang.reflect.Type;
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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONArray;

import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
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
	public Response sumNeighbors(@QueryParam("neighbors") String listNeighbors) {
		try {
			Gson gson = new Gson();
			Neighbors n = new Neighbors();
			JSONArray jsonArray = new JSONArray(listNeighbors);

			ArrayList<String> neighbors = n.getNeighbors();
			ArrayList<String> neightList = new ArrayList<>();
			ArrayList<String> neighborsRequest = new ArrayList<>();

			for (int j = 0; j < neighbors.size(); j++) {
				for (int i = 0; i < jsonArray.length(); i++) {
					if (!jsonArray.getString(i).equals(neighbors.get(j))) {
						neightList.add(neighbors.get(j));
					}
				}
			}

			// convertir jsonarray a array

			for (int i = 0; i < jsonArray.length(); i++) {
				neighborsRequest.add(jsonArray.getString(i));
			}

			// ip del equipo actual
			String ip;
			final DatagramSocket socket = new DatagramSocket();
			socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
			ip = socket.getLocalAddress().getHostAddress();
			socket.close();
			neighborsRequest.add(ip);

			NetClientNeighbor client = new NetClientNeighbor();
			Type tipoLista = new TypeToken<ArrayList<ResponseNumbers>>() {
			}.getType();

			ArrayList<ResponseNumbers> repsonsesNeighbors = new ArrayList<>();

			for (int i = 0; i < neightList.size(); i++) {
				String response = client.getNumsNeightbor(neightList.get(i), gson.toJson(neighborsRequest));
				repsonsesNeighbors.addAll(gson.fromJson(response, tipoLista));
			}
			return Response.ok(repsonsesNeighbors).build();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return Response.serverError().build();

	}
	@GET
	@Path("/sum")
	@Produces(MediaType.APPLICATION_JSON)
	public Response sum() {
		try {
		String ip;
		final DatagramSocket socket = new DatagramSocket();
		socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
		ip = socket.getLocalAddress().getHostAddress();
		socket.close();
		NetClientNeighbor client = new NetClientNeighbor();
		ArrayList<String> neighborsRequest = new ArrayList<>();
		neighborsRequest.add(ip);
		String response = client.getNumsNeightbor(ip, new Gson().toJson(neighborsRequest));
		System.out.println(response);
		return Response.ok(response).build();
		}catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
}
