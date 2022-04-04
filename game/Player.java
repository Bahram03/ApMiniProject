package game;

import game.properties.*;
import org.w3c.dom.css.CSSImportRule;

import java.util.ArrayList;

public class Player implements Owner {
    private final String name;
    private double cash;
    private int position;
    private Integer lastDiceNumber;
    private ArrayList<BuyableProperties> ownedProperties;
    private double propertyWorth;
    private double netWorth;
    private int noTax;
    private int noJail;
    private int ownedCinemas;
    private double investedMoney = 0;
    private int rank;
    private boolean inJail;
    private final int BUILDINGS_LIMIT = 5;
    private int builtBuildings;
    // these counters are going to use in emptyField Class, if they equal 3 it means it's Monopoly!
    // (the specific boolean will be True)
    // if Monopoly happens the rent price will be double!
    public int blueProperties;
    public int redProperties;
    public int greenProperties;
    public int yellowProperties;
    public boolean blueMonopoly;
    public boolean redMonopoly;
    public boolean greenMonopoly;
    public boolean yellowMonopoly;
    public Player(String name, double cash) {
        this.name = name;
        this.cash = cash;
    }

    public void setPropertyWorth(double propertyWorth) {
        this.propertyWorth = propertyWorth;
    }

    public void setNetWorth(double netWorth) {
        this.netWorth = netWorth;
    }

    public double getCash() {
        return cash;
    }

    public void setCash(double cash) {
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

    public double getPropertyWorth() {
        return propertyWorth;
    }

    public double getNetWorth() {
        return netWorth;
    }

    public int getOwnedCinemas() {
        return ownedCinemas;
    }

    public double getInvestedMoney() {
        return investedMoney;
    }

    public boolean isInJail() {
        return inJail;
    }

    public ArrayList<BuyableProperties> getOwnedProperties() {
        return ownedProperties;
    }

    public void setOwnedProperties(ArrayList<BuyableProperties> ownedProperties) {
        this.ownedProperties = ownedProperties;
    }
    
    public int getBuiltBuildings() {
        return builtBuildings;
    }

    public void setBuiltBuildings(int builtBuildings) {
        this.builtBuildings = builtBuildings;
    }

    public void setPropertyWorth(int propertyWorth) {
        this.propertyWorth = propertyWorth;
    }

    public void setNetWorth(int netWorth) {
        this.netWorth = netWorth;
    }

    public void setOwnedCinemas(int ownedCinemas) {
        this.ownedCinemas = ownedCinemas;
    }

    public void setInJail(boolean inJail) {
        this.inJail = inJail;
    }

    public void setLastDiceNumber(Integer lastDiceNumber) {
        this.lastDiceNumber = lastDiceNumber;
    }

    public void setInvestedMoney(double investedMoney) {
        this.investedMoney = investedMoney;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public Integer getLastDiceNumber() {
        return lastDiceNumber;
    }

    public int getRank() {
        return rank;
    }

    public int getBUILDINGS_LIMIT() {
        return BUILDINGS_LIMIT;
    }

    /**
     * move method receive a die number and set the new position
     * @param diceNum diceNum entered dice
     */
    public void move(int diceNum){
        lastDiceNumber = diceNum; //save the dice number
        position += diceNum; // move the player
    }

    /**
     *   state method take every penalty and rent and throws exception if there is not enough cash for payment
     *   then shows the state of player (available orders)
     */
    public void state(){
        Board board = Board.getInstance();
        String defOrders = "index, rank, time, property, sell ";
        if (board.fields[position-1] instanceof FreeParking){
            FreeParking tmp = new FreeParking(1);
            tmp.enterParking();
            System.out.println("available orders are:\n"+defOrders);
        } else if (board.fields[position-1] instanceof EmptyField){
            EmptyField tmp = (EmptyField) board.fields[position-1];
            if (tmp.getOwner() == this){
                System.out.println("you are at your own property!");
                System.out.println("available orders are:\n"+defOrders
                + ",buy, build");
            }else if(tmp.getOwner() == BankManager.getInstance()){
                System.out.println("this is a bank property!");
                System.out.println("available orders are:\n"+defOrders
                + ",buy");
            }else {
                tmp.chargeRent(this);
                System.out.println("you are at someone else's property!\nwe received the rent");
                System.out.println("available orders are:\n"+defOrders);
            }

        } else if (board.fields[position-1] instanceof Airport){
            System.out.println("you are at airport NO."+position);
            System.out.println("available orders are:\n"+defOrders
            + ",fly");

        }else if (board.fields[position-1] instanceof Cinema){
            Cinema tmp = (Cinema) board.fields[position-1];
            if (tmp.getOwner() == this){
                System.out.println("you are at your own cinema!");
                System.out.println("available orders are:\n"+defOrders);
            }else if (tmp.getOwner() == BankManager.getInstance()){
                System.out.println("this cinema is a bank property, you can buy it!");
                System.out.println("available orders are:\n"+defOrders +
                ",buy");
            } else {
                tmp.chargeRent(this);
                System.out.println("you are at someone else's cinema!\nenjoy the movie!");
                System.out.println("available orders are:\n"+defOrders);
            }

        }else if (board.fields[position-1] instanceof Road){
            Road road = (Road) board.fields[position-1];
            road.payToll(this);
            System.out.println("you are on the road, the 100$ toll was received");
        }else if (board.fields[position-1] instanceof Prize){
            Prize prize = (Prize) board.fields[position-1];
            prize.wonPrize(this);
            System.out.println("you won a 200$ prize!");
            System.out.println("available orders are:\n"+defOrders);

        }else if (board.fields[position-1] instanceof Prison){
            System.out.println("don't worry, you just passed the prison");
            System.out.println("available orders are:\n"+defOrders);
        }else if (board.fields[position-1] instanceof Tax){
            Tax tax = (Tax) board.fields[position-1];
            tax.chargeTax(this);
            System.out.println("Should 10 percent appear too small, Be thankful I don't take it all");
            System.out.println("available orders are:\n"+defOrders);
        }else if (board.fields[position-1] instanceof Bank){
            System.out.println("welcome to bank!\n if you invested any money here we'll give it to you now!");
            Bank bank = (Bank) board.fields[position-1];
            bank.profit(this);
            System.out.println("you can invest your money here");
            System.out.println("available orders are:\n"+defOrders + ",invest");

        }else if (board.fields[position-1] instanceof QuestionMark){
            System.out.println("well well well! let see what's behind the cards");
            QuestionMark questionMark = (QuestionMark) board.fields[position-1];
            questionMark.randomCard(this);
        }
    }

    /**
     * index method shows the position of player
     */
    public void index(){
        System.out.println("you are at field " + position);
    }

    /**
     * shows the amount of cash and name of the owned properties
     */
    public void property(){
        System.out.println("cash : " + cash + "$  property worth : " + propertyWorth + "$  net worth : " + netWorth +"$");
        for (int i=0; i<ownedProperties.size();i++){
            System.out.printf("%d - ",i+1);
            if (ownedProperties.get(i) instanceof Cinema){
                System.out.printf("Cinema : \n");
            } else {
                System.out.printf("EmptyField : \n");
            }
            System.out.printf("position : " + ownedProperties.get(i).getAtField() + "  color : "
                    + ownedProperties.get(i).getColor());
            if (ownedProperties.get(i) instanceof EmptyField){
                EmptyField tmp = (EmptyField) ownedProperties.get(i);
                if (tmp.isThereHotel()){
                    System.out.printf("there is a Hotel here");
                } else {
                    System.out.printf("buildings : " + tmp.getNumberOfBuildings());
                }
            }
        }
    }

    /**
     * this method receive the players orders and throws exception if they are illegal
     * otherwise it executes the command
     * @param commands player order
     * @param index for sale and fly orders
     */
    public void order(Commands commands, Integer index){
        switch (commands){
            case BUY : buy(); break;
            case BUILD: build(); break;
            case FLY: fly(index); break;
            case PROPERTY: propertyHandler(); break;
            case INVEST: invest(); break;
            case SELL: sell(index); break;
            case INDEX: indexShower(); break;
            case RANK: rankShower(); break;
            case PASS: pass(); break;
        }
    }
    private void buy(){
        Board board = Board.getInstance();
        if (board.fields[position-1] instanceof BankProperties){ // for unbuyable fields
            System.out.println("This field isn't Buyable!");
        }else if (board.fields[position-1] instanceof BuyableProperties){ // buyable properties different situations
            Owner BankManager = game.BankManager.getInstance();
            if (((BuyableProperties) board.fields[position-1]).getOwner() != BankManager ||
                ((BuyableProperties) board.fields[position-1]).getOwner() != this){ // another player's property
                System.out.println("This property belongs to another player!");
            }else if (((BuyableProperties) board.fields[position-1]).getOwner() == this){ // self property
                System.out.println("You own this property!");
            }else if (((BuyableProperties) board.fields[position-1]).getOwner() == BankManager){ // Authorized purchase
                if (board.fields[position-1] instanceof EmptyField){ // buying a EmptyField
                       EmptyField emptyField = new EmptyField(position);
                       emptyField.buy(this);
                    System.out.println("You've bought this EmptyField!");
                } else if (board.fields[position-1] instanceof Cinema){ //buying a Cinema
                    Cinema cinema = new Cinema(position);
                    cinema.buy(this);
                    System.out.println("You owned this cinema right now!");
                }
            }
        }
    }
    private void build(){
        Board board = Board.getInstance();
        if (board.fields[position-1] instanceof BankProperties || board.fields[position-1] instanceof Cinema){ // for unconstructable
            System.out.println("You can't build anything here!");
        }else if (board.fields[position-1] instanceof EmptyField){
            Owner BankManager = game.BankManager.getInstance();
            if (((EmptyField) board.fields[position-1]).getOwner() == BankManager){
                System.out.println("This field belongs to bank, you could buy it and then you can build on it");
            }else if (((EmptyField) board.fields[position-1]).getOwner() != BankManager
                    && ((EmptyField) board.fields[position-1]).getOwner() != this){
                System.out.println("This field belongs to another player");
            }else {
                EmptyField emptyField = new EmptyField(position);
                emptyField.addBuilding(this);
                System.out.println("Building added!");
            }
        }
    }
    private void fly(Integer index){
        Board board = Board.getInstance();
        if (board.fields[position-1] instanceof Airport){
            if (position == index){
                System.out.println("You already at the destination!");
            }else if (board.fields[index-1] instanceof Airport){
                Airport airport = new Airport(position);
                airport.fly(this,position);
                System.out.println("The plane landed at the desired destination ");
            }else {
                System.out.println("There is no airport at the given destination!");
            }
        }else {
            System.out.println("You are not at any airport!");
        }
    }
    private void propertyHandler(){
        this.property();
    }
    private void invest(){
    }
    private void sell(Integer index){
    }
    private void indexShower(){
    }
    private void rankShower(){
    }
    private void pass(){
    }
    /**
     * rank method shows the rank of the player by getting a sorted list of Players
     */
    public void rank(){
        ArrayList<Player> sorted = BankManager.getSortedList();
        for (int i=0;i<sorted.size();i++){
            if (sorted.get(i) == this){
                rank = i+1;
            }
        }
        System.out.println("Rank : " + rank);
    }
    
    public void updateNetWorth(){
        netWorth = propertyWorth + cash;
    }
}
