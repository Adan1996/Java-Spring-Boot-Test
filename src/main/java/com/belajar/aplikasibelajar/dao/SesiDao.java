package com.belajar.aplikasibelajar.dao;

import java.util.Date;

import com.belajar.aplikasibelajar.entity.Materi;
import com.belajar.aplikasibelajar.entity.Sesi;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface SesiDao extends PagingAndSortingRepository<Sesi, String> {
    // mencari materi tertentu
    public Page<Sesi> findByMateri(Materi m, Pageable page);

    // how to create your own query
    @Query("SELECT x FROM Sesi x WHERE x.mulai >= :m AND x.mulai < :s AND x.materi.kode = :k ORDER BY x.mulai DESC")
    public Page<Sesi> cariBerdasarkanTanggalMulaiDanKodeMateri(@Param("m") Date mulai, @Param("s") Date sampai,
            @Param("k") String kode, Pageable page);
}
