import java.util.Random;

final class Card
{

//  RANK NAME. Printable names of card ranks.

  private static final String [] rankName =
  {
    "ace",       //   0
    "two",       //   1
    "three",     //   2
    "four",      //   3
    "five",      //   4
    "six",       //   5
    "seven",     //   6
    "eight",     //   7
    "nine",      //   8
    "ten",       //   9
    "jack",      //  10
    "queen",     //  11
    "king"       //  12
  };

//  SUIT NAME. Printable names of card suits.

  private static final String [] suitName =
  {
    "clubs",     //  0
    "diamonds",  //  1
    "hearts",    //  2
    "spades"     //  3
  };

  private int rank;  //  Rank of this CARD, between 0 and 12.
  private int suit;  //  Suit of this CARD, between 0 and 3.

//  CARD. Constructor. Make a new CARD, with a given RANK and SUIT.

  public Card(int rank, int suit)
  {
    if (0 <= rank && rank <= 12 && 0 <= suit && suit <= 3)
    {
      this.rank = rank;
      this.suit = suit;
    }
    else
    {
      throw new IllegalArgumentException("Illegal rank or suit.");
    }
  }

//  GET RANK. Return the RANK of this CARD.

  public int getRank()
  {
    return rank;
  }

//  GET SUIT. Return the SUIT of this CARD.

  public int getSuit()
  {
    return suit;
  }

//  TO STRING. Return a STRING that describes this CARD. For printing only!

  public String toString()
  {
    return rankName[rank] + " (" + rank + ") of " + suitName[suit];
  }
}
class Deck{
  int count = 0;
  Card popped;
  Card myDeck[];
  Random rand = new Random();
  public Deck(){
    myDeck = new Card[52];
    for(int suits = 0; suits < 4; suits ++){
      for(int ranks = 0; ranks < 13; ranks++){
        myDeck[count] = new Card(ranks, suits);
        count++;
      }
    }
  }
  public Card deal(){
    if (isEmpty()){
      throw new IllegalStateException("Can't deal an empty deck.");
    }
  else{
    popped = myDeck[count-1];
    myDeck[count-1] = null;
    count -= 1;
    return popped;
    }
  }
  public void shuffle(){
    if(count < 51){
      throw new IllegalStateException("Can't shuffle once cards have been dealt.");
    } 
    else{
      for(int i = 51; i > 0; i--){
        int j = rand.nextInt(i+1);
        if(j != i){
          Card store = myDeck[j];
          myDeck[j] = myDeck[i];
          myDeck[i] = store;
        }
      }
    }  
  }
  public boolean isEmpty(){
    return count == 0;
  }
}
class Pile{
  private class Layer{
    private Card card;
    private Layer next;
    private Layer(Card card, Layer next){
      this.card = card;
      this.next = next;
    }
  }
  private Layer top;
  public Pile(){
    top = null;
  }
  public void add(Card card){
    top = new Layer(card, top);
  }
  public Card draw(){
    Card popped;
    if (isEmpty()){
      throw new IllegalStateException("Can't draw from an empty pile.");
    }
    else{
      popped = top.card;
      top = top.next;
      return popped;
    }
  }
  public boolean isEmpty(){
    return top == null;
  }
}
class Tableau{
  Pile T[];
  Deck tableauDeck;
  public Tableau(){
    T = new Pile[13];
    tableauDeck = new Deck();
    tableauDeck.shuffle();
    for(int piles = 0; piles < T.length; piles++){
      T[piles] = new Pile();
      for(int deals = 0; deals < 4; deals++){
        T[piles].add(tableauDeck.deal());
      }
    }
	}
  public void play() {
    int curr_pile = 0;
    Card c1 = T[curr_pile].draw();
    Card c2;
    boolean msg = false;
    int empty_pile = 0;
    System.out.println("Got " + c1 + " from pile 0.");
    while (!T[curr_pile].isEmpty()){
      c2 = T[curr_pile].draw();
      System.out.println("Got " + c2 + " from pile " + curr_pile + ".");
      if (c1.getSuit() == c2.getSuit()){
        curr_pile = c1.getRank();
      }
      else{
        curr_pile = c2.getRank();
      }
      c1 = c2;
    }
    while(empty_pile < T.length){
      if (T[empty_pile].isEmpty()){
        msg = true;
      }
      else{
        msg = false;
        break;
      }
      empty_pile++;
    }
    if(msg){
      System.out.println("We Won!");
    }
    else{
      System.out.println("Pile " + curr_pile + " is empty. We lost!");
    }
  }
}
public class Game{
  public static void main(String [] args){
    Tableau table = new Tableau(); 
    table.play();
  }
}
