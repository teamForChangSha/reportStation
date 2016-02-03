package com.jxxp.dao;

import java.util.List;

public interface CompanyQuestionMapper {
	boolean add(long companyId,String questionKey);
	List<String> getQuestKeyByCompanyId(long companyId);
}
