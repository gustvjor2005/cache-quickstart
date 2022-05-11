package org.acme.cache;

import java.time.LocalDate;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;

import io.quarkus.cache.Cache;
import io.quarkus.cache.CacheName;
import io.quarkus.cache.CaffeineCache;
import io.smallrye.mutiny.Uni;

@ApplicationScoped
public class WeatherForecastService {
	
	@CacheName("my-cache")
	Cache cache;

	public Set<Object> getAllCacheKeys(){
		
		return cache.as(CaffeineCache.class).keySet();		
	}
	
	public String getValue(String key) {
		
		return cache.get(key,  k -> getDailyForecast(k)).await().indefinitely();
	}
	
    //@CacheResult(cacheName = "weather-cache")
    public String getDailyForecast(String city) {
        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return " will be " +  " in " + city;
    }

    private String getDailyResult(int dayOfMonthModuloFour) {
        switch (dayOfMonthModuloFour) {
            case 0:
                return "sunny";
            case 1:
                return "cloudy";
            case 2:
                return "chilly";
            case 3:
                return "rainy";
            default:
                throw new IllegalArgumentException();
        }
    }
}
