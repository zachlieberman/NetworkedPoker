import javax.swing.ImageIcon;

public class Card
{
   private int value; //Card values 2-14; Jack = 11, Queen = 12,King = 13, Ace = 14
   private int suit; //Spades = 0, Clubs = 1, Hearts = 2, Diamonds = 3
   private ImageIcon front = new ImageIcon();
   private ImageIcon back = new ImageIcon();
   
   public Card(int v, int s)
   {
      value = v;
      suit = s;
      back = new ImageIcon("Cards/cardback.png");
   }
   
   public int getValue()
   {
      return value;
   }
   
   public int getSuit()
   {
      return suit;
   }
   
   
   public ImageIcon getFront()
   {
      return front;
   }
   
   public ImageIcon getBack()
   {
      return back;
   }
         
   public void setFront(String f)
   {
      front = new ImageIcon(f);
   }
   
   public static String getValueName(int s)
   {
      if(s==2)
         return "Two";
      if(s==3)
         return "Three";
      if(s==4)
         return "Four";
      if(s==5)
         return "Five";
      if(s==6)
         return "Six";
      if(s==7)
         return "Seven";
      if(s==8)
         return "Eight";
      if(s==9)
         return "Nine";
      if(s==10)
         return "Ten";
      if(s==11)
         return "Jack";
      if(s==12)
         return "Queen";
      if(s==13)
         return "King";
      if(s==14)
         return "Ace";
         
         
      return "?";
   }
   
   
   public static String getSuitName(int s)
   {
      if(s==0)
         return "Spades";
      if(s==1)
         return "Clubs";
      if(s==2)
         return "Hearts";
      if(s==3)
         return "Diamonds";
      return "?";
   }
   
   public String toString()
   {
      return (getValueName(this.getValue()) + " of " + getSuitName(this.getSuit()));
   }
   
}