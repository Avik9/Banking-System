/**
 * @author Avik Kadakia
 * 
 */

public class CreditCard extends BankCard
{
    private int expDate;
    protected double creditLimit;
    
    public CreditCard(String cardHolder, long cardNumber, int expiration, double limit)
    {
        super(cardHolder, cardNumber);
        expDate = expiration;
        creditLimit = limit;
    }
    
    public CreditCard(String cardHolder, long cardNumber, int expiration)
    {
        super(cardHolder, cardNumber);
        expDate = expiration;
        creditLimit = 500;
    }
    
    public double limit()
    {
        return creditLimit;
    }
    
    public double availableCredit()
    {
        return (creditLimit - iBalance);
    }
    
    public int expiration()
    {
        return expDate;
    }
    
    public String toString()
    {
        return super.toString() + '\t' + "Expiration Date: " + expDate;
    }
    
    public boolean addTransaction(Transaction t)
    {
        boolean a = true;
        if(t.type().equals("debit") && t.amount() <= this.availableCredit())
        {
            iBalance += t.amount();
            trans.add(t);
        }
        
        if(t.type().equals("debit") && t.amount() > this.availableCredit())
        {
            a = false;
        }
        
        if(t.type().equals("credit"))
        {
            iBalance += t.amount();
            trans.add(t);
        }
        
        else
        {
            a = false;
        }
        
        return a;
    }
    
    public void printStatement()
    {
        System.out.println("\n\n" + "Card holder's name: " + this.cardHolder() + '\t' + 
                        "Card Number: " + cardNum + '\t' + "Expiration date: " + expDate
                        + '\t' + "Current Balance: " + iBalance + '\n');
        
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
    
    public boolean CashAdvance(double cash)
    {
        double fee = 0.05 * cash;
        
        if((fee + cash) <= availableCredit())
        {
            iBalance -= (fee + cash);
            
            Transaction advance = new Transaction("advance", "CSEBank", cash);
            
            addTransaction(advance);
            
            Transaction f = new Transaction("fee", "Cash advance fee", fee);
            
            addTransaction(f);
            
            return true;
        }
        
        else
        {
            return false;
        }
    }
}
