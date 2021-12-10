package com.soa.user.repository;

import com.soa.user.model.Card;
import com.soa.user.model.CardPrimary;
import org.springframework.data.repository.CrudRepository;

public interface CardRepository  extends CrudRepository<Card, CardPrimary> {

}
