package com.soa.order.repository;

import com.soa.order.model.Orders;
import org.springframework.data.repository.CrudRepository;

public interface OrdersRepository extends CrudRepository<Orders,String> {

}
