package inventory.rest;

import java.io.File;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import inventory.dao.ItemDao;
import inventory.dao.MenuDao;
import inventory.dao.SalesDao;
import inventory.entity.Item;
import inventory.entity.Item_Quantity;
import inventory.entity.Menu;
import inventory.entity.Message;
import inventory.entity.Sales;
import inventory.entity.SoldMenuDetails;

@RestController
@RequestMapping("/sales")
public class SalesRestController {

	private SalesDao salesDao;
	private MenuDao menuDao;
	private ItemDao itemDao;
	
	@Autowired
	public SalesRestController(SalesDao salesDao, MenuDao menuDao, ItemDao itemDao) {
		this.salesDao = salesDao;
		this.menuDao = menuDao;
		this.itemDao = itemDao;
	}
	
	@PostMapping("/addSales")
	public Message addSales(@RequestBody Sales sales) {
		Date date = new Date();  
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	    formatter.setTimeZone(TimeZone.getTimeZone("IST"));
	    String strDate= formatter.format(date); 
	    sales.setSellId(strDate);
		for(SoldMenuDetails soldMenuDetails : sales.soldMenuDetails) {
			sales.addMenuSold(soldMenuDetails.getMenuId(), soldMenuDetails.getDish(), soldMenuDetails.getQuantitySold());
			//logic to decrease item count
			updateItemQuantity(soldMenuDetails.getMenuId(), soldMenuDetails.getQuantitySold());
		}
		salesDao.addSales(sales);
		return new Message(true, "Transaction successful");
	}
	
	@GetMapping("/getSales")
	public List<Sales> getSales(@RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate){
		List<Sales> sales = salesDao.getSales(startDate,endDate);
		for(Sales salesItr : sales) {
			List<SoldMenuDetails> soldMenuDetails = salesDao.getSoldMenuDetails(salesItr.getSellId());
			salesItr.setSoldMenuDetails(soldMenuDetails);
		}
		return sales;
	}
	
	public void updateItemQuantity(String menuId, int quantitySold) {
		List<Item_Quantity> itemQuantity = menuDao.getItemQuantityByMenuId(menuId);
		for(Item_Quantity itemQuant : itemQuantity) {
			Item item = itemDao.getItemById(itemQuant.getItemId());
			float quantityUsed = item.getQuantity() - (itemQuant.getQuantity() * (float)quantitySold);
			item.setQuantity(quantityUsed);
			itemDao.updateItem(item);
		}
	}

}
