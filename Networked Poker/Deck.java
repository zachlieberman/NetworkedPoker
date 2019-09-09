import java.util.ArrayList;

public class Deck
{
   private ArrayList<Card> deck = new ArrayList();
   private int size;
   public Deck()
   {
      int iter = 0;
      
      for(int s = 0; s < 4; s++)//Spades = 0, Clubs = 1, Hearts = 2, Diamonds = 3
      {
         for(int v = 2; v < 15; v++)
         {
            deck.add(new Card(v,s));
            iter++;
            size++;
         }
      }
      
      //sets the picture for the front of each card
      deck.get(0).setFront("Cards/Spades/two_spades.png");
      deck.get(1).setFront("Cards/Spades/three_spades.png");
      deck.get(2).setFront("Cards/Spades/four_spades.png");
      deck.get(3).setFront("Cards/Spades/five_spades.png");
      deck.get(4).setFront("Cards/Spades/six_spades.png");
      deck.get(5).setFront("Cards/Spades/seven_spades.png");
      deck.get(6).setFront("Cards/Spades/eight_spades.png");
      deck.get(7).setFront("Cards/Spades/nine_spades.png");
      deck.get(8).setFront("Cards/Spades/ten_spades.png");
      deck.get(9).setFront("Cards/Spades/jack_spades.png");
      deck.get(10).setFront("Cards/Spades/queen_spades.png");
      deck.get(11).setFront("Cards/Spades/king_spades.png");
      deck.get(12).setFront("Cards/Spades/ace_spades.png");
   
      deck.get(13).setFront("Cards/Clubs/two_clubs.png");
      deck.get(14).setFront("Cards/Clubs/three_clubs.png");
      deck.get(15).setFront("Cards/Clubs/four_clubs.png");
      deck.get(16).setFront("Cards/Clubs/five_clubs.png");
      deck.get(17).setFront("Cards/Clubs/six_clubs.png");
      deck.get(18).setFront("Cards/Clubs/seven_clubs.png");
      deck.get(19).setFront("Cards/Clubs/eight_clubs.png");
      deck.get(20).setFront("Cards/Clubs/nine_clubs.png");
      deck.get(21).setFront("Cards/Clubs/ten_clubs.png");
      deck.get(22).setFront("Cards/Clubs/jack_clubs.png");
      deck.get(23).setFront("Cards/Clubs/queen_clubs.png");
      deck.get(24).setFront("Cards/Clubs/king_clubs.png");
      deck.get(25).setFront("Cards/Clubs/ace_clubs.png");
   
      deck.get(26).setFront("Cards/Hearts/two_hearts.png");
      deck.get(27).setFront("Cards/Hearts/three_hearts.png");
      deck.get(28).setFront("Cards/Hearts/four_hearts.png");
      deck.get(29).setFront("Cards/Hearts/five_hearts.png");
      deck.get(30).setFront("Cards/Hearts/six_hearts.png");
      deck.get(31).setFront("Cards/Hearts/seven_hearts.png");
      deck.get(32).setFront("Cards/Hearts/eight_hearts.png");
      deck.get(33).setFront("Cards/Hearts/nine_hearts.png");
      deck.get(34).setFront("Cards/Hearts/ten_hearts.png");
      deck.get(35).setFront("Cards/Hearts/jack_hearts.png");
      deck.get(36).setFront("Cards/Hearts/queen_hearts.png");
      deck.get(37).setFront("Cards/Hearts/king_hearts.png");
      deck.get(38).setFront("Cards/Hearts/ace_hearts.png");
   
      deck.get(39).setFront("Cards/Diamonds/two_diamonds.png");
      deck.get(40).setFront("Cards/Diamonds/three_diamonds.png");
      deck.get(41).setFront("Cards/Diamonds/four_diamonds.png");
      deck.get(42).setFront("Cards/Diamonds/five_diamonds.png");
      deck.get(43).setFront("Cards/Diamonds/six_diamonds.png");
      deck.get(44).setFront("Cards/Diamonds/seven_diamonds.png");
      deck.get(45).setFront("Cards/Diamonds/eight_diamonds.png");
      deck.get(46).setFront("Cards/Diamonds/nine_diamonds.png");
      deck.get(47).setFront("Cards/Diamonds/ten_diamonds.png");
      deck.get(48).setFront("Cards/Diamonds/jack_diamonds.png");
      deck.get(49).setFront("Cards/Diamonds/queen_diamonds.png");
      deck.get(50).setFront("Cards/Diamonds/king_diamonds.png");
      deck.get(51).setFront("Cards/Diamonds/ace_diamonds.png");
   }
   
   //shuffles the deck
   public void shuffle()
   {
      for(int i = 0; i < 1000; i++)
      {
         int t1 = (int)(Math.random()*deck.size());
         int t2 = (int)(Math.random()*deck.size());
         while(t1 == t2)
         {
            t1 = (int)(Math.random()*deck.size());
            t2 = (int)(Math.random()*deck.size());
         }
         swap(t1, t2); //cuts the deck in half
      }
   }
   
   /*
    Swaps two integers.
 
    a and b are two integers.
    switches the two objects' values.
 */
   public void swap(int a, int b)
   {
      Card temp = deck.get(a);
      deck.set(a, deck.get(b));
      deck.set(b, temp);
   }
   
   public Card drawCard()
   {
      if(deck.size()>0)
      {
         int rand = (int)(Math.random()*size);
         Card c = deck.remove(rand);
         size--;
         return c;
      }
      return null;
   }
     
   public Card getCard(int i)
   {
      return deck.get(i);
   }
   
   public int getSize()
   {
      return size;
   }
}