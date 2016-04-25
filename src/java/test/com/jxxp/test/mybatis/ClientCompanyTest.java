package com.jxxp.test.mybatis;

import static org.junit.Assert.assertTrue;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.jxxp.dao.ClientCompanyMapper;
import com.jxxp.dao.CompanyMapper;
import com.jxxp.pojo.ClientCompany;
import com.jxxp.pojo.Company;
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
public class ClientCompanyTest {
	@Resource
	private CompanyMapper companyMapper;
	@Resource
	private ClientCompanyMapper clientCompanyMapper;
	private Company company1;
	private ClientCompany client1;

	@Before
	public void init() {
		company1 = CompanyTest.getCompany();
	}

	@Test
	public void saveClientCompany() {
		companyMapper.insert(company1);
		client1 = new ClientCompany();
		client1.setCompanyId(company1.getCompanyId());
		client1.setExpiryDate(new Date(new Timestamp(new Date().getTime() / 1000 * 1000).getTime()));
		client1.setCreateTime(new Date(new Timestamp(new Date().getTime() / 1000 * 1000).getTime()));
		clientCompanyMapper.insert(client1);
		ClientCompany client2 = clientCompanyMapper.getClientCompanyById(company1.getCompanyId());
		assertTrue(CaseTest.isEqual(client1, client2));
	}

	@Test
	public void delClientCompany() {
		companyMapper.insert(company1);
		client1 = new ClientCompany();
		client1.setCompanyId(company1.getCompanyId());
		clientCompanyMapper.insert(client1);
		ClientCompany client2 = clientCompanyMapper.getClientCompanyById(company1.getCompanyId());
		clientCompanyMapper.delClientCompany(company1.getCompanyId());
		ClientCompany client3 = clientCompanyMapper.getClientCompanyById(company1.getCompanyId());

		assertTrue(TestUtil.isEqual(client1, client2));
		assertTrue(client3 == null);

	}

	@Test
	public void updateClientCompany() {
		companyMapper.insert(company1);
		client1 = new ClientCompany();
		client1.setCompanyId(company1.getCompanyId());
		clientCompanyMapper.insert(client1);
		ClientCompany client2 = clientCompanyMapper.getClientCompanyById(company1.getCompanyId());
		client2.setCreateTime(new Date(new Time(new Date().getTime() / 1000 * 1000).getTime()));
		ClientCompany client3 = clientCompanyMapper.getClientCompanyById(company1.getCompanyId());

		assertTrue(TestUtil.isEqual(client2, client3));
		assertTrue(!TestUtil.isEqual(client1, client3));

	}

	@After
	public void clear() {
		if (company1 != null) {
			companyMapper.deleteById(company1.getCompanyId());
		}
		if (client1 != null) {
			clientCompanyMapper.delClientCompany(client1.getCompanyId());
		}
	}

}
