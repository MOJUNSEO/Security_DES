public class DES 
{
	public static void main(String[] args) 
	{
		Key_Generation Key = new Key_Generation();
		Cipher cipher = new Cipher();
		System.out.println("��--------------------- < ��ȣȭ > ---------------------��");
		Key.Key_Generation();
		cipher.setRoundKey(Key.getRoundKey());
		cipher.chipher();
		System.out.println("��--------------------- < ��ȣȭ > ---------------------��");
		cipher.decoder();
	}
}


