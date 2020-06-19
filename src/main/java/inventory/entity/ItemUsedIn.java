package inventory.entity;

import javax.persistence.Column;

public class ItemUsedIn {
	
	private String dish;
	
	private float quantity;

	public ItemUsedIn(){}

	public ItemUsedIn(String dish, float quantity) {
		super();
		this.dish = dish;
		this.quantity = quantity;
	}

	public String getDish() {
		return dish;
	}

	public void setDish(String dish) {
		this.dish = dish;
	}

	public float getQuantity() {
		return quantity;
	}

	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}
	
}
