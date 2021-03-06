package com.example.matasolutions.pintindex;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import org.junit.Test;
import static org.junit.Assert.*;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.util.ArrayList;


public class HelperMethodsTest {


    @Test
    public void getHoursMinutesNow() {
    }

    @Test
    public void Test_HelperMethods_CalculationByDistance() {

        //Newcastle
        LatLng ncl_Lat = new LatLng(54.9783, 1.6178);
        //London
        LatLng ldn_lat = new LatLng(51.5074, 0.1278);
        //Amsterdam
        LatLng ams_lat = new LatLng(52.3680, 4.9036);

        double actualResult1 = HelperMethods.CalculationByDistance(ncl_Lat,ldn_lat);

        double expectedResult1 = 398.45;

        assertEquals(expectedResult1, actualResult1,1);

        double actualResult2 = HelperMethods.CalculationByDistance(ncl_Lat,ams_lat);

        double expectedResult2 = 361.98;

        assertEquals(expectedResult2, actualResult2,1);

    }


    @Test
    public void Test_HelperMethods_IsPubOpen() {

        try {
            assertTrue(HelperMethods.isPubOpen("15:00","02:00","01:59"));
            assertFalse(HelperMethods.isPubOpen("10:00","02:00","02:30"));
            assertTrue(HelperMethods.isPubOpen("12:00", "01:00", "17:45"));

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void LookupProductPrice() {
    }

    @Test
    public void Test_HelperMethods_DoProductsMatch() {

        Product p1 = new Product(Brand.HEINEKEN, DrinkType.BEER,Amount.PINT );

        Product p2 = new Product(Brand.HEINEKEN, DrinkType.BEER,Amount.PINT );
        Product p3 = new Product(Brand.HEINEKEN, DrinkType.BEER,Amount.HALF_PINT );
        Product p4 = new Product(Brand.STELLA_ARTOIS, DrinkType.BEER,Amount.PINT );

        assertTrue(HelperMethods.DoProductsMatch(p1,p2));

        assertFalse(HelperMethods.DoProductsMatch(p1,p3));

        assertFalse(HelperMethods.DoProductsMatch(p1,p4));

    }

    @Test
    public void Test_HelperMethods_FindProductinPub() {

        ArrayList<Product> products =new ArrayList<>();

        products.add(new Product(Brand.STELLA_ARTOIS, DrinkType.BEER,Amount.PINT ));
        products.add(new Product(Brand.HEINEKEN, DrinkType.BEER,Amount.PINT));
        products.add(new Product(Brand.PERONI, DrinkType.BEER,Amount.PINT));
        products.add(new Product(Brand.STELLA_ARTOIS, DrinkType.BEER,Amount.PINT));
        Pub pub = new Pub();

        ArrayList<Price> prices = new ArrayList<>();

        prices.add(new Price(products.get(0), 3.5));
        prices.add(new Price(products.get(1), 4.5));
        prices.add(new Price(products.get(2), 5.5));
        prices.add(new Price(products.get(3), 6.5));

        Product expectedProduct = new Product(Brand.HEINEKEN, DrinkType.BEER,Amount.PINT);
        double expectedPrice = 4.5;


        pub.setPrices(new Prices(prices));

        assertEquals(expectedPrice,HelperMethods.FindProductinPub(expectedProduct,pub).price,0.001);

    }

    @Test
    public void findPubsWithProduct() {




    }
}