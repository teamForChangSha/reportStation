package com.jxxp.test.mybatis;

import static org.junit.Assert.assertTrue;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jxxp.dao.DictionaryBeanMapper;
import com.jxxp.pojo.DictionaryBean;
import com.jxxp.test.unit.TestUtil;

/**
 * @author gcx
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml", "classpath:spring-mybatis.xml",
		"classpath:spring-mvc.xml" })
public class DictionaryTest {
	@Resource
	private DictionaryBeanMapper dictionaryBeanMapper;

	private DictionaryBean dictionary;

	/**
	 * 
	 * 删除某个字典类型
	 */
	@Test
	public void delDictionaryByNameAndValue() {
		dictionary = getDictionary();
		int count = dictionaryBeanMapper.insert(dictionary);
		assertTrue(count == 1);
		DictionaryBean getDic = dictionaryBeanMapper.getDictionary(dictionary.getDictType(),
				Integer.parseInt(dictionary.getDictValue()));
		assertTrue(TestUtil.isEqual(getDic, dictionary));
		dictionaryBeanMapper.delByTypeAndValue(dictionary.getDictType(),
				Integer.parseInt(dictionary.getDictValue()));
		DictionaryBean getDic2 = dictionaryBeanMapper.getDictionary(dictionary.getDictType(),
				Integer.parseInt(dictionary.getDictValue()));
		assertTrue(getDic2 == null);
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
