package com.ltu.repository.mybatis;

import java.util.List;
import java.util.Map;

import com.ltu.entity.InspectionReportItem;
import com.ltu.model.clientunitfee.ClientUnitFeeModel;

import jarlun.framework.core.base.mybatis.MyBatisRepository;

@MyBatisRepository
public interface ClientUnitFeeMapper {
	
	List<ClientUnitFeeModel> findUnitFeeList(Map<String, Object> map);
	
	List<InspectionReportItem> findInspecList(List<Long> listIds);
}
