import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.TimerTask;
import java.io.*;
import java.awt.event.KeyEvent;

public class PokerTable extends JPanel implements MouseListener, MouseMotionListener
{
   
   private JLabel tablePic, potNum;
   private ImageIcon background = new ImageIcon("table.jpg");
   private static Dimension screenSize;
   private static int screenWidth;		//size of the screen in pixels
   private static int screenHeight;
   
   private static final int FLOP = 1;
   private static final int TURN = 2;
   private static final int RIVER = 3;
   private static final int CLEAR = 4;
   private static final int DEALT = 5;
   
   private static Card card1;
   private static Card card2;
   private static Card card3;
   private static Card card4;
   private static Card card5;
   private static ArrayList<Card> centerCards;
   
   private static int drawn;
   //private JButton draw;
   private static Deck deck;
   private static int round;
   private static ArrayList<PokerPlayer> players;
   private static ArrayList<PokerPlayer> winner;
   private static int numPlayers;
   private static boolean isWinner;
   private int mouseX, mouseY;
   
   private static boolean isClientReady = false;   //Are they ready to start the game?
   private static boolean isServerReady = false;
   private static boolean GlobalIsServer;
   
   public PokerTable()
   {
      screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      screenWidth = (int)(screenSize.getWidth());
      screenHeight = (int)(screenSize.getHeight()); 
      isWinner = false;
      round = 1;
      //draw = new JButton("Deal");  
      //draw.addMouseListener(new dealCard());
      //draw.setBounds(screenWidth/2 - 100, 0, 200, 100);
      //add(draw);  
      deck = new Deck();  
      drawn = CLEAR;
      addMouseMotionListener(this);
   
      numPlayers = 8;   // number of players edit here
      
      players = new ArrayList();
      centerCards = new ArrayList();
      
      for(int i = 0; i < numPlayers; i++)
      {
         players.add(new PokerPlayer(i, "Player " + (i)));
      }
      repaint();
   }
   
   //keyboard responses
   public void processUserInput(int k, boolean isServer)
   {
      /*
      Stages of each poker game:
      
      CHOICES: Each player has their equal turn to choose their play for the turn: 
      Fold, Bet, Raise, Call, or Check -- Maybe can be time for communicating on message board
      
      0)Everyone selects 'R' to ready up
      1)Cards are first dealt out - DEALT
      2)Blinds are taken from small and big blinds
      3)CHOICES
      4)FLOP
      5)CHOICES
      6)TURN
      7)CHOICES
      8)RIVER
      9)FINAL CHOICES
      10)If game has lasted this long with more than one player still in, the winner will be declared and those who were in will have their cards revealed
      
      
      */
      
      
      
      
      
      
      if(k==KeyEvent.VK_ESCAPE)//End the program	
         System.exit(0);
      /*else
         if(k==KeyEvent.VK_D)
            dealCards();*/
      
      //**********STEP ZERO**********
      //pre:there are 4-8 players logged onto the server
      //post: when player presses 'R' they ready up which allows for the game to begin
      //       EACH PLAYER NEEDS TO READY UP TO BEGIN...WILL IMPLEMENT WITH COUNTING PLAYERS WITH THE MULTITHREADING
      
      //SERVER
      if(isServer == true)
      {
         //Ready to START game
         if(k=='R')
         {
            isServerReady = true;
            System.out.println("R server works");
            repaint();
         }
         //Player can't do anything until each person is ready
         if(isServerReady == true && isClientReady == true)
         {
            //stuff that the server can do
         }
      }
      //CLIENTS
      else //isClient
      {  
      //Ready to START game
         if(k=='R')
         {
            isClientReady = true;
            System.out.println("Client R Button Works");
         }
         //Player can't do anything until each person is ready
         if(isServerReady == true && isClientReady == true)
         {
            //actions of the clients
         }
      }
      
      /*int numReady=0;
      numPlayers
      if(numPlayers = numReady)
         isClientReady = true;*/
      
      //************STEP ONE****************
      //pre:each player has readied up, the round begins
      
      //Player can't fire until each person is ready
      if(isServerReady == true /*&& isClientReady == true && drawn==CLEAR*/)//first hand before anyone has their cards
      {
         if(k==KeyEvent.VK_D)
         {
            dealCards(); //deals each player's first hand when everyone readies up for the first time     
         }
      }
         
      //}
      else //isClient
      {
         //Player can't fire until each person is ready
         /*if(isServerReady == true && isClientReady == true)
         {
            if(k==KeyEvent.VK_ADD)
            {
               if(clientAmmo > 0)
               {
                  Bullet temp= null;
                  clientAmmo--;
                  if(clientRotation == NORTH)
                  {
                     temp = new Bullet("UP", (int)((double)clientC/3 *SIZE-15), (int)((double)clientR/3 *SIZE-55), bulletImages, SPEED);
                     temp.setDirection(NORTH);
                  }
                  else if(clientRotation == EAST)
                  {
                     temp = new Bullet("RIGHT", (int)((double)clientC/3 *SIZE+30), (int)((double)clientR/3 *SIZE-15), bulletImages, SPEED);
                     temp.setDirection(EAST);
                  }
                  else if(clientRotation == SOUTH)
                  {
                     temp = new Bullet("DOWN", (int)((double)clientC/3 *SIZE-14), (int)((double)clientR/3 *SIZE+25), bulletImages, SPEED);
                     temp.setDirection(SOUTH);
                  }
                  else if(clientRotation == WEST)
                  {
                     temp = new Bullet("LEFT", (int)((double)clientC/3 *SIZE-50), (int)((double)clientR/3 *SIZE-13), bulletImages, SPEED);
                     temp.setDirection(WEST);
                  }
                  if(temp != null)        
                     bullets2.add(temp);
                  if(bullets2.size()>BULLETLIMIT2)	//remove earliest fired bullet if we have more than the bullet limit
                     bullets2.remove(0);
               
                  repaint();
                  return;
               }
            }
         }*/
      }
      
   
   }
   
   public void showBoard(Graphics g)	
   {
      g.drawImage(background.getImage(), 0, 0,  screenWidth, screenHeight,null);
      g.drawString("Hand: " + round, screenWidth/2,screenHeight/2-100);
      
      for(int i =0; i < centerCards.size(); i++)
      {
         g.drawImage(centerCards.get(i).getFront().getImage(), (screenWidth/2)-189+(77*i), screenHeight/2, 71, 94, null);
      }
      showDeck(g);
      showHands(g);
      showWinner(g);
      
      //Mouse Coordinates
      g.drawString("Mouse X: " + mouseX + " Mouse Y: " + mouseY, 100,100);
      
      
      //NETWORKING
      
      //information bellow ready button
      g.setColor(Color.WHITE);
      g.setFont(new Font("Copperplate Gothic", Font.BOLD, 25));
      g.drawString("Click R to ready up", screenWidth/2+40, 185);
         //ready rectangle
      Color myColor1 = new Color(170,170,170);
      g.setColor(myColor1);
      g.fillRect(screenWidth/2+20, 60, 270, 100);
            
      //ready Display
      if(isServerReady == true && isClientReady == false)
      {
         Color myColor3 = new Color(98, 228, 56);
         g.setColor(myColor3);
         g.setFont(new Font("Impact", Font.PLAIN, 100)); 
         g.drawString("  PLAY", screenWidth/2+40, 150);
      }
            //not ready Display
      else
      {
         Color myColor2 = new Color(241, 7, 39);
         g.setColor(myColor2);
         g.setFont(new Font("Impact", Font.PLAIN, 73)); 
         g.drawString("WAITING", screenWidth/2+30, 140);
      }
   
   
   }
   
   public void showDeck(Graphics g)
   {
      for(int i = 0; i < deck.getSize(); i++)
      {
         g.drawImage(deck.getCard(i).getBack().getImage() , (screenWidth/2)+(i*5)-100, (screenHeight/2)- 250, 71, 94, null);
      }
   }
   
   public void showHands(Graphics g)
   {
      setPlayersCoors(players);
      for(int i = 0; i < players.size(); i++)
      {
         ArrayList<Card> hand = players.get(i).getHand();
         String score = players.get(i).setScore(centerCards);
         //System.out.println(players.get(i).getName()+ " : "+ players.get(i).getBestHand());
         g.drawString(score, players.get(i).getX(),  players.get(i).getY() - 10);
         g.drawString("Player " + players.get(i).getPlayerNum(), players.get(i).getX(),  players.get(i).getY() - 20);
         for(int a = 0; a < hand.size(); a++)
         {
            g.drawImage(hand.get(a).getFront().getImage(), players.get(i).getX() + a*16, players.get(i).getY(), 71, 94, null);
         }
      }
   }
   
   public void setPlayersCoors(ArrayList<PokerPlayer> players)
   {
      if(players.get(0) != null&&players.get(0).getPlayerNum() == 0)
      {
         players.get(0).setX((int)(1/4.0 * screenWidth)); //480
         players.get(0).setY((int)(83/120.0 * screenHeight)); //830
      }
   
      if(players.get(1) != null&&players.get(1).getPlayerNum() == 1)
      {
         players.get(1).setX((int)(77/192.0 * screenWidth)); //770
         players.get(1).setY((int)(83/120.0 * screenHeight)); //830
      }
   
      if(players.get(2) != null&&players.get(2).getPlayerNum() == 2)
      {
         players.get(2).setX((int)(53/96.0 * screenWidth)); //1060
         players.get(2).setY((int)(83/120.0 * screenHeight)); //830
      }
   
      if(players.get(3) != null&&players.get(3).getPlayerNum() == 3)
      {
         players.get(3).setX((int)(45/64.0 * screenWidth)); //1350
         players.get(3).setY((int)(83/120.0 * screenHeight)); //830
      }
   
      if(players.get(4) != null&&players.get(4).getPlayerNum() == 4)
      {
         players.get(4).setX((int)(307/384.0 * screenWidth)); //1535
         players.get(4).setY((int)(9/16.0 * screenHeight)); //675
      }
   
      if(players.get(5) != null&&players.get(5).getPlayerNum() == 5)
      {
         players.get(5).setX((int)(307/384.0 * screenWidth)); //1535
         players.get(5).setY((int)(1/3.0 * screenHeight)); //400
      }
   
      if(players.get(6) != null&&players.get(6).getPlayerNum() == 6)
      {
         players.get(6).setX((int)(5/32.0 * screenWidth)); //300
         players.get(6).setY((int)(1/3.0 * screenHeight)); //400
      }
   
      if(players.get(7) != null&&players.get(7).getPlayerNum() == 7)
      {
         players.get(7).setX((int)(5/32.0 * screenWidth)); //300
         players.get(7).setY((int)(9/16.0 * screenHeight)); //675
      }
   }
   
   public void compareScore(ArrayList<PokerPlayer> players) 
   {
   // 2000000 = pair, 3000000 = two pair , 4000000 = three of a kind,
   // 5000000 = straight, 6000000 = flush, 7000000 = Full House                                                               
   // 8000000 = four of a kind, 9000000 = straight flush
   // 10000000 = Royal Flush ,   1000000 = nothing/highcard
      ArrayList<PokerPlayer> temp = new ArrayList();
      for(int i = 0; i < players.size(); i++)
      {
         players.get(i).setScore(centerCards);
         temp.add(players.get(i));
      }
   
      for(int i = 0; i < temp.size(); i++)
      {
         for(int j = temp.size()-1; j > i; j--)
         {
            if(temp.get(i).getScore() < temp.get(j).getScore())
            {
               PokerPlayer temp1 = temp.get(i);
               temp.set(i, temp.get(j));
               temp.set(j, temp1);
            }
         }
      }
      
      int val = temp.get(0).getScore();
      winner = new ArrayList();
      
      for(int i = 0; i < temp.size();i++)
      {
         if(temp.get(i).getScore() == val)
         {
            winner.add(temp.get(i));
         }
      }
   }
   
   //post:shows on board the winner
   public void showWinner(Graphics g)
   {
      if(isWinner)
      {
         String winLabel = "WINNER: ";
         for(int i = 0; i < winner.size(); i++)
         {
            winLabel = winLabel + winner.get(i).getName();
            if(i <= winner.size()-3)
            {
               winLabel = winLabel + ", ";
            }
            else if(i == winner.size()-2)
            {
               winLabel = winLabel + " and ";
            }
         }
         winLabel = winLabel + " with " + winner.get(0).getBestSet();
         g.drawString(winLabel, screenWidth/2-100, screenHeight/2-50);
      }
   }
     
     
   //post: Checks if there is only one player remaining then declares them the winner
   public boolean checkWinner()
   {
      boolean oneLeft = false;
      if(players.size() == 1)
      {
         players = winner;
         oneLeft = true;
      }
      return oneLeft;
   }

   public void dealCards()
   {
      if(drawn == CLEAR)
      {
         for(int a = 0; a < 2; a++)
         {
            for(int i = 0; i<players.size(); i++)
            {
               Card c = deck.drawCard();
               players.get(i).addCard(c);
                  //System.out.println(c);
            }
         }
         if(checkWinner())                                      //If all but one folded then break
         {
            drawn = CLEAR;
         }
         drawn = DEALT;
      }
      else if(drawn == DEALT)
      {
         for(int i = 0; i < 3; i++)
         {
            centerCards.add(deck.drawCard());
         }
         if(checkWinner())                                      //If all but one folded then break
            drawn = CLEAR;
         drawn = FLOP;
      }
      else if(drawn == FLOP)
      {
         centerCards.add(deck.drawCard());
         if(checkWinner())                                      //If all but one folded then break
            drawn = CLEAR;
         drawn = TURN;
      }
      else if(drawn == TURN)
      {
         centerCards.add(deck.drawCard());
         compareScore(players);
         isWinner = true;
         if(checkWinner())                                      //If all but one folded then break
            drawn = CLEAR;
         drawn = RIVER;
      }
      else if(drawn == RIVER)
      {
         drawn = CLEAR;
         isWinner = false;
         centerCards.clear();
         round++;
         deck = new Deck();
         for(int i = 0; i < players.size(); i++)
         {
            players.get(i).clearHand();
         }
            
      }
      repaint();
   }
   
   @Override
   public void mouseClicked(MouseEvent e) {
    // TODO Auto-generated method stub
   }
   
   @Override
      public void mouseEntered(MouseEvent arg0) {
      // TODO Auto-generated method stub
   }
   
   @Override
      public void mouseExited(MouseEvent arg0) {
      // TODO Auto-generated method stub
   }
   
   @Override
      public void mousePressed(MouseEvent arg0) {
      // TODO Auto-generated method stub
   }
   
   @Override
      public void mouseReleased(MouseEvent arg0) {
      // TODO Auto-generated method stub
   }

   @Override
   public void mouseDragged(MouseEvent e) {
    // TODO Auto-generated method stub
   }
   
   public void mouseMoved(MouseEvent e) {
      mouseX = e.getX();
      mouseY = e.getY();
      repaint();
   }
   
   public void paintComponent(Graphics g)
   {
      super.paintComponent(g); 
      g.setColor(Color.white);		//draw a blue border around the board
      g.fillRect(0, 0, screenWidth, screenHeight);
      showBoard(g);					//draw the contents of the board on the screen
   }   
}