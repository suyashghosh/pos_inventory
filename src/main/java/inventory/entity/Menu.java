package inventory.entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;



@Entity
@Table(name = "Menu")
public class Menu {

	@Id
	@Column(name = "m_id")
	private String menuId;
	
	@Column(name = "dish")
	private String dish;
	
	@JsonIgnoreProperties("menu")
	@OneToMany(mappedBy = "menu", cascade = CascadeType.ALL,
			orphanRemoval = true)
	List<Recipe> items = new ArrayList<>();	
		
	public Menu() {}
	
	public Menu(String menuId, String dish) {
		super();
		this.menuId = menuId;
		this.dish = dish;
	}
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
		
		
	public List<Recipe> getItems() {
		return items;
	}

	public void setItems(List<Recipe> items) {
		this.items = items;
	}

	public void addItem(Item item, float quantity) {
        Recipe recipe = new Recipe(this, item, quantity);
        items.add(recipe);
//        item.getDishes().add(recipe);      //for 1-d
    }
	
	public void removeItem(Item item) {
        for (Iterator<Recipe> iterator = items.iterator();
             iterator.hasNext(); ) {
            Recipe recipe = iterator.next();
 
            if (recipe.getMenu().equals(this) &&
                    recipe.getItem().equals(item)) {
                iterator.remove();
//                recipe.getItem().getDishes().remove(recipe);         //for 1-d
                recipe.setMenu(null);
                recipe.setItem(null);
            }
        }
    }

}
