package com.jxxp.test.mybatis;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Ignore;
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

	@Test
	public void saveArea() {
		AreaInfo area1 = getProvince();
		areaInfoMapper.insert(getProvince());
		AreaInfo area2 = areaInfoMapper.getById(area1.getAreaId());
		assertTrue(TestUtil.isEqual(area1, area2));
	}

	@Test
	public void testGetAreaById() {
		AreaInfo area1 = getProvince();
		areaInfoMapper.insert(area1);
		AreaInfo area2 = areaInfoMapper.getById(area1.getAreaId());
		assertTrue(TestUtil.isEqual(area1, area2));
		areaInfoMapper.deleteById(area1.getAreaId());
	}

	@Test
	public void getByParentId() {
		AreaInfo province = getProvince();
		areaInfoMapper.insert(province);
		AreaInfo city = getCity(province.getAreaId());
		assertTrue(areaInfoMapper.insert(city) > 0);
		assertTrue(areaInfoMapper.getAreaByParent(province.getAreaId()).size() > 0);
		areaInfoMapper.deleteById(city.getAreaId());
		areaInfoMapper.deleteById(province.getAreaId());
	}

	@Ignore
	public void testGetCityByCompanyId() {
		List<AreaInfo> cityList = areaInfoMapper.getCityByCompanyId(new Long(1), new Long(10001));
		assertNotNull(cityList);
	}

	@Ignore
	public void testGetProvinceByCompanyId() {
		List<AreaInfo> Province = areaInfoMapper.getProvinceByCompanyId(new Long(1));
		assertNotNull(Province);
	}

	private AreaInfo getProvince() {
		AreaInfo province = new AreaInfo();
		province.setLevel(2);
		List<AreaInfo> list = areaInfoMapper.getAll();
		province.setAreaId(list.get(list.size() - 1).getAreaId() + 1);
		province.setName("xx省");
		province.setParentId(10000);
		return province;
	}

	private AreaInfo getCity(long prentId) {
		AreaInfo city = new AreaInfo();
		city.setLevel(3);
		List<AreaInfo> list = areaInfoMapper.getAll();
		city.setAreaId(list.get(list.size() - 1).getAreaId() + 1);
		city.setName("xx市");
		city.setParentId(prentId);
		return city;
	}

}
