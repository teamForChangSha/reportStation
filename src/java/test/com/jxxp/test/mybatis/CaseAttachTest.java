package com.jxxp.test.mybatis;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jxxp.dao.CaseAttachMapper;
import com.jxxp.pojo.CaseAttach;
import com.jxxp.test.unit.TestUtil;

/**
 * @author gcx
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml", "classpath:spring-mybatis.xml",
		"classpath:spring-mvc.xml" })
public class CaseAttachTest {

	@Resource
	private CaseAttachMapper caseAttachMapper;

	public static String trackingNo = "GCX20160502";

	private CaseAttach caseAttach1;

	@Before
	public void init() {
		caseAttach1 = getAttach();
	}

	/*
	 * 保存附件,由于CaseAttach实体类中有一个thumb对象为Image缩略图，数据库中没有匹配类型。
	 * 因此无法比较两个CaseAttach对象所以属性是否相等。
	 */
	// @Test
	public void addAttach() {
		caseAttachMapper.insert(caseAttach1);
		CaseAttach caseAttach2 = caseAttachMapper.getById(caseAttach1.getCaId());
		assertTrue(caseAttach2 != null);
		assertTrue(TestUtil.isEqual(caseAttach1, caseAttach2));

	}

	/*
	 * 获取附件
	 */
	// @Test
	public void TestGetAttach() {
		caseAttach1 = getAttach();
		caseAttachMapper.insert(caseAttach1);
		CaseAttach caseAttach2 = caseAttachMapper.getById(caseAttach1.getCaId());
		assertTrue(TestUtil.isEqual(caseAttach1, caseAttach2));
	}

	/*
	 * 更新附件
	 */
	@Test
	public void testUpdateAttach() {
		caseAttach1 = getAttach();
		caseAttachMapper.insert(caseAttach1);
		CaseAttach caseAttach2 = caseAttachMapper.getById(caseAttach1.getCaId());
		caseAttach2.setAttachName("change fileName");
		assertTrue(caseAttachMapper.update(caseAttach2) > 0);
		CaseAttach caseAttach3 = caseAttachMapper.getById(caseAttach1.getCaId());
		assertTrue(!isEqual(caseAttach1, caseAttach3));
	}

	/*
	 * 删除
	 */
	// @Test
	public void TestDelAttach() {
		caseAttach1 = getAttach();
		caseAttachMapper.insert(caseAttach1);
		assertTrue(caseAttachMapper.deleteById(caseAttach1.getCaId()) > 0);
		CaseAttach caseAttach2 = caseAttachMapper.getById(caseAttach1.getCaId());
		assertTrue(caseAttach2 == null);
	}

	@After
	public void clear() {
		caseAttachMapper.deleteById(caseAttach1.getCaId());
	}

	public static CaseAttach getAttach() {
		CaseAttach caseAttach = new CaseAttach();
		caseAttach.setAttachExt("扩展文本");
		caseAttach.setAttachName("附件名");
		caseAttach.setTrackingNo(trackingNo);
		caseAttach.setAttachSize(200);
		caseAttach.setAttachFileName("fileName");
		caseAttach.setAttachPath("upload");
		caseAttach.setAttachUrl("http://");
		caseAttach.setState(1);
		caseAttach.setDescription("附件描述");
		caseAttach.setThumb(null);
		return caseAttach;
	}

	/**
	 * java反射机制验证两个对象的属性值是否相等
	 * 
	 * @param src
	 * @param dst
	 * @return
	 */
	public boolean isEqual(CaseAttach src, CaseAttach dst) {
		Field[] fields = src.getClass().getDeclaredFields();
		boolean flag = true;
		for (Field srcField : fields) {
			srcField.setAccessible(true);
			try {
				if (!(srcField.getName().equals("thumb"))) {
					// 获取原对象字段值
					Object srcFieldData = srcField.get(src);
					Field dstField = dst.getClass().getDeclaredField(srcField.getName());
					dstField.setAccessible(true);
					Object dstFieldData = dstField.get(dst);
					if (!srcFieldData.equals(dstFieldData)) {
						flag = false;
						break;
					}
				}
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return flag;
	}
}
