/**
 * Descrição da classe.
 *
 * @author <a href="mailto:luizhenriquesantana@gmail.com">Luiz Henrique Santana</a>.
 * @version $Revision: 1.1 $
 */
package com.luxoft.instrument.repository;

import org.springframework.data.repository.CrudRepository;

import com.luxoft.instrument.Entity.InstrumentPriceModifier;

/**
 * Descrição da classe.
 *
 * @author <a href="mailto:luizhenriquesantana@gmail.com">Luiz Henrique Santana</a>.
 * @version $Revision: 1.1 $
 */
public interface InstrumentRepository extends CrudRepository<InstrumentPriceModifier, Long> {

	/**
	 * 
	 * Method will find the document by its ID.
	 *
	 * @author <a href="mailto:luizhenriquesantana@gmail.com">Luiz Henrique Santana</a>.
	 * @param id
	 * @return
	 * Document
	 */
	InstrumentPriceModifier findById(Long id);
	
	/**
	 * 
	 * Method will find the document by its ID.
	 *
	 * @author <a href="mailto:luizhenriquesantana@gmail.com">Luiz Henrique Santana</a>.
	 * @param id
	 * @return
	 * Document
	 */
	InstrumentPriceModifier findByName(String name);
	
}
