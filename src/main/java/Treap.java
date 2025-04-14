import java.util.Random;

public  class Treap<E extends Comparable<E>> {

    private Node<E> root = null;

    /* Treap Operations*/

    public Treap(){};

    public Treap(E root){
        insert(root);
    }

    public Treap(E [] arr){
        for(E e : arr){
            insert(e);
        }
    }

    /* Element Operations */

    public void insert(E value){
        Random rand = new Random();
        int priority = rand.nextInt();

        Node<E> pointer = root;

        if(pointer == null){
            root = new Node<>( value, priority, null, null, null );
            return;
        }

        while (pointer != null && value.compareTo(pointer.getValue()) < 0 && (pointer.getLeft() == null || pointer.getRight() == null)) {
            if(value.compareTo(root.getValue()) > 0){
                pointer = pointer.getLeft();
            }else{
                pointer = pointer.getRight();
            }
        }

        pointer = new Node<>( value, priority, null, null, pointer );


        while (pointer.parent != null && pointer.parent.priority < pointer.priority) {
            if(pointer.parent.left == pointer){
                rotateRight(pointer);
            }else{
                rotateLeft(pointer);
            }
        }

    }

    private void rotateLeft(Node<E> pointer){
        Node<E> left = pointer.getLeft();
        Node<E> parent = pointer.getParent();

        pointer.setParent(parent.getParent());
        if(parent.getParent().getLeft() == parent){
            pointer.getParent().setLeft(pointer);
        }else{
            pointer.getParent().setRight(pointer);
        }

        pointer.setLeft(parent);
        parent.setParent(pointer);

        parent.setRight(left);
        left.setParent(parent);
    }

    private void rotateRight(Node<E> pointer){
        Node<E> right = pointer.getRight();
        Node<E> parent = pointer.getParent();

        pointer.setParent(parent.getParent());
        if(parent.getParent().getLeft() == parent){
            pointer.getParent().setLeft(pointer);
        }else{
            pointer.getParent().setRight(pointer);
        }

        pointer.setRight(parent);
        parent.setParent(pointer);

        parent.setLeft(right);
        right.setParent(parent);
    }

    public void delete(E value){
        Node<E> pointer = getNode(value);

        if(pointer == null) return;

        while(pointer.getLeft() != null || pointer.getRight() != null){
            if(pointer.getLeft() != null){
                rotateRight(pointer.getLeft());
            }else{
                rotateLeft(pointer.getRight());
            }
        }

        if(pointer.getParent().getLeft() == pointer){
            pointer.getParent().setLeft(null);
        }else{
            pointer.getParent().setRight(null);
        }
    }

    private Node<E> getNode(E value){
        Node<E> pointer = root;

        while(true){
            if(pointer == null){
                return null;
            }else if(pointer.getValue().compareTo(value) == 0){
                return pointer;
            }else if(pointer.getValue().compareTo(value) < 0){
                pointer = pointer.getLeft();
            }else{
                pointer = pointer.getRight();
            }
        }
    }

    /* Bulk Operations */

    public void join(){
        //TODO
    }

    public void split(){
        //TODO
    }



    private static class Node<E> {

        private int priority;
        private E value;

        private Node<E> left;
        private Node<E> right;
        private Node<E> parent;

        public Node(E value, int priority, Node<E> left, Node<E> right, Node<E> parent){
            this.value = value;
            this.priority = priority;
            this.left = left;
            this.right = right;
            this.parent = parent;
        }

        public void setPriority(int priority) {this.priority = priority;}
        public int getPriority() {return priority;}

        public void setLeft(Node<E> left){this.left = left;}
        public Node<E> getLeft(){return left;}

        public void setRight(Node<E> right){this.right = right;}
        public Node<E> getRight(){return right;}

        public void setParent(Node<E> parent) {this.parent = parent;}
        public Node<E> getParent() {return parent;}

        public void setValue(E value){this.value = value;}
        public E getValue(){return value;}
    }
}
