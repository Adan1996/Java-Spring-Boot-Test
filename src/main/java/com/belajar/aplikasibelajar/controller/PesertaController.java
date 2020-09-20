package com.belajar.aplikasibelajar.controller;

import javax.validation.Valid;

import com.belajar.aplikasibelajar.dao.PesertaDao;
import com.belajar.aplikasibelajar.entity.Peserta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PesertaController {

    @Autowired
    private PesertaDao pd;

    // mengambil semua data peserta
    @RequestMapping(value = "/peserta", method = RequestMethod.GET)
    public Page<Peserta> cariPeserta(Pageable page) {
        // kembalian page yang berisi data peserta
        return pd.findAll(page);
    }

    // input data peserta
    @RequestMapping(value = "/peserta", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public void insertPesertabaru(@RequestBody @Valid Peserta p) {
        pd.save(p);
    }

    // update data peserta
    @RequestMapping(value = "/peserta/{id}", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    public void updatePeserta(@PathVariable("id") String id, @RequestBody @Valid Peserta p) {
        p.setId(id);
        pd.save(p);
    }

    // cari peserta by Id
    @RequestMapping(value = "/peserta/{id}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<Peserta> cariPesertaById(@PathVariable("id") String id) {
        Peserta hasil = pd.findById(id).orElse(null);
        if (hasil == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Peserta>(hasil, HttpStatus.OK);
    }

    // delete data peserta
    @RequestMapping(value = "/peserta/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    public void deletePeserta(@PathVariable("id") String id) {
        pd.deleteById(id);
    }
}
