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
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
/**
 *
 * @author hannu
 */
@Controller
public class InitController {
    
    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    
    @Autowired
    private UutinenRepository uutinenRepository;
    
    @Autowired 
    private KategoriaRepository kategoriaRepository;
    
    @Autowired
    private KirjoittajaRepository kirjoittajaRepository;
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @PostConstruct
    @Transactional
    public void init(){
        
        //Luodaan oletuskäyttäjä
    
        Account kayttaja = new Account();
        kayttaja.setUsername("oletuskäyttäjä");
        kayttaja.setPassword(passwordEncoder().encode("salasana"));
        kayttaja = this.accountRepository.save(kayttaja);
       
        
        //Luodaan valmiina oleva kirjoittaja
        
        ArrayList<Kirjoittaja> kirjoittajat = new ArrayList();
        Kirjoittaja kirjoittaja = new Kirjoittaja();
        kirjoittaja.setNimi("Hannu");
        kirjoittajat.add(kirjoittaja);
        
        //Valmiina olevat uutiset
        Uutinen eka = new Uutinen();
        eka.setOtsikko("Tiistaiksi voimakasta lumisadetta luvassa");
        eka.setIngressi("Ajokeli muuttuu erittäin huonoksi Etelä- ja Keski-Suomessa");
        eka.setTeksti("Lumisade alkaa maanantain ja tiistain välisenä yönä, mutta muuttuu eteläisessä Suomessa päivän mittaan räntä- ja vesisateeksi. "
                + "Pohjoisemmassa lunta sataa koko päivän.");
        eka.setKirjoittajat(kirjoittajat);
       
        Uutinen toka = new Uutinen();
        toka.setOtsikko("Bitcoin johdannaisten kauppa aloitettiin chicagon pörssissä");
        toka.setIngressi("Asiantuntijat varoittelevat mahdollisesta romahduksesta");
        toka.setTeksti("Bitcoin futuurien myynti alkoi pörssissä ja hinta nousi nopeasti 15 000 dollarista 18 000 dollariin. "
                + "Bitcoinin hinta oli vielä vuoden alussa n. tuhat dollaria, mutta hinta on noussut nyt yli 16 000 dollarin."
                + " Arvo perustuu kuitenkin vain ostajien odotuksiin tulevasta arvonnoususta, joten asiantuntijat ovat huolissaan romahduksesta.");
        toka.setKirjoittajat(kirjoittajat);
        
        Uutinen kolmas = new Uutinen();
        kolmas.setOtsikko("Bitcoinin hinta noussut hurjiin lukemiin");
        kolmas.setIngressi("Hinta kävi jopa yli 16 000 dollarissa");
        kolmas.setTeksti("Bitcoinin hinta oli korkeimmillaan jopa 16 666 dollaria, mutta laski sitten alle 15 000 dollariin."
                + "Vuoden alussa hinta oli n. 1000 dollaria. Viikossa kurssi nousi kuitenkin yli 30 prosenttia.");
        kolmas.setKirjoittajat(kirjoittajat);
        
        ArrayList<Uutinen> uutiset = new ArrayList();
        ArrayList<Uutinen> lista = new ArrayList();
        
        Kategoria saa = new Kategoria("Sää",uutiset);
        Kategoria talous = new Kategoria("Talous",uutiset);
        
        lista.add(eka);
        lista.add(toka);
        lista.add(kolmas);
        
        
        eka.getKategoriat().add(saa);
        toka.getKategoriat().add(talous);
        kolmas.getKategoriat().add(talous);
        this.uutinenRepository.saveAll(lista);

        
        this.kirjoittajaRepository.save(kirjoittaja);
        
        saa.lisaaUutinen(eka);
        saa.lisaaUutinen(toka);
        talous.lisaaUutinen(kolmas);
        
        this.kategoriaRepository.save(saa);
        this.kategoriaRepository.save(talous);


    }
    
}
