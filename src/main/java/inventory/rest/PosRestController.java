package inventory.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import inventory.dao.ItemDao;
import inventory.dao.MenuDao;
import inventory.entity.ExhaustedItems;
import inventory.entity.Item;
import inventory.entity.Item_Quantity;
import inventory.entity.Menu;
import inventory.entity.MenuDetails;
import inventory.entity.Message;

@RestController
@RequestMapping("/pos")
public class PosRestController {
	
	private ItemDao itemDao;
	private MenuDao menuDao;
	
	@Autowired
	public PosRestController(ItemDao itemDao, MenuDao menuDao) {
		this.itemDao = itemDao;
		this.menuDao = menuDao;
	}

	//Item 
	
	@PostMapping("/addItem")
	public Message addItem(@RequestBody Item item) {
		if(itemDao.checkPresence(item.getItemName())) {
			return new Message(false, "Item already present");
		}
		item.setItemId(itemDao.generateId(item.getItemName()));
		itemDao.addItem(item);
		return new Message(true, "Item Added");
	}
	
	@PostMapping("/updateItem")
	public Message updateItem(@RequestBody Item item) {
		if (item.getItemName() == null || item.getItemName().trim() == "") {
			return new Message(false, "Invalid item name");
		}
		itemDao.updateItem(item);
		return new Message(true,"Item Updated");
		
	}		
	
	@GetMapping("/getItem/{itemId}")
	public Item getItem(@PathVariable String itemId) {	
		Item item = itemDao.getItemById(itemId);
		item.setItemUsedIn(itemDao.getItemUsedIn(itemId));
		return item;
	}
	
	@GetMapping("/getItem")
	public List<Item> getItems() {
		List<Item> items = itemDao.getItems();
		for(Item item : items) {
			item.setItemUsedIn(itemDao.getItemUsedIn(item.getItemId()));
		}
		return items;
	}
	
	@GetMapping("/deleteItem/{itemId}")
	public Message deleteItem(@PathVariable String itemId) {		
		return itemDao.deleteItem(itemDao.getItemById(itemId));
	}
	
	@GetMapping("/getExhaustedItems")
	public ExhaustedItems getExhaustedItems() {
		return itemDao.getExhaustedItems();
	}
	
	//Menu
	
	@GetMapping("/getMenu/{menuId}")
	public Menu getMenuById(@PathVariable String menuId) {
		
		return menuDao.getMenuById(menuId);
	}
	
	@GetMapping("/getMenu")
	public List<Menu> getMenu() {
		
		return menuDao.getMenu();
	}
	
	
	@GetMapping("/deleteMenu/{id}")
	public void deleteMenu(@PathVariable String id) {
		
		menuDao.deleteMenu(menuDao.getMenuById(id));
	}
	
	@PostMapping("/addMenu")
	public Message addMenu(@RequestBody MenuDetails menuDetails) {
		Message msg;
		Menu menu = new Menu();
		if (menuDetails.getMenuId() == null || menuDetails.getMenuId().trim() == "") {
			if (menuDao.checkPresence(menuDetails.getDish())) {
				return new Message(false, "Dish already exists");
			}
			menu.setMenuId(menuDao.generateId(menuDetails.getDish()));
			msg = new Message(true, "Menu dish added");
		}else {
			menu.setMenuId(menuDetails.getMenuId());
			deleteMenu(menuDetails.getMenuId());
			msg = new Message(true, "Menu Updated");
		}
				
		menu.setDish(menuDetails.getDish());
		for(Item_Quantity item_qty : menuDetails.getItemQuantity()) {
			Item item = itemDao.getItemById(item_qty.getItemId());
			menu.addItem(item, item_qty.getQuantity());
		}
		menuDao.addMenu(menu);
		
		return msg;
	}

}

