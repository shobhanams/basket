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
 * A discount applicable to all items with the same id, during a period.
 * @author shobhana
 * 
 */
@Data
public class SingleTemporaryItemDiscount implements IDiscount {

	private static final Logger logger = Logger.getLogger(SingleTemporaryItemDiscount.class);

	/**
	 * The description.
	 */
	private String description;

	/**
	 * The description to use in tickets.
	 */
	private String ticketDescription;

	/**
	 * The item to discount.
	 */
	private CatalogItem item;

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

	@Override
	/**
	 * {@inheritDoc}
	 */
	public float getValue(List<CatalogItem> basket,LocalDate currentDate,Map<String,Integer> shoppingCart) {
		LocalDate initialDate = initDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate finalDate = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		if (initialDate.isBefore(currentDate)
				&& finalDate.isAfter(currentDate)) {
			List<CatalogItem> itemsToDiscount = basket.stream()
					.filter(itemToCheck -> itemToCheck.equals(item))
					.collect(Collectors.toList());

			float discount = (float) itemsToDiscount.stream()
					.mapToDouble(item -> (item.getPrice()*shoppingCart.get(item.getId())) * percentage / 100)
					.sum();

			if (logger.isDebugEnabled()) {
				logger.debug("Discount of item " + item.getId() + " is " + discount);
			}
			return discount;

		} else return (float)0.0;
	}

}
