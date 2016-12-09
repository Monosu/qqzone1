package com.higgs.qqzone1.biz;

import com.higgs.qqzone1.dao.MoodDao;
import com.higgs.qqzone1.dao.impl.MoodDaoImpl;
import com.higgs.qqzone1.model.Mood;

public class MoodBiz {
	private static MoodDao oMoodDao = new MoodDaoImpl();

	public static boolean addMood(Mood oMood) {
		return oMoodDao.addMood(oMood);
	}
}
