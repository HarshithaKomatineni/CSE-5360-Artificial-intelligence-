Class DisplayRoute{
private static void displayRoute(City city) {
		System.out.println("distance: " + city.getCost() + " km\nroute:");
		List<String> finalRoute = new ArrayList<String>();
		while (city.getParentCity() != null) {
			String path = city.getParentCity().getCityName() + " to " + city.getCityName() + ", "
					+ city.getParentDistance() + "km";
			finalRoute.add(path);
			city = city.getParentCity();
		}
		Collections.reverse(finalRoute);
		for (String string : finalRoute) {
			System.out.println(string);
		}
	}
}