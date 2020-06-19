package inventory.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Sales_MenuId implements Serializable{
	
	@Column(name = "sell_id")
	private String sellId;
	
	@Column(name = "m_id")
	private String menuId;
	
	public Sales_MenuId() {}

	public Sales_MenuId(String sellId, String menuId) {
		super();
		this.sellId = sellId;
		this.menuId = menuId;
	}

	public String getSellId() {
		return sellId;
	}

	public void setSellId(String sellId) {
		this.sellId = sellId;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}	

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
 
        if (o == null || getClass() != o.getClass())
            return false;
 
        Sales_MenuId that = (Sales_MenuId) o;
        return Objects.equals(menuId, that.menuId) &&
               Objects.equals(sellId, that.sellId);
    }
 
    @Override
    public int hashCode() {
        return (menuId+sellId).hashCode();
    }
}
