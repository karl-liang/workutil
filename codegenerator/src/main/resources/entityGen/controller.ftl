package ${controllerPackageName};

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

package com.sciov.cnicg.code.web.controller;

import com.sciov.cnicg.code.module.response.ResponseData;

import ${dtoPackageName}.${className}DTO;

import com.sciov.cnicg.code.service.IAbEntityAbilityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/abentityability")
@Api(tags = "实体能力管理")
public class AbEntityAbilityController {
    private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");;

    private static final Logger logger = LoggerFactory.getLogger(AbEntityAbilityController.class);;

    @Autowired
    private IAbEntityAbilityService abEntityAbilityServiceImpl;

    @ApiOperation(value = "查询实体能力")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseData<AbEntityAbilityVo> getAbEntityAbility(@PathVariable int id) {
         return new ResponseData<>(abEntityAbilityServiceImpl.getAbEntityAbility(id));
    }

    @ApiOperation(value = "新增实体能力")
    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseData<Void> addAbEntityAbility(@Valid @RequestBody AbEntityAbilityVo vo) {
        abEntityAbilityServiceImpl.addAbEntityAbility(vo);
        return new ResponseData(200, "添加成功");
    }

    @ApiOperation(value = "删除实体能力")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseData<Void> deleteAbEntityAbility(@PathVariable int id) {
        abEntityAbilityServiceImpl.deleteAbEntityAbility(id);
        return new ResponseData(200, "删除成功");
    }

    @ApiOperation(value = "更新实体能力")
    @RequestMapping(value = "", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseData<Void> updateAbEntityAbility(@Valid @RequestBody AbEntityAbilityVo vo) {
        abEntityAbilityServiceImpl.updateAbEntityAbility(vo);
        return new ResponseData(200, "更新成功");
    }

    @ApiOperation("实体能力列表")
	@ApiImplicitParams({
	    @ApiImplicitParam(value = "id型查询条件", name = "condtionId", dataType = "int", paramType = "query", required = true),
	    @ApiImplicitParam(value = "字符串型查询条件", name = "conditionStr", dataType = "String", paramType = "query", required = false),
	    @ApiImplicitParam(value = "页码", name = "pageNum", dataType = "int", paramType = "query", defaultValue = "1"),
	    @ApiImplicitParam(value = "显示数量", name = "pageSize", dataType = "int", paramType = "query", defaultValue = "100000")
	})
    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseData<List<AbEntityAbilityVo>> findAbEntityAbilityList(@RequestParam(defaultValue = "") String conditionStr, @RequestParam(required = false) String startDateStr, @RequestParam(required = false) String endDateStr, @RequestParam(defaultValue = "0") int condtionId, int pageNum, int pageSize) throws ParseException {
        Date startDate = null;
        if(!StringUtil.isEmpty(startDateStr)) {
			startDate = format.parse(startDateStr);
    	}
        Date endDate = null;
        if(!StringUtil.isEmpty(endDateStr)) {
		endDate = format.parse(endDateStr);
    	}
        AbEntityAbilityVo abEntityAbilityVo= new AbEntityAbilityVo();
        return abEntityAbilityServiceImpl.findAbEntityAbilityList(abEntityAbilityVo,startDate,endDate,pageNum,pageSize);
    }
}
