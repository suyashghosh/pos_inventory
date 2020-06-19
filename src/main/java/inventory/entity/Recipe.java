package inventory.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "recipe")
public class Recipe {

	@EmbeddedId
	private RecipeId recipeId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("menuId")
	@JoinColumn(name = "m_id")
	private Menu menu;
	
	@ManyToOne     //(fetch = FetchType.LAZY)  if lazy then will have to fetch it in MenuDao, which led to duplicate menu on fetching all menu
	@MapsId("itemId")
	@JoinColumn(name = "i_id") 
	@JsonIgnoreProperties("dishes")
	private Item item;
	
	@Column(name = "quantity")
	private float quantity;
	
	public Recipe() {}

	public Recipe(Menu menu, Item item, float quantity) {
		super();
		this.recipeId = new RecipeId(menu.getMenuId(), item.getItemId());
		this.menu = menu;
		this.item = item;
		this.quantity = quantity;
	}


	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public float getQuantity() {
		return quantity;
	}

	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}	
	
}
