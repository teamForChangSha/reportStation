package com.jxxp.test.service;

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
import com.jxxp.service.AreaService;
import com.jxxp.test.unit.TestUtil;

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

	/**
	 * @exception 接口实现不正确
	 * 
	 *                获取所有省
	 */
	@Ignore
	public void getAllProvince() {
		// 获取所有省，等级为2的即是省份
		List<AreaInfo> priorList = areaInfoMapper.getAllByLevel(2);
		List<AreaInfo> interList = areaService.getAllProvince();
		assertTrue(priorList.size() == interList.size());
		assertTrue(TestUtil.isEqual(priorList, interList));
	}

	/**
	 * 测试通过省获取市 添加的数量是否正确
	 */
	@Test
	public void getProvinceCity() {
		AreaInfo province = getProvince();
		areaInfoMapper.insert(province);
		List<AreaInfo> cityList1 = areaService.getProvinceCity(province);
		assertTrue(cityList1.size() == 0);

		AreaInfo city1 = getCity(province);
		AreaInfo city2 = getCity(province);
		city2.setAreaId(city1.getAreaId() + 1);
		areaInfoMapper.insert(city1);
		areaInfoMapper.insert(city2);
		List<AreaInfo> cityList2 = areaService.getProvinceCity(province);
		assertTrue(cityList2.size() == 2);
		areaInfoMapper.deleteById(city1.getAreaId());
		areaInfoMapper.deleteById(city2.getAreaId());
		areaInfoMapper.deleteById(province.getAreaId());
	}

	/**
	 * 测试通过省获取市
	 * 
	 * 删除所添加的市的数量是否正确
	 */
	@Test
	public void getProvinceCityAferDel() {
		AreaInfo province = getProvince();
		areaInfoMapper.insert(province);
		List<AreaInfo> cityList1 = areaService.getProvinceCity(province);
		assertTrue(cityList1.size() == 0);

		AreaInfo city1 = getCity(province);
		AreaInfo city2 = getCity(province);
		city2.setAreaId(city1.getAreaId() + 1);
		areaInfoMapper.insert(city1);
		areaInfoMapper.insert(city2);
		List<AreaInfo> cityList2 = areaService.getProvinceCity(province);
		assertTrue(cityList2.size() == 2);
		areaInfoMapper.deleteById(city1.getAreaId());
		areaInfoMapper.deleteById(city2.getAreaId());
		List<AreaInfo> cityList3 = areaService.getProvinceCity(province);
		assertTrue(cityList3.size() == 0);
		areaInfoMapper.deleteById(province.getAreaId());

	}

	/**
	 * 脏数据检查
	 * 
	 * 1、 检查所有城市数据是否都合法（parentid合法） 2、根据level直接获取所有市的信息，和上述步骤获取的信息的数量是否相等
	 */
	@Test
	public void CheckAreaInfo() {
		List<AreaInfo> allProvinc = areaService.getAllProvince();
		int cityTotalNum = 0;
		for (int i = 0; i < allProvinc.size(); i++) {
			AreaInfo parentProvince = allProvinc.get(i);
			List<AreaInfo> childrenArea = areaService.getProvinceCity(parentProvince);
			for (int j = 0; j < childrenArea.size(); j++) {
				AreaInfo childArea = childrenArea.get(j);
				assertTrue(childArea.getLevel() == 3);
				assertTrue(childArea.getParentId() == parentProvince.getAreaId());
				cityTotalNum++;
			}
		}
		// 获取所有省下的市数量和原来存在的所有市级的数量相等
		List<AreaInfo> cities = areaInfoMapper.getAllByLevel(3);
		assertTrue(cities.size() == cityTotalNum);
	}

	@Test
	public void TestGetProvince() {
		AreaInfo province1 = getProvince();
		areaInfoMapper.insert(province1);
		AreaInfo province2 = areaService.getProvince(province1.getAreaId());
		assertTrue(TestUtil.isEqual(province1, province2));
		areaInfoMapper.deleteById(province1.getAreaId());
		AreaInfo delProvince = areaService.getProvince(province1.getAreaId());
		assertTrue(delProvince == null);
	}

	@Test
	public void TestGetCity() {
		AreaInfo province = getProvince();
		areaInfoMapper.insert(province);
		AreaInfo priorCity = getCity(province);
		areaInfoMapper.insert(priorCity);
		AreaInfo laterCity = areaService.getCity(priorCity.getAreaId());
		assertTrue(TestUtil.isEqual(priorCity, laterCity));
		List<AreaInfo> allCity = areaService.getByParent(province);
		boolean flag = false;
		for (int i = 0; i < allCity.size(); i++) {
			if (TestUtil.isEqual(laterCity, allCity.get(i))) {
				flag = true;
				break;
			}
		}
		assertTrue(flag);
		areaInfoMapper.deleteById(priorCity.getAreaId());
		AreaInfo delCity = areaService.getCity(priorCity.getAreaId());
		assertTrue(delCity == null);
		assertTrue(!allCity.contains(delCity));
		areaInfoMapper.deleteById(province.getAreaId());

	}

	/*
	 * @Ignore public void getWithNoPrent() { AreaInfo area = getArea(1);
	 * areaInfoMapper.insert(area); assertNull(areaService.getByParent(area));
	 * areaInfoMapper.deleteById(area.getAreaId()); }
	 * 
	 * @Ignore public void testGetCityByCompanyId() { List<AreaInfo> cityList =
	 * areaService.getCityByCompanyId(new Long(1), new Long(10001));
	 * assertNotNull(cityList); }
	 * 
	 * @Ignore public void testGetProvinceByCompanyId() { List<AreaInfo>
	 * Province = areaService.getProvinceByCompanyId(new Long(1));
	 * assertNotNull(Province); }
	 */
	private AreaInfo getProvince() {
		AreaInfo province = new AreaInfo();
		province.setLevel(2);
		province.setAreaId(100000);
		province.setName("xx省");
		province.setParentId(1111);
		return province;
	}

	private AreaInfo getCity(AreaInfo parent) {
		AreaInfo city = new AreaInfo();
		city.setLevel(3);
		city.setAreaId(1000000);
		city.setName("xx市");
		city.setParentId(parent.getAreaId());
		return city;
	}

	public void delData() {
	}
	/*
	 * private AreaInfo getArea(int level) { AreaInfo area = new AreaInfo();
	 * area.setLevel(2); area.setAreaId(100000); area.setName("xx市");
	 * area.setParentId(10000); return area; }
	 */

}
