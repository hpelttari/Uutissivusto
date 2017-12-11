/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.controller;

import java.io.IOException;
import java.time.LocalDate;
import javax.transaction.Transactional;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import wad.domain.*;
import wad.repository.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.*;
import java.util.List;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.bcrypt.*;
import wad.service.CustomUserDetailsService;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import javax.servlet.http.*;
import org.springframework.security.core.*;
import org.springframework.security.core.userdetails.UserDetails;
import java.time.LocalDateTime;
/**
 *
 * @author hannu
 */
@Controller
public class InitController {
    
        @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    
    @Autowired
    private UutinenRepository uutinenRepository;
    
    @Autowired 
    private KategoriaRepository kategoriaRepository;
    
    @Autowired
    private KirjoittajaRepository kirjoittajaRepository;
    
    @PostConstruct
    @Transactional
    public void init(){
        
        //Luodaan oletuskäyttäjä
        Account kayttaja = new Account();
        kayttaja.setUsername("hannu");
        kayttaja.setPassword(passwordEncoder.encode("salasana"));
        kayttaja = this.accountRepository.save(kayttaja);
        
        //Luodaan valmiina oleva kirjoittaja
        ArrayList<Kirjoittaja> kirjoittajat = new ArrayList();
        Kirjoittaja kirjoittaja = new Kirjoittaja();
        kirjoittaja.setNimi("Hannu");
        kirjoittajat.add(kirjoittaja);
        
        //Valmiina olevat uutiset
        Uutinen eka = new Uutinen();
        eka.setOtsikko("Ensimäinen Uutinen");
        eka.setIngressi("Hieno Ingressi");
        eka.setTeksti("Kiinnostavaa asiaa");
        eka.setKirjoittajat(kirjoittajat);
       
        Uutinen toka = new Uutinen();
        toka.setOtsikko("Toinen Uutinen");
        toka.setIngressi("Vielä hienompi Ingressi");
        toka.setTeksti("Suht kiinostavaa asiaa");
        toka.setKirjoittajat(kirjoittajat);
        
        Uutinen kolmas = new Uutinen();
        kolmas.setOtsikko("Kolmas Uutinen");
        kolmas.setIngressi("Astetta Huonompi Ingressi");
        kolmas.setTeksti("Samaa paskaa eri paketissa");
        kolmas.setKirjoittajat(kirjoittajat);
        
        ArrayList<Uutinen> uutiset = new ArrayList();
        ArrayList<Uutinen> lista = new ArrayList();
        
        Kategoria k = new Kategoria("Jotain",uutiset);
        Kategoria k1 = new Kategoria("Muuta",uutiset);
        
        lista.add(eka);
        lista.add(toka);
        lista.add(kolmas);
        
        
        eka.getKategoriat().add(k);
        toka.getKategoriat().add(k);
        kolmas.getKategoriat().add(k1);
        this.uutinenRepository.saveAll(lista);

        
        this.kirjoittajaRepository.save(kirjoittaja);
        
        k.lisaaUutinen(eka);
        k.lisaaUutinen(toka);
        k1.lisaaUutinen(kolmas);
        
        this.kategoriaRepository.save(k);
        this.kategoriaRepository.save(k1);


    }
    
}
