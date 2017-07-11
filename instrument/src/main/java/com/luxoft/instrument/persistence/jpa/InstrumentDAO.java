/**
 * Descrição da classe.
 *
 * @author <a href="mailto:luizhenriquesantana@gmail.com">Luiz Henrique Santana</a>.
 * @version $Revision: 1.1 $
 */
package com.luxoft.instrument.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luxoft.instrument.Entity.InstrumentPriceModifier;

/**
 * Descrição da classe.
 *
 * @author <a href="mailto:luizhenriquesantana@gmail.com">Luiz Henrique Santana</a>.
 * @version $Revision: 1.1 $
 */
public interface InstrumentDAO extends JpaRepository<InstrumentPriceModifier, Long> {

}