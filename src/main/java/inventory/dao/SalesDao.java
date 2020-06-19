package inventory.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import inventory.entity.Sales;
import inventory.entity.SoldMenuDetails;

@Repository
public class SalesDao {

	private EntityManager entityManager;
	
	@Autowired
	public SalesDao(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}
	
	@Transactional
	public void addSales(Sales sales) {
		entityManager.merge(sales);
	}
	
	@Transactional
	public List<SoldMenuDetails> getSoldMenuDetails(String sellId) {
		Query query = entityManager.createNativeQuery("select m_id, dish, quantity_sold from sales_menu where sell_id = :sellId");
		query.setParameter("sellId", sellId);
		List soldMenu = query.getResultList();
		
		List<SoldMenuDetails> soldMenuDetails = new ArrayList();
		for(int i=0; i<soldMenu.size(); i++) {
			Object[] obj = (Object[]) soldMenu.get(i);
			SoldMenuDetails temp = new SoldMenuDetails();
			temp.setMenuId(obj[0].toString());
			temp.setDish(obj[1].toString());
			temp.setQuantitySold(((Integer)obj[2]).intValue());
			soldMenuDetails.add(temp);
		}
		return soldMenuDetails;
	}
	
	@Transactional
	public List<Sales> getSales(String startDate, String endDate){
		Query query = entityManager.createNativeQuery("select * from sales where date(time) >= :startDate and date(time) <= :endDate", Sales.class);
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);
		if (startDate == null && endDate == null) {
			query = entityManager.createQuery("from Sales",Sales.class);
		}
		
		List<Sales> sales = query.getResultList();
		return sales;
	}
	
//	@Transactional
//	public void deleteSales(){
//		TypedQuery<Sales> query = entityManager.createQuery("from Sales where sellId = 'we'",Sales.class);
//		Sales sales = query.getSingleResult();
//		entityManager.remove(sales);
//		
//	}
}
