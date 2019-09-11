
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class MainApp {
	private static BinaryTree<String> defaultTree = new BinaryTree<String>();
	private static ArrayList<String> list = new ArrayList<String>();
	
	public static void main(String[] args) throws ClassNotFoundException {
		Scanner in = new Scanner(System.in);
		String input = "";
		
		createMenu();
				
				while(true) {
					System.out.println("\n\nWould you like to");
					System.out.println("\t1. Load the default tree");
					System.out.println("\t2. Load a custom tree");
					System.out.println("\t3. Save the current tree");
					System.out.println("\t4. Quit");
					
					input = in.nextLine();
					
					if(input.equals("4")) break;
					
					switch(input) {
					case "1":
						loadDefaultTree(defaultTree);
						break;
					case "2":
						defaultTree = deserialiseTree();
						break;
					case "3":
						serialiseTree(defaultTree);
						break;
						default:
							loadDefaultTree(defaultTree);
							break;
					}
					
					System.out.println("Some Stats about the Tree :\n");
					getInfo(defaultTree);
					System.out.println("\n");
					
					while(true) {
						System.out.println("\nStarting traversals\n");
						askQuestions(defaultTree.getRootNode());
						
						System.out.println("\nWould you like to Play Again?\n");
						input = in.nextLine();
						
						if(input.equals("y")) continue;
						if(!input.equals("y")) break;
					}
				}
				
				
		

	}
	
	public static void createMenu() {
		System.out.println("****************************************");
		System.out.println("*                                      *");
		System.out.println("*      BinaryTree Guessing Game        *");
		System.out.println("*                                      *");
		System.out.println("****************************************\n\n");
		
		System.out.println("\t<y for yes n for no>\n\n\n");
	}
	
	public static void loadDefaultTree(BinaryTree<String> tree) {
		//Custom values for the Default Tree
		String[] customArr =  {"Are you thinking of an Animal?", "Is it a Vertebrae?", 
				"Are you thinking of a Plant?", "Is it a Mammal?", "Is it an Insect?", 
				"Does it have Seeds?", "You're thinking of Humans?", 
				"Do they lay eggs?", "Are you thinking of a Bird", 
				"Does it Fly?", "Is it an Arachnid?", "Does it have flowers?", 
				"Does it have Roots?", 
				"Are you thinking of Homo Sapiens?", "Germs... you're thinking of germs...", 
				"I guess : Platypus!", null, "I guess : Crow!", null, "I guess : Bee!", null, 
				"I guess : Wolfspider!", null, "I guess : Conifers!", null, "I guess : Ferns!", 
				null, "How self centered... You're thinking of You..", null, "I guess : Bacteria!", 
				null};
		
		BinaryNode<String> root = new BinaryNode<String>();
		
		
		//Add them to a list
			//The reason I use a list to make things much more comfortable
			//The array could have been used directly
		for(int i = 0; i < customArr.length; i++) {
			list.add(customArr[i]);
		}
		
		//Insert the custom data into the tree 
		root = insertLevelOrder(list, root, 0);
		tree.setRootNode(root);
	}
	
	public static BinaryNode<String> insertLevelOrder(ArrayList<String> arr, BinaryNode<String> rootNode, int i) {
		//The only time this was used was to create the default tree
			//rather than populating the tree manually by going through each and every node
			//and setting their data, this algorithm is used instead.
		
		//It is recursive - so the base case
		if(i < arr.size()) {
			BinaryNode<String> temp = new BinaryNode<String>(list.get(i));
			rootNode = temp;
			
			rootNode.setLeftChild(insertLevelOrder(arr, (BinaryNode<String>)rootNode.getLeftChild(), 2*i+1));
			rootNode.setRightChild(insertLevelOrder(arr, (BinaryNode<String>)rootNode.getRightChild(), 2*i+2));
		}
		
		return rootNode;
	}
	
	public static void askQuestions(BinaryNodeInterface<String> node) {
		
		//pre-order Traversal is used to ask the questions rather than in-order
			//just to test out how a different algorithm than the one implemented in
			//the interface would work. In addition, having it implemented here,
			//helped me make the code feel a bit more flexible when "asking questions"
			//rather than calling it the traversal algorithm implemented in the interface
			//and modifying it to return a value
		
		Scanner in = new Scanner(System.in);
		String input = "";
		
		if(node.getData() != null) {
			//First print out the nodes data
			System.out.println(node.getData());
			input = in.nextLine();
			
			if(input.equals("y") && !node.isLeaf()) {
				//if the user answers yes, we traverse to the left
				askQuestions(node.getLeftChild());
				
			} else if(!input.equals("y") && !node.isLeaf()) {
				//if the user answers no, we traverse to the right
				askQuestions(node.getRightChild());
				
			} else if(input.equals("y") && node.isLeaf()) {
				//once we reach a leaf node and the user answers Yes,
					//we know we've guessed correctly
				System.out.println("I am correct");
				
			} else if(!input.equals("y") && node.isLeaf()) {
				//if we're at a leaf node and the input is not yes
				System.out.println("My guess failed...");
				System.out.println("Would you like to insert a new path?");
				input = in.nextLine();
				
				if(input.equals("y")) {
					insertNewPath(node);
				}
			}
		} else {
			//There is also the case where there are no more paths to be handled.
			System.out.println("I'm out of guesses\n");
			System.out.println("Would you like to insert a new path?");
			input = in.nextLine();
			
			if(input.equals("y")) {
				insertNewPath(node);
			}
		}
	}
	
	public static void getInfo(BinaryTree<String> tree)
	{
		//As in the lecture notes
		if (tree.isEmpty()) {
			System.out.println("The tree is empty");
			return;
		}
		
		System.out.println("Root of tree is " + tree.getRootData());
		System.out.println("Height of tree is " + tree.getHeight());
		System.out.println("No. of nodes in tree is " + tree.getNumberOfNodes());
	}
	
	public static void insertNewPath(BinaryNodeInterface<String> node) {
		
		//The benefit of using the interface here and calling the node.setData() 
			//is that there is no need to return a node with left and right data
			//and combine it in different parts of the code
		//using the interface and the methods defined within it will directly change the node
		//in the tree
		//Hence there is no return statement
		
		
		String input = "";
		Scanner in = new Scanner(System.in);
		
		//The node passed in as a parameter will be null
		System.out.println("Distinguishing question?");
		input = in.nextLine();
		node.setData(input);
		
		//We set it's left
		System.out.println("Left Node Response to 'yes'");
		input = in.nextLine();
		node.setLeftChild(new BinaryNode<String>(input));
		
		//Then set it's right
		System.out.println("Answer to No");
		input = in.nextLine();
		node.setRightChild(new BinaryNode<String>(input));
	}
	
	public static boolean serialiseTree(BinaryTree<String> tree) {
		//Pretty simple and straightforward
		System.out.println("Enter the name of the file to save");
		String filePath = "";
		Scanner in = new Scanner(System.in);
		filePath = in.nextLine();
		
		try {
			FileOutputStream fsOut = new FileOutputStream(filePath);
			ObjectOutputStream osOut = new ObjectOutputStream(fsOut);
			
			osOut.writeObject(tree);
			
			osOut.close();
			fsOut.close();
			
			System.out.println("Tree has been saved");
			return true;
			
		} catch(IOException ioex) {
			System.out.println(ioex);
			return false;
			
		}
	}
	
	@SuppressWarnings("unchecked")
	public static BinaryTree<String> deserialiseTree() {
		System.out.println("Enter the path to the file to load");
		String filePath = "";
		Scanner in = new Scanner(System.in);
		filePath = in.nextLine();
		
		BinaryTree<String> serialTree = new BinaryTree<String>();
		
		try {
			FileInputStream fsIn = new FileInputStream(filePath);
			ObjectInputStream osIn = new ObjectInputStream(fsIn);
			
			//Figuring out the need to cast was the only "tricky" bit
			serialTree = (BinaryTree<String>) osIn.readObject();
			
			osIn.close();
			fsIn.close();
			
			System.out.println("tree has been loaded");
			return serialTree;
			
		} catch(IOException ex) {
			System.out.println("Error: " + ex);
			return serialTree;
			
		} catch(ClassNotFoundException ex) {
			System.out.println("Error: " + ex);
			return serialTree;
			
		}
	}

}
