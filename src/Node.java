public class Node {
    char nodeCharacter;
    Node left;
    Node middle;
    Node right;
    boolean endOfWord;

    public Node(char nodeCharacter){
        this.nodeCharacter = nodeCharacter;
        this.endOfWord = false;
        this.left = null;
        this.right = null;
        this.middle = null;
    }
}
