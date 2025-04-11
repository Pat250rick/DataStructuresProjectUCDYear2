
public class Treap {



    private class Node<E> {

        private int key;
        private E value;
        private Node<E> left;
        private Node<E> right;

        public void setKey(int key) {this.key = key;}
        public int getKey() {return key;}

        public void setLeft(Node<E> left){this.left = left;}
        public Node<E> getLeft(){return left;}

        public void setRight(Node<E> right){this.right = right;}
        public Node<E> getRight(){return right;}

        public void setValue(E value){this.value = value;}
        public E getValue(){return value;}
    }
}
