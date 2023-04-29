class Poly{
    private class Term{
        private int coef;
        private int expo;
        private Term next;
        private Term(int coef, int expo, Term next){
            this.coef = coef;
            this.expo = expo;
            this.next = next;
        }
    }
    private Term first;
    private Term last;
    public Poly(){
        Term head = new Term(10, Integer.MAX_VALUE, null);
        this.first = head;
        this.last = head;
    }
    public boolean isZero(){
        return (first.next==null);
    }
    public Poly minus(){
        Poly result = new Poly();
        Term temp = this.first.next;
        while(temp != null){
            result.last.next = new Term((-temp.coef), temp.expo, null);
            result.last = result.last.next;
            temp = temp.next;
        }
        return result;
    }
    public Poly plus(Poly that){
        Poly result = new Poly();
        Term left = this.first.next;
        Term right = that.first.next;
        while((left != null) && (right != null)){
            if (left.expo > right.expo){
                result.last.next = new Term(left.coef, left.expo, null);
                left = left.next;
                result.last = result.last.next;
            }
            else if (left.expo < right.expo){
                result.last.next = new Term(right.coef, right.expo, null);
                right = right.next;
                result.last = result.last.next;
            }
            else{
                int sum = left.coef + right.coef;
                if (sum != 0){
                    result.last.next = new Term(sum, left.expo, null);
                    result.last = result.last.next;
                }
                left = left.next;
                right = right.next;
            }
        }
        if ((left != null) && (right == null)){
            result.last.next = left;
        }
        else if ((right != null) && (left == null)){
            result.last.next = right;
        }
        return result;
    }

    public Poly plus(int coef, int expo){
        if ((coef == 0) || (expo < 0) || (expo >= this.last.expo)){
            throw new IllegalArgumentException("Coef can't be 0 and expo must be positive.");
        }
        else{
            this.last.next = new Term(coef, expo, null);
            this.last = this.last.next;
            return this;
        }
    }

    public String toString(){
        StringBuilder str = new StringBuilder();
        Term curr = this.first.next;
        if (isZero()){
                str.append("0");
                return str.toString();
            }
        while (curr != null){
            if ((curr.coef > 0) || (curr == this.first.next)){
                str.append(curr.coef + "X" + (curr.expo));
                nextTerm(curr, str);
            }
            else{
                str.append((-curr.coef) + "X" + (curr.expo));
                nextTerm(curr, str);
                }
            curr = curr.next;
        }
        return str.toString();
    }

    public void nextTerm(Term termite, StringBuilder stringed){
        if (termite.next != null){
            if (termite.next.coef < 0){
                stringed.append(" - ");
            }
            else{
                stringed.append(" + ");
            }
        }
    }
}


class Driver 
{ 
  public static void main(String[] args) 
  { 
    Poly p = new Poly().plus(3,5).plus(2,4).plus(2,3).plus(-1,2).plus(5,0); 
    Poly q = new Poly().plus(7,4).plus(1,2).plus(-4,1).plus(-3,0); 
    Poly z = new Poly(); 
 
    System.out.println(p);                 // 3x⁵ + 2x⁴ + 2x³ - 1x² + 5x⁰ 
    System.out.println(q);                 // 7x⁴ + 1x² - 4x¹ - 3x⁰ 
    System.out.println(z);                 // 0 
 
    System.out.println(p.minus());         // -3x⁵ - 2x⁴ - 2x³ + 1x² - 5x⁰ 
    System.out.println(q.minus());         // -7x⁴ - 1x² + 4x¹ + 3x⁰ 
    System.out.println(z.minus());         // 0 
 
    System.out.println(p.plus(q));         // 3x⁵ + 9x⁴ + 2x³ - 4x¹ + 2x⁰ 
    System.out.println(p.plus(z));         // 3x⁵ + 2x⁴ + 2x³ - 1x² + 5x⁰ 
    System.out.println(p.plus(q.minus())); // 3x⁵ - 5x⁴ + 2x³ - 2x² + 4x¹ + 8x⁰
    System.out.println(p.plus(q.minus()).plus(q).plus(p.minus()).plus(p.minus()));

    try{
        System.out.println(p.plus(5, 100));
        System.out.println(p.plus(5, -100));
        System.out.println(p.plus(0, -100));
    }
    catch(IllegalArgumentException ignore){
        System.out.println("The one piece is real.");
    }

    System.out.println(z.isZero());

  } 
}