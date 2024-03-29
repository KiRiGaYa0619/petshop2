package jp.co.brightstar.petshop.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.brightstar.petshop.mapper.orderMapper;
import jp.co.brightstar.petshop.model.Orderinfo;

@Service
public class orderService {
	@Autowired
	private orderMapper mapper;

	public int storeSpace(Orderinfo order) {

		return mapper.storeSpace(order.getStoreid(), order.getSpaceStu());

	}

	public int orderSpace(Orderinfo order) {

		return mapper.orderSpace(order.getStoreid(), order.getSpaceStu());

	}

	public Date maxStartDate(Orderinfo order) {

		return mapper.maxStartDate(order.getStoreid(), order.getSpaceStu());

	}

	public Date minEndDate(Orderinfo order) {

		return mapper.minEndDate(order.getStoreid(), order.getSpaceStu());

	}

	
	public void addNewOrder(Orderinfo order,String userId) {
		mapper.addNewOrder(order, userId);
	}
	
	public int countDayBetween(Orderinfo order) {
		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	        //String型轉Localdate型
	        LocalDate startDate = LocalDate.parse(order.getStartDate(), formatter);
	        LocalDate endDate = LocalDate.parse(order.getEndDate(), formatter);
	        int dayBetween  = (int) ChronoUnit.DAYS.between(startDate, endDate);
			return dayBetween;
	 }
}
