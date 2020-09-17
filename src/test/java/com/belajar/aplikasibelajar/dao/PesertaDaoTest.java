package com.belajar.aplikasibelajar.dao;

import com.belajar.aplikasibelajar.AplikasiBelajarApplication;
// unit testing
import com.belajar.aplikasibelajar.entity.Peserta;

import org.junit.runner.RunWith;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AplikasiBelajarApplication.class)
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/data/peserta.sql")
public class PesertaDaoTest {

    @Autowired
    private PesertaDao pd;

    // deklarasi koneksi ke database (javax.sql)
    @Autowired
    private DataSource ds;

    // delete otomatis
    @After
    public void hapusData() throws Exception {
        String sql = "DELETE FROM peserta WHERE email = 'peserta001@gmail.com'";

        try (Connection con = ds.getConnection()) {
            con.createStatement().executeUpdate(sql);
        }
    }

    // insert otomatis
    @Test
    public void testInsert() throws SQLException {
        Peserta p = new Peserta();
        // menginputkan data
        p.setNama("Peserta 001");
        p.setEmail("peserta001@gmail.com");
        p.setTanggalLahir(new Date());

        // menyimpan data
        pd.save(p);

        // query pengecekan jumlah data yang masuk
        String sql = "SELECT COUNT(*) as jumlah FROM peserta WHERE email = 'peserta001@gmail.com'";

        try (Connection con = ds.getConnection()) {
            ResultSet rs = con.createStatement().executeQuery(sql); // eksekusi query
            Assert.assertTrue(rs.next()); // test hasil jika benar (true)

            Long jumlahRow = rs.getLong("jumlah"); // mengambil jumlah row
            Assert.assertEquals(1L, jumlahRow.longValue()); // pengecekan antara perkiraan dan data yang ada
            // jika perkiraan dan data yang ter-record sama, maka eksekusi berhasil
        }
    }

    // hitung otomatis
    @Test
    public void testHitung() {
        Long jumlah = pd.count();
        Assert.assertEquals(3L, jumlah.longValue());
    }

    // cari otomatis
    @Test
    public void testCariById() {
        // cari data yang ada
        Peserta p = pd.findById("aa1").orElse(null);
        Assert.assertNotNull(p);
        Assert.assertEquals("Peserta Test 001", p.getNama());
        Assert.assertEquals("peserta.test.001@gmail.com", p.getEmail());

        // cari data yang tidak ada
        Peserta px = pd.findById("xx").orElse(null);
        Assert.assertNull(px);
    }

}
