   import java.io.*;
   import java.util.*;
    public class Poker
   {
      //**************************************************************************
       public static class Card implements Comparable
      {
         private int value; //1-13 is A-K, 14 is A
         private int suit;	//3-heart,4-diamond,5-club,6-spade
         private boolean drawn;	//true if the card has been drawn from the deck - false if the card is still in the deck
              
          public Card(int v, int s)
         { value=v;	suit=s;	drawn=false;}
         
          public String toString()
         {
            if(value==1 || value==14)
               return "A" + (char)(suit);
            if(value==11)
               return "J" + (char)(suit);
            if(value==12)
               return "Q" + (char)(suit);
            if(value==13)
               return "K" + (char)(suit);
            return "" + value + (char)(suit);
         }
         
          public int getValue()
         {
            return value;
         }
      	
          public int getSuit()
         {
            return suit;
         }
      	
          public boolean getDrawn()
         {
            return drawn;
         }
      	
          public int getBlackjackValue()
         {
            if(value>10)
               return 10;
            return value;
         }
         
          public int compareTo(Object rhs)
         {
            Card other = (Card)(rhs);
            if(this.value < other.value)
               return -1;
            if(this.value == other.value)
               return 0;
            return 1;
         }
         
          public void putBack()
         {
            drawn = false;
         }
         
          public Card draw()
         {
            drawn = true;
            return this;
         }
      }							//end Card Object
      //**************************************************************************
   
       public static Card[] createDeck()
      {
         Card[] deck = new Card[52];
         int index=0;
         for(int v=1; v<=13; v++)
         {
            for(int s=3; s<=6; s++)
            {
               deck[index] = new Card(v,s);
               index++;
            }
         }
         return deck;
      }
   
       public static void showArray(double[] freq)
      {
         for(int i=0; i<freq.length; i++)
            System.out.print(freq[i]+" ");
         System.out.println();
      }
   
       public static void show(Card[] deck)
      {
         for(int i=0; i<deck.length; i++)
            System.out.print(deck[i]+" ");
         System.out.println();
      }
   
       public static void swap(Comparable[] nums, int a, int b)
      {
       //pre:  a and b are valid index #s of nums
      //post: swaps nums[a] with nums[b]
         Comparable temp = nums[a];
         nums[a] = nums[b];
         nums[b] = temp;
      }
      
       public static void swap(int[] nums, int a, int b)
      {
       //pre:  a and b are valid index #s of nums
      //post: swaps nums[a] with nums[b]
         int temp = nums[a];
         nums[a] = nums[b];
         nums[b] = temp;
      }
   	       
       public static void selSort(Comparable[] nums)
      {
      //post: nums is sorted in ascending order    
         int min, size = nums.length;
         for (int i=0; i < size; i++)
         {
            min = i;
            for (int j = i + 1; j < size; j++)
            {
               if (nums[j].compareTo(nums[min]) < 0)
                  min = j;
            }
            swap (nums, i, min);
         }
      }
      
       public static void swap(ArrayList nums, int a, int b)
      {
       //pre:  a and b are valid index #s of nums
      //post: swaps nums[a] with nums[b]
         Object temp = nums.get(a);
         nums.set(a,nums.get(b));
         nums.set(b,temp);
      }
   
       public static void selSort(ArrayList<Comparable> nums)
      {
      //post: nums is sorted in ascending order    
         int min, size = nums.size();
         for (int i=0; i < size; i++)
         {
            min = i;
            for (int j = i + 1; j < size; j++)
            {
               if (nums.get(j).compareTo(nums.get(min)) < 0)
                  min = j;
            }
            swap (nums, i, min);
         }
      }
   
       public static void selSort(int[] nums)
      {
      //post: nums is sorted in decending order    
         int max, size = nums.length;
         for (int i=0; i < size; i++)
         {
            max = i;
            for (int j = i + 1; j < size; j++)
            {
               if (nums[j]>nums[max])
                  max = j;
            }
            swap (nums, i, max);
         }
      }
   
       public static void shuffle(Card[] deck)
      {
         for(int i=0; i<deck.length; i++)
            deck[i].putBack();
         for(int i=0; i<20000; i++)
         {
            int r1=(int)(Math.random()*deck.length);
            int r2=(int)(Math.random()*deck.length);
            swap(deck, r1, r2);
         }
      }
   
       public static int highCard(Card[] hand)
      {
         selSort(hand);
         if (hand[0].getValue()==1)
            return 14;									//ACE
         return hand[hand.length-1].getValue();
      }
   
       public static int pair(Card[] hand)
      {
      //if there is one pair, returns the value of the pair
         int[]freq = new int[15];			//index 0 will be unused.  index will correspond to card value
         for(int i=0; i<hand.length; i++)
         {
            if (hand[i].getValue()==1)
               freq[14]++;
            else
               freq[hand[i].getValue()]++;
         } 
         int numOfPairs=0;
         int numOfTrips=0;
         int numOfQuads=0;
         int indexOfPair=0;
         for(int i=0; i<freq.length; i++)
         {
            if(freq[i]==2)
            {
               numOfPairs++;
               indexOfPair=i;
            }   
            if(freq[i]==3)
               numOfTrips++;
            if(freq[i]==4)
               numOfQuads++;   
         }
         if(numOfPairs==1 && numOfTrips==0)
            return indexOfPair;
         return 0;
      }
   
       public static int[] twoPair(Card[] hand)
      {
      //if there is two pair, returns the value of the pair as an array: index 0 is the higher of the two pair
      //returns null if there is not two pair
         int[]freq = new int[15];			//index 0 will be unused.  index will correspond to card value
         int[]pairValues=new int[3];		//you might have three pairs, so index 2 will be unused when returned		
         for(int i=0; i<hand.length; i++)
         {
            if (hand[i].getValue()==1)
               freq[14]++;
            else
               freq[hand[i].getValue()]++;
         } 
         int numOfPairs=0;
         int index=0;
         for(int i=0; i<freq.length; i++)
         {
            if(freq[i]==2)
            {
               numOfPairs++;
               pairValues[index]=i;
               index++;
            }   
         }
         if(numOfPairs>1)
         {
            selSort(pairValues);               
            return pairValues;
         } 
      
         return null;
      }
   
       public static int trips(Card[] hand)
      {
      //if there is one set of trips and no pair, returns the value of the trips
         int[]freq = new int[15];			//index 0 will be unused.  index will correspond to card value
         for(int i=0; i<hand.length; i++)
         {
            if (hand[i].getValue()==1)
               freq[14]++;
            else
               freq[hand[i].getValue()]++;
         }
         int numOfPairs=0;
         int numOfTrips=0;
         int indexOfTrips=0;
         for(int i=0; i<freq.length; i++)
         {
            if(freq[i]==2)
               numOfPairs++;
            if(freq[i]==3)
            {
               numOfTrips++;
               indexOfTrips=i;    
            }	
         }
         if(numOfPairs==0 && numOfTrips==1)
            return indexOfTrips;
         return 0;
      }
   
       public static int straight(Card[] hand)
      {
      //if there is a straight, returns the value of the nutz
         selSort(hand);
         int[]freq = new int[15];			//index 0 will be unused.  index will correspond to card value.
         //an Ace will be at index 1 and at index 14  
         for(int i=0; i<hand.length; i++)
         {
            freq[hand[i].getValue()]++;
            if (hand[i].getValue()==1)
               freq[14]++;    
         }
         int nutz=0;
         for(int i=0; i<freq.length-4; i++)
         {
            if(freq[i]!=0 && freq[i+1]!=0 &&
               freq[i+2]!=0 && freq[i+3]!=0 && 
            	freq[i+4]!=0)
            {	
               nutz = i+4;
            }   
         }
         /*
         if (nutz==14)			//HIGH ACE is now 14
            return 1;	 */
         return nutz;
      }
   
       public static int[] flush(Card[] hand)
      {
      //index 0 is the int suit value of a flush - 3-heart,4-diamond,5-club,6-spade, 0 if there is no flush
      //index 1 is the nutz of the flush, 0 if there is no flush
         int[]ans = new int[2];
         ArrayList[] freq = new ArrayList[7]; 	//indicies 0-2 are unused.  3-6 correspond to the suit values of a Card
         for(int i=0; i<freq.length; i++)			//freq[3] is an ArrayList of all the cards in the hand that are hearts, etc
            freq[i]=new ArrayList();  
      	
         for(int i=0; i<hand.length; i++)
            freq[hand[i].getSuit()].add(hand[i]);
            
         for(int i=0; i<freq.length; i++)
            if(freq[i].size()>=5)
            {
               selSort(freq[i]);
               Card lowCard = (Card)freq[i].get(0);
               Card nutzCard = (Card)freq[i].get(freq[i].size()-1);
               ans[0] = nutzCard.getSuit();	//suit of high value card
               ans[1] = nutzCard.getValue();	//value of high value card
               if (lowCard.getValue()==1)
                  ans[1]=14;						//ACE is now 14
            }      
         if(ans[0]==0 && ans[1]==0)
            return null;		
         return ans;
      }
   
       public static int[] fullHouse(Card[] hand)
      {
      //returns the value of the full house as an array: index 0 is the trips part, index 1 is the pairs part
         int[]freq = new int[15];			//index 0 will be unused.  index will correspond to card value
         int[]ans = new int[2];
         for(int i=0; i<hand.length; i++)
         {
            if (hand[i].getValue()==1)
               freq[14]++;
            else
               freq[hand[i].getValue()]++;
         } 
         int numOfPairs=0;
         int numOfTrips=0;
         int[] indexOfPairs = {0,0,0};		//out of 7 cards, you can have a trip and two pair which is still a full house
         int iOP=0;								//index within indexOfPairs
         int[] indexOfTrips={0,0};			//you can also have two trips which is still a full house
         int iOT=0;								//index within indexOfTrips
         for(int i=0; i<freq.length; i++)
         {
            if(freq[i]==2)
            {
               numOfPairs++;
               indexOfPairs[iOP++]=i;    //record the value of the pair
            }	
            if(freq[i]==3)
            {
               numOfTrips++;
               indexOfTrips[iOT++]=i;    			//record the value of the trips
            }	
         }
         //System.out.println(numOfPairs+" "+numOfTrips);
         if((numOfPairs>=1 && numOfTrips==1) || numOfTrips==2)
         {
            if(numOfTrips==2)
            {
               if (indexOfTrips[0]>indexOfTrips[1])	//the higher of the trips is the trips part of the full house
               {													//the lower of the trips is the pair part of the full house
                  ans[0]=indexOfTrips[0];
                  ans[1]=indexOfTrips[1];
               }
               else
               {
                  ans[0]=indexOfTrips[1];
                  ans[1]=indexOfTrips[0];
               }
               return ans;	
            }
            ans[0]=indexOfTrips[0];
            if(numOfPairs==2)								//the higher of the pair is the pair part of the full house
            {
               if (indexOfPairs[0]>indexOfPairs[1])
                  ans[1]=indexOfPairs[0];
               else
                  ans[1]=indexOfPairs[1];
               return ans;    
            }
            ans[1]=indexOfPairs[0];
         }
         if(ans[0]==0 && ans[1]==0)
            return null;
         return ans;
      }
     
       public static int quads(Card[] hand)
      {
      //if there is a four of a kind, returns the value of it
         int[]freq = new int[15];			//index 0 will be unused.  index will correspond to card value (14 is also ACE)
         for(int i=0; i<hand.length; i++)
         {
            if (hand[i].getValue()==1)
               freq[14]++;
            else
               freq[hand[i].getValue()]++;
         }   
         int numOfQuads=0;
         int indexOfQuads=0;
         for(int i=0; i<freq.length; i++)
         {
            if(freq[i]==4)
            {
               numOfQuads++;   
               indexOfQuads=i;   
            }
         }
         if(numOfQuads==1)
            return indexOfQuads;
         return 0;
      }
   
       public static int straightFlush(Card[] hand)
      {
         //if there is a straight flush, returns the value of the nutz
         if(flush(hand)!=null && straight(hand)!=0)
         {
            int[]flushValue = flush(hand);
            int flushSuit = flushValue[0];
         
            ArrayList flush = new ArrayList();
            for(int i=0; i<hand.length; i++)
               if(hand[i].getSuit()==flushSuit)
                  flush.add(hand[i]);
            Card[]flush2=new Card[flush.size()];
            for(int i=0; i<flush.size(); i++)
               flush2[i]=(Card)(flush.get(i));
            selSort(flush2);
            return straight(flush2);
         }   
         return 0;
      }
    
       public static int[] determineValue(Card[] hand)
      {
      //2-14 for HIGH-CARD (ACE is 14), 
      //22-34 for PAIR, 
      //42-54 for high pair in TWO-PAIR: 62-74 for low pair in two pair, 
      //82-94 for TRIPS, 
      //102-114 for STRAIGHT, 
      //122-134 for FLUSH,
      //142-154 for trips in FULL-HOUSE: 162-174 for pair part,  
      //182-194 for QUADS, 
      //202-214 for STRAIGHT-FLUSH
         int[] ans = {0, 0};
         if(straightFlush(hand)!=0)
         {
            System.out.println("Straight Flush");
            ans[0]=200 + straightFlush(hand);
         }
         else
            if(quads(hand)!=0)
            {
               System.out.println("Four of a Kind");
               ans[0]=180 + quads(hand);
            }
            else
               if(fullHouse(hand)!=null)
               {
                  int[] value=fullHouse(hand);
                  System.out.println("Full House");
                  ans[0]=140 + value[0];
                  ans[1]=160 + value[1];
               }
               else
                  if(flush(hand)!=null)
                  {
                     int[] value=flush(hand);
                     System.out.println("Flush");
                     ans[0]=120 + value[1];
                  }
                  else
                     if(straight(hand)!=0)
                     {
                        System.out.println("Straight");
                        ans[0]=100 + straight(hand);
                     }
                     else
                        if(trips(hand)!=0)
                        {
                           System.out.println("Trips");
                           ans[0]=80+trips(hand);
                        }
                        else
                           if(twoPair(hand)!=null)
                           {
                              int[] value=twoPair(hand);
                              System.out.println("Two Pair");
                              ans[0]=40 + value[0];
                              ans[1]=60 + value[1];
                           }
                           else
                              if(pair(hand)!=0)
                              {
                                 System.out.println("Pair");
                                 ans[0]=20+pair(hand);
                              }
                              else
                              {
                                 System.out.println("High Card");
                                 ans[0]=highCard(hand);
                              }
      
         return ans;
      }
   	
       public static ArrayList createHands(int players)
       //creates players # of seven card hands
      {
         ArrayList hands = new ArrayList();
         Card[] deck = createDeck();
         shuffle(deck);
      
      	//5 community cards
         Card[] community = new Card[5];
         for(int i=0; i<5; i++)
         {
            int r;
            do{
               r=(int)(Math.random()*deck.length);
            }while(deck[r].getDrawn());
            community[i]=deck[r].draw();
         }
      
         for(int p=0; p<players; p++)
         {
            Card[] hand = new Card[7];		//7 random cards for testing (5 community + 2 pocket)
            for(int i=0; i<2; i++)			//each player gets 2 pocket cards
            {
               int r;
               do{
                  r=(int)(Math.random()*deck.length);
               }while(deck[r].getDrawn());
               hand[i]=deck[r].draw();
            }
            for(int i=0; i<5; i++)
               hand[i+2] = new Card(community[i].getValue(), community[i].getSuit());
            selSort(hand);
            hands.add(hand);
         }
         return hands;
      }
   	
   	  
       public static void countFreq()throws IOException
      {
      //creates a bunch of hands and keeps track of the frequency of each type of winning hand
      /*
         Card[] deck = {new Card(9,3), new Card(9,4), new Card(9,5), new Card(3,6), new Card(3,5),new Card(5,4),new Card(5,3)};
         int[] test = fullHouse(deck);
         System.out.println(test[0] + " " + test[1]);
      */
         BufferedReader input = new BufferedReader (new InputStreamReader(System.in));
      
         Card[] deck = createDeck();	//full deck of 52 cards
         double [] freq={0,0,0,0,0,0,0,0,0}, percent={0,0,0,0,0,0,0,0,0};
         double count=0;
      	
         while(true)
         {
            shuffle(deck);
            Card[] hand = new Card[7];		//7 random cards for testing (5 community + 2 pocket)
            for(int i=0; i<7; i++)	
            {
               int r;
               do{
                  r=(int)(Math.random()*deck.length);
               }while(deck[r].getDrawn());
               hand[i]=deck[r].draw();
            }
            selSort(hand);
            show(hand);
            int[]value = determineValue(hand);
              
            if(value[0]<=14)  freq[0]++;//2-14 for HIGH-CARD (ACE is 14), 
            else
               if(value[0]<=34)  freq[1]++;//22-34 for PAIR, 
               else
                  if(value[0]<=74)  freq[2]++;//42-54 for high pair in TWO-PAIR: 62-74 for low pair in two pair, 
                  else
                     if(value[0]<=94)  freq[3]++;//82-94 for TRIPS, 
                     else
                        if(value[0]<=114)  freq[4]++; //102-114 for STRAIGHT, 
                        else
                           if(value[0]<=134)  freq[5]++; //122-134 for FLUSH,
                           else
                              if(value[0]<=174)  freq[6]++;//142-154 for trips in FULL-HOUSE: 162-174 for pair part,
                              else
                                 if(value[0]<=194)  freq[7]++;//182-194 for QUADS,
                                 else
                                    if(value[0]<=214)  freq[8]++; //202-214 for STRAIGHT-FLUSH
            count++;
            for(int i=0; i<freq.length; i++)
               percent[i]=(freq[i]/count)*100.0;                        
            for(int i=0; i<percent.length; i++)
            {
               if(i==0)
                  System.out.print("HIGH CARD: ");
               else
                  if(i==1)
                     System.out.print("PAIR: ");
                  else
                     if(i==2)
                        System.out.print("TWO PAIR: ");
                     else
                        if(i==3)
                           System.out.print("SET: ");
                        else
                           if(i==4)
                              System.out.print("STRAIGHT: ");
                           else
                              if(i==5)
                                 System.out.print("FLUSH: ");
                              else
                                 if(i==6)
                                    System.out.print("BOAT: ");
                                 else
                                    if(i==7)
                                       System.out.print("QUADS: ");
                                    else
                                       if(i==8)
                                          System.out.print("STRAIGHT FLUSH: ");
               System.out.println(percent[i]);
            }       
            System.out.println(count);
            //System.out.println(value[0]+" "+value[1]);
            //input.readLine();
         }
         
      }
   
       public static int determineWinner(ArrayList players)
      {
      //players is a collection of player hands (7 cards)
      //returns the index of the player that wins
         double[] freq = new double[players.size()];
         for(int i=0; i<players.size(); i++)
         {
            int sfvalue=0, qvalue=0, svalue=0, tvalue=0, pvalue=0, hcvalue=0;
            int[] fhvalue, fvalue, tpvalue; 
            sfvalue=straightFlush((Card[])players.get(i));
            qvalue=quads((Card[])players.get(i));
            fhvalue = fullHouse((Card[])players.get(i));
            fvalue = flush((Card[])players.get(i));
            tvalue = trips((Card[])players.get(i));
            tpvalue = twoPair((Card[])players.get(i));
            pvalue = pair((Card[])players.get(i));
            hcvalue = highCard((Card[])players.get(i));
            show((Card[])players.get(i));
         
            if(sfvalue!=0)
            {
               freq[i]=700+sfvalue;
               System.out.println("Straight Flush!  OOOOOOOOOAH!");
            }
            else  
               if(qvalue!=0)
               {
                  freq[i]=600+qvalue;
                  System.out.println("Four of a Kind!");
               }
               else
                  if(fhvalue!=null)
                  {
                     freq[i]=500+ (fhvalue[0]+10) + fhvalue[1];
                     System.out.println("Full HOUSE!");
                  }
                  else
                     if(fvalue!=null)
                     {
                        freq[i]=400+fvalue[1];
                        System.out.println("Flush!");
                     }
                     else
                        if(tvalue!=0)
                        {
                           freq[i]=300+tvalue;
                           System.out.println("TRIPS!");
                        }   
                        else
                           if(tpvalue!=null)
                           {
                              freq[i]=200+ (tpvalue[0]+10) + tpvalue[1];
                              System.out.println("Two Pair");
                           }
                           else
                              if(pvalue!=0)
                              {
                                 freq[i] = 100 + pvalue;
                                 System.out.println("Pair");
                              }
                              else
                              {
                                 freq[i] = hcvalue;
                                 System.out.println("Nothing");
                              
                              }
                 	
         }
         //showArray(freq);
         int maxI=0;
         for(int i=0; i<players.size(); i++)
         {
            if (freq[i] > freq[maxI])
               maxI = i;
         }
         //System.out.println(maxI);
         return maxI;
      }
   
       public static void main(String[]arg)throws IOException
      {
      
         countFreq();
         ArrayList<Card[]> hands = createHands(8);
         for(int i=0; i<hands.size(); i++)
            show((Card[])hands.get(i));
         int winner = determineWinner(hands);
         System.out.println("\nThe winner is:");
         show(hands.get(winner));
      }
   
   }