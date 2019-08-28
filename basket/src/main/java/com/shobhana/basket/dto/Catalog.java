/**
 * 
 */
package com.shobhana.basket.dto;

import lombok.Data;

import java.util.Map;

/**
 * The information about the available items, and the sort of containers and
 * prices availables for each item.
 * @author shobhana
 * 
 */
@Data
public class Catalog {

	/**
	 * It contains the relationship of items available in the catalog, and the
	 * available containers, indexed by the id of the item.
	 */
	private Map<String, CatalogItem> availableItems;
}
