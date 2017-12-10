/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.domain;

/**
 *
 * @author hannu
 */

import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.*;
import java.util.*;


@AllArgsConstructor
@Data
@Entity
public class Uutinen extends AbstractPersistable<Long> {
    
    private String otsikko;
    private String ingressi;
    private String teksti;
    private LocalDateTime aika;
    
    @Lob
    @Basic(fetch=FetchType.LAZY)
    private byte[] content;
    
    @ManyToMany(mappedBy="uutiset")
    private List<Kirjoittaja> kirjoittajat;
    
    @ManyToMany(cascade=CascadeType.ALL, mappedBy="uutiset")
    private List<Kategoria> kategoriat;
    
    public Uutinen(){
        this.aika=LocalDateTime.now();
        this.kategoriat=new ArrayList<>();
        this.kirjoittajat=new ArrayList<>();
    }
    
}
