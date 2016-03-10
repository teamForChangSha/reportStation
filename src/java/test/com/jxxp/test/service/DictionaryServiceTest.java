package com.jxxp.test.service;

import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jxxp.dao.DictionaryBeanMapper;
import com.jxxp.pojo.DictionaryBean;
import com.jxxp.service.DictionaryService;
import com.jxxp.test.unit.TestUtil;

/**
 * @author gcx
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml", "classpath:spring-mybatis.xml",
		"classpath:spring-mvc.xml" })
public class DictionaryServiceTest {
	@Resource
	private DictionaryService dictionaryService;
	@Resource
	private DictionaryBeanMapper dictionaryBeanMapper;

	private DictionaryBean dictionary;

	/**
	 * 存在的类型和值
	 */
	@Test
	public void getDictionaryByNameAndValue() {
		dictionary = getDictionary();
		int count = dictionaryBeanMapper.insert(dictionary);
		assertTrue(count == 1);
		DictionaryBean getDic = dictionaryService.getDictName(dictionary.getDictType(),
				Integer.parseInt(dictionary.getDictValue()));
		assertTrue(TestUtil.isEqual(getDic, dictionary));
	}

	/**
	 * 值存在，类型不存在
	 */
	@Test
	public void getDictionaryByNotExistType() {
		dictionary = getDictionary();
		int count = dictionaryBeanMapper.insert(dictionary);
		assertTrue(count == 1);
		DictionaryBean getDic = dictionaryService.getDictName("noType",
				Integer.parseInt(dictionary.getDictValue()));
		assertTrue(getDic == null);
	}

	/**
	 * 值正确，类型为空null
	 */
	@Test
	public void getDictionaryByNullType() {
		dictionary = getDictionary();
		int count = dictionaryBeanMapper.insert(dictionary);
		assertTrue(count == 1);
		DictionaryBean getDic = dictionaryService.getDictName(null,
				Integer.parseInt(dictionary.getDictValue()));
		assertTrue(getDic == null);
	}

	/**
	 * 值正确，类型为空串
	 */
	@Test
	public void getDictionaryByEmptyType() {
		dictionary = getDictionary();
		int count = dictionaryBeanMapper.insert(dictionary);
		assertTrue(count == 1);
		DictionaryBean getDic = dictionaryService.getDictName("",
				Integer.parseInt(dictionary.getDictValue()));
		assertTrue(getDic == null);
	}

	/**
	 * 类型存在，值不存在
	 */
	@Test
	public void getDictionaryByNotExistValue() {
		dictionary = getDictionary();
		int count = dictionaryBeanMapper.insert(dictionary);
		assertTrue(count == 1);
		DictionaryBean getDic = dictionaryService.getDictName(dictionary.getDictType(),
				Integer.parseInt("2000000"));
		assertTrue(getDic == null);
	}

	/**
	 * 类型正确，值为空null
	 */
	@Test
	public void getDictionaryByNullValue() {
		dictionary = getDictionary();
		int count = dictionaryBeanMapper.insert(dictionary);
		assertTrue(count == 1);
		DictionaryBean getDic = dictionaryService.getDictName(dictionary.getDictType(), null);
		assertTrue(getDic == null);
	}

	@Test
	public void getByType() {
		dictionary = getDictionary();
		dictionaryBeanMapper.insert(dictionary);
		List<DictionaryBean> dictList = dictionaryService.getDictTypeList(dictionary.getDictType());
		assertTrue(dictList.size() > 0);
		int count = 0;
		for (int i = 0; i < dictList.size(); i++) {
			DictionaryBean dictBean = dictList.get(i);
			if (TestUtil.isEqual(dictBean, dictionary)) {
				count++;
			}
		}
		assertTrue(count == 1);
	}

	private DictionaryBean getDictionary() {
		DictionaryBean dictionary = new DictionaryBean();
		dictionary.setDictDesc("字典描述");
		dictionary.setDictName("国有");
		dictionary.setDictType("company.type");
		dictionary.setDictValue("111");
		return dictionary;
	}

	@After
	public void clear() {
		if (dictionary != null) {
			dictionaryBeanMapper.delByTypeAndValue(dictionary.getDictType(),
					Integer.parseInt(dictionary.getDictValue()));

		}
	}
}
