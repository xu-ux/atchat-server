package com.xu.atchat.config;

import com.xu.atchat.constant.FriendsRequestStatus;
import org.apache.ibatis.type.*;

import java.sql.*;
import java.util.Arrays;

/**
 * @author xucl
 * @version 1.0
 * @date 2020/5/4 21:04
 * @description
 */
/* 数据库中的数据类型 */
@MappedJdbcTypes(JdbcType.INTEGER)
/* 转化后的数据类型 */
@MappedTypes(value = FriendsRequestStatus.class)
public class EnumIntegerTypeHandler extends BaseTypeHandler<FriendsRequestStatus> {

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, FriendsRequestStatus friendsRequestStatus, JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(i,friendsRequestStatus.getId());
    }

    @Override
    public FriendsRequestStatus getNullableResult(ResultSet resultSet, String s) throws SQLException {
        int i = resultSet.getInt(s);
        FriendsRequestStatus friendsRequestStatusById = FriendsRequestStatus.getFriendsRequestStatusById(i);
        return friendsRequestStatusById;
    }

    @Override
    public FriendsRequestStatus getNullableResult(ResultSet resultSet, int i) throws SQLException {
        int anInt = resultSet.getInt(i);
        FriendsRequestStatus friendsRequestStatusById = FriendsRequestStatus.getFriendsRequestStatusById(anInt);
        return friendsRequestStatusById;
    }

    @Override
    public FriendsRequestStatus getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        int anInt = callableStatement.getInt(i);
        FriendsRequestStatus friendsRequestStatusById = FriendsRequestStatus.getFriendsRequestStatusById(anInt);
        return friendsRequestStatusById;
    }
}
