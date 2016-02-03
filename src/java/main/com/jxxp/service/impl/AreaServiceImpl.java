package com.jxxp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jxxp.dao.AreaInfoMapper;
import com.jxxp.pojo.AreaInfo;
import com.jxxp.service.AreaService;

@Service("areaService")
public class AreaServiceImpl implements AreaService {
	@Resource
	private AreaInfoMapper areaMapper;

	@Override
	public List<AreaInfo> getAllProvince() {
		return areaMapper.getAll();
	}

	@Override
	public List<AreaInfo> getProvinceCity(AreaInfo province) {
		List<AreaInfo> areaList = areaMapper.getAreaByParent(province.getAreaId());
		return areaList;
	}

	@Override
	public AreaInfo getProvince(long provinceId) {
		return areaMapper.getById(provinceId);
	}

	@Override
	public AreaInfo getCity(long cityId) {
		return areaMapper.getById(cityId);
	}

	@Override
	public List<AreaInfo> getByParent(AreaInfo parent) {
		long parentId = 0;
		if (parent.getAreaId() != 0) {
			parentId = parent.getAreaId();
		} else if (parent.getName() != null) {
			parentId = areaMapper.findByName(parent.getName()).getAreaId();

		} else
			parentId = 0;
		List<AreaInfo> list = areaMapper.getAreaByParent(parentId);
		return list;
	}
}
