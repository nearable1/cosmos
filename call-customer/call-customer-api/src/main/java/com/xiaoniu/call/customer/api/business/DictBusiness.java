package com.xiaoniu.call.customer.api.business;

import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.call.customer.api.dto.BigDictDTO;
import com.xiaoniu.call.customer.api.dto.SmallDictDTO;
import com.xiaoniu.call.customer.api.vo.BigDictQueryVO;
import com.xiaoniu.call.customer.api.vo.BigDictVO;
import com.xiaoniu.call.customer.api.vo.SmallDictQueryVO;
import com.xiaoniu.call.customer.api.vo.SmallDictVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@FeignClient(value = "customer", contextId = "dictBusiness")
public interface DictBusiness {

    @PostMapping("/queryBigList")
    PageResult<BigDictDTO> queryBigList(@Valid @RequestBody BigDictQueryVO bigDictQueryVO);

    @PutMapping("/updateBigDict")
    void updateBigDict(@Valid @RequestBody BigDictVO bigDictVO);

    @PostMapping("/saveBigDict")
    void saveBigDict(@Valid @RequestBody BigDictVO bigDictVO);

    @DeleteMapping("/deleteBigDict")
    void deleteBigDict(@RequestParam(value = "bigId") Long bigId);

    /*********************************************************************/
    /**************************** 小类管理 **********************************/
    /*********************************************************************/

    @PostMapping("/smallList")
    PageResult<SmallDictDTO> smallList(@Valid @RequestBody SmallDictQueryVO smallDictQueryVO);

    @PutMapping("/updateSmallDict")
    void updateSmallDict(@Valid @RequestBody SmallDictVO smallDictVO);

    @PostMapping("/saveSmallDict")
    void saveSmallDict(@Valid @RequestBody SmallDictVO smallDictVO);

    @DeleteMapping("/deleteSmallDict")
    void deleteSmallDict(@RequestParam(value = "smallId") Long smallId);

    @GetMapping("{type}")
    List<SmallDictDTO> querySubclassByBigCode(@PathVariable String type);

    /**
     * 根据大类和小类查询小类内容
     * @param bigCode
     * @param smallCode
     * @return
     */
    @GetMapping("/queryBySmallCode")
    SmallDictDTO queryBySmallCode(@RequestParam(value = "bigCode") String bigCode,@RequestParam(value = "smallCode") String smallCode);
}
