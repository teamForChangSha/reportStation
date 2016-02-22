package com.jxxp.test.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jxxp.dao.AreaInfoMapper;
import com.jxxp.pojo.AreaInfo;
import com.jxxp.service.AreaService;

/**
 * @author gcx
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml", "classpath:spring-mybatis.xml",
		"classpath:spring-mvc.xml" })
public class AreaInfoServiceTest {

	@Resource
	private AreaService areaService;
	@Resource
	private AreaInfoMapper areaInfoMapper;
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void getByParent() {
		AreaInfo province = getProvince();
		areaInfoMapper.insert(province);
		AreaInfo city = getCity(province.getAreaId());
		assertTrue(areaInfoMapper.insert(city) > 0);
		assertTrue(areaService.getByParent(province).size() > 0);
		areaInfoMapper.deleteById(city.getAreaId());
		areaInfoMapper.deleteById(province.getAreaId());
	}

	/**
	 * 区域信息为顶级类则没有父类,为空
	 */
	@Test
	public void getWithNoPrent() {
		AreaInfo area = getArea(1);
		areaInfoMapper.insert(area);
		assertNull(areaService.getByParent(area));
		areaInfoMapper.deleteById(area.getAreaId());
	}

	/**
	 * 如果AreaInfo对象级别不是省则抛出异常
	 */
	@Test
	public void TestGetNotProvince() {
		List<AreaInfo> alist = areaInfoMapper.getAllByLevel(3);
		thrown.expect(NullPointerException.class);
		areaService.getProvince(alist.get(0).getAreaId());
	}

	/**
	 * 如果AreaInfo对象级别不是市抛出异常
	 */
	@Test
	public void TestGetNotCity() {
		List<AreaInfo> alist = areaInfoMapper.getAllByLevel(2);
		thrown.expect(NullPointerException.class);
		areaService.getCity(alist.get(0).getAreaId());

	}

	@Test
	public void testGetCityByCompanyId() {
		List<AreaInfo> cityList = areaService.getCityByCompanyId(new Long(1), new Long(10001));
		assertNotNull(cityList);
	}

	@Test
	public void testGetProvinceByCompanyId() {
		List<AreaInfo> Province = areaService.getProvinceByCompanyId(new Long(1));
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

	private AreaInfo getArea(int level) {
		AreaInfo area = new AreaInfo();
		area.setLevel(level);
		List<AreaInfo> list = areaInfoMapper.getAll();
		area.setAreaId(list.get(list.size() - 1).getAreaId() + 1);
		area.setName("xx省");
		area.setParentId(10000);
		return area;
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
