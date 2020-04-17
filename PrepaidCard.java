/**
 * @author Avik Kadakia
 * 
 * 
 */ 

public class PrepaidCard extends BankCard
{

    public PrepaidCard(String cardHolder, long cardNumber, double balance)
    {
       super(cardHolder, cardNumber);
       iBalance = balance;
    }
    
    public PrepaidCard(String cardHolder, long cardNumber)
    {
        super(cardHolder, cardNumber);
        iBalance = 0;
    }
    
    public boolean addTransaction(Transaction t)
    {
        boolean a = true;
        if("debit".equals(t.type()) && t.amount() <= this.iBalance)
        {
            iBalance -= t.amount();
            trans.add(t);
        }
        
        else if("debit".equals(t.type()) && t.amount() > this.iBalance)
        {
            a = false;
        }
        
        else if("credit".equals(t.type()))
        {
            iBalance -= t.amount();
            trans.add(t);
        }
        
        else
        {
            a = false;
        }
        
        return a;
    }
    
    public boolean addFunds(double amount)
    {
        if(amount > 0)
        {
            iBalance += amount;
            new Transaction("top-up", "User Payment", (-1 * amount));
        }
        
        else
        {
            return false;
        }
        
        return true;
    }
    
    public String toString()
    {
        return super.toString();
    }
    
    public void printStatement()
    {
        System.out.println("\n\n" + "Card holder's name: " + cardHolder() + '\t' + 
                "Card Number: " + cardNum + '\t' + "Current Balance: " 
                + iBalance + '\n');
        
        for(int i = 0; i < trans.size() - 1; i++)
        {
            if((trans.get(i).type().equals("debit")) && (trans.get(i + 1).type().equals("debit")))
            {
                if(trans.get(i).merchant().equals(trans.get(i + 1).merchant()))
                {
                    if((trans.get(i).amount() < 1.5) && (trans.get(i + 1).amount() < 1.5))
                    {
                        iBalance += ((-1.0) * (trans.get(i).amount() + trans.get(i + 1).amount()));
                        
                        System.out.println(trans.get(i));
                        System.out.println(trans.get(i + 1));
                        Transaction fr1 = new Transaction("credit", "Fraud", ((-1.0) * (trans.get(i).amount())));
                        Transaction fr2 = new Transaction("credit", "Fraud", ((-1.0) * (trans.get(i + 1).amount())));
                        
                        trans.add((i + 1), fr1);
                        trans.add((i + 3), fr2);
                        
                        System.out.println(trans.get(i + 1));
                        System.out.println(trans.get(i + 3));
                        
                        System.out.print("New balance is: " + iBalance);
                        
                        System.out.println("** Account frozen due to suspect fraud **");
                        break;
                    }
                }
            }
            else
            {
                System.out.println(trans.get(i));
            }
        }
    }
}
