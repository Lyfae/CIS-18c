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
class BinarySearchTree<T extends Comparable>{
    Node<T> root;

    public BinarySearchTree(Node<T> root){
        this.root = root;
    }

    public Node search(Node<T> root, T key){
        // Base Cases: root is null or key is present at root
        if (root==null || root.data==key)
            return root;

        // val is greater than root's key
        if (!root.data.equals(key))
            return search(root.left, key);

        // val is less than root's key
        return search(root.right, key);
    }

    void insert(Node<T> node) {
        this.root.insert(node);
    }

    public void insert(T newData){
        Node<T> newNode = new Node();
        newNode.data = newData;
        this.root.insert(newNode);
    }

}
