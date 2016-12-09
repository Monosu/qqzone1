package com.higgs.qqzone1.dao;

import java.util.List;

import com.higgs.qqzone1.model.UserFiles;

public interface UserFilesDao {
	public UserFiles uploadFiles(UserFiles oFiles);
	public List<UserFiles> getFilesPath();
}
