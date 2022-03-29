package game.properties;

import game.Player;

public class Prison extends BankProperties{
    public final static int[] atFields = {13};
    private final double FREEDOM_COST = 50.0;
    public Prison(int atField){
        this.atField = atField;
    }
    public static void imprisonment(Player player){
        if (player.getNoJail() > 0){
            System.out.println("You had a NO_JAIL coupon! Be careful next time.");
            player.setNoJail(player.getNoJail() - 1);
        } else {
            player.setInJail(true);
            player.setPosition(atFields[0]); // change the position of player to it's
        }
    }
    public void free(Player player){
        if (player.getCash() < FREEDOM_COST && player.getLastDiceNumber() != 1){
            throw new NotEnoughCashForFreedom("you haven't enough cash to get outta here, try sell your properties in order to do so");
        }else if (player.getLastDiceNumber() != 1){
            player.setCash(player.getCash() - FREEDOM_COST);
        }
        player.setInJail(false);
        System.out.println("you're free to go!");
    }
}
