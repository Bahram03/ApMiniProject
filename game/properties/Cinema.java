package game.properties;

import game.*;
import game.exceptions.*;

public class Cinema extends BuyableProperties{
    public final static int[] atFields = {4,8,15,22};
    {
        purchasePrice = 200;
        baseRentPrice = 25;
    }

    public Cinema(int atField){
        switch (atField){
            case 4:
                color = Colors.RED;
                break;
            case 8:
                color = Colors.BLUE;
                break;
            case 15:
                color = Colors.GREEN;
                break;
            case 22:
                color = Colors.YELLOW;
        }
        this.atField = atField;
    }

    @Override
    public void buy(Player player){
        if(this.owner != BankManager.getInstance()){
            throw new IllegalPurchase("This property belongs to " + (Player)owner.getName()+ " you can't purchase it!");
        }
        else if(purchasePrice > player.getCash()){
            throw new NotEnoughCashToBuy("You don't have enough money to buy this property! You can sell some of your properties in order to do so.");
        }
        player.setCash(player.getCash() - purchasePrice);//get the money
        this.owner = player;//give the Ownership
        player.getOwnedProperties().add(this);//add the property to the player's list of owned properties
        player.setPropertyWorth(player.getPropertyWorth() + purchasePrice/2);//update property worth
        int newNetWorth = player.getCash() + player.getPropertyWorth();
        player.setNetWorth(newNetWorth);//update netWorth
        player.setOwnedCinemas(player.getOwnedCinemas() + 1);//update number of owned cinemas
    }

    @Override
    public void sell(Player player){
        if(this.owner == BankManager.getInstance()){
            throw new IllegalSell("This property belongs to the Bank you can't sell it!");
        }
        else if(this.owner != player){
            throw new IllegalSell("This property belongs to " + (Player)owner.getName() + "you can't sell it!");
        }
        player.setCash(player.getCash() + purchasePrice/2);//give the money
        this.owner = BankManager.getInstance();//take the Ownership
        player.getOwnedProperties().remove(this);//remove the sold properties from the player's list of owned properties
        player.setPropertyWorth(player.getPropertyWorth() - purchasePrice/2);//update property worth
        int newNetWorth = player.getCash() + player.getPropertyWorth();
        player.setNetWorth(newNetWorth);//update net worth
        player.setOwnedCinemas(player.getOwnedCinemas() - 1);//update number of owned cinemas
    }

    @Override
    public void chargeRent(Player player){
        int rent = 0;
        if(this.owner != BankManager.getInstance()){
            switch ((Player)this.owner.getOwnedCinemas()){
                case 1:
                      rent = 25 * 1;
                    break;
                case 2:
                      rent = 25 * 2;
                    break;
                case 3:
                      rent = 25 * 4;
                    break;
                case 4:
                      rent = 25 * 8;
            }
            if(rent > player.getCash()){
                throw new NotEnoughCashToRent("You don't have enough money to watch a movie! Try selling you're properties");
            }
            player.setCash(player.getCash - rent);
        }

    }
}
