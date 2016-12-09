package com.higgs.qqzone1.biz;

import java.util.List;

import com.higgs.qqzone1.dao.UserFilesDao;
import com.higgs.qqzone1.dao.impl.UserFilesDaoImpl;
import com.higgs.qqzone1.model.UserFiles;

public class UserFilesBiz {
	private static UserFilesDao oFilesDao = new UserFilesDaoImpl();

	public static UserFiles uploadFiles(UserFiles oFiles) {
		return oFilesDao.uploadFiles(oFiles);
	}
	public static List<UserFiles> getFilesPath(){
		return oFilesDao.getFilesPath();
		
	};
}
