package backend;
import java.io.*;
import java.util.*;


/*
 * RUZZLE BOARD POSITIONS:
 *  0  1  2  3
 *  4  5  6  7
 *  8  9 10 11
 * 12 13 14 15
 */


public class Board {

	private static HashMap<String,HashSet<int[]>> wordsToPath;
	private static char[] converter;
	private static MyTrie Dictionary;
	private static HashMap<Integer,int[]> validMove;
	private static void defineBoard()
	{
		validMove = new HashMap<Integer,int[]>();
		validMove.put(0, new int[] {1,4,5});
		validMove.put(1, new int[] {0,2,4,5,6});
		validMove.put(2, new int[] {1,3,5,6,7});
		validMove.put(3, new int[] {2,6,7});
		validMove.put(4, new int[] {0,1,5,8,9});
		validMove.put(5, new int[] {0,1,2,4,6,8,9,10});
		validMove.put(6, new int[] {1,2,3,5,7,9,10,11});
		validMove.put(7, new int[] {2,3,6,10,11});
		validMove.put(8, new int[] {4,5,9,12,13});
		validMove.put(9, new int[] {4,5,6,8,10,12,13,14});
		validMove.put(10, new int[] {5,6,7,9,11,13,14,15});
		validMove.put(11, new int[] {6,7,10,14,15});
		validMove.put(12, new int[] {8,9,13});
		validMove.put(13, new int[] {8,9,10,12,14});
		validMove.put(14, new int[] {9,10,11,13,15});
		validMove.put(15, new int[] {10,11,14});
	}

	private static boolean isInArray(int[] array, int check)
	{
		for (int i = 0; i < array.length; i++)
		{
			if (array[i] == check)
				return true;
		}
		return false;
	}
	
	private static void findPaths (int[] path, String word)
	{
		int depth=path.length;
		int lastPos = path[depth-1];
	
		for (int nextPos : validMove.get(lastPos)) // searching over all possible valid moves
		{
			if (!isInArray(path,nextPos)) //can't pass over an already marked tile!
			{
				// creates a word (previous word + new tile) and its path (previus path + new position)
				String nextWord = word+converter[nextPos];
				int[] nextPath = new int[depth + 1];
				for (int i = 0; i < depth; i++)
						nextPath[i] = path[i];
				nextPath[depth] = nextPos;			

				// Ask if this word is a dictionary word, if yes -> populate map word->paths
				if (Dictionary.isaWord(nextWord))
				{
					if (!wordsToPath.containsKey(nextWord))
						wordsToPath.put(nextWord,new HashSet<int[]>());
					wordsToPath.get(nextWord).add(nextPath);
				}
				
				// Ask if this word is a prefix, if yes -> continue recursion
				if (Dictionary.isaPrefix(nextWord))
				{
					findPaths(nextPath,nextWord);
				}	
			}
		}
	}
	
	public static void printPaths(Set<int[]> paths)
	{
		for (int[] path : paths)
		{
			for (int move : path)
			{
				System.out.print(move+"->");
			}
			System.out.println("");
		}
	}
	
	public static int scrabblePoints (String word)
	{
		int points = 0;
		for (int i = 0; i < word.length(); i++)
		{
			switch (word.charAt(i))
			{
			case 'a':
			case 'e':
			case 'i':
			case 'o':
				points+=1; break;
			case 'c':
			case 'r':
			case 's':
			case 't':
				points+=2; break;
			case 'l':
			case 'm':
			case 'n':
			case 'u':
				points+=3; break;
			case 'b':
			case 'd':
			case 'f':
			case 'p':
			case 'v':
				points+=5; break;
			case 'g':
			case 'h':
			case 'z':
				points+=8; break;
			case 'q':
				points+=10; break;
			}
			
		}
		return points;
	}
	
	public static void main(String[] args) throws IOException {
		
		if (args.length != 2)
		{
			System.out.println("Wrong argument count, please use: ");
			System.out.println(args[0]+ " dictionaryfile");
			System.exit(0);
		}
		
		System.out.println("Defining board");		
		defineBoard();

		System.out.println("Loading dictionary");		
		Dictionary = new MyTrie ("args[1]");
		

		System.out.println("Initialization completed - Please insert conversion string");
		System.out.println("i.e. all 16 charachter on the board, as they are normally read");
		
		while (true)
		{
		
			BufferedReader brStdin = new BufferedReader(new InputStreamReader(System.in) );
			String board = brStdin.readLine();

			if (board.length() != 16)
			{
				System.out.println("Stringa non valida");
				continue;
			}
			
			System.out.println("Starting search - String board is " + board);
			converter = new char[16] ;
			for (int i = 0; i < 16 ; i++)
			{
				converter[i] = board.charAt(i);
			}

			wordsToPath = new HashMap<String,HashSet<int[]>>();

			for (int i = 0 ; i<16; i++)
			{	
				int path[] = new int[] {i};
				String word = ""+converter[i];
				// Starting recursion
				findPaths(path,word);
				System.out.println("Phase "+ i +" done");
			}
			
			String[] sortedWords = new String[wordsToPath.size()];
			wordsToPath.keySet().toArray(sortedWords);
			
		    Arrays.sort(sortedWords, new Comparator<String>() {
		        public int compare(String s1, String s2) {
		          int c = scrabblePoints(s1) - scrabblePoints(s2);
		          return c;
		        }
		      });

		    
		    for (String word : sortedWords)
			{
				System.out.print(word +": ");		
				printPaths(wordsToPath.get(word));
			}
			
			System.out.println("\n\nPlease insert new conversion string\n");
			wordsToPath = null;

		}
	}
}
