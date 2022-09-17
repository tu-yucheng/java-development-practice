package cn.tuyucheng.taketoday.beanpostprocessor;

import java.util.Date;

public record StockTrade(String symbol, int quantity, double price, Date tradeDate) {

}