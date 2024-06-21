public class City {
  public double x = 0, y = 0;
  public String name = "nie nazwano";

  /**
   * Konsrukcjor pojedyńczego miasta
   * 
   * @param x    Pozycja x
   * @param y    Pozycja y
   * @param name Nazwa miasta
   */
  public City(double x, double y, String name) {
    this.x = x;
    this.y = y;
    this.name = name;
  }

  /**
   * Liczy dystans między miastami jako
   * 
   * @param otherCity Porównywane miasto
   * @return Dystans jako {@code int}
   */
  public int distance(City otherCity) {
    double result = Math.sqrt(Math.pow((this.x - otherCity.x), 2) + Math.pow((this.y - otherCity.y), 2));

    return (int) Math.round(result);
  }


  @Override
    public String toString() {
        return name+"("+x+","+y+")";
    }
}
