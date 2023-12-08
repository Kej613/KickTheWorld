package com.example.Pay.repository;

import com.example.Pay.domain.Pay;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PayRepository extends CrudRepository<Pay, String> {
    public Optional<Pay> findByOrdNo(String ordNo) throws Exception;

    public Optional<List<Pay>> findAllByPayStatusIn(List<String> statusList) throws Exception;
}
