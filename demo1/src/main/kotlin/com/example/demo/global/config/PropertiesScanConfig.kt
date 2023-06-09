package com.example.demo.global.config

import com.example.demo.global.security.SecurityProperties
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.context.annotation.Configuration

@ConfigurationPropertiesScan(
    basePackageClasses = [
        SecurityProperties::class
    ]
)
@Configuration
class PropertiesScanConfig {
}