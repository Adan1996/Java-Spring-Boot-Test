package com.belajar.aplikasibelajar.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Sesi {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Temporal(TemporalType.DATE)
    private Date mulai;

    @Temporal(TemporalType.DATE)
    private Date sampai;

    @ManyToOne
    @JoinColumn(name = "id_materi", nullable = false)
    private Materi materi;

    @ManyToMany
    @JoinTable(name = "peserta_pelatihan", joinColumns = @JoinColumn(name = "id_sesi"), inverseJoinColumns = @JoinColumn(name = "id_peserta"))
    private List<Peserta> daftarPeserta = new ArrayList<>();

    /**
     * @return String return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return Date return the mulai
     */
    public Date getMulai() {
        return mulai;
    }

    /**
     * @param mulai the mulai to set
     */
    public void setMulai(Date mulai) {
        this.mulai = mulai;
    }

    /**
     * @return Date return the sampai
     */
    public Date getSampai() {
        return sampai;
    }

    /**
     * @param sampai the sampai to set
     */
    public void setSampai(Date sampai) {
        this.sampai = sampai;
    }

    /**
     * @return Materi return the materi
     */
    public Materi getMateri() {
        return materi;
    }

    /**
     * @param materi the materi to set
     */
    public void setMateri(Materi materi) {
        this.materi = materi;
    }

    /**
     * @return List<Peserta> return the daftarPeserta
     */
    public List<Peserta> getDaftarPeserta() {
        return daftarPeserta;
    }

    /**
     * @param daftarPeserta the daftarPeserta to set
     */
    public void setDaftarPeserta(List<Peserta> daftarPeserta) {
        this.daftarPeserta = daftarPeserta;
    }

}
