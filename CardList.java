/**
 * @author Avik Kadakia
 * 
 */

import java.util.*;
import java.io.PrintWriter;

public class CardList 
{
    private ArrayList <BankCard> bCard = new ArrayList();
    
    public CardList()
    {
        new ArrayList();
    }
    
    public void add(BankCard b)
    {
        bCard.add(b);
    }
    
    public void add(int index, BankCard b)
    {
        bCard.add(b);
    }
    
    public int size()
    {
        return bCard.size();
    }
    
    public BankCard get(int index)
    {
            return bCard.get(index);
    }
    
    public int indexOf(long cardNumber)
    {
        int num = 0;
        
        for(int i = 0; i < bCard.size(); i++)
        {
           if (bCard.get(i).number() == cardNumber)
           {
               num = i;
               return i;
           }
        }        
        return num;
    }
}
