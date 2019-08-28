/**
 * 
 */
package com.shobhana.basket.service;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import com.shobhana.basket.dto.Catalog;
import com.shobhana.basket.dto.CatalogItem;
import com.shobhana.basket.service.discount.IDiscount;

import lombok.Getter;
import lombok.Setter;


/**
 * @author shobhana
 * 
 */
public class ShoppingService {

	private static final Logger logger = Logger.getLogger(ShoppingService.class);

	/**
	 * The catalog of items and containers.
	 */
	@Setter
	@Getter
	private Catalog catalog;

	/**
	 * The available discounts.
	 */
	@Setter
	@Getter
	private Set<IDiscount> discounts;

	/**
	 * It processes a shopping list.
	 * @param idsItems The ids of items of the shopping list.
	 */
	public float processShoppingList(Map<String,Integer> shoppingCart,LocalDate date)
			throws ItemsException {
		checkItems(shoppingCart); 

		// We create a list of basket items.
		List<CatalogItem> basketItems = shoppingCart.keySet().stream()
				.map(idItem -> catalog.getAvailableItems().get(idItem))
				.collect(Collectors.toList());

		// We calculate the subtotal.
		float subtotalPrice = getSubtotalPrice(basketItems,shoppingCart);

		// We apply the discounts.
		return getTotalPrice(basketItems, subtotalPrice,date,shoppingCart);
	}

	/**
	 * It returns the subtotal price, before applying discounts.
	 * @param basketItems List of items of the basket.
	 * @return Subtotal price.
	 */
	private float getSubtotalPrice(List<CatalogItem> basketItems,Map<String,Integer> shoppingCart) {
		float subTotalPrice = 0;
		for (CatalogItem item : basketItems) {
			System.out.println(item.getTicketDescription() + ": "
					+ item.getId() + ": "
					+ item.getPrice());
			subTotalPrice = subTotalPrice + (item.getPrice()*shoppingCart.get(item.getId()));
		}
		System.out.println("Subtotal: " + subTotalPrice);
		return subTotalPrice;
	}

	/**
	 * It returns the total price of the basket, after applying the discounts of the catalog.
	 * @param basketItems The items of the basket.
	 * @param subtotal The subtotal.
	 * @return The total of the purchase.
	 */
	private float getTotalPrice(List<CatalogItem> basketItems, float subtotal, LocalDate date,
			Map<String,Integer> shoppingCart) {
		float totalPrice = subtotal;
		for (IDiscount discount : discounts) {
			float amount = discount.getValue(basketItems,date,shoppingCart);
			if (amount > 0.000001) {
				System.out.println(discount.getTicketDescription() + ": "
						+ amount);
				totalPrice = totalPrice - amount;
			}
		}
		if (totalPrice == subtotal) {
			System.out.println("(No offers available)");
		}
		System.out.println("Total price: " + totalPrice);
		return totalPrice;
	}

	/**
	 * It checks if the ids match the catalog.
	 * @param idsItems The ids of the items.
	 * @throws ItemsException Exception if there are ids wrong.
	 */
	private void checkItems(Map<String,Integer> shoppingCart) throws ItemsException {
		List<String> wrongIds = shoppingCart.keySet().stream()
				.filter(idItem -> !catalog.getAvailableItems().containsKey(idItem))
				.collect(Collectors.toList());

		if (!wrongIds.isEmpty()) {
			throw new ItemsException(wrongIds, "Ids of items don't found in the catalog. ");
		}
	}
}
