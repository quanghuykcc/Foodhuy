package com.devlin.core.util;

import com.devlin.core.model.entities.FavoriteRestaurant;
import com.devlin.core.model.entities.Restaurant;

import org.greenrobot.eventbus.EventBus;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 9/6/2016.
 */
public class Utils {
    public static List<Integer> updateFavoriteRestaurants(List<Restaurant> restaurants, List<FavoriteRestaurant> favoriteRestaurants) {
        EventBus eventBus = EventBus.getDefault();
        List<Integer> indices = new ArrayList<>();

        if (restaurants == null || favoriteRestaurants == null) {
            return indices;
        }
        if (restaurants.size() == 0 || favoriteRestaurants.size() == 0) {
            return indices;
        }

        int restaurantNumbers = restaurants.size();
        for (int i = 0; i < restaurantNumbers; i++) {
            Restaurant restaurant = restaurants.get(i);
            int favoriteNumbers = favoriteRestaurants.size();

            int j;
            for (j = 0; j < favoriteNumbers; j++) {
                FavoriteRestaurant favoriteRestaurant = favoriteRestaurants.get(j);
                if (restaurant.getId() == favoriteRestaurant.getRestaurantId()) {
                    if (!restaurant.isFavorite()) {
                        restaurant.setFavorite(true);
                        indices.add(i);

                    }
                    break;
                }


            }
            if (j == favoriteNumbers && restaurant.isFavorite()) {
                restaurant.setFavorite(false);
                indices.add(i);
            }
        }
        return indices;
    }

}
