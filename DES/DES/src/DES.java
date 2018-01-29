public class DES 
{
	public static void main(String[] args) 
	{
		Key_Generation Key = new Key_Generation();
		Cipher cipher = new Cipher();
		System.out.println("★--------------------- < 암호화 > ---------------------★");
		Key.Key_Generation();
		cipher.setRoundKey(Key.getRoundKey());
		cipher.chipher();
		System.out.println("★--------------------- < 복호화 > ---------------------★");
		cipher.decoder();
	}
}


