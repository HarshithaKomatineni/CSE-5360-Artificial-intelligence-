import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class find_route {

	private static int Gen_Nodes = 1;
	private static int Ex_Nodes = 0;
	private static int Pop_Nodes = 0;
	private static boolean is_Heuristic = false;

	public static void main(String[] args) {



		String inputF = args[0];
		String Start_Node = args[1];
		String Goal_Node = args[2];
		String Heuristic_File = null;
		if (args.length > 3) {
			Heuristic_File = args[3];
			is_Heuristic = true;
		}

		String[] input;
		ArrayList<InputPath> Input_Routes = new ArrayList<InputPath>();
		Map<String, Integer> Heuristic_Data = new HashMap<String, Integer>();

		try  {
			BufferedReader br = new BufferedReader(new FileReader(inputF));
			String inputLine = null;
			while ((inputLine = br.readLine()) != null) {

				if (inputLine.equals("END OF INPUT"))
					break;

				input = inputLine.split(" ");

				InputPath Input_Route = new InputPath();

				Input_Route.setSrc(input[0]);
				Input_Route.setDest(input[1]);
				Input_Route.setDist(Float.parseFloat(input[2]));

				Input_Routes.add(Input_Route);

			}
			br.close();
			if (is_Heuristic) {
				br = new BufferedReader(new FileReader(Heuristic_File));
					inputLine = null;

					while ((inputLine = br.readLine()) != null) {
						if (inputLine.equals("END OF INPUT"))
							break;

						input = inputLine.split(" ");
						Heuristic_Data.put(input[0], Integer.parseInt(input[1]));

					}
					br.close();
				 
			}
		} 
		catch (FileNotFoundException excep) {
			System.out.println("Unable to locate the file " + Heuristic_File);
		}

		catch (IOException excep) {
			System.out.println("Exception while reading the file " + Heuristic_File);
		}
		
		ArrayList<String> closedCities = new ArrayList<String>();
		City inceptionNode = new City(Start_Node, 0, 0, 0, null, null);
		PriorityQueue<City> fringe = new PriorityQueue<City>(1000, new CityComparator());

		fringe.add(inceptionNode); 
		City city = null;
		while (true) {
			city = fringe.poll(); 

			Pop_Nodes++;

			if (city.getCity().equals(Goal_Node)) {
				break;
			}

			if (!closedCities.contains(city.getCity())) {

				Ex_Nodes++;
				closedCities.add(city.getCity());
				getChildrenCities(city, Input_Routes, Heuristic_Data);
				for (int i = 0; i < city.getChildrenCities().size(); i++) {
					City childCity = city.getChildrenCities().get(i);
					fringe.add(childCity);
				}
			}

			if (fringe.isEmpty()) {
				break;
			}
		}
		System.out.println("Nodes Popped:" + Pop_Nodes);
		System.out.println("Nodes Expanded: " + Ex_Nodes);
		System.out.println("Nodes Generated: " + Gen_Nodes);
		if (fringe.isEmpty()) {
			System.out.println("Distance: Infinity");
			System.out.println("Route:" + '\n' + "None");
		} else
			displayRoute(city);
	}

	private static void displayRoute(City city) {
		System.out.println("Distance: " + city.getCost() + " km\nRoute:");
		List<String> Final_Route = new ArrayList<String>();
		while (city.getprtCity() != null) {
			String path = city.getprtCity().getCity() + " to " + city.getCity() + ", "
					+ city.getprtDistance() + "km";
			Final_Route.add(path);
			city = city.getprtCity();
		}
		Collections.reverse(Final_Route);
		for (String string : Final_Route) {
			System.out.println(string);
		}
	}

	private static void getChildrenCities(City city, ArrayList<InputPath> Input_Routes,
			Map<String, Integer> Heuristic_Data) {
		ArrayList<City> childrenCities = new ArrayList<City>(); 

		for (InputPath input : Input_Routes) 
		{
			City child = null;

			if (input.getSrc().equals(city.getCity()))
				child = new City(input.getDest(), city.getCost() + input.getDist(),
						city.getCost() + input.getDist(), input.getDist(), city, null);
			else if (input.getDest().equals(city.getCity()))
				child = new City(input.getSrc(), city.getCost() + input.getDist(),
						city.getCost() + input.getDist(), input.getDist(), city, null);

			if (child != null) {
				Gen_Nodes = Gen_Nodes + 1;
				if (is_Heuristic)
					child.setDist(child.getDist() + Heuristic_Data.get(child.getCity()));
				childrenCities.add(child);

			}
		}
		city.setChildrenCities(childrenCities);
	}

}
