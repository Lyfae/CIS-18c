/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package texas.holdem;

import java.lang.Comparable;

/**
 *
 * @author serva
 */
public class Node<T extends Comparable> {
    T data;
    Node<T> left, right, parent;

    public void insert(Node<T> n){
        if (n.data.compareTo(this.data) > 0) {
            // New node is greater than my existing data
            // Go to the right.
            if (this.right != null) {
                // Right node is already taken.
                this.right.insert(n);
            } else {
                // We live here now.
                this.right = n;
            }
        } else {
            // Go to the left
            if (this.left != null) {
                // Left node is already taken
                this.left.insert(n);
            } else {
                // We live HERE now.
                this.left = n;
            }
        }
    }
}
