package com.belajar.aplikasibelajar.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "m_materi")
public class Materi {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(nullable = false, unique = true, length = 10)
    private String kode;

    @Column(nullable = false)
    private String nama;

    @Column(name = "tanggal_lahir", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date tanggalLahir;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "materi")
    private List<Sesi> daftarSesi = new ArrayList<>();
}
