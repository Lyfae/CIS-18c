/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package texas.holdem;

/**
 *
 * @author serva
 */
public class Card {

    //card number
    private int cNum;
    //get card face
    private int cFace;
    //get suit
    private int cSuit;
    //get suit name
    private String suitName;
    //get face name
    private String faceName;
    //get playing cards 
    private String playCard;
    

    Card(){
        
    }
    //constructor 
    Card(int n) {
        if (n >= 0 && n < 52) {
            this.cNum = n;
            this.setPlayCard();
            this.setSuit();
            this.setFace();
        } else {
            this.cNum = -1;
            this.cFace = -1;
            this.cSuit = -1;
            this.faceName = "Invalid";
            this.playCard = "Invalid";
            this.suitName = "Invlaid";
        }
    }

    //methods
    //get suit
    int getSuit() {
        return this.cSuit;
    }
    //get face
    int getFace(){
        return this.cFace;
    }
    //get number
    int getNum(){
        return this.cNum;
    }
    //get the playing cards
    String gPlayCard(){
        return this.playCard;
    }
    //get the face name
    String gFaceName(){
        return this.faceName;
    }
    //get the suits name
    String gSuitName(){
        return this.suitName;
    }
    //setSuit
    void setSuit() {
        //set the value of the clubs
        if (this.cNum < 13) {
            this.cSuit = 0;
            this.suitName = "Clubs";
        } else if (this.cNum < 26) {
            this.cSuit = 1;
            this.suitName = "Diamonds";
        } else if (this.cNum < 39) {
            this.cSuit = 2;
            this.suitName = "Hearts";
        } else if (this.cNum < 52) {
            this.cSuit = 3;
            this.suitName = "Spades";
        } else {
            this.cSuit = -1;
            this.suitName = "Invalid Card";
        }
    }

    //setFace
    void setFace() {
        //setting value of the cards
        //setting the card face equal to the card number
        if (this.cNum % 13 + 1 == 1) {
            this.cFace = 14;
        } else {
            this.cFace = this.cNum % 13 + 1;
        }

        switch (this.cFace) {
            case 2:
                this.faceName = "Two";
                break;
            case 3:
                this.faceName = "Three";
                break;
            case 4:
                this.faceName = "Four";
                break;
            case 5:
                this.faceName = "Five";
                break;
            case 6:
                this.faceName = "Six";
                break;
            case 7:
                this.faceName = "Seven";
                break;
            case 8:
                this.faceName = "Eight";
                break;
            case 9:
                this.faceName = "Nine";
                break;
            case 10:
                this.faceName = "Ten";
                break;
            case 11:
                this.faceName = "Jack";
                break;
            case 12:
                this.faceName = "Queen";
                break;
            case 13:
                this.faceName = "King";
                break;
            case 14:
                this.faceName = "Ace";
                break;
            default:
                this.faceName = "Not applicable";
        }

    }

    //set the playing card
    void setPlayCard() {
        //create the array of stings for the cards
        String[] card = {"\uD83C\uDCD1", "\uD83C\uDCD2", "\uD83C\uDCD3", "\uD83C\uDCD4", "\uD83C\uDCD5", "\uD83C\uDCD6", "\uD83C\uDCD7",
            "\uD83C\uDCD8", "\uD83C\uDCD9", "\uD83C\uDCDA", "\uD83C\uDCDB", "\uD83C\uDCDD", "\uD83C\uDCDE", "\uD83C\uDCC1",
            "\uD83C\uDCC2", "\uD83C\uDCC3", "\uD83C\uDCC4", "\uD83C\uDCC5", "\uD83C\uDCC6", "\uD83C\uDCC7", "\uD83C\uDCC8",
            "\uD83C\uDCC9", "\uD83C\uDCCA", "\uD83C\uDCCB", "\uD83C\uDCCD", "\uD83C\uDCCE", "\uD83C\uDCB1", "\uD83C\uDCB2",
            "\uD83C\uDCB3", "\uD83C\uDCB4", "\uD83C\uDCB5", "\uD83C\uDCB6", "\uD83C\uDCB7", "\uD83C\uDCB8", "\uD83C\uDCB9",
            "\uD83C\uDCBA", "\uD83C\uDCBB", "\uD83C\uDCBD", "\uD83C\uDCBE", "\uD83C\uDCA1", "\uD83C\uDCA2", "\uD83C\uDCA3",
            "\uD83C\uDCA4", "\uD83C\uDCA5", "\uD83C\uDCA6", "\uD83C\uDCA7", "\uD83C\uDCA8", "\uD83C\uDCA9", "\uD83C\uDCAA",
            "\uD83C\uDCAB", "\uD83C\uDCAD", "\uD83C\uDCAE"};

        //print out the cards
        //if cards match to the value being called
        for (int i = 0; i < 52; i++) {
            if (this.cNum == i
            ) {
            //value of card equal to the actual card
            this.playCard = card[i];
            }
        }
    }
    
    void output() {
    System.out.println(this.gPlayCard() + " " + this.gFaceName() + " " + this.gSuitName());
}

}
