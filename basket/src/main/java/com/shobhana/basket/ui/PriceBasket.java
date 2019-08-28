package com.shobhana.basket.ui;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.shobhana.basket.dto.ItemList;
import com.shobhana.basket.service.ItemsException;
import com.shobhana.basket.service.ShoppingService;


/**
 * @author shobhana
 * 
 */
public class PriceBasket {

	private static final Logger logger = Logger.getLogger(ShoppingService.class);

	private static ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext(
			new String[] {"ApplicationContext.xml"});

	/**
	 * It calculates the price of the basket.
	 * @param args The args are the ids of the items of the catalog.
	 */
	public static void main(String[] args) {
		Map<String,Integer> shoppingCart = new HashMap<>();
		ShoppingService service = appContext.getBean("service", ShoppingService.class);
		Scanner scan = new Scanner(System.in);
		int quantity;
		int input;
		LocalDate currentDate = null;
		do {
		    System.out.println(
			    "Enter the items to be added to the cart: \n1.Soup\n2.Bread\n3.Milk\n4.Apples\n5.exit");
		    input = scan.nextInt();
		    switch (input) {
		    case 1:
			System.out.println("enter the quantity required:");
			quantity = scan.nextInt();
			shoppingCart.put(ItemList.SOUP, quantity);
			break;
		    case 2:
			System.out.println("enter the quantity required:");
			quantity = scan.nextInt();
			shoppingCart.put(ItemList.BREAD, quantity);
			break;
		    case 3:
			System.out.println("enter the quantity required:");
			quantity = scan.nextInt();
			shoppingCart.put(ItemList.MILK, quantity);
			break;
		    case 4:
			System.out.println("enter the quantity required:");
			quantity = scan.nextInt();
			shoppingCart.put(ItemList.APPLE, quantity);
			break;
		    default:
			break;

		    }
		} while (input != 5);
		System.out.println("Enter the date: 1. today, 2. 5 days");
		int date = scan.nextInt();
		if (date > 1) {
			 currentDate = LocalDate.now().plusDays(5);
		} else {
			 currentDate = LocalDate.now();
		}
		try {
			service.processShoppingList(shoppingCart,currentDate);
		} catch (ItemsException e) {
			e.printStackTrace();
		}
	}
}
