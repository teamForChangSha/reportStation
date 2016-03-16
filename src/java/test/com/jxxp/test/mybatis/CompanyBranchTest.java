package com.jxxp.test.mybatis;

import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.jxxp.dao.AreaInfoMapper;
import com.jxxp.dao.CompanyBranchMapper;
import com.jxxp.dao.CompanyMapper;
import com.jxxp.pojo.AreaInfo;
import com.jxxp.pojo.Company;
import com.jxxp.pojo.CompanyBranch;
import com.jxxp.test.unit.TestUtil;

/**
 * @author gcx
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml", "classpath:spring-mybatis.xml",
		"classpath:spring-mvc.xml" })
// 添加注释，回滚对数据库的操作
@Transactional
public class CompanyBranchTest {

	@Resource
	private CompanyMapper companyMapper;
	@Resource
	private CompanyBranchMapper companyBranchMapper;
	@Resource
	private AreaInfoMapper areaInfoMapper;
	private CompanyBranch branch;
	private Company company;

	@Before
	public void init() {
		branch = getBranch();
		company = CompanyTest.getCompany();
	}

	@Test
	public void addBranch() {
		companyMapper.insert(company);
		branch.setOwner(company);
		companyBranchMapper.insert(branch);
		CompanyBranch addBranch = companyBranchMapper.getById(branch.getBranchId());
		System.out.println("---" + addBranch.getBranchName());
		assertTrue(TestUtil.isEqual(branch, addBranch));
	}

	@After
	public void clear() {
		companyMapper.deleteById(company.getCompanyId());
		companyBranchMapper.deleteById(branch.getBranchId());
	}

	/**
	 * 用于构造数据，默认将所有公司作为一个分支机构存在
	 */
	@Ignore
	public void importBranches() {
		List<Company> list = companyMapper.getAllCompany();
		int count = 0;
		for (int i = 0; i < list.size(); i++) {
			CompanyBranch branch = getBranch();
			// 默认设置未知省和市
			AreaInfo priv = new AreaInfo();
			priv.setAreaId(9990);
			AreaInfo city = new AreaInfo();
			city.setAreaId(9991);
			branch.setProvince(priv);
			branch.setCity(city);
			// 设置所属公司
			branch.setOwner(list.get(i));
			int n = companyBranchMapper.insert(branch);
			count = count + n;
		}
		assertTrue(count == list.size());
	}

	public static CompanyBranch getBranch() {
		CompanyBranch branch = new CompanyBranch();
		branch.setAddress("未知");
		branch.setPhone("未知");
		branch.setBranchName("总部");
		return branch;
	}

}
