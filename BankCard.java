/**
 * @author Avik Kadakia
 * 
 */

import java.util.*;

public abstract class BankCard 
{
    private String name = "";
    protected long cardNum;
    protected double iBalance;
    protected ArrayList <Transaction> trans = new ArrayList();
    
    public BankCard(String cardholderName, long cardNumber)
    {
        name = cardholderName;
        cardNum = cardNumber;
        iBalance = 0;
    }
    
    public double balance()
    {
        return iBalance;
    }
    
    public long number()
    {
        return cardNum;
    }
    
    public String cardHolder()
    {
        return name;
    }
    
    public String toString()
    {
        String ans = "";
        
        ans = "Card# " + cardNum + '\t' + "Current Balance: $" + iBalance;
        
        return ans;
    }
    
    public abstract boolean addTransaction(Transaction t);

    public abstract void printStatement();
}
