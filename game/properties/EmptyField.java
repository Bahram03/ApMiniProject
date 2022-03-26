package game.properties;

import game.*;
import game.exceptions.*;
import java.util.ArrayList;

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
    public void sell(Player player){
        super.sell(player);
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
        int rent = 0;
        if(isThereHotel){
            rent = 600;
        }
        else {
            switch (numberOfBuildings){
                case 0:
                    rent = baseRentPrice;
                    break;
                case 1:
                    rent = baseRentPrice + 100;
                    break;
                case 2:
                    rent = baseRentPrice + 200;
                    break;
                case 3:
                    rent = baseRentPrice + 300;
                    break;
                case 4:
                    rent = baseRentPrice + 400;
            }
        }
        if(rent > player.getCash()){
            throw new NotEnoughCashToRent("You don't have enough money to pay the rent of this field! Try selling you're properties");
        }
        player.setCash(player.getCash() - rent);
    }

    public void addBuilding(Player player){
        if(player.getCash() < BUILDING_COST){//does player have enough cash?
            throw new NotEnoughCashToBuild("You do not have enough cash to construct building/hotel! Try selling you're properties");
        }
        else if(!buildPermission(player)){//did the player distribute the building correctly?
            throw new IllegalConstruction("You have not distributed you're buildings correctly!");
        }
        else if(player.getBuiltBuildings() == 5){//has the player reached construction limit?
            throw new IllegalConstruction("You have reached you're building construction limit!");
        }
        else if(isThereHotel){//already built a hotel?
            throw new IllegalConstruction("You have already constructed a hotel in this field! there's nothing else you can do.");
        }
        else if(this.owner != player){//who's the owner?
            throw new IllegalConstruction("this field belongs to " + (Player)owner.getName() + "! you can't construct a building/hotel here.");
        }
        else if(numberOfBuildings == MAXIMUM_BUILDINGS){//does the player want to build a hotel?
            buildHotel(player);
            return;
        }
        player.setCash(player.getCash() - BUILDING_COST);//take the money
        player.setNetWorth(player.getNetWorth() + BUILDING_COST/2);//update net worth
        player.setBuiltBuildings(player.getBuiltBuildings() + 1);//update number of built buildings by the player
        numberOfBuildings++;
    }

    private void buildHotel(Player player){
        if(player.getCash() < HOTEL_LICENSE_COST){
            throw new IllegalConstruction("You do not have enough cash to construct a hotel! Try selling you're properties.");
        }
        isThereHotel = true;
        player.setCash(player.getCash() - HOTEL_LICENSE_COST);//take the money
        player.setNetWorth(player.getNetWorth() + HOTEL_LICENSE_COST/2);//update net worth
    }

    private boolean buildPermission(Player player) {//only checks distribution
        ArrayList ownedProperties = getOwnedProperties();
        for (int i = 0; i < ownedProperties.size(); i++) {
            if (ownedProperties.get(i) instanceof EmptyField) {
                if(((EmptyField) ownedProperties.get(i)).numberOfBuildings < this.numberOfBuildings)//***possible bug for not handling all scenarios***
                    return false;
            }
        }
        return true;
    }
}
