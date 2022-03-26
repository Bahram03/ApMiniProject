package game;

import game.properties.BuyableProperties;

public class Player implements Owner {
    private final String name;
    private int cash;
    private int position;
    private Integer lastDiceNumber;
    BuyableProperties[] properties;
    private int propertyWorth;
    private int netWorth;
    private int noTax;
    private int noJail;
    private int ownedCinemas;
    private int investedMoney = 0;
    private int rank;
    private boolean inJail;

    public Player(String name, int cash) {
        this.name = name;
        this.cash = cash;
    }

    public int getCash() {
        return cash;
    }

    public void setCash(int cash) {
        this.cash = cash;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getNoTax() {
        return noTax;
    }

    public void setNoTax(int noTax) {
        this.noTax = noTax;
    }

    public int getNoJail() {
        return noJail;
    }

    public void setNoJail(int noJail) {
        this.noJail = noJail;
    }

    public String getName() {
        return name;
    }

    public int getPropertyWorth() {
        return propertyWorth;
    }

    public int getNetWorth() {
        return netWorth;
    }

    public int getOwnedCinemas() {
        return ownedCinemas;
    }

    public int getInvestedMoney() {
        return investedMoney;
    }

    public boolean isInJail() {
        return inJail;
    }
}
