import java.util.ArrayList;
public class PokerPlayer
{

   private int xCoor,yCoor;
   private String name;
   private int chips;
   private ArrayList<Card> hand;
   private int score;
   private ArrayList<Card> bestCards;
   private String bestSet;
   private int playerNum;
   
   public PokerPlayer(int p, String n)
   {
      playerNum = p;
      name = n;
      chips = 500;
      hand = new ArrayList<Card>();
      bestCards = new ArrayList<Card>();
      score = 0;
      bestSet = "";
   }
   
   public int getPlayerNum()
   {
      return playerNum;
   }
   
   public int getChips()
   {
      return chips;
   }
   
   public String getName()
   {
      return name;
   }
   
   public void bet(int n)
   {
      chips = chips - n;
   }
    
   public void winsPot(int pot)
   {
      chips = chips + pot;
   } 
    
   public ArrayList<Card> getHand()
   {
      return hand;
   }
   
   public void clearHand()
   {
      hand.clear();
   }
   
   public Card getCard1()
   {
      return hand.get(0);
   }
   
   public Card getCard2()
   {
      return hand.get(1);
   }
   
   public void addCard(Card c)
   {
      if(hand.size()<2)
      {
         hand.add(c);
      }
   }
   
   public int getScore()
   {
      return score;
   }
   
   public String getBestSet()
   {
      return bestSet;
   }
   
   public ArrayList<Card> getBestHand()
   {
      return bestCards;
   }
   
   public int getX()
   {
      return xCoor;
   }
   
   public int getY()
   {
      return yCoor;
   }  
   
   public void setX(int x)
   {
      xCoor = x;
   }
   
   public void setY(int y)
   {
      yCoor = y;
   }
   
   public void organize(ArrayList<Card> dan)
   {
      for(int i = 0; i < dan.size(); i++)
      {
         for(int j = dan.size()-1; j > i; j--)
         {
            if(dan.get(i).getValue() < dan.get(j).getValue())
            {
               Card temp = dan.get(i);
               dan.set(i, dan.get(j));
               dan.set(j, temp);
            }
         }
      }
   }
   
  //This is the second method shows it in a way the user can see 
  /*More will have to be done incase two players have the same scores, ie: if one player has a pair of kings and the other
    has a pair of 2s, the player with the pair of kings must win, as of right now the methos will not catch that */

   public String setScore(ArrayList<Card> center) // 2,000,000 = Pair,           3,000,000 = Two Pair ,      4,000,000 = Three of a Kind, 
   {                                              // 5,000,000 = straight,       6,000,000 = flush,          7,000,000 = Full House
      ArrayList<Card> cards = new ArrayList();    // 8,000,000 = Four of a Kind, 9,000,000 = straight flush                                 
      for(int i = 0; i < hand.size(); i++)        //10,000,000 = Royal Flush ,   1,000,000 = nothing/highcard
      {
         cards.add(hand.get(i));
      }
      
      for(int i = 0; i < center.size(); i++)
      {
         cards.add(center.get(i));
      } 
      
      if(cards.size()<=2)
      {
         return "Nothing";
      }
      
      organize(cards);
      
      //CHECK FOR STRAIGHT FLUSH - 9,000,000
      // Straight cards in descending order, starting from the highest card
      //bestCards:(Straight card 1, Straight card 2, Straight card 3, Straight card 4, Straight card 5) 
      
      int straightFlushCheck = 0;
      Card lastCard = new Card(-1, 2);
      Card curr = null;
      Card firstCard = new Card(-1, 2);
      if(cards.get(0).getValue() == 14)
      {
         cards.add(new Card(1,cards.get(0).getSuit()));
      }
      for(int i = 0; i < cards.size(); i++)
      {               
         curr = cards.get(i);
         bestCards.add(curr);
         if(curr.getValue() == lastCard.getValue()-1&&curr.getSuit() == lastCard.getSuit())
         {
            straightFlushCheck++;
            if(straightFlushCheck == 4)
            {
               score = 9000000;
               if(firstCard.getValue() == 14)
               {
                  if(bestCards.size()==5)
                  {
                     score = 10000000;
                  }
                  return "Royal Flush!!";
               }
               if(bestCards.size()==5){
                  score  +=  (bestCards.get(0).getValue()*10000);
               }
               bestSet = "Straight flush of " + Card.getSuitName(firstCard.getSuit()) + " with a " +Card.getValueName(firstCard.getValue())+ " high";
               return bestSet; //STRAIGHT  
            }
         }
         else
         {
            straightFlushCheck = 0; 
            bestCards.clear();  
         } 
         lastCard = curr;             
      }
      if(cards.get(0).getValue() == 14)
      {
         cards.remove(cards.size()-1);
      }
      
      bestCards.clear();
      //CHECK FOR A FOUR OF A KIND - 8,000,000
      //bestCards:(Four card 1, Four card 2, Four card 3, Four card 1, Highcard)
      int fourCheck = 0;
      for(int i = 0; i < cards.size(); i++)
      {
         for(int a = 0; i < cards.size(); i++)
         {
            if(cards.get(i).getValue() == cards.get(a).getValue())
            {
               fourCheck++;
               bestCards.add(cards.get(a));
               if(fourCheck == 4)
               {
                  score = 8000000;
                  while(bestCards.size()<5)
                  {
                     int x = 0;
                     if(cards.get(x).getValue()!=bestCards.get(0).getValue())
                     {
                        bestCards.add(cards.get(x));
                     }
                     x++;
                  }
                  if(bestCards.size()==5){
                     score  +=  (bestCards.get(0).getValue()*10000) + (bestCards.get(4).getValue()*1);
                  }
                  bestSet = "Four of a Kind with " + Card.getValueName(cards.get(i).getValue()) + "'s";
                  return bestSet;
               }
            }
         }
         bestCards.clear();
         fourCheck = 0;
      }
   
      bestCards.clear();
      //CHECK FOR A FULL HOUSE - 7,000,000
      //bestCards:(Three card 1, Three card 2, Three card 3, Pair card 1, Pair card 2)
      boolean threeKindFH = false;
      int threeKindCheck = 0;
      int pairKindFH = 0;
      curr = null;
      for(int i = 0; i < cards.size(); i++)
      {
         for(int a = 0; a < cards.size(); a++)
         {
            if(cards.get(i).getValue() == cards.get(a).getValue())
            {
               if(threeKindFH == false)
               {
                  bestCards.add(cards.get(a));
                  threeKindCheck++;
                  if(threeKindCheck == 3)
                  {
                     curr = cards.get(i);
                     threeKindFH = true;
                  }
               }
            }
         }
         
         bestCards.clear();
         threeKindCheck = 0;
      }
      if(threeKindFH == true)
      {
         for(int i = 0; i < cards.size(); i++)
         {
            for(int a = 0; a < cards.size(); a++)
            {
               if(cards.get(i).getValue() == cards.get(a).getValue()&& cards.get(i).getValue()!=curr.getValue())
               {
                  pairKindFH++;
                  bestCards.add(cards.get(i));
                  bestCards.add(cards.get(a));
                  if(pairKindFH == 2)
                  {
                     score = 7000000;
                     if(bestCards.size()==5){
                        score  +=  (bestCards.get(0).getValue()*10000)  +  (bestCards.get(3).getValue()*10);
                     }
                     bestSet = "Full House with three " + Card.getValueName(curr.getValue())+"s and two " + Card.getValueName(cards.get(i).getValue()) + "s";
                     return bestSet;
                  }
               }
            }
            pairKindFH = 0;
         }
      }
      
      bestCards.clear();
      //CHECK FOR A FLUSH - 6,000,000 
      //Flush cards in descending order, starting from the highest card
      //bestCards:(Flush card 1, Flush card 2, Flush card 3, Flush card 4, Flush card 5)  
      int flushCheck = 0;
      for(int i = 0; i < cards.size(); i++)
      {
         for(int a = 0; a < cards.size(); a++)
         {
            if(cards.get(i).getSuit() == cards.get(a).getSuit())
            {
               bestCards.add(cards.get(a));
               flushCheck++;
               if(flushCheck == 5)
               {
                  score = 6000000; 
                  if(bestCards.size()==5){        
                     score  +=  (bestCards.get(0).getValue()*10000)  +  (bestCards.get(1).getValue()*1000)  +  (bestCards.get(2).getValue()*100)  +  (bestCards.get(3).getValue()*10) + (bestCards.get(4).getValue()*1);
                  }
                  bestSet = "Flush of " + Card.getSuitName(cards.get(i).getSuit());
                  return bestSet; //FLUSH
               }
            }
         }
         bestCards.clear();
         flushCheck = 0;
      }
      
      bestCards.clear();
      
      //CHECK FOR A STRAIGHT - 5,000,000
      //Straight cards in descending order, starting from the highest card
      //bestCards:(Straight card 1, Straight card 2, Straight card 3, Straight card 4, Straight card 5) 
      int straightCheck = 0;
      int lastCardValue = -1;
      curr = null;
      if(cards.get(0).getValue() == 14)
      {
         cards.add(new Card(1,cards.get(0).getSuit()));
      } 
      for(int i = 0; i < cards.size(); i++)
      {         
         curr = cards.get(i);
         
         bestCards.add(curr);
         if(curr.getValue() == lastCardValue-1)
         {
            straightCheck++;
            if(straightCheck == 4)
            {
               score = 5000000;
               if(bestCards.size()==5){
                  score  +=  (bestCards.get(0).getValue()*10000);
               }
               bestSet = "Straight of " + Card.getValueName(lastCardValue + 3) + " high";
               return bestSet; //STRAIGHT  
            }
         }
         else
         {
            if(i!=0)
            {        
               bestCards.clear();
            }
            straightCheck = 0;   
         } 
         lastCardValue = curr.getValue();             
      }
      if(cards.get(0).getValue() == 14)
      {
         cards.remove(cards.size()-1);
      }
     
     
      bestCards.clear();
      //CHECK FOR A THREE OF A KIND - 4,000,000
      //bestCards:(Three of a kind card 1, Three of a kind card 2, Three of a kind card 3, Highcard 1, Highcard 2)
      int threeCheck = 0;
      for(int i = 0; i < cards.size(); i++)
      {
         for(int a = 0; a < cards.size(); a++)
         {
            if(cards.get(i).getValue() == cards.get(a).getValue())
            {
               bestCards.add(cards.get(a));
               threeCheck++;
               if(threeCheck == 3)
               {
                  score = 4000000;   
                  int x2 = 0;
                  while(bestCards.size()<5 && bestCards.size()<cards.size())
                  {
                     if(cards.get(x2).getValue()!=bestCards.get(0).getValue())
                     {
                        bestCards.add(cards.get(x2));
                     }
                     x2++;
                  } 
                  if(bestCards.size()==5){ 
                     score  +=  (bestCards.get(0).getValue()*10000)  +  (bestCards.get(3).getValue()*10) + (bestCards.get(4).getValue()*1);
                  }
                  bestSet = "Three of a Kind of "+ Card.getValueName(cards.get(i).getValue())+"'s";
                  return bestSet;  // Three of a Kind
               }
            } 
         }
         bestCards.clear();
         threeCheck = 0;
      }
      
      
      bestCards.clear();
      //CHECK FOR A PAIR OF PAIRS - 3,000,000
      //bestCards:(higher pair card 1, higher pair card 2,lower pair card 1, lower pair card 2, Highcard) 
      int twoPair = 0;
      int pairCheck = 0;
      curr = null;
      for(int i = 0; i < cards.size(); i++)
      {
         for(int a = 0; a < cards.size(); a++)
         {
            if(cards.get(i).getValue() == cards.get(a).getValue())
            {
               pairCheck++;
               if(pairCheck == 2)
               {
                  twoPair++;  
                  if(twoPair == 2)
                  {
                     score = 3000000;
                     bestCards.add(cards.get(i));
                     bestCards.add(cards.get(a));
                     
                     int c = 0;
                     while(bestCards.size()<5 && bestCards.size()<cards.size())
                     {
                        if(cards.get(c).getValue()!=bestCards.get(0).getValue() && cards.get(c).getValue()!=bestCards.get(2).getValue())
                        {
                           bestCards.add(cards.get(c));
                        }
                        c++;
                     }
                     if(bestCards.size()==5)
                     {
                        score  +=  (bestCards.get(0).getValue()*10000)  +  (bestCards.get(2).getValue()*100)  + (bestCards.get(4).getValue()*1);
                     }
                     bestSet = "Two pairs of " + Card.getValueName(curr.getValue())+"s and " + Card.getValueName(cards.get(i).getValue()) + "s";
                     return bestSet;//Two Pair
                  }
                  bestCards.add(cards.get(i));
                  bestCards.add(cards.get(a));
                  curr = cards.get(i);
                  cards.remove(i);
                  
               }
            }
         }
         pairCheck = 0;
      }
     
     //CHECK FOR A PAIR - 2,000,000 
     //bestCards:(pair card 1, pair card 2, Highcard 1, Highcard 2, Highcard 3)
      if(twoPair == 1)
      {
         score = 2000000;
         int x1 = 0;
         while(bestCards.size()<5 && bestCards.size()<cards.size())
         {
            
            if(cards.get(x1).getValue()!=bestCards.get(0).getValue())
            {
               bestCards.add(cards.get(x1));
            }
            x1++;
         }
         if(bestCards.size()==5){
            score  +=  (bestCards.get(0).getValue()*10000)  +  (bestCards.get(2).getValue()*100)  +  (bestCards.get(3).getValue()*10) + (bestCards.get(4).getValue()*1);
         }
         bestSet = "Pair of "+ Card.getValueName(curr.getValue())+"'s";
         return bestSet;  // Pair
      }
      
      
      bestCards.clear();
      //CHECK FOR HIGHEST CARD - 1,000,000
      //bestCards:(Highest card, 2nd Highest card, 3rd Highest card, 4th Highest card, 5th Highest card)
      score = 1000000;
      
      int x = 0;
      while(bestCards.size()<5 && bestCards.size()<cards.size())
      {
         bestCards.add(cards.get(x));
         x++;
      }
      if(bestCards.size()==5){
         score  +=  (bestCards.get(0).getValue()*10000)  +  (bestCards.get(1).getValue()*1000)  +  (bestCards.get(2).getValue()*100)  +  (bestCards.get(3).getValue()*10) + (bestCards.get(4).getValue()*1);
      }
      bestSet = "Highcard of " + cards.get(0);
      return bestSet;
   }
   
   public String toString()
   {
      return ""+getScore();
   }
}