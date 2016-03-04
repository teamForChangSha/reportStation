package com.jxxp.test.service;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jxxp.dao.AreaInfoMapper;
import com.jxxp.dao.CompanyBranchMapper;
import com.jxxp.dao.CompanyMapper;
import com.jxxp.pojo.AreaInfo;
import com.jxxp.pojo.Company;
import com.jxxp.pojo.CompanyBranch;
import com.jxxp.service.AreaService;
import com.jxxp.test.mybatis.CompanyBranchTest;
import com.jxxp.test.mybatis.CompanyTest;
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
	@Resource
	private CompanyMapper companyMapper;
	@Resource
	private CompanyBranchMapper companyBranchMapper;
	private List<AreaInfo> addProvinces;
	private List<CompanyBranch> addBranches;
	private List<AreaInfo> addcities;
	private Company owner;

	/**
	 * 获取所有省
	 */
	@Test
	public void getAllProvince() {
		// 获取所有省，等级为2的即是省份
		List<AreaInfo> priorList = areaInfoMapper.getAllByLevel(2);
		List<AreaInfo> interList = areaService.getAllProvince();
		assertTrue(priorList.size() == interList.size());
		assertTrue(TestUtil.isEqual(priorList, interList));
	}

	/**
	 * 获取所有省，添加
	 */
	@Test
	public void getAllProvinceWithAdd() {
		// 添加前后取出来的省份数量+1；
		List<AreaInfo> interList = areaService.getAllProvince();
		AreaInfo province = getProvince();
		areaInfoMapper.insert(province);
		List<AreaInfo> interList2 = areaService.getAllProvince();
		assertTrue((interList.size() + 1) == interList2.size());
		// 和数据库中的省份数量相同
		List<AreaInfo> priorList = areaInfoMapper.getAllByLevel(2);
		assertTrue(priorList.size() == interList2.size());
		assertTrue(TestUtil.isEqual(priorList, interList2));
		// 删除
		areaInfoMapper.deleteById(province.getAreaId());
	}

	/**
	 * 获取所有省，删除
	 */
	@Test
	public void getAllProvinceWithDel() {
		// 删除前所取得的和数据库中的相同
		AreaInfo province = getProvince();
		areaInfoMapper.insert(province);
		List<AreaInfo> interList1 = areaService.getAllProvince();
		List<AreaInfo> priorList = areaInfoMapper.getAllByLevel(2);
		assertTrue(TestUtil.isEqual(priorList, interList1));

		// 删除后取出来的省份数量少1，且和数据库中的数量以及值相同；
		areaInfoMapper.deleteById(province.getAreaId());
		List<AreaInfo> interList2 = areaService.getAllProvince();
		assertTrue(!TestUtil.isEqual(interList1, interList2));

		assertTrue(interList1.size() == (interList2.size() + 1));
		priorList = areaInfoMapper.getAllByLevel(2);
		assertTrue(priorList.size() == interList2.size());
		assertTrue(TestUtil.isEqual(priorList, interList2));

	}

	/**
	 * 获取所有省，修改
	 */
	@Test
	public void getAllProvinceWithUpdate() {
		// 修改前所取得的和数据库中的数量相同
		AreaInfo province = getProvince();
		areaInfoMapper.insert(province);
		List<AreaInfo> interList1 = areaService.getAllProvince();
		List<AreaInfo> priorList = areaInfoMapper.getAllByLevel(2);
		assertTrue(priorList.size() == interList1.size());

		// 修改前后取出来的省信息不同相同但是数量相等,且数据库中的数量相同
		province.setName("修改省份名字");
		assertTrue(areaInfoMapper.update(province) > 0);
		List<AreaInfo> interList2 = areaService.getAllProvince();

		System.out.println("is same??=" + TestUtil.isEqual(interList1, interList2));
		assertTrue(interList1.size() == interList2.size());
		priorList = areaInfoMapper.getAllByLevel(2);
		assertTrue(priorList.size() == interList2.size());
		// 删除
		areaInfoMapper.deleteById(province.getAreaId());

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
	public void CheckCity() {
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

	/**
	 * TODO 脏数据检查 待改正
	 * 
	 * 2、 检查所有省数据是否都合法（parentid合法）
	 * 
	 * a、验证parentId和法：根据level=2直接获取所有省，省的parent（上一级别）的地区存在，且level=1
	 * b、parent的下一级别中是否含有省；
	 * 
	 * c、验证数量正确性：获取所有级别为国家（级别为1）下的所有省数目和a中的数量是否一致；
	 */
	@Ignore
	public void CheckProvince() {
		List<AreaInfo> allProvince = areaInfoMapper.getAllByLevel(2);
		for (int i = 0; i < allProvince.size(); i++) {
			AreaInfo province = allProvince.get(i);
			AreaInfo country = areaInfoMapper.getById(province.getParentId());
			assertTrue(country.getLevel() == 1);
			List<AreaInfo> childrenProvince = areaService.getByParent(country);
			boolean isExist = childrenProvince.contains(province);
			assertTrue(isExist);
		}
		int provinceNum = 0;
		List<AreaInfo> allCountry = areaInfoMapper.getAllByLevel(1);
		for (int i = 0; i < allCountry.size(); i++) {
			List<AreaInfo> childProvince = areaService.getByParent(allCountry.get(i));
			provinceNum = provinceNum + childProvince.size();
		}
		assertTrue(allProvince.size() == provinceNum);
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
		AreaInfo addCity = areaService.getCity(priorCity.getAreaId());
		assertTrue(TestUtil.isEqual(priorCity, addCity));
		List<AreaInfo> allCity = areaService.getByParent(province);
		boolean flag = false;
		for (int i = 0; i < allCity.size(); i++) {
			if (TestUtil.isEqual(addCity, allCity.get(i))) {
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

	/**
	 * 获取某个公司（所有分支机构）所在的省份
	 * 
	 * 正常情况 1、需验证取出来的省份信息是否和所添加的相同
	 */
	@Test
	public void getProvinceByCompanyId() {
		owner = CompanyTest.getCompany();
		companyMapper.insert(owner);
		initBraches(owner);
		List<AreaInfo> getProvinces = areaService.getProvinceByCompanyId(owner.getCompanyId());
		assertTrue(getProvinces.size() == 2);
		assertTrue(TestUtil.isEqual(addProvinces, getProvinces));
		delList();
	}

	/**
	 * 获取某个公司（所有分支机构）所在的省份
	 * 
	 * 2、不正常的情况：不含分支机构，行政区域未知 需验证取则出来的省份size==0
	 */
	@Test
	public void getProvinceWithNoBranch() {
		owner = CompanyTest.getCompany();
		companyMapper.insert(owner);
		List<AreaInfo> list = areaService.getProvinceByCompanyId(owner.getCompanyId());
		assertTrue(list.size() == 0);
		companyMapper.deleteById(owner.getCompanyId());

	}

	/**
	 * 获取某个公司（所有分支机构）所在的省份
	 * 
	 * 3、不正常的情况：含分支机构，行政区域未知 需验证取则出来的省份size==0
	 */
	@Test
	public void getProvinceWithNoPrarentArea() {
		owner = CompanyTest.getCompany();
		companyMapper.insert(owner);
		initBrachesWithNoArea(owner);
		for (int i = 0; i < addBranches.size(); i++) {
			List<AreaInfo> list = areaService.getProvinceByCompanyId(owner.getCompanyId());
			assertTrue(list.size() == 0);
		}
		delList();

	}

	/**
	 * 获取某个公司（所有分支机构），某个省下的拥有分支机构的城市（List）
	 * 
	 * 1、公司存在（companyId存在），需验证取出来的城市信息是否和所添加的相同
	 */
	@Test
	public void getCityByCompanyId() {
		owner = CompanyTest.getCompany();
		companyMapper.insert(owner);
		initBraches(owner);
		List<AreaInfo> getCitis1 = new ArrayList<AreaInfo>();
		List<AreaInfo> getCitis2 = new ArrayList<AreaInfo>();
		for (int i = 0; i < addProvinces.size(); i++) {
			getCitis1 = areaService.getCityByCompanyId(owner.getCompanyId(), addProvinces.get(i)
					.getAreaId());
			assertTrue(getCitis1.size() > 0);
			getCitis2.addAll(getCitis1);
		}
		assertTrue(TestUtil.isEqual(addcities, getCitis2));
		assertTrue(addcities.size() == getCitis2.size());
		delList();
	}

	/**
	 * 获取某个公司（所有分支机构），某个省下的拥有分支机构的城市（List）
	 * 
	 * 2、公司(分支机构)不存在，即是（companyId不存在），列表为空size==0;
	 */
	@Test
	public void getCityWithNoCompanyId() {
		owner = CompanyTest.getCompany();
		companyMapper.insert(owner);
		initBraches(owner);
		// 删除公司
		companyMapper.deleteById(owner.getCompanyId());
		// 删除分支机构
		for (int i = 0; i < addBranches.size(); i++) {
			companyBranchMapper.deleteById(addBranches.get(i).getBranchId());
		}
		List<AreaInfo> getCitis = new ArrayList<AreaInfo>();
		for (int i = 0; i < addProvinces.size(); i++) {
			getCitis = areaService.getCityByCompanyId(owner.getCompanyId(), addProvinces.get(i)
					.getAreaId());
			assertTrue(getCitis.size() == 0);
		}
		delList();
	}

	/**
	 * 获取某个公司（所有分支机构），某个省下的拥有分支机构的城市（List）
	 * 
	 * 3、未知行政区域的公司
	 */
	@Test
	public void getCityWithNoPrarentArea() {
		owner = CompanyTest.getCompany();
		companyMapper.insert(owner);
		initBrachesWithNoArea(owner);
		for (int i = 0; i < addBranches.size(); i++) {
			List<AreaInfo> list = areaService.getCityByCompanyId(owner.getCompanyId(), addBranches
					.get(i).getProvince().getAreaId());
			assertTrue(list.size() == 0);
		}
		delList();

	}

	public void delList() {
		for (int i = 0; i < 2; i++) {
			if (owner != null) {
				companyMapper.deleteById(owner.getCompanyId());
			}
			if (addBranches != null) {
				companyBranchMapper.deleteById(addBranches.get(i).getBranchId());
			}
			if (addProvinces != null) {
				areaInfoMapper.deleteById(addProvinces.get(i).getAreaId());
			}
			if (addcities != null) {
				areaInfoMapper.deleteById(addcities.get(i).getAreaId());
			}
		}
	}

	private void initBrachesWithNoArea(Company company) {
		addBranches = new ArrayList<CompanyBranch>();
		for (int i = 0; i < 2; i++) {
			CompanyBranch branch = CompanyBranchTest.getBranch();
			// 关联公司
			branch.setOwner(owner);
			companyBranchMapper.insert(branch);
			branch.setProvince(new AreaInfo());
			branch.setCity(new AreaInfo());
			addBranches.add(branch);
		}
	}

	private void initBraches(Company owner) {
		addProvinces = new ArrayList<AreaInfo>();
		addcities = new ArrayList<AreaInfo>();
		addBranches = new ArrayList<CompanyBranch>();
		for (int i = 0; i < 2; i++) {
			CompanyBranch branch = CompanyBranchTest.getBranch();
			AreaInfo province = getProvince();
			province.setAreaId(province.getAreaId() + i);
			// 设置省信息
			areaInfoMapper.insert(province);
			addProvinces.add(province);
			branch.setProvince(province);
			// 设置市信息
			AreaInfo city = getCity(province);
			city.setAreaId(city.getAreaId() + i);
			areaInfoMapper.insert(city);
			addcities.add(city);
			branch.setCity(city);
			// 关联公司
			branch.setOwner(owner);
			companyBranchMapper.insert(branch);
			addBranches.add(branch);
		}

	}

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

}
