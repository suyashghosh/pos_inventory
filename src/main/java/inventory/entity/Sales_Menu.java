package inventory.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(name = "sales_menu")
public class Sales_Menu {

	@EmbeddedId
	private Sales_MenuId sales_menuId;
	
	@ManyToOne(fetch = FetchType.LAZY,
			cascade = CascadeType.ALL)
	@MapsId("sellId")
	@JoinColumn(name = "sell_id")
	private Sales sales;
	
	
	@Column(name = "dish")
	private String dish;
	
	@Column(name = "quantity_sold")
	private int quantitySold;
	
	public Sales_Menu() {}

	public Sales_Menu(Sales sales, String menuId, String dish, int quantitySold) {
		super();
		this.sales_menuId = new Sales_MenuId(sales.getSellId(), menuId);
		this.sales = sales;
		this.dish = dish;
		this.quantitySold = quantitySold;
	}

	public Sales getSales() {
		return sales;
	}

	public void setSales(Sales sales) {
		this.sales = sales;
	}

	public String getDish() {
		return dish;
	}

	public void setDish(String dish) {
		this.dish = dish;
	}

	public int getQuantitySold() {
		return quantitySold;
	}

	public void setQuantitySold(int quantitySold) {
		this.quantitySold = quantitySold;
	}
	
}
