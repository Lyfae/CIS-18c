/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package texas.holdem;

import java.util.Iterator;
import java.util.Set;
import java.util.*; 
import java.util.Map.Entry;

/**
 *
 * @author serva
 */
public class Hand extends Player {
    //The Hand object but I need to make this into a constructor
    //Hand hand;
    
    //Poker straight, Poker flush, Poker pair, and Poker straight flush
    private int pS, pf, pP, pSF;
    //Type of hand
    private int handType;
    private int getNum;
    //Name of hand
    private String handName;
    
    //Need map and multimap of suits and faces
   Map<Integer, Integer> suits = new HashMap<>();
   Map<Integer, Integer> faces = new HashMap<>();
   

   Map<Integer, Integer> getSuits(){
       return this.suits;
   }
   
   Map<Integer,Integer> getFaces(){
       return this.faces;
   }
    
    //Returns the name of the hand
    String getHandName(){
        //Error: Cannot find method getHand()
        //Most likely because there are still errors in getHand()
        switch(this.getHand()){
            case 0: this.handName = "High-Card";
                break;
            case 1: this.handName = "One-Pair";
                break;
            case 2: this.handName = "Two-Pair";
                break;
            case 3: this.handName = "Three-of-a-kind";
                break;
            case 4: this.handName = "Straight";
                break;
            case 5: this.handName = "Flush";
                break;
            case 6: this.handName = "Full-House";
                break;
            case 7: this.handName = "Four-of-a-kind";
                break;
            case 8: this.handName = "Straight-Flush";
                break;
            default: this.handName = "Invalid";
        }
        return this.handName;
    }
    
    //Creates the hand for the player
    int getHand(){
        this.checkStraight();
        //Error: Cannot find methods setSuits() and setFaces()
        this.setSuits();
        this.setFaces();

        if (this.pS != 0 && this.pf != 0) {
            this.pSF = 1;
            this.handType = 8;
            
            //Need help here from here
            //Error: Not certain about the proper syntax for java sets
            TreeSet<Integer> stFl = new TreeSet<>();
            //Set<Integer> stFl = new TreeSet();
            
            //Error: getCards(), getSuit(), and getFace() | should there be a method in this class for those?
            for (Card it : this.getCards()) {
                if (it.getSuit() == this.pf) {
                    stFl.add(it.getFace());
                }
            }
            
            if (this.pS == 15) {
                for (int i = 14; i > 9; i--) {
                    if (stFl.last()==i) this.pSF = 0;
                }
            } else if (this.pS == 15) {
                for (int i = 5; i > 1; i--) {
                    if (stFl.last()==i) this.pSF = 0;
                }
            } else {
                for (int i = this.pS; i> this.pS - 5; i--) {
                    if (stFl.last()==i) this.pSF = 0;
                }
            }
            // to here
            
            if (this.pSF == 0) {
                this.handType = 5;
            }
        }
        else if (this.pP == 7) {
            this.handType = 7;
        } else if (this.pP == 6) {
            this.handType = 6;
        } else if (this.pf != 0) {
            this.handType = 5;
        } else if (this.pS != 0) {
            this.handType = 4;
        } else if (this.pP == 3) {
            this.handType = 3;
        } else if (this.pP == 2) {
            this.handType = 2;
        } else if (this.pP == 1) {
            this.handType = 1;
        } else {
            this.handType = 0;
        }
        return this.handType;
    }

    //Checks proper order
    private void checkStraight() {
        this.pS = 0;
        
        //Need help here from here
        //Error: Unsure about the proper syntax for sets in java
        TreeSet<Integer> faceSet = new TreeSet();
        int cnt = 0;
        
        for (Card it : this.getCards()) {
            faceSet.add(it.getFace());
        }
        
        Iterator<Integer> it = faceSet.iterator();
        // to here
        Integer j = it.next();
        while (j != faceSet.last() && cnt != 4) {
            if (j - faceSet.ceiling(j) == 1) {
                cnt++;
            } else {
                cnt = 0;
            }
            //++it;
            //it.hasNext();
            j = it.next();
        }
        
        if (cnt == 4 && j + 4 != 14) {
            this.pS = j + 4;
        } else if (cnt == 4 && j + 4 == 14) {
            this.pS = 15;
        }
        if (j == 14) {
            while (j != faceSet.last()&& cnt != 3) {
                if (j - faceSet.ceiling(j) == 1) {
                    cnt++;
                } else {
                    cnt = 0;
                }
                //++it;
                //it.hasNext();
                j = it.next();
            }
        }
        if (cnt == 3 && j + 3 == 5) {
            this.pS = 14;
        }
        faceSet.clear();
    }

    //Set suit
    private void setSuits() {
        this.pf = 0;
        
        //Need help here from here
        //Error: Not sure about multiset translation from C++
        TreeSet<Integer> suitSet = new TreeSet();
        HashMap<Integer,Integer> pair = new HashMap();
        
        //Error: Same as above about getCards() and getSuit()
        for (Card it: this.getCards()) {
            suitSet.add(it.getSuit());
        }
        
        for (int i = 0; i < 4; i++) {
            if (suitSet.floor(i) > 4) { //check this : used to be count
                //Error: Need maps up top for suits and then proper translation
                //this.suits.insert(pair<Integer, Integer>(suitSet.floor(i), i));
                this.suits.put(suitSet.floor(i), i);
                this.pf = 1;
            }
        }
        
        suitSet.clear();
        // to here
    }

    //Set face
    private void setFaces() {
        this.pP = 0;
        
        //Need help from here
        //Error: Not sure about multiset translation from C++
        TreeSet<Integer> faceSet = new TreeSet();
        
        //Error: Same as above about getCards() but now with getFace();
        for (Card it : this.getCards()) {
            faceSet.add(it.getFace());
        }
        
        for (int i = 2; i < 15; i++) {
            //if (faceSet.count(i) > 1){
            if(faceSet.contains(i)){
                //Error: need multimap for faces then translation
                //this.faces.insert(pair<Intger, Integer>(faceSet.count(i), i));
                this.faces.put(faceSet.floor(i), i);
            }
        }
        
        //Error: New map translation
        Set<Entry<Integer, Integer>> it = faces.entrySet();
        Iterator<Entry<Integer, Integer>> pos = it.iterator();
        Integer j = pos.next().getKey();
        if (this.faces.isEmpty()) {
            this.pP = 0;
        } else if (this.faces.size() == 1) {
            if (j == 2) this.pP = 1;
            else if (j == 3) this.pP = 3;
            else if (j == 4) this.pP = 7;

        } else if (this.faces.size() > 1) {
            //it.iterator();
            if (j == 2) this.pP = 2;
            else if (j == 3) this.pP = 6;
        }
        
        faceSet.clear();
    }
    
    //Erases hand
    void clearHands(){
        this.clearHand();
        this.faces.clear();
        this.suits.clear();
        this.pSF = 0;
        this.pS = 0;
        this.pf = 0;
        this.pP = 0;
        this.handType = 0;
        this.handName= "";
    }
}
