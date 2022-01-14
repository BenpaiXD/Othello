package othello;

import java.util.*;

public class consoleOthello {
	
	static Scanner scan = new Scanner(System.in);
	

	static boolean flag = false;
	static boolean gameOver = false;
	static int playableTiles = 0;
	
	public static void print2DArray(int[][]array) {
		for(int i = 0; i < array.length; i++) {
			for(int j = 0; j < array[0].length;j++) {
				System.out.print(array[i][j]+" ");
			}
			System.out.println();
		}
	}	
	
	/*
	* Method Name: printFormatArray
	* Author: Benjamin Bliss
	* Creation Date; December 11, 2018
	* Modified Date: December 11, 2018
	* Description: prints out the 2D array with proper headings
	* @Parameters: int[][]array
	* @Return Value: none
	* Data Type: void
	* Dependencies: None
	* Throws/Exceptions: None
	*/
	public static void printFormatArray(int[][]array) {
		System.out.println("    1 2 3 4 5 6 7 8 \n");
		for(int i = 0; i < array.length; i++) {
			
			System.out.print((i+1)+"   ");
			
			for(int j = 0; j < array[i].length; j++) {
				
				System.out.print(array[i][j]+" ");
			}//end of nested for
			System.out.println();
		}//end of base for
	}//end of method
	
	
	/*
	* Method Name: scanInt
	* Author: Benjamin Bliss
	* Creation Date; November 21, 2018
	* Modified Date: December 11, 2018
	* Description: scans for an integer
	* @Parameters: none
	* @Return Value: intstr
	* Data Type: int
	* Dependencies: None
	* Throws/Exceptions: None
	*/
	public static int scanInt() {
		try {
			String str = scan.nextLine();
			int intstr = Integer.parseInt(str);
			return intstr;
		}//end of try
		catch(Exception e){
			return -1;
		}//end of catch		
	}//end of method
	
	
	public static int[][] copy2DArray(int[][]copyFromArray,int[][]copyToArray) {
		copyToArray = new int[copyFromArray.length][copyFromArray[0].length];
		for(int i = 0; i < copyToArray.length; i++) {
			for(int j = 0; j <copyToArray[0].length;j++) {
				copyToArray[i][j] = copyFromArray[i][j];
			}
		}
		
		return copyToArray;
	}
	
	public static int[] copyArray(int[]copyFromArray, int[]copyToArray) {
		copyToArray = new int[copyFromArray.length];
		for (int i = 0; i< copyFromArray.length; i++) {
			copyToArray[i] = copyFromArray[i];
		}
		return copyToArray;
	}
	
	public static int[] changeIndex(int[]array, int y, int x) {
		
		array[0] += y;
		array[1] += x;
		
		return array;
	}
	public static boolean check(int y, int x, int[][]array, int[]i, int[]index2,boolean TF) {
		int v0=0;
		int v1=0;
		if(TF ==true) {
			v0 = index2[0];
			v1 = index2[1];
		}
		//check for right border
		if(y==0 && x==1) {
			if(TF ==false) {
				v0= array.length-1;
				v1= array.length-1;
			}
			if(i[0]<=v0 && i[1]<=v1) {
				return true;
			}
		}
		
		else if(y==0 && x==-1) {
			if(TF ==false) {
				v0= array.length-1;
				v1= 0;
			}
			if(i[0]<=v0 && i[1]>=v1) {
				return true;
			}
		}
		else if(y==1 && x==0) {
			if(TF ==false) {
				v0= array.length-1;
				v1= array.length-1;
			}
			if(i[0]<=v0 && i[1]<=v1) {
				return true;
			}
		}
		else if(y==-1 && x==0) {
			if(TF ==false) {
				v0= 0;
				v1= array.length-1;
			}
			if(i[0]>=v0 && i[1]<=v1) {
				return true;
			}
		}
		
		//check for bottom and right border
		else if(y==1 && x==1) {
			if(TF ==false) {
				v0= array.length-1;
				v1= array.length-1;
			}
			if(i[0]<=v0 && i[1]<=v1) {
				return true;
			}
		}
		else if(y==1 && x==-1) {
			if(TF ==false) {
				v0= array.length-1;
				v1= 0;
			}
			if(i[0]<=v0 && i[1]>=v1) {
				return true;
			}
		}
		else if(y==-1 && x==-1) {
			if(TF ==false) {
				v0= 0;
				v1= 0;
			}
			if(i[0]>=v0 && i[1]>=v1) {
				return true;
			}
		}
		else if(y==-1 && x==1) {
			if(TF ==false) {
				v0= 0;
				v1= array.length-1;
			}
			if(i[0]>=v0 && i[1]<=v1) {
				return true;
			}
		}
		
		return false;
	}
	
	public static int[][]tileFlip(int[][]array,int[]index,int y, int x, int yourTile){
		int NMETile = 0;
		int[] i = {};
		boolean check0= false;
		boolean valid =false;
		int z = 0;
		int[] index2= {0,0};
		
		
		if(yourTile == 1) {
			NMETile = 2;
		}
		
		if(yourTile == 2) {
			NMETile = 1;
		}
		
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
						//checking if the next piece is a 2 at each index
						if(array[i[0]][i[1]]== yourTile &&check0==false) {
							//saving the index the found 2
							index2[0]= i[0];
							index2[1]= i[1];
							valid = true;
							//force leaving the for loop after the index found is a 2
							break;
						}//end of if
					
					}//end of for

				//setting all the values between the place pieces and the found 2 to 2
					if (valid == true) {
						flag = true;
						for(i = copyArray(index,i); check(y,x,array,i,index2,true); i=changeIndex(i,y,x)) {
							array[i[0]][i[1]] = yourTile;
						
						}//end of for
					}//end of if valid
					
				}//end of switch
			}//end of nested if
			check0 = false;
			valid = false;
		}
		catch(Exception e){
			
			
		}
		
		return array;
	}
	
	public static int[] checkScore(int[][]array) {
		int[]pieceCount = new int[3];
		int[]winner = new int[2];
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
		if(pieceCount[0] == 0||pieceCount[1] == 0||pieceCount[2] == 0) {
			if (pieceCount[1]== pieceCount[2]) {
				winner[0]=3;
				winner[1]=pieceCount[1];
			}
			if (pieceCount[1] > pieceCount[2]) {
				winner[0]=1;
				winner[1]=pieceCount[1];
			}
			if (pieceCount[1] < pieceCount[2]) {
				winner[0]=2;
				winner[1]=pieceCount[2];
			}
			gameOver = true;
		}
		return winner;
	}//end of method win check
	
	public static int[][]flipAll(int[][]array, int player, int[]index) {
		
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
	
	public static int[][]playerTurn(int[][]gameBoard,int player){
		
		if(checkPlayable(gameBoard,player) == true) {
			
			int[] index = new int[2];
			System.out.println("Player "+ player +"'s turn");
			printFormatArray(gameBoard);
			do {	
				try {
					
					System.out.println("Enter the y then x coordonate");
					index[0] = scanInt()-1;
					System.out.println("Now enter the x coordonate");
					index[1] = scanInt()-1;
					
					
					flag = false;
					
					try {
						if(gameBoard[index[0]][index[1]] ==0) {
							System.out.println(player);
							gameBoard = flipAll(gameBoard,player,index);
							
						}
						else {
							System.out.println("please place your piece on a '0'");
							flag = false;
						}
					}
					catch(Exception e){
						System.out.println("please enter a coordonate between 1 and 8");
						flag = false;
					}
					
				}//end of try
				catch(Exception e){
					System.out.println("please enter a proper character");
					flag = false;
				}//end of catch
				
				
				
			}while (flag == false);//end of do
			System.out.println();
		}//end of checking for playable
		
		else {
			System.out.println("there are no Playable places for player "+ player);
		}
		
		
		return gameBoard;
	}
	
	public static boolean checkPlayable(int[][]array, int player) {
		
		int [][]copyArray = {{}};
		
		int[]index= {0,0};
		playableTiles = 0;
		
		
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
	
	
	public static int[][]CPUTurn(int[][]array,int player){
		
		if(checkPlayable(array, player) == true) {
			System.out.println("CPU's turn");
			printFormatArray(array);
			int[][]playableList = new int[playableTiles][2];
			int[][]copyArray = {{}};
			int[]index= {0,0};
			int f = 0;
			
			for(int i = 0; i < array.length; i++) {
				for(int j = 0; j < array[0].length; j++) {
					if(array[i][j] == 0) {
						
						index[0]=i;
						index[1]=j;
						flag = false;
						
						copyArray =copy2DArray(array,copyArray);
						
						copyArray = flipAll(copyArray,player,index);
						if(flag == true) {
							playableList[f] = copyArray(index,playableList[f]);
							f++;
							
						}//end of nested if	
					}//end of base loop				
				}//end of nested for				
			}//end of base for
			//print2DArray(playableList);
			index = copyArray(playableList[(int)(Math.random()*playableTiles)],index);
				
			array = flipAll(array, player, index);
				
		}//end of check playable
		else {
			System.out.println("No playable places for CPU");
		}
		System.out.println();
		return array;
	}//end of method 
	
	public static void main(String[] args) {
		
		//created a 2D array to be use as the game board		
		int[][] gameBoard = new int[8][8];
		int[] winScore = {};
		int option = 0;
		
		//Setting the starting tiles for P1 and P2
		gameBoard[4][4] = 1;
		gameBoard[3][3] = 1;
		gameBoard[3][4] = 2;
		gameBoard[4][3] = 2;
	
		while(flag == false) {
			try {
				System.out.println("Would you like to \n 1) Play against another player"
						+ "\n 2) Play against a CPU");
				option = scanInt();
				flag = true;
				if(option != 1 && option != 2) {
					System.out.println("Please enter a valid input");
					flag = false;
				}
				
			}
			catch(Exception e) {
				System.out.println("Please enter a valid input");
				flag = false;
			}
		}
		flag = false;
		
		while(gameOver == false) {
			
			if(gameOver != true) {
				gameBoard = playerTurn(gameBoard,1);				
			}
			winScore = checkScore(gameBoard);
			
			if(gameOver != true) {
				if(option == 2) {
					
					gameBoard = CPUTurn(gameBoard, 2);
				}
				else {
					gameBoard = playerTurn(gameBoard,2);
				}
				
			}
			winScore = checkScore(gameBoard);
			
		}//end of infinite while
		if(winScore[0]!=3) {
			System.out.println("Contgratulations to player " + winScore[0]+" with "
		+winScore[1]+" of his pieces on the board");
		}
		
	}//end of main
}//end of class
