package com.dark_horse.controller;

import com.dark_horse.rate_limiter.annotation.RateLimiter;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api")
@RateLimiter(limit = 3)
public class TestController {
 @GetMapping("test/{id}")
 @Cacheable(key = "id")
 public String getdata(@PathVariable int id)
 {
     return "Hello";
 }
}
