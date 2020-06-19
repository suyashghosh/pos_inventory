package inventory.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import inventory.entity.Item_Quantity;
import inventory.entity.Menu;
import inventory.entity.MenuDetails;
import inventory.entity.Message;

@Repository
public class MenuDao {

	private EntityManager entityManager;
	
	@Autowired
	public MenuDao(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}
	
	@Transactional
	public Menu getMenuById(String id) {
		
//		TypedQuery<Menu> query = entityManager.createQuery("from Menu where m_id = ?1",Menu.class); // table name should be caps		
//		query.setParameter(1,id);
//		Menu menu = entityManager.find(Menu.class, id);	

		Menu menu = entityManager.createQuery(
			    "select m " +
			    "from Menu m " +
			    "join fetch m.items re " +
			    "join fetch re.item " +
			    "where m.menuId = :menuId", Menu.class)
			.setParameter( "menuId", id )
			.getSingleResult();
		return menu;
	}
	
	@Transactional
	public List<Menu> getMenu() {
		TypedQuery<Menu> query = entityManager.createQuery("from Menu", Menu.class);
		List<Menu> menu = query.getResultList();
		return menu;
	}
	
	@Transactional
	public boolean checkPresence(String searchKeyword) {
		TypedQuery query = (TypedQuery) entityManager.createQuery("from Menu where lower(dish) = lower(:keyword)");
		query.setParameter("keyword",searchKeyword);		
		if (query.getResultList().size() > 0) {
			return true;
		}
		return false;
	}
	
	@Transactional
	public String generateId(String dish) {
		
		String twoChar = dish.trim().substring(0, 2);		
		TypedQuery query = (TypedQuery) entityManager.createQuery("from Menu where lower(dish) like lower(:keyword)");
		query.setParameter("keyword",twoChar+"%");
		int count = query.getResultList().size();
		
		if(count > 0) {
			return (twoChar.toUpperCase()+count);
		}else {
			return (twoChar.toUpperCase());
		}
	}
	
	@Transactional
	public void addMenu(Menu menu) {		
		entityManager.merge(menu);		
	}
	
	@Transactional
	public void deleteMenu(Menu menu) {		
		entityManager.remove(menu);		
	}
	
	@Transactional
	public List<Item_Quantity> getItemQuantityByMenuId(String menuId) {
		Query query = entityManager.createNativeQuery("select i_id, quantity from recipe where m_id = :menuId");
		query.setParameter("menuId", menuId);
		List itemQuant = query.getResultList();
		
		List<Item_Quantity> itemQuantity = new ArrayList();
		
		for(int i=0; i<itemQuant.size(); i++) {
			Object[] obj = (Object[]) itemQuant.get(i);
			Item_Quantity temp = new Item_Quantity();
			temp.setItemId(obj[0].toString());
			temp.setQuantity(((Float)obj[1]).floatValue());
			itemQuantity.add(temp);
		}
		return itemQuantity;
	}
}
