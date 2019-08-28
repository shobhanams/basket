/**
 * 
 */
package com.shobhana.basket.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.shobhana.basket.dto.Catalog;
import com.shobhana.basket.test.SpringTest;


/**
 * @author shobhana
 * 
 */
public class ShoppingServiceTest extends SpringTest {

	static final Logger logger = Logger.getLogger(ShoppingService.class);

	/**
	 * Service to test
	 */
	@Autowired
	private ShoppingService service;

	/**
	 * Catalog
	 */
	@Autowired
	private Catalog catalog;
	
	/**
	 * It tests the initialization of the service
	 */
	@Test
	public void testService() {
		assertNotNull("service not initialised", service);
	}

	/**
	 * It tests the process of simple shopping lists.
	 */
	@Test
	public void testSimpleProcess1() throws Exception {
		LocalDate testDate = LocalDate.now();
		service.setCatalog(catalog);
		Map<String,Integer> itemsMap = new HashMap<>();
		itemsMap.put("Soup",3);
		itemsMap.put("Bread",2);
		float price = service.processShoppingList(itemsMap,testDate);
		if (logger.isDebugEnabled()) {
			logger.debug("The price is " + price);
		}
		assertEquals("Unexpected Total price", 3.15, (double)price, 0.000001);
	}

	/**
	 * It tests the process of simple shopping lists with discount over the bread.
	 */
	@Test
	public void testSimpleProcess2() throws Exception {
		LocalDate testDate = LocalDate.now();
		service.setCatalog(catalog);
		Map<String,Integer> itemsMap = new HashMap<>();
		itemsMap.put("Apple",6);
		itemsMap.put("Milk",1);
		float price = service.processShoppingList(itemsMap,testDate);
		if (logger.isDebugEnabled()) {
			logger.debug("The price is " + price);
		}
		assertEquals("Unexpected total price", 1.90, (double)price, 0.000001);
	}
	
	@Test
	public void testSimpleProcess3() throws Exception {
		LocalDate testDate = LocalDate.now().plusDays(5);
		service.setCatalog(catalog);
		Map<String,Integer> itemsMap = new HashMap<>();
		itemsMap.put("Apple",6);
		itemsMap.put("Milk",1);
		float price = service.processShoppingList(itemsMap,testDate);
		if (logger.isDebugEnabled()) {
			logger.debug("The price is " + price);
		}
		assertEquals("Unexpected total price", 1.84, (double)price, 0.000001);
	}
	
	@Test
	public void testSimpleProcess4() throws Exception {
		LocalDate testDate = LocalDate.now().plusDays(5);
		service.setCatalog(catalog);
		Map<String,Integer> itemsMap = new HashMap<>();
		itemsMap.put("Apple",3);
		itemsMap.put("Soup",2);
		itemsMap.put("Bread",1);
		float price = service.processShoppingList(itemsMap,testDate);
		if (logger.isDebugEnabled()) {
			logger.debug("The price is " + price);
		}
		assertEquals("Unexpected total price", 1.97, (double)price, 0.000001);
	}
}
