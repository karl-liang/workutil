package ${serviceImplPackageName};

import ${serviceIntfPackageName}.${className}Service;
import ${packageName}.${className};
import ${reposityPackageName}.${className}Repository;
import ${dtoPackageName}.${className}DTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.mapstruct.*;
import java.util.Optional;
/**
 * Service Implementation for managing JobHistory.
 */
@Service
@Transactional
public class ${className}ServiceImpl implements ${className}Service {

    private final Logger log = LoggerFactory.getLogger(${className}ServiceImpl.class);

    private final ${className}Repository ${className?uncap_first}Repository;

    private final ${className}Mapper ${className?uncap_first}Mapper;

    public ${className}ServiceImpl(${className}Repository ${className?uncap_first}Repository,${className}Mapper ${className?uncap_first}Mapper) {
        this.${className?uncap_first}Repository = ${className?uncap_first}Repository;
        this.${className?uncap_first}Mapper = ${className?uncap_first}Mapper;
    }

    /**
     * Save a ${className}.
     *
     * @param ${className}DTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ${className}DTO save(${className}DTO ${className?uncap_first}DTO) {
        log.debug("Request to save ${className} : {}", ${className?uncap_first}DTO);
        ${className} ${className?uncap_first} = ${className?uncap_first}Mapper.toEntity(${className?uncap_first}DTO);
        ${className?uncap_first} = ${className?uncap_first}Repository.save(${className?uncap_first});
        return ${className?uncap_first}Mapper.toDto(${className?uncap_first});
    }

    /**
     * Get all the ${className?uncap_first}.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<${className}DTO> findAll(Pageable pageable) {
        log.debug("Request to get all ${className}");
        return ${className?uncap_first}Repository.findAll(pageable)
            .map(${className?uncap_first}Mapper::toDto);
    }


    /**
     * Get one jobHistory by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<${className}DTO> findOne(Integer id) {
        log.debug("Request to get ${className} : {}", id);
        return Optional.of(${className?uncap_first}Repository.findOne(id))
            .map(${className?uncap_first}Mapper::toDto);
    }

    /**
     * Delete the jobHistory by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Integer id) {
        log.debug("Request to delete ${className} : {}", id);
        ${className?uncap_first}Repository.delete(id);
    }
    
    @Mapper(componentModel = "spring")
    interface ${className}Mapper {

	    ${className}DTO toDto(${className} ${className?uncap_first});
	
	    ${className} toEntity(${className}DTO ${className?uncap_first}DTO);
	
	    default ${className} fromId(Integer id) {
	        if (id == null) {
	            return null;
	        }
	        ${className} ${className?uncap_first} = new ${className}();
	        ${className?uncap_first}.setId(id);
	        return ${className?uncap_first};
	    }
	}
    
    
}

