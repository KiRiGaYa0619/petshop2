package jp.co.brightstar.dvd.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import jp.co.brightstar.dvd.model.Condi;
import jp.co.brightstar.dvd.model.DVD;
import jp.co.brightstar.dvd.model.lendInfo;
import jp.co.brightstar.dvd.service.DVDService;

@Controller
public class DVDController {
	@Autowired
	private DVDService service;

	@GetMapping("/dvdindex")
	public String dvdindex() {
		return "dvdindex";
	}

	@GetMapping("/insert")
	public String insert() {
		return "insert";
	}

	@PostMapping("/insert")
	public ModelAndView insert(String name) {
		ModelAndView mav = new ModelAndView();

		service.insertDVD(name);

		String msg = "DVD「" + name + "」の新規に成功しました！";
		mav.addObject("msg", msg);

		mav.setViewName("insert");

		return mav;
	}

	@GetMapping("/select")
	public ModelAndView select(Condi condi) {
		ModelAndView mav = new ModelAndView("select");
		if (condi.getSousaname() != null) {
			if ("lend".equals(condi.getSousa())) {
				service.lendDVD(condi.getSousaid());
				service.info(condi.getSousaid());
			} else if ("delete".equals(condi.getSousa())) {
				service.deleteDVD(condi.getSousaid());
			}
		}
		//			List<ValueAndText>stateList = service.getList("state");
		//			List<ValueAndText>countList = service.getList("count");
		//			mav.addObject("stateList", stateList);
		//			mav.addObject("countList", countList);
		List<DVD> dvds = service.selectDVD(condi);
		mav.addObject("dvds", dvds);
		//		for (DVD dvd : dvds) {
		//			System.out.println(dvd);
		//		}

		return mav;
	}

	@PostMapping("/delete")
	@ResponseBody
	public Map<String, Object> delete(DVD dvd) {
		Map<String, Object> map = new HashMap<>();
		return map;
	}	
	
	@GetMapping("/detail")
	public ModelAndView detail(Condi condi) {
		ModelAndView mav = new ModelAndView("detail");
		String id = condi.getDetailId();
		DVD detailDVD = service.detail(id);
		List<lendInfo> lendInfoList = service.getLendInfoById(id);

		mav.addObject("detailDVD", detailDVD);
		mav.addObject("lendInfoList", lendInfoList);
		return mav;
	}

	@PostMapping("/lend")
	@ResponseBody
	
	//页面取得id，name
	public Map<String, Object> lend(String id, String name) {
	    Map<String, Object> map = new HashMap<>();
	    	//数据库交互
	        service.lendDVD(id);
	        service.info(id);
	        //取得dvd細節更新頁面
	        DVD info = service.detail(id);
	        String msg = "DVD「" + name + "」の借出に成功しました！";
	        map.put("info", info);
	        	map.put("msg", msg);
	    return map;
	}

	@PostMapping("/return")
	@ResponseBody
	public Map<String, Object> returnDVD(String id, String name) {
	    Map<String, Object> map = new HashMap<>();
	    service.updateDVDInfo(id);
	    service.updateDVDLendInfo(id);
	    String cost = service.dvdCost(id);
	    String msg = "DVD「" + name + "」の归还に成功しました！ 花费 " + cost + "元";
	    map.put("msg", msg);
	    return map;
	}

	
	}
	

	
