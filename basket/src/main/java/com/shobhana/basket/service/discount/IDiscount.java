/**
 * 
 */
package com.shobhana.basket.service.discount;


import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.shobhana.basket.dto.CatalogItem;

/**
 * @author shobhana
 *
 */
public interface IDiscount {
	
	/**
	 * It returns the description.
	 * @return The description.
	 */
	public String getDescription();
	
	/**
	 * It returns the description to use in tickets.
	 * @return The description to use in tickets.
	 */
	public String getTicketDescription();
	
	/**
	 * It returns the discount to apply to a list of items.
	 * @param basket List of items.
	 * @return the amount to discount.
	 */
	public float getValue(List<CatalogItem> basket,LocalDate date,Map<String,Integer> shoppingCart);
	
}
