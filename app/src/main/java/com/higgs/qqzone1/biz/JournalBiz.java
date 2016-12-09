package com.higgs.qqzone1.biz;

import java.util.List;

import com.higgs.qqzone1.dao.JournalDao;
import com.higgs.qqzone1.dao.impl.JournalDaoImpl;
import com.higgs.qqzone1.model.Journal;

public class JournalBiz {
	JournalDao oDao=new JournalDaoImpl();
	public List<Journal> getList() {
		return oDao.getList();
	}
}
