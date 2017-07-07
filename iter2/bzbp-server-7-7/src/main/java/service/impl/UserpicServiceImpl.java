package service.impl;

import java.io.File;

import dao.UserpicDao;
import model.Userpic;
import service.UserpicService;
import util.ByteUtil;

public class UserpicServiceImpl implements UserpicService {

	private UserpicDao userpicDao;

	public void setUserpicDao(UserpicDao userpicDao) {
		this.userpicDao = userpicDao;
	}

	@Override
	public void save(int uid, File file) {
		byte[] pic = ByteUtil.FileToString(file);
		Userpic userpic = new Userpic(uid, pic);
		userpicDao.save(userpic);
	}

	@Override
	public byte[] getPicById(int uid) {
		Userpic userpic = userpicDao.getUserpicById(uid);
		return userpic.getPic();
	}

}
