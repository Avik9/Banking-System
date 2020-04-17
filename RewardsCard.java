/**
 * @author Avik Kadakia
 * 
 * 
 */ 

public class RewardsCard extends CreditCard
{
    protected int rewardPoints;
    
    public RewardsCard(String holder, long number, int expiration, double limit)
    {
        super(holder, number, expiration, limit);
        rewardPoints = 0;
    }
    
    public RewardsCard(String holder, long number, int expiration)
    {
        super(holder, number, expiration);
        creditLimit = 500;
        rewardPoints = 0;
    }
    
    public int rewardPoints()
    {
        return rewardPoints; 
    }
    
    public boolean redeemPoints(int points)
    {
        double dPoints = points/100.00;
        
        if(points <= rewardPoints)
        {
            iBalance -= dPoints;
            rewardPoints -= points;
            new Transaction("redemption", "CSEBank", dPoints);
            
            return true;
        }
        
        else
        {
            iBalance = 0;
        }
        
        return false;
    }
    
    public String toString()
    {
        return super.toString() + '\t' + "Available reward points: " + rewardPoints;
    }
    
    public boolean addTransaction(Transaction t)
    {
        boolean a = true;
        
        if(t.type().equals("debit") && t.amount() <= this.availableCredit())
        {
            iBalance += t.amount();
            trans.add(t);
            rewardPoints += ((int)(t.amount() * 100));
            
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
        
        return a;
    }
    public void printStatement()  
    {
            System.out.println("\n\n" + "Card holder's name: " + this.cardHolder() + '\t' + 
                "Card Number: " + this.cardNum + '\t' + "Expiration date: " + 
                this.expiration() + '\t' + "Credit Limit: " + this.limit() + '\t' + "Current Balance: " + iBalance
                + '\t' + "Reward Points: " + rewardPoints + '\n');
            
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
