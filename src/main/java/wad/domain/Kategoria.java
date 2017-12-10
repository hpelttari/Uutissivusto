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
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import org.springframework.data.jpa.domain.AbstractPersistable;
import java.util.ArrayList;
import javax.persistence.Column;

@NoArgsConstructor
//@AllArgsConstructor
@Data
@Entity
public class Kategoria extends AbstractPersistable<Long> {
    
    @Column
    private String nimi;
    
    private int uutistenLKM;
        
    @ManyToMany
    private List<Uutinen> uutiset;
    
    public Kategoria(String nimi, ArrayList lista){
        
        this.uutiset = lista;
        this.nimi=nimi;
        this.uutistenLKM=this.uutiset.size();
        
    }
    
    public void lisaaUutinen(Uutinen lisattava){
        this.uutiset.add(lisattava);
    }
    
    public int getUutistenLKM(){
        return this.uutistenLKM;
    }
    
}
