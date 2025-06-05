package com.mystore.shopstore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

// annotation for configuration , it tells to Spring to run it when program run
@Configuration
// a Java class for Security Configuration
public class SecurityConfig {
    // bean is a object of a class which made by Spring not by new
    @Bean
    // this method make a object of type SecurityFilterChain which manage all HTTP
    // security config
    // HttpSecurity Http inject by spring for :
    // چطور درخواستها احراز هویت بشوند
    // دسترسی به مسیر چگونه باشد
    // CORS, CSRF, فرم لاگین و session, logout
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll());
        return http.build();
    }
    // CSRF رو غیرفعال می‌کنه. CSRF یک مکانیزم امنیتی هست برای جلوگیری از ارسال
    // درخواست مخرب از سمت مرورگر.
    // برای پروژه‌هایی که فرم POST ندارن یا API هستن یا در فاز تست و توسعه هستن،
    // معمولاً CSRF خاموش می‌شه تا راحت‌تر تست کنیم.
    // اگر CSRF فعال بود، درخواست‌های POST و DELETE و PUT که توشون CSRF token نبود،
    // رد می‌شدن.

    // auth.anyRequest().permitAll());
    // هر کسی می‌تونه به همه مسیرهای وب‌سایت دسترسی داشته باشه، حتی /admin یا
    // /checkout یا غیره. بدون لاگین.
    // در حالت فعلی، هیچ محافظتی روی هیچ مسیر وب وجود نداره. انگار امنیت غیرفعاله.
}
