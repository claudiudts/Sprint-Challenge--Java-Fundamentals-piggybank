package com.lambdaschool.piggybank.controllers;


import com.lambdaschool.piggybank.models.Coin;
import com.lambdaschool.piggybank.repositories.CoinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CoinController
{

    @Autowired
    CoinRepository coinrepos;

    //endpoint for all coins = localhost:2019//coins/all
    @GetMapping(value = "/coins/all", produces = {"application/json"})
    public ResponseEntity<?> listAllCoins()
    {
        List<Coin> myList = new ArrayList<>();
        coinrepos.findAll()
                .iterator()
                .forEachRemaining(myList::add);
        myList.sort((c1, c2) -> c1.getName().compareToIgnoreCase(c2.getName()));
        return new ResponseEntity<>(myList, HttpStatus.OK);
    }

    //endpoint for total coins = localhost:2019/total
    @GetMapping(value = "total", produces = {"application/json"})
    public ResponseEntity<?> listTotalCoins()
    {
        List<Coin> myList = new ArrayList<>();
        coinrepos.findAll()
                .iterator()
                .forEachRemaining(myList::add);
        myList.forEach(c -> {
            if(c.getQuantity() > 1){
                System.out.println(c.getQuantity() + " " + c.getNameplural());
            } else {
                System.out.println(c.getQuantity() + " " + c.getName());
            }
        });

        double total = 0;
        for(Coin c : myList)
        {
            total = total + c.getQuantity() * c.getValue();
        }
        System.out.println("The piggy bank holds " + total );
        return new ResponseEntity<>(myList, HttpStatus.OK);
    }
}
