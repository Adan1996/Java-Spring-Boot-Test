package com.belajar.aplikasibelajar.dao;

import com.belajar.aplikasibelajar.entity.Peserta;
import org.springframework.data.repository.PagingAndSortingRepository;

//                                                              class, tipe data PK dari class tersebut
public interface PesertaDao extends PagingAndSortingRepository<Peserta, String> {

}
