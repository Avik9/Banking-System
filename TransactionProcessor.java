/**
 * @author Avik Kadakia
 * 
 * Driver class for the entire project
 */ 

import java.util.*;
import java.io.*;

public class TransactionProcessor
{
    public static void main(String [] args) throws Exception
    {
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Please enter the name of the card data file:");
        
        String clName = sc.nextLine();
        
        CardList cl = loadCardData(clName);
        
        System.out.print("Please enter the name of the transaction data file: ");
        
        String tName = sc.nextLine();
        
        processTransactions(tName, cl);
        
        for(int i = 0; i < cl.size(); i++)
        {
            cl.get(i).printStatement();
        }
        
    }
    
    private static String getCardType (long number)
    {
        String result;

        int firstTwo = Integer.parseInt(("" + number).substring(0,2));

        switch(firstTwo)
        {
                case 84:
                case 85: result = "CreditCard"; break;
                case 86:
                case 87: result = "RewardsCard"; break;
                case 88:
                case 89: result = "PrepaidCard"; break;
                default: result = null; // invalid card number
        }

        return result;
    }

    public static BankCard convertToCard(String data)
    {
        Scanner s = new Scanner(data);

        long num = s.nextLong();
        String name = s.next();

        String cardType = getCardType(num);

        if(cardType.equals("CreditCard"))
        {
            int exp = s.nextInt();

            if (s.hasNextDouble())
            {
                double limit = s.nextDouble();

                return new CreditCard(name.replaceAll("_", " "), num, exp, limit);
            }

            else
            {
                return new CreditCard(name.replaceAll("_", " "), num, exp);
            }
        }

        if(cardType.equals("RewardsCard"))
        {
            int exp = s.nextInt();

            if (s.hasNextDouble())
            {
                double limit = s.nextDouble();

                return new RewardsCard(name.replaceAll("_", " "), num, exp, limit);
            }

            else
            {
                return new RewardsCard(name.replaceAll("_", " "), num, exp);
            }
        }

        if(cardType.equals("PrepaidCard"))
        {
            if (s.hasNextDouble())
            {
                double bal = s.nextDouble();

                return new PrepaidCard(name.replaceAll("_", " "), num, bal);
            }

            else
            {
                return new PrepaidCard(name.replaceAll("_", " "), num);
            }
        }
        s.close();

        return null;
    }

    public static CardList loadCardData(String fName) throws IOException
    {
        File file = new File(fName);
        Scanner c = new Scanner(file);

        CardList data = new CardList();

        String card = "";

        while(c.hasNextLine())
        {
           card = c.nextLine();
           data.add(convertToCard(card));
        }

        return data; 
    }

    public static void processTransactions(String filename, CardList c) throws Exception
    {
        File p = new File(filename);

        Scanner f = new Scanner(p);

        String line = "";

        while(f.hasNextLine())
        {
            line = f.nextLine();
            
            String [] content = line.split(" ");

            long cardNum = Long.parseLong(content[0]);

            int index = c.indexOf(cardNum);

            if(content[1].equals("redeem"))
            {
                int points = Integer.parseInt(content[2]);

                RewardsCard rc = (RewardsCard)c.get(index);

                rc.redeemPoints(points);
            }

            else if(content[1].equals("top-up"))
            {
                double money = Double.parseDouble(content[2]);

                PrepaidCard pc = (PrepaidCard)c.get(index);

                pc.addFunds(money);
            }

            else
            {
                double money = Double.parseDouble(content[2]);

                Transaction t = new Transaction(content[1], content[3].replaceAll("_", " "), money);

                BankCard bc = (BankCard)c.get(index);

                bc.addTransaction(t);
            }
        }
    }
}
