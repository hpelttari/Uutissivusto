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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import wad.domain.*;
import wad.repository.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.*;

/**
 *
 * @author hannu
 */
@Controller
public class UutinenController {
    
    @Autowired
    private UutinenRepository uutinenRepository;
    
    @Autowired 
    private KategoriaRepository kategoriaRepository;
    
    @Autowired
    private KirjoittajaRepository kirjoittajaRepository;
    
    @GetMapping("/")
    public String listaaUutiset(Model model){
        
        Pageable pageable1 = PageRequest.of(0, 5, Sort.Direction.DESC, "aika");
        Pageable pageable2 = PageRequest.of(0, Integer.MAX_VALUE,Sort.Direction.DESC,"aika");
        
        model.addAttribute("uutiset", this.uutinenRepository.findAll(pageable1));
        model.addAttribute("kategoriat",this.kategoriaRepository.findAll());
        model.addAttribute("uutisetjulkaisuajanperusteella",this.uutinenRepository.findAll(pageable2));
        
        return "uutiset";
    }
    
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
        
        Kategoria k = new Kategoria();
        k.setNimi(kategoria);
        
        for(Kategoria kat : this.kategoriaRepository.findAll()){
            if(kat.getNimi().equals(kategoria)){
                k=kat;
            }
        }
        
        if(!this.kategoriaRepository.existsByNimi(kategoria)){
            this.kategoriaRepository.save(k);
            
        }
        
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
        
        model.addAttribute("uutiset", this.kategoriaRepository.findByNimi(kategoria).getUutinen());
        
        return "kategoriat";
    }
    
    
}