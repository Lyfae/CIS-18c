/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package texas.holdem;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.Random;
/**
 *
 * @author serva
 */
public class Dealer extends Hand {

    private Hand[] player;
    private Card deck[];
    private int potTot;

    //Queue | Search enqueue with crtl f ("AQ")
    int qCount = 0;
    static Queue<Integer> q = new Queue();

    //initial Bet
    private int iBet;

    //get number of Players
    private int nPlayers;

    //small Blind
    private int smallB;

    //big Blind
    private int bigB;

    //rounds 
    private int rounds;

//  Dealer(int n) {
//    super(n);
//  }
    
    Dealer(int num) {
        int menu = 1;
        
        this.setnPlayers(num);
        this.DOC();

        do {
            this.setBlinds();
            this.setIBet(50);
            this.resetPot();
            this.shuffle();
            this.preFlop();

            int amount = roundOneBet();
            System.out.println("Initial bet is Done");

            for (int i = 0; i < this.nPlayers; i++) {
                int order = (i + this.bigB) % this.nPlayers;
                this.pInfo(order);
            }

            int cnt = 1;
            int nP = this.nPlayers;
            int x = 1;
            int temp;
            do {
                amount = this.roundTwoBet(((cnt + this.smallB) % nP), amount);

                if (((cnt + this.bigB) % nP) == this.bigB) {
                    x = 0;
                }
                if (this.getNumAct() == 1) {
                    System.out.println("find the winner and terminate the round");
                    x = 0;
                }

                cnt++;
                temp = amount - this.player[(cnt + this.smallB) % nP].potTot();

            } while (temp != 0 || x != 0);

            System.out.println("bettingPrompt2 is Done");

            this.flop();
            for (int i = 0; i < this.nPlayers; i++) {
                int order = (i + this.bigB) % this.nPlayers;
                this.pInfo(order);
            }
            cnt = 0;
            x = 1;

            while ((amount - this.player[(cnt + this.bigB) % nP].potTot()) != 0 || x != 0) {
                int aP = this.getNumAct();

                amount = this.roundThreeBet(((cnt + this.bigB) % nP), amount);
                cnt++;

                if ((cnt + this.bigB) % nP == this.bigB) {
                    x = 0;
                }

                if (this.getNumAct() == 1) {
                    System.out.println("find the winner");

                    x = 0;
                }
            }

            System.out.println("bettingPrompt3 is Done");

            this.turn();
            for (int i = 0; i < this.nPlayers; i++) {
                int order = (i + this.bigB) % this.nPlayers;
                this.pInfo(order);
            }

            cnt = 0;
            x = 1;

            while ((amount - this.player[(cnt + this.bigB) % nP].potTot()) != 0 || x != 0) {
                int aP = this.getNumAct();

                amount = this.roundThreeBet(((cnt + this.bigB) % nP), amount);
                cnt++;

                if ((cnt + this.bigB) % nP == this.bigB) {
                    x = 0;
                }

                if (this.getNumAct() == 1) {
                    System.out.println("find the winner");

                    x = 0;
                }
            }

            System.out.println("bettingPrompt 4 is Done");

            this.river();
            for (int i = 0; i < this.nPlayers; i++) {
                int order = (i + this.bigB) % this.nPlayers;
                this.pInfo(order);
            }
            cnt = 0;
            x = 1;

            while ((amount - this.player[(cnt + this.bigB) % nP].potTot()) != 0 || x != 0) {
                int aP = this.getNumAct();

                amount = this.roundThreeBet(((cnt + this.bigB) % nP), amount);
                cnt++;

                if ((cnt + this.bigB) % nP == this.bigB) {
                    x = 0;
                }

                if (this.getNumAct() == 1) {
                    System.out.println("find the winner");

                    x = 0;
                }
            }

            System.out.println("test"); 
            for (int i = 0; i < this.nPlayers; i++) {
                int order = (i + this.bigB) % this.nPlayers;
                System.out.println("Player" + order + ": " + this.player[order].getHandName());
            }

            this.calcTot();

            for (int i = 0; i < this.nPlayers; i++) {
                int order = (i + this.bigB) % this.nPlayers;
                this.pInfo(order);
            }

            menu = this.reset();
        } while (menu != 0);

    }
    
    private void setIBet(int n) {
        this.iBet = n;
    }

    private void resetPot() {
        this.potTot = 0;
    }

    int getnRounds() {
        return this.rounds;
    }

    int getnPlayers() {
        return this.nPlayers;
    }

    int getSmallBlind() {
        return this.smallB;
    }
    //get the big blind

    int getBigBlind() {
        return this.bigB;
    }

    int grandPot() {
        return this.potTot;
    }

    void nRound() {
        this.rounds++;
    }
   
    

    void setnPlayers(int num) {
        this.nPlayers = num;
        this.player = new Hand[this.nPlayers];

        for (int i = 0; i < this.nPlayers; i++) {
            this.player[i] = new Hand();
            this.player[i].setName("player#" + Integer.toString(i));
        }
    }

    void setBlinds() {
        this.bigB = this.getnRounds() % (this.getnPlayers() - 1);
        this.smallB = this.bigB + 1;
    }

    void pInfo(int num) {
        System.out.println(this.player[num].getName());

        if (num == this.getBigBlind()) {
            System.out.println("BB");
        } else if (num == this.getSmallBlind()) {
            System.out.println("SB");
        }

        System.out.println("Balance: $" + this.player[num].getTotal());

        for (Card it : this.player[num].getCards()) {
            it.output();
        }
    }

    int getNumAct() {
        int num = 0;
        for (int i = 0; i < this.nPlayers; i++) {
            if (this.player[i].pStatus()) {
                num++;
            }
        }
        return num;
    }

    void calcTot() {
        int win = this.determineWinner();
        System.out.println("Congratulations! Player" + win + " has won $" + this.grandPot());
        this.player[win].addTot(this.grandPot());
        this.resetPot();
    }

    int determineWinner() {
        int numPlayers = this.getnPlayers();
        int winner = 0;
        int strongest = 0;

        for (int i = 0; i < numPlayers; i++) {
            if (this.player[i].pStatus() == true) {
                if (this.player[i].getHand() > strongest) {
                    strongest = this.player[i].getHand();
                    winner = i;
                }
            }
        }

        return winner;
    }

    int reset() {
        Scanner userInput = new Scanner(System.in);
        Integer input;
        this.nRound();

        for (int i = 0; i < this.getnPlayers(); i++) {
            this.player[i].clearHand();
        }

        //Queue Dequeue
        System.out.println("Grand Pot History: ");
        for (int k = 0; k < this.qCount; k++) {
          System.out.print("$");
          System.out.print(q.dequeue());
          System.out.println();
        }

        System.out.println("Game Master if you want to quit press 0");
        String inputS = userInput.nextLine();
        input = Integer.parseInt(inputS);

        return input;
    }

    private void DOC() {
        this.deck = new Card[52];
        for (int i = 0; i < 52; i++) {
            this.deck[i] = new Card(i);
        }
    }

    void shuffle() {
        
        for (int i = 0; i < 52; i++) {
            Double randomDouble = Math.random();
            randomDouble = randomDouble * 52;
            int randomInt =  randomDouble.intValue();
            swap(this.deck[i], this.deck[randomInt]);
        }
    }
    
    void swap(Card a, Card b){
        Card temp = a;
        a = b;
        b =temp;
        
    }

    void preFlop() {
        int nCards = 2;
        int order;
        int nP = this.nPlayers;
        int bB = this.bigB;

        for (int i = 0; i < nCards; i++) {
            for (int j = 0; j < nP; j++) {
                order = (j + bB) % nP;
                this.player[order].addCards(this.deck[j + nP * i]);
            }
        }
    }

    void flop() {
        int nCards = 3;
        int nP = this.nPlayers;
        int bB = this.bigB;

        for (int i = nP * 2 + 1; i < nP * 2 + 1 + nCards; i++) {
            for (int j = 0; j < nP; j++) {
                int order = (j + bB) % nP;
                this.player[order].addCards(this.deck[i]);
            }
        }
    }

    void turn() {
        int nCards = 1;
        int nP = this.nPlayers;
        int bB = this.bigB;

        for (int i = nP * 2 + 1 + 3 + 1; i < nP * 2 + 1 + 3 + 1 + nCards; i++) {
            for (int j = 0; j < nP; j++) {
                int order = (j + bB) % nP;
                this.player[order].addCards(this.deck[i]);
            }
        }
    }

    void river() {
        int nCards = 1;
        int nP = this.nPlayers;
        int bB = this.bigB;

        for (int i = nP * 2 + 1 + 3 + 1 + 1 + 1; i < nP * 2 + 1 + 3 + 1 + 1 + 1 + nCards; i++) {
            for (int j = 0; j < nP; j++) {
                int order = (j + bB) % nP;
                this.player[order].addCards(this.deck[i]);
            }
        }
    }

    int roundOneBet() {
        Scanner userInput = new Scanner(System.in);
        Integer input;
        int raise = 0;
        int diff;
        int nP = this.nPlayers;
        int amount;
        this.player[bigB].bet(this.iBet);
        this.potTot += this.iBet;
        //Add Queue (AQ)
        this.qCount++;
        q.enqueue(this.potTot);
        System.out.println("Player" + this.bigB + ": $" + this.iBet + " into the pot");
        System.out.println("Pot: $" + this.grandPot());
        amount = this.player[bigB].potTot();

        this.player[smallB].bet(this.iBet / 2);
        this.potTot += this.iBet / 2;
        //AQ
        this.qCount++;
        q.enqueue(this.potTot);
        diff = amount - this.iBet / 2;

        System.out.println("Player" + this.smallB + ": $" + this.iBet / 2 + " into the pot");
        System.out.println("Pot: $" + this.grandPot());

        do {
            System.out.println();
            System.out.println("To call you have to put $" + diff);
            System.out.println("Player" + this.smallB + ": Call - 1, Raise - 2, Fold - 3");
            String inputS = userInput.nextLine();
            input = Integer.parseInt(inputS);

            if (input == 1) {
                this.player[smallB].bet(diff);
                this.potTot += diff;
                //AQ
                this.qCount++;
                q.enqueue(this.potTot);
                System.out.println("Player#" + smallB + ": $" + diff);
                System.out.println("Pot: $" + this.grandPot());
            } else if (input == 2) {
                System.out.println("amount: ");
                do {
                    inputS = userInput.nextLine();
                    input = Integer.parseInt(inputS);
                    if (raise <= diff) {
                        System.out.println("Amount should be greater than call Amount");
                    }
                } while (raise <= diff);
                this.player[smallB].bet(raise + diff);
                this.potTot += (raise + diff);
                //AQ
                this.qCount++;
                q.enqueue(this.potTot);
                System.out.println("Player#" + smallB + ": $" + raise + diff + " into the pot");
                System.out.println("Pot: $" + this.grandPot());
                amount = this.player[smallB].potTot();
            } else if (input == 3) {
                this.player[smallB].setAFK();
                System.out.println("Player#" + smallB + ": folded");
                System.out.println("Pot: $" + this.grandPot());
            } else {
                System.out.println("wrong input");
            }
        } while (input < 1 || input > 3);

        return amount;
    }

    int roundTwoBet(int num, int amount) {
        Scanner userInput = new Scanner(System.in);
        Integer input = null;
        do {
            if (this.player[num].pStatus()) {
                int nP = this.nPlayers;
                Integer raise;
                int diff = amount - this.player[num].potTot();
                System.out.println();
                System.out.println("To call you have to put $" + diff);
                System.out.println("Player" + num + ": call - 1, Raise - 2, Fold - 3");
                String inputS = userInput.nextLine();
                input = Integer.parseInt(inputS);

                if (input == 1) {
                    this.player[num].bet(diff);
                    this.potTot += (diff);
                    //AQ
                    this.qCount++;
                    q.enqueue(this.potTot);
                    System.out.println("Player#" + num + ": $" + diff);
                    System.out.println("Pot: $" + this.grandPot());
                } else if (input == 2) {
                    System.out.println("Amount: ");
                    do {
                        String raiseUser = userInput.nextLine();
                        raise = Integer.parseInt(raiseUser);
                        if (raise <= diff) {
                            System.out.println("Amount should be greater than call Amount");
                        }
                    } while (raise <= diff);

                    this.player[num].bet(raise + diff);
                    this.potTot += (raise + diff);
                    //AQ
                    this.qCount++;
                    q.enqueue(this.potTot);
                    System.out.println("Player#" + num + ": $" + (raise + diff));
                    System.out.println("Pot: $" + this.grandPot());
                    amount = this.player[num].potTot();
                } else if (input == 3) {
                    this.player[num].setAFK();
                    System.out.println("Player#" + num + ": folded");
                    System.out.println("Pot: $" + this.grandPot());
                } else {
                    System.out.println("wrong input");
                }
            }
        } while (input < 1 || input > 3);

        return amount;
    }

    int roundThreeBet(int num, int amount) {
        Scanner userInput = new Scanner(System.in);
        int nP = this.nPlayers;
        Integer input = null;
        Integer raise =null;
        Integer dif =0;

        do {
            if (this.player[num].pStatus()) {
                dif = amount - this.player[num].potTot();
                
                if (dif > 0) {
                    System.out.println();
                    System.out.println("You can check");
                    System.out.println("Player" + num + ": check - 1, Raise - 2, Fold -3");
                } else {
                    System.out.println();
                    System.out.println("To call you have to put $" + dif);
                    System.out.println("Player" + num + ": call - 1, Raise - 2, Fold -3");
                }
                
                String raiseUser = userInput.nextLine();
                input = Integer.parseInt(raiseUser);

                if (input == 1) {
                    this.player[num].bet(dif);
                    this.potTot += (dif);
                    //AQ
                    this.qCount++;
                    q.enqueue(this.potTot);
                    System.out.println("Player#" + num + ": $" + dif);
                    System.out.println("Pot: $" + this.grandPot());
                } else if (input == 2) {
                    System.out.println("Amount: ");
                    do {
                        raiseUser = userInput.nextLine();
                        input = Integer.parseInt(raiseUser);
                        if (raise <= dif) {
                            System.out.println("Amount should be greater than call Amount");
                        }
                    } while (raise <= dif);

                    this.player[num].bet(raise + dif);
                    this.potTot += (raise + dif);
                    //AQ
                    this.qCount++;
                    q.enqueue(this.potTot);
                    System.out.println("Player#" + num + ": $" + raise + dif);
                    System.out.println("Pot: $" + this.grandPot());
                    amount = this.player[num].potTot();
                } else if (input == 3) {
                    this.player[num].setAFK();
                    System.out.println("Player#" + num + ": folded");
                    System.out.println("Pot: $" + this.grandPot());
                } else {
                    System.out.println("wrong input");
                }
            }
        } while (input < 1 || input > 3);

        return amount;
    }



    int roundFourBet(int num, int amount) {
        Scanner userInput = new Scanner(System.in);
        int nP = this.nPlayers;
        Integer input = null;
        Integer raise = null;
        int dif=0;

        do {
            if (this.player[num].pStatus()) {
                dif = amount - this.player[num].potTot();
                if (dif > 0) {
                    System.out.println();
                    System.out.println("You can check");
                    System.out.println("Player" + num + ": check - 1, Raise - 2, Fold -3");
                } else {
                    System.out.println();
                    System.out.println("To call you have to put $" + dif);
                    System.out.println("Player" + num + ": call - 1, Raise - 2, Fold -3");
                }

                String raiseUser = userInput.nextLine();
                input = Integer.parseInt(raiseUser);
                if (input == 1) {
                    this.player[num].bet(dif);
                    System.out.println("Player#" + num + ": $" + dif);
                    System.out.println("Pot: $" + this.grandPot());
                } else if (input == 2) {
                    System.out.println("Amount: ");
                    do {
                        raiseUser = userInput.nextLine();
                        input = Integer.parseInt(raiseUser);
                        if (raise <= dif) {
                            System.out.println("Amount should be greater than call Amount");
                        }
                    } while (raise <= dif);

                    this.player[num].bet(raise + dif);
                    this.potTot += (raise + dif);
                    //AQ
                    this.qCount++;
                    q.enqueue(this.potTot);
                    System.out.println("Player#" + num + ": $" + raise + dif);
                    System.out.println("Pot: $" + this.grandPot());
                    amount = this.player[num].potTot();
                } else if (input == 3) {
                    this.player[num].setAFK();
                    System.out.println("Player#" + num + ": folded" + raise + dif);
                    System.out.println("Pot: $" + this.grandPot());
                } else {
                    System.out.println("wrong input");
                }
            }
        } while (input < 1 || input > 3);

        return amount;
    }
}
