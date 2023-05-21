package exercise.controller;

import exercise.CityNotFoundException;
import exercise.dto.WeatherDto;
import exercise.dto.WeatherShortDto;
import exercise.model.City;
import exercise.repository.CityRepository;
import exercise.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.StreamSupport;

@RestController
public class CityController {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/cities")
    public Iterable<City> getAllCities() {
        return cityRepository.findByNameIsNotNullOrderByName();
    }

    @GetMapping("/cities/{id}")
    public WeatherDto getWeatherByCityId(@PathVariable("id") long id) {
        var city = cityRepository.findById(id)
                .orElseThrow(() -> new CityNotFoundException("City with id " + id + " not found"));

        return weatherService.getWeather(city.getName());
    }

    @GetMapping("/search")
    public List<WeatherShortDto> findCity(@RequestParam(value = "name", required = false) String name) {
        var cities = name != null ? cityRepository.findByNameStartingWithIgnoreCase(name) : getAllCities();
        return StreamSupport.stream(cities.spliterator(), false)
                .map(it -> {
                    var weather = getWeatherByCityId(it.getId());
                    return new WeatherShortDto(it.getName(), String.valueOf(weather.temperature()));
                })
                .toList();
    }
}

