package com.jxxp.test.mybatis;

import static org.junit.Assert.assertTrue;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jxxp.dao.AreaInfoMapper;
import com.jxxp.pojo.AreaInfo;
import com.jxxp.test.unit.TestUtil;

/**
 * @author gcx
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml", "classpath:spring-mybatis.xml",
		"classpath:spring-mvc.xml" })
public class AreaInfoTest {

	@Resource
	private AreaInfoMapper areaInfoMapper;
	private AreaInfo area1;

	@Before
	public void init() {
		area1 = getArea(2);
	}

	@Test
	public void saveArea() {
		area1 = getProvince();
		areaInfoMapper.insert(area1);
		AreaInfo area2 = areaInfoMapper.getById(area1.getAreaId());
		assertTrue(TestUtil.isEqual(area1, area2));
	}

	@Test
	public void testGetAreaById() {
		area1 = getProvince();
		areaInfoMapper.insert(area1);
		AreaInfo area2 = areaInfoMapper.getById(area1.getAreaId());
		assertTrue(TestUtil.isEqual(area1, area2));
	}

	@Test
	public void delAreaInfo() {
		area1 = getProvince();
		areaInfoMapper.insert(area1);
		AreaInfo area2 = areaInfoMapper.getById(area1.getAreaId());
		assertTrue(TestUtil.isEqual(area1, area2));
		areaInfoMapper.deleteById(area1.getAreaId());
		AreaInfo area3 = areaInfoMapper.getById(area1.getAreaId());
		assertTrue(area3 == null);

	}

	@After
	public void del() {
		areaInfoMapper.deleteById(area1.getAreaId());
	}

	private static AreaInfo getProvince() {
		AreaInfo province = new AreaInfo();
		province.setLevel(2);
		province.setAreaId(100000);
		province.setName("xx省");
		province.setParentId(1111);
		return province;
	}

	private static AreaInfo getArea(int level) {
		AreaInfo city = new AreaInfo();
		city.setLevel(level);
		city.setAreaId(1000000);
		city.setName("xx市");
		city.setParentId(1111);
		return city;
	}
}
