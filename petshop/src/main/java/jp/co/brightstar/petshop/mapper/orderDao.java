package jp.co.brightstar.petshop.mapper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jp.co.brightstar.petshop.model.DateRanger;
import jp.co.brightstar.petshop.model.Orderinfo;
public class orderDao {
    public static List<DateRanger> getDateFromDatabase(Orderinfo order) {
        String storeId = order.getStoreid();
        String spaceStu = order.getSpaceStu();
        List<DateRanger> dateRanges = new ArrayList<>();
        try (Connection conn = JdbcUtil.connect();) {
            String sql = "SELECT START_DATE, END_DATE FROM order_info WHERE store_id = ? AND space_stu = ? AND order_sta = 1 OR order_sta = 0;";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, storeId);
            stmt.setString(2, "1");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                LocalDate startDate = rs.getDate("START_DATE").toLocalDate();
                LocalDate endDate = rs.getDate("END_DATE").toLocalDate();
                DateRanger dateRanger = new DateRanger(startDate, endDate);
                dateRanges.add(dateRanger);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dateRanges;
    }
}
