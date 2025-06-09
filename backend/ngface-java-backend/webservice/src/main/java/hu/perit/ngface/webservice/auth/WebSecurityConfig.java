/*
 * Copyright 2020-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package hu.perit.ngface.webservice.auth;

import hu.perit.spvitamin.spring.security.auth.SimpleHttpSecurityBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

/**
 * #know-how:simple-httpsecurity-builder
 *
 * @author Peter Nagy
 */

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig
{
    @Bean
    @Order(1)
    public SecurityFilterChain permitAll(HttpSecurity http) throws Exception
    {
        SimpleHttpSecurityBuilder.newInstance(http)
            .defaults()
            .authorizeRequests(i -> i.requestMatchers("/**").permitAll())
            .and().sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.ALWAYS));

        return http.build();
    }
}
