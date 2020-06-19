package inventory.entity;

public class MenuDetails {

	private String menuId;	
	private String dish;	
	private Item_Quantity itemQuantity[];


	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getDish() {
		return dish;
	}

	public void setDish(String dish) {
		this.dish = dish;
	}

	public Item_Quantity[] getItemQuantity() {
		return itemQuantity;
	}

	public void setItemQuantity(Item_Quantity[] itemQuantity) {
		this.itemQuantity = itemQuantity;
	}
		
}
