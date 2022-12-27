import java.util.Comparator;

public class CityComparator implements Comparator<City> {

	@Override
	public int compare(City city1, City city2) {
		if (city1.getDist() > city2.getDist())
			return 1;
		if (city1.getDist() == city2.getDist())
			return 0;
		return -1;
	}

}
