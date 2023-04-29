import java.io.FileReader;   //  Read Unicode chars from a file.
import java.io.IOException;  //  In case there's IO trouble.

class AnagramTree{
    private class TreeNode{
        private byte[] summary;
        private WordNode words;
        private TreeNode left;
        private TreeNode right;
        private TreeNode(byte[] summary, WordNode words, TreeNode left, TreeNode right){
            this.summary = summary;
            this.words = words;
            this.left = left;
            this.right = right;
        }
    }
    private class WordNode{
        private String word;
        private WordNode next;
        private WordNode(String word, WordNode next){
            this.word = word;
            this.next = next;
        }
    }
    
    private TreeNode head;
    public AnagramTree(){
        head = new TreeNode(new byte[26], null, null, null);
    }
    
    public void add(String word){
        TreeNode subtree = head;
        byte[] wordsum = stringToSummary(word);
        while(true){
            int w = compareSummaries(subtree.summary, wordsum);
            if(w>0){
                if(subtree.left != null){
                    subtree = subtree.left;
                }
                else{
                    subtree.left = new TreeNode(wordsum, (new WordNode(word, null)), null, null);
                    return;
                }
            }
            else if (w<0){
                if (subtree.right != null){
                    subtree = subtree.right;
                }
                else{
                    subtree.right = new TreeNode(wordsum, (new WordNode(word, null)), null, null);
                    return;
                }
            }
            else{
                if (newAnagram(subtree.words, word)){
                    subtree.words = new WordNode(word, subtree.words);
                }
                return;
            }
        }
    }

    public void anagrams(){
        traverser(head.right);
    }

    private void traverser(TreeNode troot){ //troot will be head.right
        if(troot != null){
            WordNode temp = troot.words;
            if(temp.next != null){ //so that nodes with  no anagrams don't get printed
                while(temp != null){
                    System.out.print(temp.word + " ");
                    temp = temp.next;
                }
                System.out.println();
            }
        traverser(troot.left);
        traverser(troot.right);
        }
    }

    private boolean newAnagram(WordNode exists, String word){
        WordNode temp = exists;
        while(temp != null){
            if(word.equals(temp.word)){
                return false;
            }
            temp = temp.next;
        }
        return true;
    }

    private int compareSummaries(byte[] left, byte[] right){
        for(int index = 0; index < 26; index++){
            if(left[index] != right[index]){
                return left[index] - right[index];
            }
        }
        return 0;
    }

    private byte[] stringToSummary(String word){
        byte[] temp = new byte[26];
        for(int index = 0; index < word.length(); index++){
            temp[((int) word.charAt(index)) - 'a'] += 1; //temp is a summary, we get the index of a letter by casting a character to it's ascii, then subtracting 97
        }
        return temp;
    }
}

class Anagrammer{
    public static void main(String[] args){
        Words wordy = new Words("/Users/berni/Desktop/CSCI1933_S23/projects/warAndPeace.txt");
        AnagramTree at = new AnagramTree();
        while(wordy.hasNext()){
            String curr = wordy.next();
            at.add(curr);
        }
        at.anagrams();
    }
}


class Words
{
  private int           ch;      //  Last CHAR from READER, as an INT.
  private FileReader    reader;  //  Read CHARs from here.
  private StringBuilder word;    //  Last word read from READER.

//  Constructor. Initialize an instance of WORDS, so it reads words from a file
//  whose pathname is PATH. Throw an exception if we can't open PATH.

  public Words(String path)
  {
    try
    {
      reader = new FileReader(path);
      ch = reader.read();
    }
    catch (IOException ignore)
    {
      throw new IllegalArgumentException("Cannot open '" + path + "'.");
    }
  }

//  HAS NEXT. Try to read a WORD from READER, converting it to lower case as we
//  go. Test if we were successful.

  public boolean hasNext()
  {
    word = new StringBuilder();
    while (ch > 0 && ! isAlphabetic((char) ch))
    {
      read();
    }
    while (ch > 0 && isAlphabetic((char) ch))
    {
      word.append(toLower((char) ch));
      read();
    }
    return word.length() > 0;
  }

//  IS ALPHABETIC. Test if CH is an ASCII letter.

  private boolean isAlphabetic(char ch)
  {
    return 'a' <= ch && ch <= 'z' || 'A' <= ch && ch <= 'Z';
  }

//  NEXT. If HAS NEXT is true, then return a WORD read from READER as a STRING.
//  Otherwise, return an undefined STRING.

  public String next()
  {
    return word.toString();
  }

//  READ. Read the next CHAR from READER. Set CH to the CHAR, represented as an
//  INT. If there are no more CHARs to be read from READER, then set CH to -1.

  private void read()
  {
    try
    {
      ch = reader.read();
    }
    catch (IOException ignore)
    {
      ch = -1;
    }
  }

//  TO LOWER. Return the lower case ASCII letter which corresponds to the ASCII
//  letter CH.

  private char toLower(char ch)
  {
    if ('a' <= ch && ch <= 'z')
    {
      return ch;
    }
    else
    {
      return (char) (ch - 'A' + 'a');
    }
  }

//  MAIN. For testing. Open a text file whose pathname is the 0th argument from
//  the command line. Read words from the file, and print them one per line.

  public static void main(String [] args)
  {
    Words words = new Words("/Users/berni/Desktop/CSCI1933_S23/projects/warAndPeace.txt");
    while (words.hasNext())
    {
      System.out.println("'" + words.next() + "'");
    }
  }
}
