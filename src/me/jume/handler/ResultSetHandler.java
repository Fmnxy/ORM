package me.jume.handler;

import java.sql.ResultSet;

public interface ResultSetHandler {

    Object handler(ResultSet rs);
}

