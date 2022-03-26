package game.properties;

import game.*;
import game.exceptions.IllegalPurchase;
import game.exceptions.IllegalSell;
import game.exceptions.NotEnoughCashToBuy;

public class EmptyField extends BuyableProperties{
    public final static int[] atFields = {2,7,9,12,14,18,19,23};
    private int numberOfBuildings;
    private boolean isThereHotel = false;
    private final int MAXIMUM_BUILDINGS = 4;
    private final int BUILDING_COST = 150;
    private final int HOTEL_LICENSE_COST = 100;
    {
        purchasePrice = 100;
        baseRentPrice = 50;
    }

    public EmptyField(int atField){
        switch(atField){
            case 2:
            case 12:
                this.color = Colors.GREEN;
                break;
            case 7:
            case 19:
                this.color = Colors.YELLOW;
                break;
            case 9:
            case 18:
                this.color = Colors.RED;
                break;
            case 14:
            case 23:
                this.color = Colors.BLUE;
        }
        this.atField = atField;
    }

    @Override
    public void buy(Player player){
        if(this.owner != BankManager.getInstance()){
            if(this.owner == player){
                throw new IllegalPurchase("You have already purchased this property!");
            }
            else{
                throw new IllegalPurchase("This property belongs to " + (Player)owner.getName()+ " you can't purchase it!");
            }
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
    }

    @Override
    public void sell(Player player){
        if(this.owner == BankManager.getInstance()){
            throw new IllegalSell("This property belongs to the Bank you can't sell it!");
        }
        else if(this.owner != player){
            throw new IllegalSell("This property belongs to " + (Player)owner.getName() + "you can't sell it!");
        }
        int emptyFieldWorth = purchasePrice + (numberOfBuildings * BUILDING_COST);
        if(isThereHotel){
            emptyFieldWorth += HOTEL_LICENSE_COST;
        }
        player.setCash(player.getCash() + emptyFieldWorth/2);//give the money
        this.owner = BankManager.getInstance();//take the Ownership
        player.getOwnedProperties().remove(this);//remove the sold properties from the player's list of owned properties
        player.setPropertyWorth(player.getPropertyWorth() - emptyFieldWorth/2);//update property worth
        int newNetWorth = player.getCash() + player.getPropertyWorth();
        player.setNetWorth(newNetWorth);//update net worth
        numberOfBuildings = 0;
        isThereHotel = false;
    }

    @Override
    public void chargeRent(Player player){

    }

    public void addBuilding(Player player){

    }

    private void buildHotel(Player player){

    }

}
