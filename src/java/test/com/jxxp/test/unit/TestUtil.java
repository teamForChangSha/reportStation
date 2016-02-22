package com.jxxp.test.unit;

import java.lang.reflect.Field;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestUtil {
	private static Logger log = LoggerFactory.getLogger(TestUtil.class);

	private static boolean isWrapperClass(Class clz) {
		try {
			return ((Class) clz.getField("TYPE").get(null)).isPrimitive();
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 判断两个对象是否相等，判断规则如下：<br>
	 * 1、简单对象字段直接使用==方法 <br>
	 * 2、String对象字段直接使用equals方法 <br>
	 * 3、复杂对象使用递归方式继续比较
	 * 
	 * @param src
	 *            需要比较的原对象
	 * @param dst
	 *            需要比较的目标对象
	 * @return 对象相等则返回真，否则返回假
	 */
	public static boolean isEqual(Object src, Object dst) {
		boolean result = true;
		// 判断两个对象有为空的情况
		if (src == null && dst == null) {
			return true;
		} else if (src == null || dst == null) {
			Class type = (src == null) ? (dst.getClass()) : (src.getClass());
			// isAssingableFrom只能判断是否是参数的父类或父接口，反之不行
			if (List.class.isAssignableFrom(type)) {
				// 如果是list，则null和size为0的对象视为相等
				int size = (src == null) ? ((List) dst).size() : ((List) src).size();
				if (size == 0) {
					return true;
				}
			}
			return false;
		}
		// 如果类型不符，则返回假
		if (!src.getClass().equals(dst.getClass())) {
			return false;
		}
		// 比较各个字段
		Field[] srcFields = src.getClass().getDeclaredFields();
		for (Field srcField : srcFields) {
			srcField.setAccessible(true);
			try {
				// 获取原对象字段值
				Object srcFieldData = srcField.get(src);
				String fieldName = srcField.getName();
				// 获取目标对象字段值
				Field dstField = dst.getClass().getDeclaredField(fieldName);
				dstField.setAccessible(true);
				Object dstFieldData = dstField.get(dst);
				// 两个字段进行比较，先处理空值
				if (srcFieldData == null && dstFieldData == null) {
					// 都为空，不影响结果，继续下一个字段的比较
					continue;
				} else if (srcFieldData == null || dstFieldData == null) {
					// 需要考虑为list的情况
					return isEqual(srcFieldData, dstFieldData);
				}
				// 根据字段类型用不同的方法比较
				if (srcField.getType().isPrimitive()) {
					// 基本类型，原本应该使用==，但因为之前是将data定义成Object，因此也使用equals
					// 因为逻辑不一样，因此没有合并代码
					result &= (srcFieldData.equals(dstFieldData));
				} else if (srcField.getType().getName().equals("java.lang.String")
						|| srcField.getType().getName().equals("java.sql.Timestamp")
						|| isWrapperClass(srcField.getType())) {
					// 基本类型的包装类型和String类型使用equals方法
					result &= (srcFieldData.equals(dstFieldData));
				} else {
					// 复杂类型的比较，递归使用本方法
					result &= (isEqual(srcFieldData, dstFieldData));
				}
				if (result) {
					continue;
				} else {
					return false;
				}
			} catch (IllegalArgumentException e) {
				log.debug("Object compare with illegal-argument exception " + e.getMessage());
				return false;
			} catch (IllegalAccessException e) {
				log.debug("Object compare with illegal-access exception " + e.getMessage());
				return false;
			} catch (SecurityException e) {
				log.debug("Object compare with security exception " + e.getMessage());
				return false;
			} catch (NoSuchFieldException e) {
				log.debug("Object compare with no-such-field exception " + e.getMessage());
				return false;
			}
		}
		return result;
	}

}
