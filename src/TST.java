import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;

public class TST {
    HashMap stopsMap;
    public static Node rootNode;
    public ArrayList<String> list;

    public TST (){
        rootNode = null;
    }

    public static boolean isEmpty(){
        return rootNode == null;
    }

    public void  makeEmpty(){
        rootNode = null;
    }

    public void insert(String word) {
        rootNode = insert(rootNode, word.toCharArray(), 0);
    }

    public Node insert(Node rootNode, char[] word, int pointer) {
        if (rootNode == null){
            rootNode = new Node(word[pointer]);
        }
        if (word[pointer] < rootNode.nodeCharacter) {
            rootNode.left = insert(rootNode.left, word, pointer);
        } else if (word[pointer] > rootNode.nodeCharacter) {
            rootNode.right = insert(rootNode.right, word, pointer);
        } else {
            if (pointer + 1 < word.length) {
                rootNode.middle = insert(rootNode.middle, word, pointer + 1);
            } else {
                rootNode.endOfWord = true;
            }
        }
        return rootNode;
    }

    public void delete(String word){
        delete(rootNode, word.toCharArray(), 0);
    }

    public void delete(Node rootNode, char[] word, int pointer){
        if (rootNode == null){
            return;
        }
        if (word[pointer] < rootNode.nodeCharacter){
            delete(rootNode.left, word, pointer);
        } else if (word[pointer] > rootNode.nodeCharacter){
            delete(rootNode.right, word, pointer);
        } else {
            if (rootNode.endOfWord && pointer == word.length -1) {
                rootNode.endOfWord = false;
            } else if (pointer + 1 < word.length) {
                delete(rootNode.middle, word, pointer + 1);
            }
        }
    }

    public String search(String word){
        return search(rootNode, word.toCharArray(), 0);
    }

    private String search(Node rootNode, char[] word, int pointer) {
        if (rootNode == null) {
            return "";
        }
        if (word[pointer] < rootNode.nodeCharacter) {
            return search(rootNode.left, word, pointer);
        } else if (word[pointer] > rootNode.nodeCharacter) {
            return search(rootNode.right, word, pointer);
        } else {
            if (rootNode.endOfWord && pointer == word.length - 1){
                return String.valueOf(word);
            } else if (pointer == word.length - 1) {
                char nextCharacter = rootNode.middle.nodeCharacter;
                char[] restOfWord = new char[word.length + 1];
                for (int i = 0; i < word.length; i++) {
                    restOfWord[i] = word[i];
                }
                restOfWord[restOfWord.length - 1] = nextCharacter;
                return search(rootNode.middle, restOfWord, pointer + 1);
            } else {
                return search(rootNode.middle, word, pointer + 1);
            }
        }
    }

    public String toString(){
        list = new ArrayList<String>();
        traverse(rootNode, "");
        return "\nTernary Search Tree : " + list;
    }

    public void traverse(Node rootNode, String str) {
        if (rootNode != null) {
            traverse(rootNode.left, str);
            str = str + rootNode.nodeCharacter;
            if(rootNode.endOfWord){
                list.add(str);
            }
            traverse(rootNode.middle, str);
            str = str.substring(0,str.length() - 1);

            traverse(rootNode.right, str);
        }
    }
}