package other;

public class ItemString extends Item{
	private String string;
	/*
	 * Item --> String
	 */
	public ItemString (String string) {
		this.string = string;
	}
	public void display(String indentit) {
		System.out.println(indentit + "StringItem "+string);
	}
}
