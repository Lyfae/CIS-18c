/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package texas.holdem;

import java.util.ArrayList;
import java.util.List;

//Read in imports
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
//Write out import
import java.io.PrintWriter;

/**
 *
 * @author serva
 */
public class Player extends Card {
    private List<Card> pCard = new ArrayList<>();
    private boolean done = false;
    private int pot;
    private int total;
    private String name;

    public List<Card> getCards(){
        return this.pCard;
    }

    public String getName(){
        return this.name;
    }

    public int getTotal(){
        return this.total;
    }
    public boolean getConfirm(){
        return this.done = true;
    }
    //setters
    //set a player's yes
    public void setConfirm(){
        this.done = true; //true
    }
    //set the player's name
    public void setName(String n){
        this.name = n;
    }
    //set the player's total
    public void setPlayerTotal(){
        this.total = 100; //for now
    }
    //if the player doesn't want to participate in the round
    public void setAFK(){
        this.done = false;
    }
    //initialize the pot to 0
    public void setPot(){
        this.pot = 0;
    }
    //add cards to the hand

    //take money from player and add to pot
    public void bet(int b){
        this.total -= b;
        this.pot +=b;
    }
    //track how much is in the pot
    public int potTot(){
        return this.pot;
    }
    //check if the player is still playing
    public boolean pStatus(){
        return !this.done;
    }
    //add to pot from different rounds
    void addTot(int pot){
        this.total += pot;
    }
    //reset
    void clearHand(){
       this.pCard.clear();
    }

    public void addCards(Card c){
        this.pCard.add(c);
    }


    Player() {
        this.setPlayerTotal();
    }

    //Read in
    static int playerAmount() throws IOException{
        String findFile = "PlayerCountT.txt";
        String output;
        Integer out = 0;
        int num;
        
        try{
            FileInputStream fStream = new FileInputStream(findFile);
            try (DataInputStream dStream = new DataInputStream(fStream)) {
                BufferedReader bReader = new BufferedReader(new InputStreamReader(dStream));
                output = bReader.readLine();
                out = Integer.parseInt(output);
            }
        }
        catch (FileNotFoundException ex){
            System.out.println("That file does not exist.");
        }
        return out;
    }

    //Write out
    public static void writeFile(Integer numPlayer) throws FileNotFoundException {
        String newName = "PlayerCountT.txt";
        PrintWriter write = new PrintWriter(newName);
        write.print(numPlayer);
        write.flush();
    }
}