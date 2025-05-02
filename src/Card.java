public class Card {
    private String name;
    private String png;
    private boolean isFacingUp;
    private static int numCardsFacingDown = 0;
    private String description;
    private String type;
   
    public Card(String n, String p, String d, String t) {
        name = n;
        png = p;
        description = d;
        isFacingUp = false;
        type = t;
        numCardsFacingDown++;
    }
   
    public String getName() {
        return name;
    }
   
    public String getPNG() {
        return png;
    }

    public String getType(){
        return type;
    }
   
    public boolean getIsFacingUp() {
        return isFacingUp;
    }
   
    public static int getNumCardsFacingDown() {
        return numCardsFacingDown;
    }

    public static void setNumCardsFacingDown(int changeValue){
        numCardsFacingDown += changeValue;
    }
   
    public void setFacingUp() {
        isFacingUp = true;
    }
   
    public void setFacingDown() {
        isFacingUp = false;
    }
   
    public String getDescription() {
        return description;
    }
   
    public boolean equals(Card c) {
        return name.equals(c.getName()) && isFacingUp == c.getIsFacingUp();
    }
   
    @Override
    public String toString() {
        return "Name: " + name + "\n\nDescription: " + description;
    }
}
