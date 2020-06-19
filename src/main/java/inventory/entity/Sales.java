package inventory.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "sales")
public class Sales {

	@Id
	@Column(name = "sell_id")
	private String sellId;
		
	@Column(name = "time")
	private String time;
	
	@Column(name = "customer_name")
	private String customerName;
	       
	@Column(name = "mobile_no")
	private int mobileNo;
	
	@Transient
	public List<SoldMenuDetails> soldMenuDetails;
	
	@OneToMany(mappedBy = "sales", cascade = CascadeType.ALL,
			orphanRemoval = true)
	private List<Sales_Menu> menuSold = new ArrayList();
	
	public Sales() {}

	public Sales(String sellId, String time, String customerName, int mobileNo) {
		super();
		this.sellId = sellId;
		this.time = time;
		this.customerName = customerName;
		this.mobileNo = mobileNo;
	}

	public String getSellId() {
		return sellId;
	}

	public void setSellId(String sellId) {
		this.sellId = sellId;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public int getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(int mobileNo) {
		this.mobileNo = mobileNo;
	}
	
	public void setSoldMenuDetails(List<SoldMenuDetails> soldMenuDetails) {
		this.soldMenuDetails = soldMenuDetails;
	}
	
	public void addMenuSold(String menuId, String dish, int quantitySold) {
		Sales_Menu salesMenu = new Sales_Menu(this, menuId, dish, quantitySold);
		menuSold.add(salesMenu);
	}
	
}
