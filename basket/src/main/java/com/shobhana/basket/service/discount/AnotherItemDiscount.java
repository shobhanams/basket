/**
 * 
 */
package com.shobhana.basket.service.discount;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import com.shobhana.basket.dto.CatalogItem;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

/**
 * A discount that is applied only when a specific number of containers of an
 * item are bought, and it's applied with a percentage of discount over the
 * price of another container of another item.
 * Every n-containers of one item you buy, you have right to have a discount over
 * 1 container of another item.
 * @author shobhana
 * 
 */
@Data
public class AnotherItemDiscount implements IDiscount {

	private static final Logger logger = Logger.getLogger(AnotherItemDiscount.class);

	/**
	 * The description
	 */
	private String description;

	/**
	 * The description to use in the ticket.
	 */
	private String ticketDescription;

	/**
	 * The item required to apply the discount.
	 */
	private CatalogItem itemRequired;

	/**
	 * The number of items required to apply the discount.
	 */
	private int numItemsRequired;

	/**
	 * The item to which applying the discount over.
	 */
	private CatalogItem itemDiscounted;
	
	/**
	 * The init date of the application of the discount
	 */
	private Date initDate;

	/**
	 * The finish date of the application of the discount
	 */
	private Date endDate;

	/**
	 * The percentage of discount to apply.
	 */
	@Setter(AccessLevel.NONE)
	private double percentage;

	/**
	 * It sets the percentage of discount to apply.
	 * @param percentage The percentage to set
	 */
	public void setPercentage(double percentage) {
		this.percentage = percentage;
		assert ((percentage >= 0) && (percentage <= 100));
	}

	private long getTimesToApply(Map<String,Integer> shoppingCart) {
		long numItemsFound;
		if(shoppingCart.get(itemRequired.getId()) != null) {
			 numItemsFound = shoppingCart.get(itemRequired.getId());
		}else {
			numItemsFound = 0;
		}
		return numItemsFound / numItemsRequired;
	}

	@Override
	/**
	 * {@inheritDoc}Date
	 */
	public float getValue(List<CatalogItem> basket,LocalDate currentDate,Map<String,Integer> shoppingCart) {
		LocalDate initialDate = initDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate finalDate = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		if (initialDate.isBefore(currentDate)
				&& finalDate.isAfter(currentDate)) {
		long numTimesDiscount = getTimesToApply(shoppingCart);

		List<CatalogItem> itemsToDiscount = basket.stream()
				.filter(item -> item.equals(itemDiscounted))
				.limit(numTimesDiscount).collect(Collectors.toList());

		float discount = (float) itemsToDiscount.stream()
				.mapToDouble(item -> item.getPrice() * percentage / 100)
				.sum();

		if (logger.isDebugEnabled()) {
			logger.debug("Discount over items " + itemDiscounted.getId() + " is "
					+ discount);
		}
		return discount;
		} else return (float)0.0;
	}

}
