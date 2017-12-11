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
public class UutinenController {
    
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
    
    
    //Uutisten listaus etusivulla
    @GetMapping("/")
    public String listaaUutiset(Model model){
        
        Pageable pageable1 = PageRequest.of(0, 5, Sort.Direction.DESC, "aika");
        Pageable pageable2 = PageRequest.of(0, Integer.MAX_VALUE,Sort.Direction.DESC,"aika");
        
        model.addAttribute("uutiset", this.uutinenRepository.findAll(pageable1));
        model.addAttribute("kategoriat",this.kategoriaRepository.findAll());
        model.addAttribute("uutisetjulkaisuajanperusteella",this.uutinenRepository.findAll(pageable2));
        
        return "uutiset";
    }
    
    //Näytetään tietyn uutisen uutissivu
    @GetMapping("/uutinen/{id}")
    public String naytaUutinen(@PathVariable Long id, Model model){
        
        Uutinen uutinen = this.uutinenRepository.getOne(id);
        
        model.addAttribute("uutinen", uutinen);
        model.addAttribute("kategoriat", this.kategoriaRepository.findAll());
        
        return "uutinen";
    }
    
    
    @GetMapping("/hallintapaneeli")
    public String hallintapaneeli(Model model){
        
        model.addAttribute("uutiset", this.uutinenRepository.findAll());
        model.addAttribute("kategoriat",this.kategoriaRepository.findAll());
        
        return "hallintapaneeli";
    }
    
    @PostMapping("/hallintapaneeli")
    @Transactional
    public String luoUutinen(@RequestParam String kategoria, @RequestParam String otsikko, @RequestParam String ingressi, @RequestParam String teksti, @RequestParam String nimi, @RequestParam("file") MultipartFile file) throws IOException {
        
        Uutinen uutinen = new Uutinen();
        uutinen.setOtsikko(otsikko);
        uutinen.setIngressi(ingressi);
        uutinen.setTeksti(teksti);
        
        ArrayList<Uutinen> uutiset = new ArrayList();
        
        Kategoria k = new Kategoria(kategoria,uutiset);
        
        
        for(Kategoria kat : this.kategoriaRepository.findAll()){
            if(kat.getNimi().equals(kategoria)){
                k=kat;
            }
        }
        /*
        if(!this.kategoriaRepository.existsByNimi(kategoria)){
            this.kategoriaRepository.save(k);
            
        }*/
        this.kategoriaRepository.save(k);
        
        uutinen.getKategoriat().add(k);
        uutinen.setContent(file.getBytes());
        
        Kirjoittaja kirjoittaja = new Kirjoittaja();
        kirjoittaja.setNimi(nimi);
        
        for(Kirjoittaja kirj : this.kirjoittajaRepository.findAll()){
            if(kirj.getNimi().equals(nimi)){
                kirjoittaja=kirj;
            }   
        }
        
        if(!this.kirjoittajaRepository.existsByNimi(nimi)){
            this.kirjoittajaRepository.save(kirjoittaja);
        }
        
        k.lisaaUutinen(uutinen);
        
        uutinenRepository.save(uutinen);
        
        return "redirect:/hallintapaneeli";
    }
    
    @DeleteMapping("/uutinen/{id}")
    public String poistaUutinen(@PathVariable Long id, Model model) {
        
        if(uutinenRepository.existsById(id)){
            
            uutinenRepository.deleteById(id);            
            return hallintapaneeli(model);
    }
        return "redirect:/hallintapaneeli";
    }
    
    @GetMapping("/{kategoria}")
    public String kategoria(@PathVariable String kategoria, Model model){
        
        model.addAttribute("uutiset", this.kategoriaRepository.findByNimi(kategoria).getUutiset());
        
        return "kategoriat";
    }
    
    @GetMapping("/kategorianmukaan")
    public String jarjestaKategorianMukaan(Model model){
        
        Pageable pageable = PageRequest.of(0,Integer.MAX_VALUE, Sort.Direction.DESC,"kategoriat");
        model.addAttribute("kategoriat",this.kategoriaRepository.findAll());
        model.addAttribute("uutiset",this.uutinenRepository.findAll(pageable));
        return "järjestysKategorianMukaan";
    }
    
    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }
    
    @PostMapping("/login")
    public String login(@RequestParam String kayttajatunnus, @RequestParam String salasana){
        
        if(this.customUserDetailsService.loadUserByUsername(kayttajatunnus).getPassword().equals(salasana)){
            UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(kayttajatunnus);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            authentication.setAuthenticated(true);
            return "redirect:/hallintapaneeli";
        }
        
        return "login";
    }
    
    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request, HttpServletResponse response){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null){
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/";
    }
    
    @GetMapping("/Edellisen_viikon_uutiset")
        public String edellisenViikonUutiset(Model model){
        
        Pageable pageable1 = PageRequest.of(0, Integer.MAX_VALUE, Sort.Direction.DESC, "aika");
        Pageable pageable2 = PageRequest.of(0, Integer.MAX_VALUE,Sort.Direction.DESC,"uutistenLKM");
        
        ArrayList viimeViikonUutiset = new ArrayList();
        LocalDateTime aika = LocalDateTime.now().minusWeeks(1);
        for(Uutinen uutinen : this.uutinenRepository.findAll()){
            if(uutinen.getAika().isAfter(aika)){
                viimeViikonUutiset.add(uutinen);
            }
        }
        model.addAttribute("uutiset", viimeViikonUutiset);
        model.addAttribute("uusimmatUutiset", this.uutinenRepository.findAll(pageable1));
        model.addAttribute("enitenUutisia",this.kategoriaRepository.findAll(pageable2));
        
        return "EdellisenViikonUutiset";
        }
    
    
    
}
