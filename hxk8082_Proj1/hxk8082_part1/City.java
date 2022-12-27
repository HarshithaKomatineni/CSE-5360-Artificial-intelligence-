import java.util.List;

public class City {
	private String city;
	private float dist;
	private float cost;
	private float prtDistance;
	private City prtCity;
	private List<City> childrenCities;
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public float getDist() {
		return dist;
	}
	public void setDist(float dist) {
		this.dist = dist;
	}
	public City getprtCity() {
		return prtCity;
	}
	public void setprtCity(City prtCity) {
		this.prtCity = prtCity;
	}
	public List<City> getChildrenCities() {
		return childrenCities;
	}
	public void setChildrenCities(List<City> childrenCities) {
		this.childrenCities = childrenCities;
	}
	
	public float getCost() {
		return cost;
	}
	public void setCost(float cost) {
		this.cost = cost;
	}
	
	public float getprtDistance() {
		return prtDistance;
	}
	public void setprtDistance(float prtDistance) {
		this.prtDistance = prtDistance;
	}
	public City(String city, float dist, float cost, float prtDistance, City prtCity,
			List<City> childrenCities) {
		super();
		this.city = city;
		this.dist = dist;
		this.cost = cost;
		this.prtDistance = prtDistance;
		this.prtCity = prtCity;
		this.childrenCities = childrenCities;
	}
	
	
}
