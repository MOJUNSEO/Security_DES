	public class Key_Generation extends Cipher
	{
		int ParityDropTable[] = {57, 49, 41, 33, 25, 17, 9, 1,
									58, 50, 42, 34, 26, 18, 10, 2,
									59, 51, 43, 35, 27,19, 11, 3,
									60, 52, 44, 36, 63, 55, 47, 39,
									31, 23, 15, 7, 62, 54, 46, 38,
									30, 22, 14, 6, 61, 53, 45, 37,
									29, 21, 13, 5, 28, 20, 12, 4};
		int ShiftTable[] = {1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1};
		int KeyCompressionTable[] = {14, 17, 11, 24, 1, 5, 3, 28,
											15, 6, 21, 10, 23, 19, 12, 4,
											26, 8, 16, 7, 27, 20, 13, 2,
											41, 52, 31, 37, 47, 55, 30, 40,
											51, 45, 33, 48, 44, 49, 39, 56,
											34, 53, 46, 42, 50, 36, 29, 32};
		int[][] RoundKeys = new int[16][48];	
		void Key_Generation()
		{
			int keyWithParities[] = new int[64];
			int key[] = {0xA, 0xA, 0xB, 0xB, 0, 9, 1, 8, 2, 7, 3, 6, 0xC, 0xC, 0xD, 0xD};
			int cipherKey[] = new int[56];
			int leftKey[] =new int [28];
			int rightKey[] = new int[28];
			int preRoundKey[] = new int[56];
			int round;
		
			bit(key, keyWithParities, 16);
			permute(64, 56, keyWithParities, cipherKey, ParityDropTable);
			split(56, 28, cipherKey, leftKey, rightKey);
			for(round=0 ; round<16 ; round++)
			{
				shiftLeft(leftKey, ShiftTable[round]);
				shiftLeft(rightKey, ShiftTable[round]); 
				combine(28, 28, leftKey, rightKey, preRoundKey);
				permute(56, 48, preRoundKey, RoundKeys[round], KeyCompressionTable);
			}
		}

		void shiftLeft(int block[], int numOfShifts)
		{
			int T;	
			for(int i=0 ; i<numOfShifts ; i++)
			{
				T = block[0];
				
				for(int j=1 ; j<28 ; j++){
					block[j-1] = block[j];
				}
				block[27] = T;
			}
		}

		int[][] getRoundKey()
		{
			return this.RoundKeys;
		}
	}
