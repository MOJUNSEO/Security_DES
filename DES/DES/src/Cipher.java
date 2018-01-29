public class Cipher
{
	int plainBlock[] = new int[64];
	int RoundKeys[][] = new int[16][48];
	int cipherBlock[] = new int[64];
	int inBlock[] = new int[64];
	int leftBlock[] = new int[64];
	int rightBlock[] = new int[64];
	int plainText[] = {1, 2, 3, 4, 5, 6, 0xA, 0xB, 0xC, 0xD, 1, 3, 2, 5, 3, 6};
	int cipherText[] = new int[16];
	
	int InitialPermutationTable[] ={58, 50, 42, 34, 26, 18, 10, 2,
										 60, 52, 44, 36, 28, 20, 12, 4,
										 62, 54, 46, 38, 30, 22, 14, 6,
										 64, 56, 48, 40, 32, 24, 16, 8,
										 57, 49, 41, 33, 25, 17, 9, 1,
										 59, 51, 43, 35, 27, 19, 11, 3,
										 61, 53, 45, 37, 29, 21, 13, 5,
										 63, 55, 47, 39, 31, 23, 15, 7};
	int FinalPermutationTable[] = {40, 8, 48, 16, 56, 24, 64, 32,
										 39, 7, 47, 15, 55, 23, 63, 31,
										 38, 6, 46, 14, 54, 22, 62, 30,
										 37, 5, 45, 13, 53, 21, 61, 29,
										 36, 4, 44, 12, 52, 20, 60, 28,
										 35, 3, 43, 11, 51, 19, 59, 27,
										 34, 2, 42, 10, 50, 18, 58, 26,
										 33, 1, 41, 9, 49, 17, 57, 25};
	
	int ExpansionPermutationTable[] = {32, 1, 2, 3, 4, 5,
											    4, 5, 6, 7, 8, 9,
											    8, 9, 10, 11, 12, 13,
											    12, 13, 14, 15, 16, 17,
											    16, 17, 18, 19, 20, 21,
											    20, 21, 22, 23, 24, 25,
											    24, 25, 26, 27, 28, 29,
											    28, 29, 30, 31, 32, 1};
	
	int [] StraightPermutationTable = {16, 7, 20, 21, 29, 12, 28, 17,
											  1, 15, 23, 26, 5, 18, 31, 10,
											  2, 8, 24, 14, 32, 27, 3, 9,
											  19, 13, 30, 6, 22, 11, 4, 25};
	
	int SubstitutionTables[][][] ={{{14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7},
										{0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8},
										{4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0},
										{15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13}},

									   {{15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10},
										{3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5},
										{0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15},
										{13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9}},

									   {{10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8},
										{13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1},
										{13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7},
										{1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12}},

									   {{7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15},
										{13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9},
										{10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4},
										{3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14}},

									   {{2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9},
										{14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6},
										{4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14},
										{11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3}},

									   {{12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11},
										{10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8},
										{9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6},
										{4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13}},

									   {{4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1},
										{13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6},
										{1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2},
										{6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12}},

									   {{13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7},
										{1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2},
										{7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8},
										{2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11}}};
	
	void chipher()
	{
		int round;
		int outBlock[] = new int[64];
		int print[] = new int[16];
		
		bit(plainText, plainBlock, 16);
	    permute(64, 64, plainBlock, outBlock, InitialPermutationTable);
		split(64, 32, outBlock, leftBlock, rightBlock);
		
		System.out.print("Plaintext : ");
		hex(plainBlock, print, 16);
		System.out.println("\n----------------------------------------------------¡Ú");
		
		System.out.print("After Initial Permutation : ");
		hex(leftBlock, print, 8);
		hex(rightBlock, print, 8);
		System.out.println();
		
		System.out.print("After Splitting : L0=");
		hex(leftBlock, print, 8);
		System.out.print("\tR0=");
		hex(rightBlock, print, 8);
		System.out.println("\n----------------------------------------------------¡Ú");
		
		System.out.println("Round\t Left\t\tRight\t\tRound Key");
		System.out.println("----------------------------------------------------¡Ú");
		
		for(round=0 ; round<16 ; round++){
			mixer(leftBlock, rightBlock, RoundKeys[round]);
			
			if(round != 15){
				swapper(leftBlock, rightBlock);
			}
			if(round+1 < 10)
			{
				System.out.print("Round " + (round+1) + "  ");
			}
			else if ( round+1 > 9 )
			{
				System.out.print("Round " + (round+1) + " ");
			}
			hex(leftBlock, print, 8);
			System.out.print("\t");
			
			hex(rightBlock, print, 8);
			System.out.print("\t");
			
			hex(RoundKeys[round], print, 12);
			System.out.println();
		}
		
		System.out.println("----------------------------------------------------¡Ú");
		System.out.print("After Combination : ");
		hex(leftBlock, print, 8);
		hex(rightBlock, print, 8);
		System.out.println();
		
		combine(32, 32, leftBlock, rightBlock, outBlock);
		permute(64, 64, outBlock, cipherBlock, FinalPermutationTable);
		
		System.out.print("Ciphertext : \t    ");
		hex(cipherBlock, print, 16);
		System.out.println();
	}
	void decoder()
	{
		int round;
		int outBlock[] = new int[64];
		int print[] = new int[16];
		
		bit(cipherText, cipherBlock, 16);
		
	    permute(64, 64, cipherBlock, outBlock, InitialPermutationTable);
		split(64, 32, outBlock, leftBlock, rightBlock);
		
		System.out.print("Chiphertext : ");
		hex(cipherBlock, print, 16);
		System.out.println("\n----------------------------------------------------¡Ú");
		
		System.out.print("After Initial Permutation : ");
		hex(leftBlock, print, 8);
		hex(rightBlock, print, 8);
		System.out.println();
		
		System.out.print("After Splitting : L0=");
		hex(leftBlock, print, 8);
		System.out.print("\tR0=");
		hex(rightBlock, print, 8);
		System.out.println("\n----------------------------------------------------¡Ú");
		
		System.out.println("Round\t Left\t\tRight\t\tRound Key");
		System.out.println("----------------------------------------------------¡Ú");
		
		for(round=0 ; round<16 ; round++){
			mixer(leftBlock, rightBlock, RoundKeys[round]);
			
			if(round != 15){
				swapper(leftBlock, rightBlock);
			}
			if(round+1 < 10)
			{
				System.out.print("Round " + (round+1) + "  ");
			}
			else if ( round+1 > 9 )
			{
				System.out.print("Round " + (round+1) + " ");
			}
			hex(leftBlock, print, 8);
			System.out.print("\t");
			
			hex(rightBlock, print, 8);
			System.out.print("\t");
			
			hex(RoundKeys[round], print, 12);
			System.out.println();
		}
		
		System.out.println("----------------------------------------------------¡Ú");
		System.out.print("After Combination : ");
		hex(leftBlock, print, 8);
		hex(rightBlock, print, 8);
		System.out.println();
		
		combine(32, 32, leftBlock, rightBlock, outBlock);
		permute(64, 64, outBlock, plainBlock, FinalPermutationTable);
		
		System.out.print("Plaintext : \t    ");
		hex(cipherBlock, print, 16);
		System.out.println();
	}
	
	void mixer(int leftBlock[], int rightBlock[], int RoundKey[]){
		int[] T1 = new int[32];
		int[] T2 = new int[32];
		int[] T3 = new int[32];
	
		copy(32, rightBlock, T1);
		function(T1, RoundKey, T2);
		exclusiveOr(32, leftBlock, T2, T3);
		copy(32, T3, leftBlock);
	}
	
	void swapper(int leftBlock[], int rightBlock[]){
		int[] T = new int[32];
		
		copy(32, leftBlock, T);
		copy(32, rightBlock, leftBlock);
		copy(32, T, rightBlock);
	}
	
	void function(int inBlock[], int RoundKey[], int outBlock[]){
		int[] T1 = new int[48];
		int[] T2 = new int[48];
		int[] T3 = new int[32];
		
		permute(48, 48, inBlock, T1, ExpansionPermutationTable);
		exclusiveOr(48, T1, RoundKey, T2);
		substitute(T2, T3, SubstitutionTables);
		permute(32, 32, T3, outBlock, StraightPermutationTable);
	}
	
	void substitute(int inBlock[], int outBlock[], int SubstitutionTables[][][]){
		int i, row, col, value;
		
		for(i=0 ; i<8 ; i++){
			row = 2 * inBlock[i*6] + inBlock[i*6+5];
			col = 8 * inBlock[i*6+1] + 4 * inBlock[i*6+2] + 2 * inBlock[i*6+3] + inBlock[i*6+4];
			value = SubstitutionTables[i][row][col];
	 
			outBlock[i*4] = value/8;
			value = value%8;
			outBlock[i*4+1] = value/4;
			value = value%4;
			outBlock[i*4+2] = value/2;
			value = value%2;
			outBlock[i*4+3] = value; 
		}
	}
	
	void permute(int n, int m, int inBlock[], int outBlock[], int permutationTable[]){
		int i=0;
		
		while (i<m) {
			outBlock[i] = inBlock[permutationTable[i]-1];
			i += 1;
		}
	}
	
	void split(int n, int m, int inBlock[], int leftBlock[], int rightBlock[]){
		 int i=0;
		 
		 while(i<m){
			 leftBlock[i] = inBlock[i];
			 rightBlock[i] = inBlock[i+m];
			 i += 1;
		 }
	 }
	
	void combine(int n, int m, int leftBlock[], int rightBlock[], int outBlock[]){
		 int i=0;
		 
		 while(i<m){
			 outBlock[i] = leftBlock[i];
			 outBlock[i+m] = rightBlock[i];
			 i += 1;
		 }
	}
	
	void exclusiveOr(int n, int firstBlock[], int secondBlock[], int outBlock[]){
		 int i=0;
		 
		 while(i<n){
			 if((firstBlock[i] + secondBlock[i]) == 1){
				 outBlock[i] =1;
			 }
			 else{
				 outBlock[i] =0;
			 }
			 i += 1;
		 }
	 }
	
	void copy(int n, int firstBlock[], int secondBlock[]){
		int i=0;
		
		while(i<n){
			secondBlock[i] = firstBlock[i];
			i += 1;
		}
	}
	 
	void bit(int str[], int output[], int length){
		int i, j, q=0, r;
		
		for (i=0; i<length; i++){
			q = str[i];
			
			for (j=3 ; j>=0 ; j--){
				r = q%2;
				output[i*4+j] = r;
				q = q/2;
			}			
		}
	}
	 void hex(int str[], int outBlock[], int length){
		int i, j, k;
		int result[] = new int[length];
		
		for(i=0 ; i<length ; i++){
			k=3;
			
			for(j=0 ; j<4 ; j++){
				if(str[i*4+j] == 1){
					result[i] +=  (int)Math.pow(str[i*4+j]*2, k);
				}
				
				k--;
			}
			
			outBlock[i] = result[i];
			cipherText[i] = outBlock[i];
			System.out.format("%01X", result[i]);
		}		
	}
	void setRoundKey(int str[][])
	{
		this.RoundKeys = str;
	}
}