/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 *
 * @author hannu
 */


@SpringBootApplication(scanBasePackages={
"wad", "wad.config", "wad.controller", "wad.domain", "wad.profile", "wad.repository", "wad.service"})
public class UutissivustoApplication {
    
    
    
    public static void main(String[] args) {
        
        
        SpringApplication.run(UutissivustoApplication.class, args);
    }
    
}
