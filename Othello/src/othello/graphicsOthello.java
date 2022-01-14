package othello;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.Arrays;

//Benjamin Bliss
//January 21, 2019
//A program that runs the game othello

public class graphicsOthello{
	//global frame variables for game screen and title screen
	static JFrame frame = new JFrame("Othello");
	static JFrame titleFrame = new JFrame("Othello");
	//global button array for the game board
	static JButton[][] buttonArray = new JButton[8][8];
	//text fields to show sho's turn it is, enter in P1 and P2 names, and show how many pieces each player has
	static JTextField PTurnTF = new JTextField(1);
	static JTextField P1NameTF = new JTextField(5);
	static JTextField P2NameTF = new JTextField(5);
	static JTextField P1Pieces = new JTextField(2);
	static JTextField P2Pieces = new JTextField(2);
	
	//buttons to select the game type
	static JButton btnPVP = new JButton("Player vs Player");
	static JButton btnPvCPU = new JButton("Player vs CPU");
	//text area to say if turn was unplayable
	static JTextArea description = new JTextArea();

	//2d array for the gameboard
	static int[][] gameBoard = new int[8][8];
	//1d array to carry winner stats
	static int[]winner = new int[4];
	//boolean flag to see if tileFlip has been performed, if playin against CPU, playing on hard mode, and if the game is over
	static boolean flag = false;
	static boolean CPU = false;
	static boolean hard =false;
	static boolean gameOver = false;
	//2 ints keep track of how many playable places there are and whos turn it is
	static int playableTiles = 0;
	static int player = 1;
		
	
	/*
	* Method Name: writeOutScores
	* Author: Benjamin Bliss
	* Creation Date; November 21, 2018
	* Modified Date: January 18, 2019
	* Description: saves the score of a game to a file
	* @Parameters: String filename, String PW, String PL
	* @Return Value: none
	* Data Type: void
	* Dependencies: None
	* Throws/Exceptions: Print exception
	*/
	public static void writeOutScore(String filename, String PW, String PL){		
		try{
			//initializing winScores as a sting array then setting it to all the previous game scores
			String winScores[]= {};
			winScores=copyArrayStr(readInScores(filename),winScores);
			//print writer to write out to a file
			PrintWriter outputfile = new PrintWriter(new BufferedWriter(new FileWriter(filename)));
			//printing out everything read in
			for(int i = 0; i < winScores.length; i++) {
				outputfile.println(winScores[i]);
			}
			//printing out the most recent game score
			outputfile.println(PW+" "+winner[1]+" - "+winner[3]+" "+PL);
			//closing file
			outputfile.close();
		}
		catch(Exception e){
			System.out.println(e);
		}
	}//end of method
	
	/*
	* Method Name: readInScores
	* Author: Benjamin Bliss
	* Creation Date; November 21, 2018
	* Modified Date: January 18, 2019
	* Description: reads in all the previous game scores into a string array
	* @Parameters: String filename
	* @Return Value: String[]winScores
	* Data Type: String[]
	* Dependencies: None
	* Throws/Exceptions: Print exception
	*/
	public static String[] readInScores(String filename) {
		try {
			//Sting array to save the win scores to
			String[] winScores;
			//bufferedReader to get access to the file
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
			//length to find amount of lines in the file
			int length= 0;
			//while loop to read and count each line
			while(in.ready()==true) {
				in.readLine();
				length++;
			}
			//setting the length of winScores
			winScores = new String[length];
			in.close();
			//resetting in
			in = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
			//saving each line to winScores
			for(int i = 0; i < length; i++) {
				winScores[i] = in.readLine();
			}
			in.close();
			
			return winScores;
		}
		
		catch(Exception e) {
			System.out.println(e);
			return null;
		}
	}
	
	/*
	* Method Name: printFormatArray
	* Author: Benjamin Bliss
	* Creation Date; December 11, 2018
	* Modified Date: December 11, 2018
	* Description: prints out the 2D array with proper headings(for testing purposes)
	* @Parameters: int[][]array
	* @Return Value: none
	* Data Type: void
	* Dependencies: None
	* Throws/Exceptions: None
	*/
	public static void printFormatArray(int[][]array) {
		//top heading
		System.out.println("    1 2 3 4 5 6 7 8 \n");
		for(int i = 0; i < array.length; i++) {
			//side heading
			System.out.print((i+1)+"   ");
			
			for(int j = 0; j < array[i].length; j++) {
				System.out.print(array[i][j]+" ");
			}//end of nested for
			System.out.println();
		}//end of base for
	}//end of method
	
	/*
	* Method Name: copy2DArray
	* Author: Benjamin Bliss
	* Creation Date; December 11, 2018
	* Modified Date: December 11, 2018
	* Description: copys a 2D array to another
	* @Parameters: int[][]copyFromArray, int[][]copyToArray
	* @Return Value: int[][] copyToArray
	* Data Type: int[][]
	* Dependencies: None
	* Throws/Exceptions: None
	*/
	public static int[][] copy2DArray(int[][]copyFromArray, int[][]copyToArray) {
		//resetting the size of the copy to array to that of copyFromArray
		copyToArray = new int[copyFromArray.length][copyFromArray[0].length];
		for(int i = 0; i < copyToArray.length; i++) {
			for(int j = 0; j <copyToArray[0].length;j++) {
				copyToArray[i][j] = copyFromArray[i][j];
			}//end of nested for
		}//end of base for
		
		return copyToArray;
	}//end of method
	
	/*
	* Method Name: copyArray
	* Author: Benjamin Bliss
	* Creation Date; December 11, 2018
	* Modified Date: December 11, 2018
	* Description: copys a 1D array to another
	* @Parameters: int[]copyFromArray, int[]copyToArray
	* @Return Value: int[] copyToArray
	* Data Type: int[]
	* Dependencies: None
	* Throws/Exceptions: None
	*/
	public static int[] copyArray(int[]copyFromArray, int[]copyToArray) {
		//resetting the size of the copy to array to that of copyFromArray
		copyToArray = new int[copyFromArray.length];
		for (int i = 0; i< copyFromArray.length; i++) {
			copyToArray[i] = copyFromArray[i];
		}//end of for
		return copyToArray;
	}//end of method
	
	/*
	* Method Name: copyArrayStr
	* Author: Benjamin Bliss
	* Creation Date: December 11, 2018
	* Modified Date: December 11, 2018
	* Description: copys a 1D array to another but String
	* @Parameters: String[]copyFromArray, String[]copyToArray
	* @Return Value: String[] copyToArray
	* Data Type: String[]
	* Dependencies: None
	* Throws/Exceptions: None
	*/
	public static String[] copyArrayStr(String[]copyFromArray, String[]copyToArray) {
		//resetting the size of the copy to array to that of copyFromArray
		copyToArray = new String[copyFromArray.length];
		for (int i = 0; i< copyFromArray.length; i++) {
			copyToArray[i] = copyFromArray[i];
		}
		return copyToArray;
	}//end of method
	
	/*
	* Method Name: changeIndex
	* Author: Benjamin Bliss
	* Creation Date: January 13, 2019
	* Modified Date: January 13, 2019
	* Description: increases the values of a 2 element array by x and y
	* @Parameters: int[]array, int y, int x
	* @Return Value: int[]array
	* Data Type: int[]
	* Dependencies: None
	* Throws/Exceptions: None
	*/
	public static int[] changeIndex(int[]array, int y, int x) {
		
		array[0] += y;
		array[1] += x;
		
		return array;
	}//end of method
	
	/*
	* Method Name: check
	* Author: Benjamin Bliss
	* Creation Date: January 6, 2019
	* Modified Date: January 7, 2019
	* Description: used as the boolean check in a for loop, checks that the for loop 
				   doesn't go past either out of bounds or past index2
	* @Parameters: int y, int x, int[][]array, int[]i, int[]index2, boolean TF
	* @Return Value: true/false
	* Data Type: boolean
	* Dependencies: None
	* Throws/Exceptions: None
	*/
	public static boolean check(int y, int x, int[][]array, int[]i, int[]index2, boolean TF) {
		//creating v0 and v1 to represent the max/min values for x and y when checking
		int v0=0;
		int v1=0;
		//if checking for index2, setting v0 and v1 to the values of index2
		if(TF ==true) {
			v0 = index2[0];
			v1 = index2[1];
		}
		//if checking for the array borders, setting v0 and v1 to either array.length-1 or 0
		if((x==1 ||x==0)&&TF ==false) {
			v1= array.length-1;;
		}
		else if(x==-1&&TF == false) {
			v1= 0;
		}
		if((y==1||y==0)&&TF ==false) {
			v0= array.length-1;
		}
		else if(y==-1&&TF==false) {
			v0= 0;
		}
		//check for right border
		if(y==0 && x==1) {
			if(i[0]<=v0 && i[1]<=v1) {
				return true;
			}
		}
		//check for left border
		else if(y==0 && x==-1) {
			if(i[0]<=v0 && i[1]>=v1) {
				return true;
			}
		}
		//check for bottom border
		else if(y==1 && x==0) {
			if(i[0]<=v0 && i[1]<=v1) {
				return true;
			}
		}
		//check for top border
		else if(y==-1 && x==0) {
			if(i[0]>=v0 && i[1]<=v1) {
				return true;
			}
		}
		//check for bottom and right border
		else if(y==1 && x==1) {
			if(i[0]<=v0 && i[1]<=v1) {
				return true;
			}
		}
		//check for bottom and left border
		else if(y==1 && x==-1) {
			if(i[0]<=v0 && i[1]>=v1) {
				return true;
			}
		}
		//check for top and left border
		else if(y==-1 && x==-1) {
			if(i[0]>=v0 && i[1]>=v1) {
				return true;
			}
		}
		//check for top and right border
		else if(y==-1 && x==1) {
			if(i[0]>=v0 && i[1]<=v1) {
				return true;
			}
		}
		
		return false;
	}//end of method
	/*
	* Method Name: tileFlip
	* Author: Benjamin Bliss
	* Creation Date: January 2, 2018
	* Modified Date: January 9, 2018
	* Description: flips all the tiles from a placed piece in a specific direction
	* @Parameters: int[][]array, int[]index, int y, int x, int yourtTile
	* @Return Value: int[][]array
	* Data Type: int[]
	* Dependencies: None
	* Throws/Exceptions: None
	*/
	public static int[][]tileFlip(int[][]array,int[]index,int y, int x, int yourTile){
		//initializing the enemies tile
		int NMETile = 0;
		//initializing i for the for loop
		int[] i = {};
		//boolean to check if another one of your tiles is found
		boolean valid =false;
		//int z the only point is to have a case statement 
		int z = 0;
		//second index to save where one of your connecting tiles are
		int[] index2= {0,0};
		
		//setting NMETile to what yourTile isnt
		if(yourTile == 1) {
			NMETile = 2;
		}
		
		if(yourTile == 2) {
			NMETile = 1;
		}
		//a try in case out of bounds exception
		try {
			int b = 0;
			//this if checks if the piece to the right is the enemie's piece
			if(array[index[0]+y][index[1]+x] ==NMETile) {
				//setting up a switch so later it can force itself out of a for loop
				switch(z) {
				case 0:
					//for loop to go through a row of the 2D array starting at the
					//piece's position
					for (i = copyArray(index,i); check(y,x,array,i,index2,false); i=changeIndex(i,y,x)) {
						
						if(array[i[0]][i[1]]== 0 && b>0) {
							break;		
						}
						b++;
						//checking if the next piece is your piece at each index
						if(array[i[0]][i[1]]== yourTile) {
							//saving the index the found 2
							index2[0]= i[0];
							index2[1]= i[1];
							valid = true;
							//force leaving the for loop after the index found is yourTile
							break;
						}//end of if
					
					}//end of for

					//setting all the values between the place pieces and the found another your piece to your piece
					if (valid == true) {
						flag = true;
						for(i = copyArray(index,i); check(y,x,array,i,index2,true); i=changeIndex(i,y,x)) {
							array[i[0]][i[1]] = yourTile;
						
						}//end of for
					}//end of if valid
					
				}//end of switch
			}//end of nested if
			//resetting valid to false
			valid = false;
		}
		catch(Exception e){}	
		return array;
	}
	
	/*
	* Method Name: checkPlayable
	* Author: Benjamin Bliss
	* Creation Date; January 9, 2018
	* Modified Date: January 12, 2018
	* Description: checks to see if a player has any playable places
	* @Parameters: int[][]array, int player
	* @Return Value: true/false
	* Data Type: boolean
	* Dependencies: None
	* Throws/Exceptions: None
	*/
	public static boolean checkPlayable(int[][]array, int player) {
		
		int [][]copyArray = {{}};
		
		int[]index= {0,0};
		playableTiles = 0;
		
		//runs flip all on each piece to see if it can be played and counts the number of playable places
		for(int i = 0; i < array.length; i++) {
			for(int j = 0; j < array[0].length; j++) {
				if(array[i][j] == 0) {
					
					index[0]=i;
					index[1]=j;
					flag = false;
					
					copyArray =copy2DArray(array,copyArray);
					
					copyArray = flipAll(copyArray,player,index);
					if(flag == true) {
						playableTiles++;
					}//end of if	
				}					
			}//end of nested for
		}//end of base for
		if(playableTiles==0) {
			return false;
		}
		else {
			return true;
		}
		
	}
	
	/*
	* Method Name: flipAll
	* Author: Benjamin Bliss
	* Creation Date; January 9, 2018
	* Modified Date: January 9, 2018
	* Description: runs the tile flip metho
	* @Parameters: int[][]array, int player, int[]index
	* @Return Value: int[][]array
	* Data Type: int[][]
	* Dependencies: None
	* Throws/Exceptions: None
	*/
	public static int[][]flipAll(int[][]array, int player, int[]index) {
		//setting the index to 0 after each time
		flag = false;		
		array = tileFlip(array,index,0,1,player);
		array[index[0]][index[1]]=0;
		array = tileFlip(array,index,0,-1,player);
		array[index[0]][index[1]]=0;
		array = tileFlip(array,index,1,0,player);
		array[index[0]][index[1]]=0;
		array = tileFlip(array,index,-1,0,player);
		array[index[0]][index[1]]=0;
		array = tileFlip(array,index,1,1,player);
		array[index[0]][index[1]]=0;
		array = tileFlip(array,index,-1,1,player);
		array[index[0]][index[1]]=0;
		array = tileFlip(array,index,1,-1,player);
		array[index[0]][index[1]]=0;
		array = tileFlip(array,index,-1,-1,player);
		if(flag == true) {
			array[index[0]][index[1]]=player;
		}
		
		
		return array;
	}
	
	/*
	* Method Name: windowsLook
	* Author: Benjamin Bliss
	* Creation Date; January 12, 2018
	* Modified Date: January 12, 2018
	* Description: gives aspects in a frame a windows look
	* @Parameters: none
	* @Return Value: none
	* Data Type: void
	* Dependencies: None
	* Throws/Exceptions: None
	*/
	public static void windowsLook() {
		//the following method was copied and used to make the mac window look like windows
		//https://stackoverflow.com/questions/1065691/how-to-set-the-background-color-of-a-jbutton-on-the-mac-os/1066016`````		
		try{
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		}catch(Exception e){
			e.printStackTrace(); 
		}
	}
	/*
	* Method Name: resetBoard
	* Author: Benjamin Bliss
	* Creation Date; January 17, 2019
	* Modified Date: January 17, 2019
	* Description: sets all places to empty, then fill the 4 center places with black and white
	* @Parameters: none
	* @Return Value: none
	* Data Type: void
	* Dependencies: None
	* Throws/Exceptions: none
	*/
	public static void resetBoard() {
		//setting all the pieces to empty
		for(int i = 0; i< gameBoard.length;i++) {
			for(int j = 0;j<gameBoard.length;j++) {
				gameBoard[i][j]=0;
			}
		}
		//setting the center pieces to black and white
		gameBoard[4][4] = 1;
		gameBoard[3][3] = 1;
		gameBoard[3][4] = 2;
		gameBoard[4][3] = 2;
		//updating their colour
		updatePieces();
	}//end of method
	
	
	/*
	* Method Name: foundInArray
	* Author: Benjamin Bliss
	* Creation Date; January 10, 2019
	* Modified Date: January 20, 2019
	* Description: checks to see if a 1D array is found in a 2D array
	* @Parameters: int[]array, int[][]array2
	* @Return Value: true/false
	* Data Type: booleann 
	* Dependencies: None
	* Throws/Exceptions: none
	*/
	public static boolean foundInArray(int[]array,int[][]array2) {
		for(int i = 0; i<array2.length;i++) {
			if(Arrays.equals(array,array2[i])) {
				return true;
			}
		}
		
		return false;	
	}
	
	/*
	* Method Name: checkScore
	* Author: Benjamin Bliss
	* Creation Date; January 10, 2019
	* Modified Date: January 20, 2019
	* Description: checks to see if either player has one and returns each players piece count
	* @Parameters: int[][]array
	* @Return Value: int[]pieceCount
	* Data Type: int[]
	* Dependencies: None
	* Throws/Exceptions: none
	*/
	public static int[] checkScore(int[][]array) {
		int[]pieceCount = new int[3];
		//counting the number of each tile on the board
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j< array[0].length; j++) {
				if (array[i][j] == 0) {
					pieceCount[0]++;					
				}//end of if
				if (array[i][j] == 1) {
					pieceCount[1]++;					
				}//end of if
				if (array[i][j] == 2) {
					pieceCount[2]++;					
				}//end of if
			}//end of nested for
		}//end of base for
		//setting the winner and saving their piece count if there is none of any pieces
		if(pieceCount[0] == 0||pieceCount[1] == 0||pieceCount[2] == 0) {
			if (pieceCount[1]== pieceCount[2]) {
				winner[0]=3;
				winner[1]=pieceCount[1];
				winner[2]=3;
				winner[3]= pieceCount[2];
			}
			if (pieceCount[1] > pieceCount[2]) {
				winner[0]=1;
				winner[1]=pieceCount[1];
				winner[2]=2;
				winner[3]=pieceCount[2];
			}
			if (pieceCount[1] < pieceCount[2]) {
				winner[0]=2;
				winner[1]=pieceCount[2];
				winner[2]=1;
				winner[3]= pieceCount[1];
			}
			gameOver = true;
		}
		return pieceCount;
	}//end of method win check
		
	/*
	* Method Name: updatePieces
	* Author: Benjamin Bliss
	* Creation Date; January 10, 2019
	* Modified Date: January 20, 2019
	* Description: updates the colour for all the pieces and makes the playable ones green
	* @Parameters: none
	* @Return Value: none
	* Data Type: void
	* Dependencies: None
	* Throws/Exceptions: none
	*/
	public static void updatePieces () {
		//setting each piece to their proper colour
		for(int i = 0; i< buttonArray.length; i++) {
			for(int j = 0; j<buttonArray.length;j++) {
				if(gameBoard[i][j] == 1) {
					buttonArray[i][j].setBackground(Color.white);
				}
				else if(gameBoard[i][j] == 2) {
					buttonArray[i][j].setBackground(Color.black);
				}
				else if(gameBoard[i][j] == 0) {
					buttonArray[i][j].setBackground(Color.lightGray);
				}
			}//end of nested for			
		}//end of base for
		if(checkPlayable(gameBoard, player) == true) {
			
			int[][]copyArray = {{}};
			int[]index= {0,0};
			
			for(int i = 0; i < gameBoard.length; i++) {
				for(int j = 0; j < gameBoard[0].length; j++) {
					if(gameBoard[i][j] == 0) {
						
						index[0]=i;
						index[1]=j;
						flag = false;
						
						copyArray =copy2DArray(gameBoard,copyArray);
						//if the piece can be played it will be turned green
						copyArray = flipAll(copyArray,player,index);
						if(flag == true) {
							buttonArray[i][j].setBackground(Color.green);;
							
						}//end of nested if	
					}//end of base loop				
				}//end of nested for				
			}//end of base for
		}//end of check playable false
	}//end of method
	
	/*
	* Method Name: placePiece
	* Author: Benjamin Bliss
	* Creation Date; January 10, 2019
	* Modified Date: January 20, 2019
	* Description: places a piece of a selected index
	* @Parameters: ActionEvent e
	* @Return Value: none
	* Data Type: void
	* Dependencies: None
	* Throws/Exceptions: none
	*/
	public static void placePiece(ActionEvent e) {
		int[]index = new int[2];
		//checking playable
		if(checkPlayable(gameBoard,player) == true) {
			//getting button index
			for(int i = 0; i< buttonArray.length; i++) {
				for(int j = 0; j<buttonArray.length;j++) {
					if(e.getSource() == buttonArray[i][j]) {
						index[0] = i;
						index[1] = j;
					}
				}//end of nested for
			}//end of base for
			//checking if index is a 0
			if(gameBoard[index[0]][index[1]] ==0) {
					//placing the piece on the board
					gameBoard = flipAll(gameBoard, player, index);
					//printing out to console so past turn can be seen(for testing)
					printFormatArray(gameBoard);
					//changes turn if piece was successfully placed
					if(flag == true) {
						if(player == 1) {
							player = 2;
							PTurnTF.setText(P2NameTF.getText()+"'s turn");
						}//end of if
						else if(player==2) {
							player = 1;
							PTurnTF.setText(P1NameTF.getText()+"'s turn");
						}//end of else if
						description.setText("");
					}//end of if
			}//end of check for 0			
			//checks for 0	
			flag = false;
			updatePieces();
		}//end of check playable
		//checking if not playable and switching player if so
		if(checkPlayable(gameBoard,player) == false) {
			description.setText("Player "+player+" has no \n playable places");
			
			if(player == 1) {
				player = 2;
			}//end of if
			else if(player==2) {
				player = 1;
			}//end of else if
			updatePieces();
		}
		//updating each player's score
		P1Pieces.setText(""+checkScore(gameBoard)[1]);
		P2Pieces.setText(""+checkScore(gameBoard)[2]);
	}//end of method
	
	/*
	* Method Name: CPUTurn
	* Author: Benjamin Bliss
	* Creation Date; January 10, 2019
	* Modified Date: January 20, 2019
	* Description: runs a computer to place a random piece
	* @Parameters: none
	* @Return Value: none
	* Data Type: void
	* Dependencies: None
	* Throws/Exceptions: none
	*/
	public static int[][]CPUTurn(){
		//checking that the game hasn't ended
		if(gameOver==false) {
			//creating an array to hold an index
			int[]index= {0,0};
			//checking that the CPU can play
			if(checkPlayable(gameBoard, player) == true) {
				//creating 2 2D arrays, to hold an array of indexes and a copy of gameBoard
				int[][]playableList = new int[playableTiles][2];
				int[][]copyArray = {{}};
				//initializing an int to hold an index for playableList
				int f = 0;
				//looping through gameBoard and adding the playable indexes to playable list
				for(int i = 0; i < gameBoard.length; i++) {
					for(int j = 0; j < gameBoard[0].length; j++) {
						if(gameBoard[i][j] == 0) {
							
							index[0]=i;
							index[1]=j;
							flag = false;
							
							copyArray =copy2DArray(gameBoard,copyArray);
							
							copyArray = flipAll(copyArray,player,index);
							//adding index to playable list if index is playable
							if(flag == true) {
								playableList[f] = copyArray(index,playableList[f]);
								f++;
								
							}//end of nested if	
						}//end of base loop				
					}//end of nested for				
				}//end of base for
				//activating hard mode
				if(hard) {
					//to check if any of the play tiles can be played
					boolean b = false;
					//2D array to keep indexes for all the must play places
					int[][]play= {
							{0,0},
							{7,7},
							{0,7},
							{7,0}
					};
					//2D array to keep all the places to avoid
					int[][]dontPlay= {
							{1,0},
							{0,1},
							{1,1},
							
							{7,6},
							{6,7},
							{6,6},
							
							{6,0},
							{7,1},
							{6,1},
							
							{0,6},
							{1,6},
							{1,7}
					};
					//boolean to check if it can avoid the dontPlay list or not
					boolean avoidedPlaces= false;
					//for loop to check if any of the indexes in playable list are on the play list
					for(int i = 0; i < playableList.length; i++) {
						if(Arrays.equals(playableList[i],play[0])) {
							index = copyArray(play[0],index);
							b = true;
						}
						else if(Arrays.equals(playableList[i],play[1])) {
							index = copyArray(play[1],index);
							b = true;
						}
						else if(Arrays.equals(playableList[i],play[2])) {
							index = copyArray(play[2],index);
							b = true;
						}
						else if(Arrays.equals(playableList[i],play[3])){
							index = copyArray(play[3],index);
							b = true;
						}
						//checking to see if the current index on playable list is on the dontPlay list
						if(foundInArray(playableList[i],dontPlay)==false) {
							avoidedPlaces=true;
						}

					}
					//only running if no play list indexes were found
					if(b == false) {
						//only chosing a dontPlay index if there is no other place to go
						if(avoidedPlaces == true) {
							do {
								index = copyArray(playableList[(int)(Math.random()*playableTiles)],index);
								System.out.println(index[0]+" "+index[1]);
								//chosing a random index until its not on the dontPlay list
							}while(foundInArray(index,dontPlay)==true);
							
							
						}
						else {
							//if there are no other playable places than on the dontPlay list
							index = copyArray(playableList[(int)(Math.random()*playableTiles)],index);
						}
						
					}
				}
				//running if on easy 
				else {
				index = copyArray(playableList[(int)(Math.random()*playableTiles)],index);
				
				}
				//placing the piece on the chosen index
				gameBoard = flipAll(gameBoard, player, index);
				//printing out to the console to see the past turns
				printFormatArray(gameBoard);
				//switching the player turns
				if(flag == true) {
					if(player == 1) {
						player = 2;
						PTurnTF.setText(P2NameTF.getText()+"'s turn");
					}//end of if
					else if(player==2) {
						player = 1;
						PTurnTF.setText(P1NameTF.getText()+"'s turn");
					}//end of else if
					description.setText("");
				}//end of if
				
			}//end of check playable
			//skipping turn if not playable
			if(checkPlayable(gameBoard,player) == false) {
				description.setText("CPU has no \n playable places");
				
				if(player == 1) {
					player = 2;
				}//end of if
				else if(player==2) {
					player = 1;
				}//end of else if
				updatePieces();
			}
		}
		//opening win screen if game is over
		if(gameOver==true) {
			frame.dispose();
			winScreen();
		}
		updatePieces();
		return gameBoard;
	}//end of method 
	
	//methods for screens
	//-----------------------------------------------------------------------------------------------------------
	
	/*
	* Method Name: titleScreen
	* Author: Benjamin Bliss
	* Creation Date; January 10, 2019
	* Modified Date: January 20, 2019
	* Description: creates a title screen to start the game
	* @Parameters: none
	* @Return Value: none
	* Data Type: void
	* Dependencies: None
	* Throws/Exceptions: none
	*/
	public static void titleScreen() {
		
		//creating a title label of othello and adding it
		JLabel othello = new JLabel("Othello",SwingConstants.CENTER);
		othello.setFont(new Font("Serif",Font.PLAIN,70));
		titleFrame.add(othello,BorderLayout.NORTH);
		//creating new panes and setting their layout
		JPanel pane1 = new JPanel();
		pane1.setVisible(true);
		JPanel pane2 = new JPanel();
		JPanel pane3 = new JPanel();
		JPanel pane4 = new JPanel();
		pane3.setLayout(new BorderLayout());
		pane1.setLayout(new GridLayout(1,2));
		pane2.setLayout(new GridLayout(2,2));
		pane4.setLayout(new GridLayout(2,1));
		//creating labels to show player 1 and 2 name and one for game type
		JLabel P1Namelbl = new JLabel("P1 Name",SwingConstants.CENTER);
		JLabel P2Namelbl = new JLabel("P2 Name",SwingConstants.CENTER);
		JLabel gameTypelbl = new JLabel("",SwingConstants.CENTER);
		
		//new button to open the history screen
		JButton history = new JButton("History");
		titleFrame.add(history,BorderLayout.SOUTH);
		history.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pane1.setVisible(false);
				historyScreen();
				titleFrame.dispose();
			}			
		});
		
		//creating a button to start the game and closing the current window
		JButton start = new JButton("Start");
		start.setFont(new Font("Serif",Font.PLAIN,30));
		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pane3.setVisible(false);
				pane2.setVisible(false);
				start.setVisible(false);
				gameScreen();
				resetBoard();
				titleFrame.dispose();
			}
		});
		//creating buttons to set difficulty
		JButton easybtn = new JButton("Easy");
		JButton hardbtn = new JButton("Hard");
		easybtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hard = false;
				start.setVisible(true);
			}
		});
		hardbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hard = true;
				start.setVisible(true);
			}
		});
		
		//adding a button to close a pane and open a new one for player vs player
		btnPVP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//hiding panes from previous window and showing new ones
				pane3.setVisible(true);
				pane2.setVisible(true);
				start.setVisible(true);
				history.setVisible(false);
				pane1.setVisible(false);
				
				pane3.add(gameTypelbl,BorderLayout.NORTH);
				pane2.add(P1Namelbl);
				pane2.add(P2Namelbl);
				pane2.add(P1NameTF);
				pane2.add(P2NameTF);
				windowsLook();
				gameTypelbl.setText("Player vs Player");
				pane3.add(pane2,BorderLayout.CENTER);
				titleFrame.add(pane3,BorderLayout.CENTER);
				titleFrame.add(start,BorderLayout.SOUTH);
				
			}
		});
		//adding a button to close a pane and open a new one for player vs CPU
		btnPvCPU.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pane3.setVisible(true);
				pane2.setVisible(true);
				pane1.setVisible(false);
				start.setVisible(false);
				history.setVisible(false);
				
				P2NameTF.setText("CPU");
				gameTypelbl.setText("Player vs CPU");	
				pane3.add(gameTypelbl,BorderLayout.NORTH);
				pane3.add(pane2,BorderLayout.CENTER);
				
				pane2.add(P1Namelbl);
				pane2.add(P1NameTF);
				pane4.add(easybtn);
				pane4.add(hardbtn);
				pane3.add(pane4,BorderLayout.EAST);
				CPU = true;
				titleFrame.add(pane3,BorderLayout.CENTER);
				titleFrame.add(start,BorderLayout.SOUTH);
			}
		});
		pane1.add(btnPVP);	
		pane1.add(btnPvCPU);
		titleFrame.add(pane1);
		titleFrame.setSize(300,240);
		titleFrame.setVisible(true);
		titleFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}//end of titleScreen
	
	/*
	* Method Name: historyScreen
	* Author: Benjamin Bliss
	* Creation Date; January 15, 2019
	* Modified Date: January 20, 2019
	* Description: creates a new window to show previous games
	* @Parameters: none
	* @Return Value: none
	* Data Type: void
	* Dependencies: None
	* Throws/Exceptions: none
	*/
	public static void historyScreen() {
		//creating new frame and pane
		JFrame frame = new JFrame();
		JPanel pane = new JPanel();;
		pane.setVisible(true);
		String[] s = {};
		//reading in the scores
		s = copyArrayStr(readInScores("Game Scores.txt"),s);
		//button to go back to the title screen
		JButton back = new JButton("back");
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pane.setVisible(false);
				frame.dispose();
				titleScreen();
			}
		});
		//creating a text area and printing out all the game scores
		JTextArea scores = new JTextArea();
		scores.setEditable(false);
		for(int i = 0; i < s.length; i++) {
			scores.append(s[i]+"\n");
		}
		
		pane.add(scores);
		pane.add(back,BorderLayout.SOUTH);
		//setting up the frame
		frame.add(pane);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	/*
	* Method Name: gameScreen
	* Author: Benjamin Bliss
	* Creation Date; January 10, 2019
	* Modified Date: January 20, 2019
	* Description: shows the main game screen where the game is played
	* @Parameters: none
	* @Return Value: none
	* Data Type: void
	* Dependencies: None
	* Throws/Exceptions: none
	*/
	public static void gameScreen() {		
		//setting all the places for the start of the game
		gameBoard[4][4] = 1;
		gameBoard[3][3] = 1;
		gameBoard[3][4] = 2;
		gameBoard[4][3] = 2;
		
		//new pane for where everything is added on to
		JPanel mainPane =new JPanel();
		mainPane.setVisible(true);
		mainPane.setLayout(new GridBagLayout());
		
		//creating grid bag constraints for a layout manager
		GridBagConstraints c = new GridBagConstraints();
		
		//new title for the game
		JLabel othello = new JLabel("Othello");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 8;
		c.gridx = 0;
		c.gridy = 0;
		mainPane.add(othello, c);
		
		//giving the frame a windows look
		windowsLook();
		//new button pane to add an array of buttons to for the game board
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new GridLayout(8, 8));
		for(int i = 0; i < buttonArray.length; i++) {
			for(int j = 0; j < buttonArray[0].length; j++) {
				buttonArray[i][j] = new JButton();
				buttonArray[i][j].setBackground(Color.lightGray);
				buttonArray[i][j].addActionListener(new ActionListener() {
					//adding action listener to perform player turn or CPU turn if the game is not over
					public void actionPerformed(ActionEvent e) {
						if(gameOver==false) {
							if(CPU ==false) {
								placePiece(e);
								checkScore(gameBoard);
							}
							else if(CPU == true) {
								if(player ==1) {
									placePiece(e);
									checkScore(gameBoard);
									if(player == 2) {
										
										CPUTurn();
										checkScore(gameBoard);
									}
									
								}
								else if(player ==2) {
									CPUTurn();
									checkScore(gameBoard);
								}
							}
						}
						//skipping turn if not playable
						if(checkPlayable(gameBoard,player) == false) {
							description.setText("CPU has no \n playable places");
							
							if(player == 1) {
								player = 2;
							}//end of if
							else if(player==2) {
								player = 1;
							}//end of else if
							updatePieces();
						}
						//closing window if game is over
						if(gameOver==true) {
							mainPane.setVisible(false);
							frame.dispose();
							winScreen();
						}
						
					}//end of action performed
				});//end of add actionListerner
				buttonPane.add(buttonArray[i][j]);
			}//end of nested for
		}//end of base for
		//text box to tell who's turn it is
		c.ipadx = 0;
		c.ipady = 0;
		c.gridx = 6;
		c.gridy = 1;
		PTurnTF.setEditable(false);
		mainPane.add(PTurnTF,c);
		PTurnTF.setText(P1NameTF.getText()+"'s turn");
		
		//making constraints then adding the button pane
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 8;
		c.gridheight = 8;
		c.gridx = 0;
		c.gridy = 3;
		c.ipadx = 100;
		c.ipady = 275;
		mainPane.add(buttonPane, c);
		
		//making 2 new pane for p1 and p2 to show each players tile colour and count 
		c.gridx = 10;
		c.gridy = 0;
		c.gridwidth = 0;
		c.gridheight = 0;
		c.ipadx = 0;
		c.ipady = 10;
		JPanel P1Pane = new JPanel();
		P1Pane.setLayout(new FlowLayout());
		JTextField tf1 =new JTextField(P1NameTF.getText()+":");
		tf1.setEditable(false);
		P1Pane.add(tf1);
		
		JButton whitebtn = new JButton();
		whitebtn.setBackground(Color.white);
		P1Pane.add(whitebtn);
		P1Pieces.setText(""+checkScore(gameBoard)[1]);
		P1Pieces.setEditable(false);
		P1Pane.add(P1Pieces);
		mainPane.add(P1Pane,c);
		
		c.gridx = 15;
		c.gridy = 2;
		c.ipady = 10;
		JPanel P2Pane = new JPanel();
		P2Pane.setLayout(new FlowLayout());
		JTextField tf2 =new JTextField(P2NameTF.getText()+":");
		tf2.setEditable(false);
		P2Pane.add(tf2);
		
		JButton blackbtn = new JButton();
		blackbtn.setBackground(Color.black);
		P2Pane.add(blackbtn);
		P2Pieces.setText(""+checkScore(gameBoard)[2]);
		P2Pieces.setEditable(false);
		P2Pane.add(P2Pieces);
		mainPane.add(P2Pane,c);
				
		//text area to say if one's turn is playable
		c.ipadx = 0;
		c.ipady = 50;
		c.gridx = 100;
		c.gridy = 11;
		mainPane.add(description,c);
		description.setEditable(false);
		description.setText("");
		//setting up the frame
		frame.add(mainPane);
		frame.setVisible(true);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		updatePieces();
	}
	

	/*
	* Method Name: winScreen
	* Author: Benjamin Bliss
	* Creation Date; January 10, 2019
	* Modified Date: January 18, 2019
	* Description: congratulates the winner in a new frame then saves the score to a file
	* @Parameters: none
	* @Return Value: none
	* Data Type: void
	* Dependencies: None
	* Throws/Exceptions: none
	*/
	public static void winScreen() {
		//new frame for win win screen
		JFrame frame = new JFrame("You Win");
		//string to keep track of player win names and player losed names
		String playerW ="";
		String playerL = "";
		//setting the winner name and loser name
		if(winner[0]== 1) {
			playerW = P1NameTF.getText();
			playerL = P2NameTF.getText();
		}
		else if(winner[0]==2) {
			playerW = P2NameTF.getText();
			playerL = P1NameTF.getText();
		}
		//printing tie if game tie or congratulating player if a win
		if(winner[0]!=3) {
			frame.add(new JTextArea("Congratulations to "+playerW+" for winning this game"
					+ "\nhe beat "+playerL+" with a score of "+winner[1]+"-"+winner[3]));
		}
		else {
			frame.add(new JTextArea("Wow your game ended in a tie, "
					+ "\n the game finished with a score of"+winner[1]+"-"+winner[3]));
		}
		//creating a button and adding an action listener to restart the program
		JButton newGame = new JButton("New Game");
		newGame.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				titleScreen();
				frame.dispose();
			}
		});
		//creating a button and adding an action listener to end the program
		JButton exit =new JButton("Exit");
		exit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});
		//creating a pane to add all the objects to and setting it up
		JPanel pane = new JPanel();
		pane.setLayout(new GridLayout(1,2));
		pane.add(newGame);
		pane.add(exit);
		frame.add(pane,BorderLayout.SOUTH);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameOver = false;
		resetBoard();
		player =1;
		//saving scores to a file
		writeOutScore("Game Scores.txt", playerW, playerL);		
	}//end of method 
	
	
	public graphicsOthello(){
		
		titleScreen();
		
	}
	
	public static void main(String[] args) {
		new graphicsOthello();
	}//end of main
}//end of class
