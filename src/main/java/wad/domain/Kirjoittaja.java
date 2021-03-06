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

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.jpa.domain.AbstractPersistable;
import lombok.NoArgsConstructor;
import javax.persistence.OneToMany;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Kirjoittaja extends AbstractPersistable<Long> {
    
    private String nimi;
    
    @ManyToMany
    private List<Uutinen> uutiset;
    
}
