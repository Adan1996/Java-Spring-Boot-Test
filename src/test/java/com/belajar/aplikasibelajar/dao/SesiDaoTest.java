package com.belajar.aplikasibelajar.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.sql.DataSource;

import com.belajar.aplikasibelajar.AplikasiBelajarApplication;
import com.belajar.aplikasibelajar.entity.Materi;
import com.belajar.aplikasibelajar.entity.Peserta;
import com.belajar.aplikasibelajar.entity.Sesi;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AplikasiBelajarApplication.class)
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = { "/data/peserta.sql", "/data/materi.sql",
        "/data/sesi.sql" })
public class SesiDaoTest {

    @Autowired
    private SesiDao sd;

    @Autowired
    private DataSource ds;

    @Test
    public void testCariByMateri() {
        Materi m = new Materi();
        m.setId("aa6");

        PageRequest page = PageRequest.of(0, 5);

        Page<Sesi> hasilQuery = sd.findByMateri(m, page);
        Assert.assertEquals(2L, hasilQuery.getTotalElements());

        Sesi s = hasilQuery.getContent().get(0);
        Assert.assertNotNull(s);
        Assert.assertEquals("Java Fundamental", s.getMateri().getNama());
    }

    @Test
    public void cariBerdasarkanTanggalMulaiDanKodeMateri() throws Exception {
        PageRequest page = PageRequest.of(0, 5);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date sejak = formatter.parse("2020-01-01");
        Date sampai = formatter.parse("2020-01-03");

        Page<Sesi> hasil = sd.cariBerdasarkanTanggalMulaiDanKodeMateri(sejak, sampai, "JF-002", page);
        Assert.assertEquals(1L, hasil.getTotalElements());
        Assert.assertFalse(hasil.getContent().isEmpty());

        Sesi s = hasil.getContent().get(0);
        Assert.assertEquals("Java Web", s.getMateri().getNama());
    }

    @Test
    public void testSaveSesi() throws SQLException, Exception {
        Peserta p1 = new Peserta();
        p1.setId("aa1");

        Peserta p2 = new Peserta();
        p2.setId("aa3");

        Materi m = new Materi();
        m.setId("aa8");

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date sejak = formatter.parse("2020-02-01");
        Date sampai = formatter.parse("2020-02-03");

        Sesi s = new Sesi();
        s.setMateri(m);
        s.setMulai(sejak);
        s.setSampai(sampai);
        s.getDaftarPeserta().add(p1);
        s.getDaftarPeserta().add(p2);

        sd.save(s);

        String idSesiBaru = s.getId();
        Assert.assertNotNull(idSesiBaru);
        System.out.println("ID Baru " + s.getId());

        String sql = "SELECT COUNT(*) FROM sesi WHERE id_materi='aa8'";
        String sqlManyToMany = "SELECT COUNT(*) FROM peserta_pelatihan WHERE id_sesi=?";

        try (Connection con = ds.getConnection()) {
            // cek tabel sesi
            ResultSet rs = con.createStatement().executeQuery(sql);

            Assert.assertTrue(rs.next());
            Assert.assertEquals(1L, rs.getLong(1));

            // cek tabel relasi Many To Many dengan peserta
            PreparedStatement ps = con.prepareStatement(sqlManyToMany);
            ps.setString(1, idSesiBaru);
            ResultSet rs2 = ps.executeQuery();

            Assert.assertTrue(rs2.next());
            Assert.assertEquals(2L, rs2.getLong(1));
        }

    }
}
