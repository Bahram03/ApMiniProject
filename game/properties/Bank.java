package game.properties;

import game.Player;

/**
 * implements the only Bank field and the actions it's capable of via two void methods.
 */
public class Bank extends BankProperties{
    /**
     * is used in initialization process in Board constructor.
     * @see game.Board
     */
    public final static int[] atFields = {21};
    private double INVEST_RATIO = 0.5;
    private int PROFIT_RATIO = 2;

    public Bank(int atField){
        this.atField = atField;
    }

    /**
     * Implements the invest command and updates player's budget.
     * @param player
     */
    public void invest(Player player){
        double investedMoney = player.getCash() * INVEST_RATIO;
        player.setInvestedMoney((int)investedMoney);
        player.setCash(player.getCash() - (int)investedMoney);
        player.setNetWorth(player.getPropertyWorth() + player.getCash());//update net worth
    }

    /**
     * is called automatically each time the player enters Bank field and returns the profit if the player's invested
     * money is not zero,and updates player's budget.
     * @param player
     */
    public void profit(Player player){
        if(player.getInvestedMoney() != 0){
            int addedCash = player.getInvestedMoney() * PROFIT_RATIO;
            player.setCash(player.getCash() + addedCash);
            player.setNetWorth(player.getPropertyWorth() + player.getCash());//update net worth
            player.setInvestedMoney(0);//update invested money
        }
    }


}
