public class City {
  public int x = 0, y = 0;
  public String name = "nie nazwano";

  public City(int x, int y, String name) {
    this.x = x;
    this.y = y;
    this.name = name;
  }

  public int distance(City otherCity) {
    double result = Math.sqrt(Math.pow((this.x - otherCity.x), 2) + Math.pow((this.y - otherCity.y), 2));

    return (int) Math.round(result);
  }
}
