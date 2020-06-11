/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package texas.holdem;

import java.util.LinkedList;
/**
 *
 * @author serva
 */
class Queue<T>{
  LinkedList<Integer> data;

  public Queue() {
    data = new LinkedList();
  }
  public void enqueue(Integer itemToEnqueue) {
    data.add(itemToEnqueue);
  }

  public Integer dequeue() {
    Integer itemToReturn = data.get(0);
    data.remove();
    return itemToReturn;
  }
}