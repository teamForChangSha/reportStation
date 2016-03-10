package com.jxxp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jxxp.dao.DictionaryBeanMapper;
import com.jxxp.pojo.DictionaryBean;
import com.jxxp.service.DictionaryService;

@Service("dictionaryService")
public class DictionaryServiceImpl implements DictionaryService {
	@Resource
	private DictionaryBeanMapper dictionaryBeanMapper;

	@Override
	public DictionaryBean getDictName(String dictType, Integer dictValue) {
		return dictionaryBeanMapper.getDictionary(dictType, dictValue);
	}

	@Override
	public List<DictionaryBean> getDictTypeList(String dictType) {
		return dictionaryBeanMapper.getByType(dictType);
	}

}
