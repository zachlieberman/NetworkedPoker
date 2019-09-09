import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.event.*;
import javax.swing.ImageIcon;

public class deckTest
{
   public static void main(String[] arg)
   {
      JFrame frame = new JFrame("DeckTester");
      JPanel screen = new JPanel();
      Deck cards = new Deck();
      screen.setLayout(new FlowLayout());
   
      for(int i = 0; i < cards.getSize(); i++)
      {
         screen.add(new JLabel(cards.getCard(i).getBack()));
         screen.add(new JLabel(cards.getCard(i).getFront()));
      }
      frame.setContentPane(screen);
      
      frame.setSize(990,435);
      frame.setLocation(200, 200);
      frame.setResizable(true);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
   }
}