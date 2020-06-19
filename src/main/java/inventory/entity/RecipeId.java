package inventory.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class RecipeId implements Serializable {

	@Column(name = "m_id")
	private String menuId;
	
	@Column(name = "i_id")
	private String itemId;
	
	public RecipeId() {}

	public RecipeId(String menuId, String itemId) {
		super();
		this.menuId = menuId;
		this.itemId = itemId;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
 
        if (o == null || getClass() != o.getClass())
            return false;
 
        RecipeId that = (RecipeId) o;
        return Objects.equals(menuId, that.menuId) &&
               Objects.equals(itemId, that.itemId);
    }
 
    @Override
    public int hashCode() {
        return (menuId+itemId).hashCode();
    }
}
