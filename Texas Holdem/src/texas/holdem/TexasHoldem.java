/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package texas.holdem;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author serva
 */
public class TexasHoldem {
  public static void main(String[] args) throws IOException {
      Scanner reader = new Scanner(System.in);

        //Dealer d = new Dealer(3);

        //Using write out and read in
        //Ask how many players
        System.out.println("How many players do we have today?:");
        String amoun = reader.nextLine();
        Integer amounNum = Integer.parseInt(amoun);
        Player.writeFile(amounNum);

        Dealer d = new Dealer(Player.playerAmount());
  }
}

