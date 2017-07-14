package service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import dao.RoutepicDao;
import model.Routepic;
import service.RoutepicService;
import util.ByteUtil;

public class RoutepicServiceImpl implements RoutepicService {

	RoutepicDao routepicDao;
	
	public RoutepicDao getRoutepicDao() {
		return routepicDao;
	}

	public void setRoutepicDao(RoutepicDao routepicDao) {
		this.routepicDao = routepicDao;
	}
	
	@Override
	public void save(String pid,byte[] routepic) {
		Routepic pic = new Routepic(pid, routepic);
		routepicDao.save(pic);
	}

	@Override
	public List<byte[]> getPicsById(String pid){
		List<Routepic> routepics = routepicDao.getRoutepicsById(pid);
		List<byte[]> picbytes = new ArrayList<>();
		int size = routepics.size();
		for (int i = 0; i < size; i++){   
			picbytes.set(i, routepics.get(i).getPic());
		}
		return null;
	}

	@Override
	public Routepic getUserPicById(String pid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void usersave(byte[] userpic, String pid) {
		// TODO Auto-generated method stub
		
	}


	
}
