package ${serviceIntfPackageName};

import java.util.List;
import java.util.Optional;
import ${dtoPackageName}.${className}DTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

<#if classComment?has_content>
${classComment}
</#if>
public interface ${className}Service {
   /**
     * Save a ${className}.
     *
     * @param locationDTO the entity to save
     * @return the persisted entity
     */
    ${className}DTO save(${className}DTO dto);

    /**
     * Get all the ${className}.
     *
     * @return the list of entities
     */
    Page<${className}DTO> findAll(Pageable pageable); 


    /**
     * Get the "id" ${className}.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<${className}DTO> findOne(Integer id);

    /**
     * Delete the "id" ${className}.
     *
     * @param id the id of the entity
     */
    void delete(Integer id);

}
